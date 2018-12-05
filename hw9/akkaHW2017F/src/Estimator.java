import java.io.File;

import akka.actor.AbstractActor;
import akka.actor.UntypedAbstractActor;

/**
 * This is the main actor and the only actor that is created directly under the
 * {@code ActorSystem} This actor creates more child actors
 * {@code WordCountInAFileActor} depending upon the number of files in the given
 * directory structure
 * 
 * @author akashnagesh
 *
 */
// word counter class
public class Estimator extends UntypedAbstractActor {
	private final double initialC = 0.1;

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof Integer){
			Messages.getInstance().getCount();//Useless

		}
	}

	public Estimator() {}

	private int estimateC(char[] text){
		int count = 0;

		for (char c : ){

		}
	}
}
