import akka.actor.*;
import scala.Int;
import scala.concurrent.Await;
import scala.concurrent.duration.Duration;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.TimeUnit;

/**
 * Main class for your estimation actor system.
 *
 * @author akashnagesh
 *
 */
public class User extends UntypedAbstractActor {

	static volatile ActorRef userActor;
	static volatile ActorRef Estimator1;
	static volatile ActorRef Estimator2;
	static volatile ActorRef Counter;
	public static int FileAmount;

	public static void main(String[] args) throws Exception {
		ActorSystem system = ActorSystem.create("EstimationSystem");
		//create props
		Props userProp = Props.create(	User.class);
		Props estimatorProp = Props.create(Estimator.class);
		Props estimator2Prop = Props.create(Estimator2.class);
		Props firstCounterProp = Props.create(FirstCounter.class);
		//create actor refs
		 userActor = system.actorOf(userProp);
		 Estimator1 = system.actorOf(estimatorProp);
		 Estimator2 = system.actorOf(estimator2Prop);
		 Counter = system.actorOf(firstCounterProp);

		 userActor.tell("Start Process", null);
		/*
		 * Create the Estimator Actor and send it the StartProcessingFolder
		 * message. Once you get back the response, use it to print the result.
		 * Remember, there is only one actor directly under the ActorSystem.
		 * Also, do not forget to shutdown the actorsystem
		 */

		System.out.println("create successfully");
	}

	@Override
	public void onReceive(Object message) throws Throwable {

		if (message instanceof String){
			File folder = new File("C:\\Users\\xu han\\7215\\7215Prallel\\hw9\\akkaHW2017F\\data");
			File[] allFiles = folder.listFiles();
			FileAmount = allFiles.length;
			int textId = 0;
			for (File f : allFiles){
				StringBuffer sb = readFiles(f);
				Messages m = new Messages(sb, allFiles.length, f.getName(), textId++);
				Estimator1.tell(m, getSelf());
				Estimator2.tell(m, getSender());
				Counter.tell(m, getSelf());

//				context().system().eventStream().publish(m);

			}
		}else if (message instanceof Messages.EstimatorRes){
			System.out.println("Estimator: "+ getSender().path().name() +" With C("+((Messages.EstimatorRes) message).id+"): "
					+((Messages.EstimatorRes) message).getCt() + "Average" + ((Messages.EstimatorRes) message).getAvg());
		}else if (message instanceof Messages.CounterRes){
			System.out.println("Counter" + getSender().path().name() + "With U(t): " + ((Messages.CounterRes) message).getUt());
		}

	}

	private static StringBuffer readFiles(File file){
		StringBuffer text = new StringBuffer();
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String currentLine = br.readLine();
			while (currentLine != null){
				currentLine = currentLine.replaceAll("[\\W+_]","");
				text.append(currentLine);
				currentLine = br.readLine();
			}

			br.close();
		}catch (Exception e){
			System.out.println("Exception");
		}
		return text;
	}
}
