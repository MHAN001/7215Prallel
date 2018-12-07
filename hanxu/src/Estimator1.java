import akka.actor.ActorRef;
import akka.actor.UntypedAbstractActor;

/**
 * This is the main actor and the only actor that is created directly under the
 * {@code ActorSystem} This actor creates more child actors
 * {@code WordCountInAFileActor} depending upon the number of files in the given
 * directory structure
 * 
 * @author xu_han
 */
public class Estimator1 extends UntypedAbstractActor {
	
	private String[] files = null;
	private ActorRef counter;
	private double g1 = 1.0;//actual
	private double g2 = 0.99, g3 = 1.01;//adjust
	private int vNum, index = 0;
	private Messages[] messages = new Messages[10];

	@SuppressWarnings("Duplicates")
	@Override
	public void onReceive(Object msg) throws Throwable {
		if(msg instanceof ActorRef){
			counter = (ActorRef) msg;
			files = User.Helper.getFiles();
			for(String file : files){
				vNum = 0;
				vNum = User.Helper.getCount(file);
				counter.tell(file, getSelf());
			}			
		} else if(msg instanceof Integer){
			int fileCount = ((Integer) msg).intValue();
			int estimatedCount = (int)(vNum * g1 * 2);
			int actualCount = vNum + fileCount;
			if(estimatedCount < actualCount){
				g1 *= g3;
			} else{
				g1 *= g2;
			}
			messages[index++] = new Messages(actualCount, estimatedCount, g1, index);
		} else{
			getContext().sender().tell(messages, getContext().sender());
		}
	}
}