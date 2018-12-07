import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.UntypedAbstractActor;
import akka.pattern.Patterns;
import akka.util.Timeout;
import scala.concurrent.Await;
import scala.concurrent.Future;

/**
 * Main class for your estimation actor system.
 *
 * @author xu_han
 */
public class User extends UntypedAbstractActor {
	private static ActorRef Estimator1;
	private static ActorRef Estimator2;
	private static ActorRef Counter;
	public static void main(String[] args) throws Exception {
		ActorSystem system = ActorSystem.create("EstimatonSystem");
		Props estimatorProps = Props.create(Estimator1.class);
		Props counterProps = Props.create(FirstCounter.class);
		Props estimator2 = Props.create(Estimator2.class);
		Props user = Props.create(User.class);
		Estimator1 = system.actorOf(estimatorProps, "Estimator_Node");
		Counter = system.actorOf(counterProps, "First_Counter_Node");
		Estimator2 = system.actorOf(estimator2, "Second_Node");
		ActorRef userNode = system.actorOf(user, "User_Node");
		userNode.tell("Start", null);
		Thread.sleep(2000);
        Future<Object> future = Patterns.ask(Estimator1, "Messages", 2000);
        Future<Object> future2 = Patterns.ask(Estimator2, "Messages", 2000);
		try{
			Timeout timeout = new Timeout(2, TimeUnit.SECONDS);
	        Messages[] res = (Messages[])Await.result(future, timeout.duration());
	        Messages[] res2 = (Messages[])Await.result(future2, timeout.duration());
			System.out.println("Estimator1 2 predictions: ");
			for (Messages out : res2){
				if (out == null){
					throw new Exception();
				}
				System.out.println(out.toString());
			}
			System.out.println("Estimator1 1 predictions: ");
 	        for(Messages output : res){
	        	if(output == null){
		        	throw new Exception();
	        	}
        		System.out.println(output);
	        }
		} catch(Exception e){
			e.printStackTrace();
		} finally{
			system.terminate();
		}
 	}

	@Override
	public void onReceive(Object message){
		if (message instanceof String && message.equals("Start")){
			Estimator1.tell(Counter, getSelf());
			Estimator2.tell(Counter,getSelf());
		}
	}

	public static class Helper {

		public static int getCount(String str){
			int count = 0;
			for(char ch : str.toCharArray()){
				ch = Character.toLowerCase(ch);
				if(ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'y'){
					count++;
				}
			}
			return count;
		}

		public static String[] getFiles() throws FileNotFoundException {
			File folder = new File("./AkkaText");
			String[] files = new String[10];
			int index = 0;
			for(File file : folder.listFiles()){
				if(file.getName().equalsIgnoreCase(".DS_Store")){
					continue;
				}
				files[index++] = scanFile(file);
			}
			return files;
		}

		public static String scanFile(File file) throws FileNotFoundException {
			StringBuilder sb = new StringBuilder();
			Scanner scan = new Scanner(file);
			while(scan.hasNext()){
				sb.append(scan.nextLine());
			}
			scan.close();
			return sb.toString();
		}

	}
}