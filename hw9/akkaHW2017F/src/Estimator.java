import java.io.File;
import java.util.ArrayList;
import java.util.List;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;
import org.omg.CORBA.PRIVATE_MEMBER;

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
	protected final double initialC = 0.1;
	private double C;
	private double CNext;
	private final double g = 0.5;
	private List<Double> UtList = new ArrayList<>();
	private double Ut;

	@SuppressWarnings("Duplicates")
	@Override
	public void onReceive(Object message) throws Throwable {
		ActorRef userAct = User.userActor, counter = User.Counter;
		Messages.EstimatorRes response;
        if (message instanceof Messages){
			double avg = calculateS( ((Messages) message).textId);

			//when receive text from user
            if(((Messages) message).textId == 0){
				response = new Messages.EstimatorRes(initialC,avg);
				userAct.tell(response, getSelf());
				counter.tell(C, getSelf());
			}else{
                response = new Messages.EstimatorRes(C, avg);
                userAct.tell(response, getSelf());
				counter.tell(C, getSelf());
			}
		}else if (message instanceof Messages.CounterRes){
			//when receive U(t) from Counter
            //refresh C
			Ut = ((Messages.CounterRes) message).getUt();
			C = getActualC(((Messages.CounterRes) message).id, Ut);
			CNext = getNextC((Messages.CounterRes) message, g);
			UtList.add(Ut);
			double avg = calculateS(((Messages.CounterRes) message).id);
            response = new Messages.EstimatorRes(C, avg);
            userAct.tell(response, getSelf());
			counter.tell(response, getSelf());

		}
	}

	public Estimator() {}

	protected double getActualC(int id, double Ut){
		if (id == 0) return Ut;
		C =	C + Ut;
		return C;
	}

	protected double getNextC(Messages.CounterRes msg, double g){
		return C * g + (1-g)*getActualC(msg.id, msg.getUt());
	}

	private double calculateS(int textId){
		if (textId == 0) return UtList.isEmpty() ? 0 : UtList.get(0);
		int sum = 0;
		for (int i = 0 ; i <= textId && i < UtList.size(); i++){
			sum += UtList.get(i);
		}
		return sum/textId;
	}


}
