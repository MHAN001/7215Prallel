import java.util.HashMap;
import java.util.List;

/**
 * Cooks are simulation actors that have at least one field, a name.
 * When running, a cook attempts to retrieve outstanding orders placed
 * by Eaters and process them.
 */
public class Cook implements Runnable {
	private final String name;
//HashMap<int, List<Food>>  //priority
	private static HashMap<Integer, List<Food>> orders;
	private final int capacity;
	/**
	 * You can feel free modify this constructor.  It must
	 * take at least the name, but may take other parameters
	 * if you would find adding them useful. 
	 *
	 * @param: the name of the cook
	 */
	public Cook(String name, int capacity) {
		this.name = name;
		this.capacity = capacity;
	}

	public String toString() {
		return name;
	}

	/**
	 * This method executes as follows.  The cook tries to retrieve
	 * orders placed by Customers.  For each order, a List<Food>, the
	 * cook submits each Food item in the List to an appropriate
	 * Machine, by calling makeFood().  Once all machines have
	 * produced the desired Food, the order is complete, and the Customer
	 * is notified.  The cook can then go to process the next order.
	 * If during its execution the cook is interrupted (i.e., some
	 * other thread calls the interrupt() method on it, which could
	 * raise InterruptedException if the cook is blocking), then it
	 * terminates.
	 */
	public void run() {
		//TODO : receive from customer(where customer carry the order)
		Simulation.logEvent(SimulationEvent.cookStarting(this));
		try {
			while(true) {
				wait();
				//YOUR CODE GOES HERE...
				/**
				 * pre-condition: customers come with order. logEvent run successfully
				 * post-condition: submit orders to machines, notify customers once order done
				 * invariants: size of orders cannot more than capacity
				 * exceptions: InterruptedException
				 */
			}
		}
		catch(InterruptedException e) {
			// This code assumes the provided code in the Simulation class
			// that interrupts each cook thread when all customers are done.
			// You might need to change this if you change how things are
			// done in the Simulation class.
			Simulation.logEvent(SimulationEvent.cookEnding(this));
		}
	}
}