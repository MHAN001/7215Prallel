import com.sun.corba.se.impl.corba.CORBAObjectImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * A Machine is used to make a particular Food.  Each Machine makes
 * just one kind of Food.  Each machine has a capacity: it can make
 * that many food items in parallel; if the machine is asked to
 * produce a food item beyond its capacity, the requester blocks.
 * Each food item takes at least item.cookTimeMS milliseconds to
 * produce.
 */
public class Machine{
	public final String machineName;
	public final Food machineFoodType;
	public List<Food> foods;
	private final int capacity;


	/**
	 * The constructor takes at least the name of the machine,
	 * the Food item it makes, and its capacity.  You may extend
	 * it with other arguments, if you wish.  Notice that the
	 * constructor currently does nothing with the capacity; you
	 * must add code to make use of this field (and do whatever
	 * initialization etc. you need).
	 */
	public Machine(String nameIn, Food foodTypeIn, int capacityIn) {
		//limit input number
		this.machineName = nameIn;
		this.machineFoodType = foodTypeIn;
		this.capacity = capacityIn;
		this.foods = new ArrayList<>(capacityIn);
		Simulation.logEvent(SimulationEvent.machineStarting(this, foodTypeIn, capacityIn));
	}


	

	

	/**
	 * This method is called by a Cook in order to make the Machine's
	 * food item.  You can extend this method however you like, e.g.,
	 * you can have it take extra parameters or return something other
	 * than Object.  It should block if the machine is currently at full
	 * capacity.  If not, the method should return, so the Cook making
	 * the call can proceed.  You will need to implement some means to
	 * notify the calling Cook when the food item is finished.
	 */
	public void makeFood(Food foodIn, Cook cook) throws InterruptedException {
		//YOUR CODE GOES HERE...
		/**
		 * TODO: Food capacity, food done
		 * pre-condition: No exceptions or errors in cook, capacity
		 * post-condition: submit give food back to cooks
		 * invariants: input numFoods cannot more than capacity
		 * exceptions: InterruptedException or illegal arguments exception
		 */
		foods.add(foodIn);
		Thread cookAnItem = new Thread(new CookAnItem(foodIn.cookTimeMS, cook));
		cookAnItem.start();
	}

	//THIS MIGHT BE A USEFUL METHOD TO HAVE AND USE BUT IS JUST ONE IDEA
	private class CookAnItem implements Runnable {
		private int sleepTime;
		private Cook cook;
		private CookAnItem(int time, Cook cook){
			this.sleepTime = time;
			this.cook = cook;
		}
		public void run() {
			try {
				Thread.sleep(sleepTime);
				synchronized (foods){
					foods.remove(0);
					foods.notifyAll();
				}
				Simulation.logEvent(SimulationEvent.machineCookingFood(Machine.this, machineFoodType));
				synchronized (cook.foodsDone){
					cook.foodsDone.add(machineFoodType);
					cook.foodsDone.notifyAll();
				}
			} catch(InterruptedException e) { }
		}
	}
 
	public synchronized boolean getMachineAvail(){
		return this.foods.size() < this.capacity;
	}
	public String toString() {
		return machineName;
	}
}