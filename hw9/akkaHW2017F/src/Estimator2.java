import java.io.File;

import akka.actor.AbstractActor;

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
public class Estimator2 extends AbstractActor {

    @Override
    public Receive createReceive() {
        return null;
    }

    public Estimator2() {

    }
}
