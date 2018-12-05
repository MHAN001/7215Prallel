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
	private int C;
	private final double g = 0.5;
	private int txtId = 0;

	@Override
	public void onReceive(Object message) throws Throwable {
		if (message instanceof Messages){
			//when receive text from user

		}else if (message instanceof Messages.CounterRes){
			//when receive U(t) from Counter
			double C1 = getEstimateC()
		}
	}

	public Estimator() {}

	private double getEstimateC(char[] text){
		if (txtId == 0){
			return initialC;
		}
		int count = 0;
		for (char c : text){
			if (isVowel(c)){
				count++;
			}
		}
	}

	\

	private boolean isVowel(char c){
		char[] vowels = {'A', 'E', 'I', 'O', 'U', 'Y'};
		for (char vowel : vowels) {
			if (c == vowel) return true;
		}
		return false;
	}
}
