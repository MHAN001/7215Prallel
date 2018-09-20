//import java.util.LinkedList;
import java.math.BigDecimal;
import java.util.*;

/**
 * This class runs <code>numThreads</code> instances of
 * <code>ParallelMaximizerWorker</code> in parallel to find the maximum
 * <code>Integer</code> in a <code>LinkedList</code>.
 */
public class ParallelMaximizer {
	
	int numThreads;
	ArrayList<ParallelMaximizerWorker> workers = new ArrayList<>(); // = new ArrayList<ParallelMaximizerWorker>(numThreads);

	public ParallelMaximizer(int numThreads) {
		this.numThreads = numThreads;
	}


	
	public static void main(String[] args) {
		int numThreads = 4; // number of threads for the maximizer
		int numElements = 10; // number of integers in the list
		
		ParallelMaximizer maximizer = new ParallelMaximizer(numThreads);
		LinkedList<Integer> list = new LinkedList();
		
		// populate the list
		// TODO: change this implementation to test accordingly
		Random r = new Random();
		for (int i=0; i<numElements; i++) 
			list.add(r.nextInt());

		for (int i : list) {
			System.out.println(i);
		}
		// run the maximizer
		try {
			System.out.println(maximizer.max(list));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Finds the maximum by using <code>numThreads</code> instances of
	 * <code>ParallelMaximizerWorker</code> to find partial maximums and then
	 * combining the results.
	 * @param list <code>LinkedList</code> containing <code>Integers</code>
	 * @return Maximum element in the <code>LinkedList</code>
	 * @throws InterruptedException
	 */
	public int max(LinkedList<Integer> list) throws InterruptedException {
		int max = Integer.MIN_VALUE; // initialize max as lowest value
		
		System.out.println(numThreads);
		// run numThreads instances of ParallelMaximizerWorker
		for (int i=0; i < numThreads; i++) {
			workers.add(i, new ParallelMaximizerWorker(list, list.size()));
			workers.get(i).start();
		}
		// wait for threads to finish
		for (int i=0; i<workers.size(); i++)
			workers.get(i).join();
		
		// take the highest of the partial maximums
		// TODO: IMPLEMENT CODE HERE
		for (int i = 0; i < numThreads; i++) {
			max = Math.max(max, workers.get(i).getPartialMax());
		}
		return max;
	}

    public BigDecimal average(LinkedList<Integer> list) throws InterruptedException {
        BigDecimal sum = new BigDecimal(0);
        int size = list.size();
        // run numThreads instances of ParallelMaximizerWorker
        for (int i=0; i < numThreads; i++) {
            workers.add(i, new ParallelMaximizerWorker(list, size));
            workers.get(i).start();
        }
        // wait for threads to finish
        for (int i=0; i<workers.size(); i++)
            workers.get(i).join();

        // take the highest of the partial maximums
        // TODO: IMPLEMENT CODE HERE
        for (int i = 0; i < numThreads; i++) {
            sum = sum.add(workers.get(i).partialAverage); //get sum
        }
        return sum;  // get the average of all sum from all threads of all sums
    }
	
}
