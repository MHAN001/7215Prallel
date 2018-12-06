import java.io.File;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
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
	private double C;
	private final double g = 0.5;
	private List<Double> UtList;
	private double S;

	@Override
	public void onReceive(Object message) throws Throwable {
		ActorRef userAct = User.userActor;
		Messages.EstimatorRes response;
		if (message instanceof Messages){
			//when receive text from user
			if(((Messages) message).textId == 0){
				C = initialC;
				double avg = calculateS((Messages) message);
				response = new Messages.EstimatorRes(C,avg);
				userAct.tell(response, getSelf());
			}
		}else if (message instanceof Messages.CounterRes){
			//when receive U(t) from Counter
			double C1 = getActualC(((Messages.CounterRes) message));

			C = getNextC((Messages.CounterRes) message);


		}
	}

	public Estimator() {}

	private double getActualCint id, double Ut){
		if (id == 0) return Ut;
		C =	C + Ut;
		return C;
	}

	private double getNextC(Messages.CounterRes msg){
		return C * g + (1-g)*getActualC(msg);
	}

	private double calculateS(Messages msg){
		if (msg.textId == 0) return UtList.get(0);
		int sum = 0;
		for (int i = 0 ; i <= msg.textId && i < UtList.size(); i++){
			sum += UtList.get(i);
		}
		return sum/msg.textId;
	}


}
