/**
 *  @author YOUR NAME SHOULD GO HERE
 */


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class AuctionServer
{
	/**
	 * Singleton: the following code makes the server a Singleton. You should
	 * not edit the code in the following noted section.
	 *
	 * For test purposes, we made the constructor protected.
	 */

	/* Singleton: Begin code that you SHOULD NOT CHANGE! */
	protected AuctionServer()
	{
	}

	private static AuctionServer instance = new AuctionServer();

	public static AuctionServer getInstance()
	{
		return instance;
	}

	/* Singleton: End code that you SHOULD NOT CHANGE! */





	/* Statistic variables and server constants: Begin code you should likely leave alone. */


	/**
	 * Server statistic variables and access methods:
	 */
	private int soldItemsCount = 0;
	private int revenue = 0;

	public int soldItemsCount()
	{
		return this.soldItemsCount;
	}

	public int revenue()
	{
		return this.revenue;
	}



	/**
	 * Server restriction constants:
	 */
	public static final int maxBidCount = 10; // The maximum number of bids at any given time for a buyer.
	public static final int maxSellerItems = 20; // The maximum number of items that a seller can submit at any given time.
	public static final int serverCapacity = 80; // The maximum number of active items at a given time.


	/* Statistic variables and server constants: End code you should likely leave alone. */



	/**
	 * Some variables we think will be of potential use as you implement the server...
	 */

	// List of items currently up for bidding (will eventually remove things that have expired).
	private List<Item> itemsUpForBidding = new ArrayList<Item>();


	// The last value used as a listing ID.  We'll assume the first thing added gets a listing ID of 0.
	private int lastListingID = -1;

	// List of item IDs and actual items.  This is a running list with everything ever added to the auction.
	private HashMap<Integer, Item> itemsAndIDs = new HashMap<Integer, Item>();

	// List of itemIDs and the highest bid for each item.  This is a running list with everything ever added to the auction.
	private HashMap<Integer, Integer> highestBids = new HashMap<Integer, Integer>();

	// List of itemIDs and the person who made the highest bid for each item.   This is a running list with everything ever bid upon.
	private HashMap<Integer, String> highestBidders = new HashMap<Integer, String>();




	// List of sellers and how many items they have currently up for bidding.
	private HashMap<String, Integer> itemsPerSeller = new HashMap<String, Integer>();

	// List of buyers and how many items on which they are currently bidding.
	private HashMap<String, Integer> itemsPerBuyer = new HashMap<String, Integer>();



	// Object used for instance synchronization if you need to do it at some point
	// since as a good practice we don't use synchronized (this) if we are doing internal
	// synchronization.
	//
	// private Object instanceLock = new Object();









	/*
	 *  The code from this point forward can and should be changed to correctly and safely
	 *  implement the methods as needed to create a working multi-threaded server for the
	 *  system.  If you need to add Object instances here to use for locking, place a comment
	 *  with them saying what they represent.  Note that if they just represent one structure
	 *  then you should probably be using that structure's intrinsic lock.
	 */


	/**
	 * Attempt to submit an <code>Item</code> to the auction
	 * @param sellerName Name of the <code>Seller</code>
	 * @param itemName Name of the <code>Item</code>
	 * @param lowestBiddingPrice Opening price
	 * @param biddingDurationMs Bidding duration in milliseconds
	 * @return A positive, unique listing ID if the <code>Item</code> listed successfully, otherwise -1
	 */
	public int submitItem(String sellerName, String itemName, int lowestBiddingPrice, int biddingDurationMs)
	{
		/**
		 * pre-condition:seller Name and itemName not null or "", lowestBiddingPrice cannot less than 0 or more than 10, biddingDurationMs cannot more than 1000
		 * Invariant: Number of items related to a person cannot more than maxSellerItems.
		 * post-condition:return -1 if submit item fail or return list id if submit successfully.
		 * Exception: null pointer and ArithmeticException when sellerName, itemName null or lowestBiddingPrice under 0
		 */
		// TODO: IMPLEMENT CODE HERE
		// Some reminders:
		//   Make sure there's room in the auction site.
		//   If the seller is a new one, add them to the list of sellers.
		//   If the seller has too many items up for bidding, don't let them add this one.
		//   Don't forget to increment the number of things the seller has currently listed.
		/**
		 * Try catch or just if else for the exception?
		 * */
		if (sellerName == null || itemName == null
				|| lowestBiddingPrice < 0 || itemsPerSeller == null
				|| itemsPerSeller.size() == 0){
			return  -1;
		}
		if (itemsPerSeller.get(sellerName) > maxSellerItems){
			return -1;
		}
		if (itemsUpForBidding.stream().anyMatch(o -> o.name().equals(itemName) && o.seller().equals(sellerName))){
			return -1;//TODO: submit same item?
		}
		//if(!contains seller){ new seller; new item; item hashcode; itemsPerSeller.add()}
		//itemsPerSeller.get(sellerName)++;
		//
		return -1;
	}



	/**
	 * Get all <code>Items</code> active in the auction
	 * @return A copy of the <code>List</code> of <code>Items</code>
	 */
	public synchronized List<Item> getItems()
	{
		/**
		 * TODO Q: Deep copy override clone in Item?
		 * pre-condition:
		 * Invariant:
		 * post-condition:return the copy of items in the list.
		 */
		// TODO: IMPLEMENT CODE HERE
		// Some reminders:
		//    Don't forget that whatever you return is now outside of your control.
		return new ArrayList<>(itemsUpForBidding);
	}


	/**
	 * Attempt to submit a bid for an <code>Item</code>
	 * @param bidderName Name of the <code>Bidder</code>
	 * @param listingID Unique ID of the <code>Item</code>
	 * @param biddingAmount Total amount to bid
	 * @return True if successfully bid, false otherwise
	 */
	public boolean submitBid(String bidderName, int listingID, int biddingAmount)
	{
		/**
		 * pre-condition: bidderName and listingID not null, listingID in list items. check status of item which should be available for bidding. legal biddingAmount.
		 * Invariant: bidding amount less than 10.
		 * post-condition: return true if submit successfully or false if not.
		 * Exception: bidderName null or biddingAmount less than 0
		 */
		// TODO: IMPLEMENT CODE HERE
		// Some reminders:
		//   See if the item exists.
		//   See if it can be bid upon.
		//   See if this bidder has too many items in their bidding list.
		//   Get current bidding info.
		//   See if they already hold the highest bid.
		//   See if the new bid isn't better than the existing/opening bid floor.
		//   Decrement the former winning bidder's count
		//   Put your bid in place

		//if(itemsUpForBidding null ) new itemsUpForBidding
		//if(itemsPerBuyer null) new itemsPerBuyer
		//
		if (!itemsUpForBidding.contains(listingID) || !itemsUpForBidding.get(listingID).biddingOpen()){
			return  false;
		}

		//highestBids.put(listingID, biddingAmount);
		for (Map.Entry<String, Integer> entry: itemsPerBuyer.entrySet()){
			if (entry.getKey().equals(bidderName)){
				return entry.getValue() > maxBidCount ? false : true; //TODO : COMPARE CURRENT VS PREVIOUS
			}
		}
		return false;
	}

	/**
	 * Check the status of a <code>Bidder</code>'s bid on an <code>Item</code>
	 * @param bidderName Name of <code>Bidder</code>
	 * @param listingID Unique ID of the <code>Item</code>
	 * @return 1 (success) if bid is over and this <code>Bidder</code> has won<br>
	 * 2 (open) if this <code>Item</code> is still up for auction<br>
	 * 3 (failed) If this <code>Bidder</code> did not win or the <code>Item</code> does not exist
	 */
	public int checkBidStatus(String bidderName, int listingID)
	{
		/**
		 * pre-condition: bidderName not null or "", legal listingID.
		 * Invariant: Item price cannot more than maxBidCount
		 * post-condition: return 3 if one of the input illegal. Return 1 if duration has passed or 2 when item is still available to bid.
		 * Exception: No match or null pointer
		 */
		// TODO: IMPLEMENT CODE HERE
		// Some reminders:
		//   If the bidding is closed, clean up for that item.
		//     Remove item from the list of things up for bidding.
		//     Decrease the count of items being bid on by the winning bidder if there was any...
		//     Update the number of open bids for this seller
		if (itemsPerBuyer.keySet().contains(bidderName)
				&& itemsPerBuyer.get(bidderName) < maxBidCount
				&& itemsUpForBidding.get(listingID).biddingOpen()){
			return 2;
		}

		if (!itemsUpForBidding.get(listingID).biddingOpen()) {
			return 1;
		}
		return -1;
	}

	/**
	 * Check the current bid for an <code>Item</code>
	 * @param listingID Unique ID of the <code>Item</code>
	 * @return The highest bid so far or the opening price if no bid has been made,
	 * -1 if no <code>Item</code> exists
	 */
	public int itemPrice(int listingID)
	{
		/**
		 * pre-condition: item exists.
		 * Invariant: listingID is legal.
		 * post-condition: return the right price of the target Item.
		 * Exception: input id error
		 */
		// TODO: IMPLEMENT CODE HERE
		return highestBids.get(listingID);
	}

	/**
	 * Check whether an <code>Item</code> has been bid upon yet
	 * @param listingID Unique ID of the <code>Item</code>
	 * @return True if there is no bid or the <code>Item</code> does not exist, false otherwise
	 */
	public Boolean itemUnbid(int listingID)
	{
		/**
		 * pre-condition: item exists.
		 * Invariant: itemsAndIDs as well as itemsUpForBidding have items.
		 * post-condition: return true if no bid or no such item. return false otherwise.
		 * Exception: input id wrong or null for one of the list
		 */
		// TODO: IMPLEMENT CODE HERE
		if (!itemsUpForBidding.contains(listingID) || itemsAndIDs.get(listingID).biddingOpen()){
			return false;
		}
		return true;
	}


}
