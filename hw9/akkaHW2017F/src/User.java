import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

/**
 * Main class for your estimation actor system.
 *
 * @author akashnagesh
 *
 */
public class User extends AbstractActor {

	private static volatile ActorRef userActor;
	private static volatile ActorRef Estimator1;
	private static volatile ActorRef Estimator2;
	private static volatile ActorRef Counter;


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

		 userActor.tell("start", null);
		 system.terminate();
		/*
		 * Create the Estimator Actor and send it the StartProcessingFolder
		 * message. Once you get back the response, use it to print the result.
		 * Remember, there is only one actor directly under the ActorSystem.
		 * Also, do not forget to shutdown the actorsystem
		 */

		System.out.println("create successfully");
	}

	@Override
	public Receive createReceive() {
		// read file content
		File folder = new File("../data");
		for (File f : folder.listFiles()){
		 	StringBuffer sb = readFiles(f);
		 	//TODO: Send sb to estimators and counter.
		}

		return null;
	}

	private static StringBuffer readFiles(File file){
		StringBuffer text = new StringBuffer();
		try{
			BufferedReader br = new BufferedReader(new FileReader(file));
			String currentLine = br.readLine();
			while (currentLine != null){
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
