import java.math.BigDecimal;
import java.util.LinkedList;

/**
 * Given a <code>LinkedList</code>, this class will find the maximum over a
 * subset of its <code>Integers</code>.
 */
public class ParallelMaximizerWorker extends Thread {

	protected LinkedList<Integer> list;
	protected volatile int listSize;
	protected BigDecimal sum;
	protected BigDecimal partialAverage;
	protected int partialMax = Integer.MIN_VALUE; // initialize to lowest value
	public ParallelMaximizerWorker(LinkedList<Integer> list, int listSize){
		this.list = list;
		this.listSize = listSize;
		this.partialAverage = new BigDecimal(0);
	}
//	public ParallelMaximizerWorker(LinkedList<Double> list, int listSize) {
//		this.doubleList = list;
//		this.listSize = listSize;
//	}
	
	/**
	 * Update <code>partialMax</code> until the list is exhausted.
	 */
	@Override
	public void run() {
		while (true) {
			int number;
			// check if list is not empty and removes the head
			if (list == null || list.size() == 0){
				return;
			}
			// synchronization needed to avoid atomicity violation
			synchronized(list) {
				if (list.isEmpty())
					return; // list is empty
				number = list.remove();
//				sum += number;
			}
//			double tmp;
//			synchronized(doubleList) {
//				if (doubleList.isEmpty())
//					return; // list is empty
//				 tmp = doubleList.remove();
//				sum += number;
//			}
			BigDecimal numTmp = new BigDecimal(number);
			BigDecimal numAve = new BigDecimal(this.listSize);
			this.partialAverage = partialAverage.add(numTmp.divide(numAve));
			partialMax = Math.max(number, partialMax);
//			System.out.println(this.getName());
//			try {
//				ParallelMaximizerWorker.sleep(10);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
		}
	}
	
	public int getPartialMax() {
		return partialMax;
	}

	public BigDecimal getPartialAverage(){return this.partialAverage;}

}
