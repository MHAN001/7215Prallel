import java.io.File;
import java.util.ArrayList;
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
public class Estimator2 extends Estimator {
    private double g = 0.4;
    private List<Double> Utlist = new ArrayList<>();
    private double Ut;
    private double Ct;
    private double CNext;

    @SuppressWarnings("Duplicates")
    @Override
    public void onReceive(Object message) throws Throwable {
        ActorRef user = User.userActor, counter = User.Counter;
        Messages.EstimatorRes response;
        if (message instanceof Messages){
            double avg = calculateS(((Messages) message).textId);
            //when receive text from user
            if (((Messages) message).textId == 0){
                response = new Messages.EstimatorRes(initialC,avg);
                user.tell(response, getSelf());
                counter.tell(Ct, getSelf());
            }else {
                response = new Messages.EstimatorRes(Ct, avg);
                counter.tell(Ct, getSelf());
                user.tell(response, getSelf());
            }
        }else if (message instanceof Messages.CounterRes){
            //when receive U(t) from Counter
            Messages.CounterRes msg = (Messages.CounterRes) message;
            Ut = (msg.getUt());
            Ct = getActualC(((Messages.CounterRes)message).id, Ut);
            CNext = getNextC(msg, g);
            Utlist.add(Ut);
            double avg = calculateS(((Messages.CounterRes) message).id);
            response = new Messages.EstimatorRes(Ct, avg);
            user.tell(response, getSelf());
            counter.tell(Ct, getSelf());
        }
    }

    private double calculateS(int id){
        if (id == 0){
            return Utlist.isEmpty()? 0 : Utlist.get(0);
        }
        int sum = 0;
        for (int i = 0 ; i <= id && i < Utlist.size(); i++){
            sum += Utlist.get(i);
        }
        return sum/id;
    }
}
