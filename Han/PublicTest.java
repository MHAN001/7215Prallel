import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.*;


import org.junit.Test;

public class PublicTest {

	private int	threadCount = 10; // number of threads to run
	private ParallelMaximizer maximizer = new ParallelMaximizer(threadCount);
	
	@Test
	public void compareMax() {
		int size = 100000; // size of list
		LinkedList<Integer> list = new LinkedList<Integer>();
		Random rand = new Random();
		int serialMax = Integer.MIN_VALUE;
		int parallelMax = 0;
		// populate list with random elements
		for (int i=0; i<size; i++) {
			int next = rand.nextInt();
			list.add(next);
			serialMax = Math.max(serialMax, next); // compute serialMax
		}
		// try to find parallelMax
		try {
			parallelMax = maximizer.max(list);
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("The test failed because the max procedure was interrupted unexpectedly.");
		} catch (Exception e) {
			e.printStackTrace();
			fail("The test failed because the max procedure encountered a runtime error: " + e.getMessage());
		}
		
		assertEquals("The serial max doesn't match the parallel max", serialMax, parallelMax);
	}

	@Test
	public void computeAverage(){
		int size = 10000; // size of list
		LinkedList<Integer> list = new LinkedList();
		BigDecimal parallelAverage = new BigDecimal(0);
		Random rand = new Random();
		int sum = 0;
		int serialAverage = 0;
		for (int i = 0; i < size; i++) {
//			int next = rand.nextInt();
			list.add(i);
			sum += i;		//compute serialAverage
		}
		serialAverage = sum/list.size();
		System.out.println("serial average:"+ serialAverage);
		try {
			parallelAverage = maximizer.average(list);
			System.out.println("parallel average:"+parallelAverage);
		}  catch (Exception e) {
			e.printStackTrace();
			fail("The test failed because the average procedure encountered a runtime error: " + e.getMessage());
		}
		// populate list with random elements
		assertEquals("The serial average doesn't match the parallel max", serialAverage, parallelAverage.intValue());

	}
}
