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
public class Estimator2 extends UntypedAbstractActor {
    private final double initialC = 0.1;
    private final double g = 0.4;
    private int txtId = 0;

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof StringBuffer){
            //when receive text from user

        }else if (message instanceof Double){
            //when receive U(t) from Counter

        }
    }

    public Estimator2() {}

    private double getEstimatorPercent(){

    }

    private double getActualPercent(){

    }

    private double getAverage(){

    }

}
