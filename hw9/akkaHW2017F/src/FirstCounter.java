import akka.actor.AbstractActor;
import akka.actor.UntypedAbstractActor;

/**
 * this actor reads the file, counts the vowels and sends the result to
 * Estimator. 
 *
 * @author akashnagesh
 *
 */
public class FirstCounter extends UntypedAbstractActor {
	private double Ut;
	int vNum = 0, lt = 0;
	double pt;

	@Override
	public void onReceive(Object message) throws Throwable {
		Messages.CounterRes res;
		if (message instanceof Messages){
			vNum = countVowels(((Messages) message).getFileContent());
			lt = ((Messages) message).getFileContent().length();
			res = new Messages.CounterRes(vNum/lt);
			User.Estimator1.tell(res, getSelf());
		}else if (message instanceof Messages.EstimatorRes){
			if (lt == 0){
				Ut = 0;
				res = new Messages.CounterRes(Ut);
				getSender().tell(res, getSelf());
			}
			else {
				pt = vNum/lt;
				Ut = pt - ((Messages.EstimatorRes) message).getCt();
				res = new Messages.CounterRes(Ut);
				getSender().tell(res, getSelf());
			}
		}
	}

	public FirstCounter() {
		// TODO Auto-generated constructor stub
	}

	private int countVowels(StringBuffer txt){
		String s = String.valueOf(txt);
		int count = 0;
		for (char a : s.toCharArray()){
			if (isVowel(a)){
				count++;
			}
		}
		return count;
	}

	private boolean isVowel(char c){
		char[] vowels = {'A', 'E', 'I', 'O', 'U', 'Y'};
		for (char vowel : vowels) {
			if (c == vowel) return true;
		}
		return false;
	}
}
