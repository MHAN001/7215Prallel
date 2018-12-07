import akka.actor.UntypedAbstractActor;

/**
 * This actor reads the file, counts the vowels and sends the messages to
 * Estimator1.
 *
 * @author xu_han
 */
public class FirstCounter extends UntypedAbstractActor {
	
	@Override
	public void onReceive(Object msg){
		if(msg instanceof String){
			int count = User.Helper.getCount((String)msg);
			getSender().tell(count, null);
		}
	}
	
}