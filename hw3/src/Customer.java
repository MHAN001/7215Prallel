import java.util.List;

/**
 * Customers are simulation actors that have two fields: a name, and a list
 * of Food items that constitute the Customer's order.  When running, an
 * customer attempts to enter the coffee shop (only successful if the
 * coffee shop has a free table), place its order, and then leave the 
 * coffee shop when the order is complete.
 */
public class Customer implements Runnable {
	//JUST ONE SET OF IDEAS ON HOW TO SET THINGS UP...
	private final String name;
	private final List<Food> order;
	private final int orderNum;    
	
	private static int runningCounter = 0;

	private final int priority;
	private final int numTables;
	private static int avalTbls;
	/**
	 * You can feel free modify this constructor.  It must take at
	 * least the name and order but may take other parameters if you
	 * would find adding them useful.
	 */
	public Customer(String name, List<Food> order, int priority, int tables) {
		this.name = name;
		this.order = order;
		this.orderNum = ++runningCounter;
		this.priority = priority;
		this.numTables = tables;
	}

	public String toString() {
		return name;
	}

	/** 
	 * This method defines what an Customer does: The customer attempts to
	 * enter the coffee shop (only successful when the coffee shop has a
	 * free table), place its order, and then leave the coffee shop
	 * when the order is complete.
	 */
	public void run(){
		//YOUR CODE GOES HERE...
		//TODO : Enter signal? leave immediately if food list null or food list.size()==0
		/**
		 * pre-condition: must initiated with order and orderNum
		 * post-condition: submit the order to the cook
		 * invariants:avalTbls cannot more than numTables
		 * exceptions:InterruptedException
		 */
		//TODO synchronization
		//synchronize while(!enterCoffeeShop()) wait();
		try{
			if (order == null || order.size() == 0){
				leaveCoffeeShop();
				return;
			}
			if (!enterCoffeeShop()){
				wait();
			}


			leaveCoffeeShop();
		}catch (InterruptedException e){

		}
		
	}

	private synchronized boolean enterCoffeeShop(){
		if (avalTbls > 0){
			avalTbls--;
			return true;
		}else {
			return false;
		}
	}

	private synchronized boolean leaveCoffeeShop(){
		if (avalTbls < numTables){
			avalTbls++;
		}
		return true;
	}

	public int getPriority(){
		return this.priority;
	}

	public int getRunningCounter(){
		//For testing the number of customers
		return runningCounter + 1;
	}

}