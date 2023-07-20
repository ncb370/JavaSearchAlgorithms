// Nicole Barrington
// 11082099
// NCB370
// CMPT 280

/* ChainedHashTable280.java
 * ---------------------------------------------
 * Copyright (c) 2004 University of Saskatchewan
 * All Rights Reserved
 * --------------------------------------------- */

package lib280.hashtable;

import lib280.base.CursorPosition280;
import lib280.dictionary.HashTable280;
import lib280.exception.AfterTheEnd280Exception;
import lib280.exception.InvalidArgument280Exception;
import lib280.exception.ItemNotFound280Exception;
import lib280.exception.NoCurrentItem280Exception;
import lib280.list.LinkedIterator280;
import lib280.list.LinkedList280;
import lib280.list.LinkedNode280;

import java.util.LinkedList;

/**	A HashTable dictionary that uses chaining.  Items can be 
	inserted, deleted, obtained, and searched for by hashValue.  
	An iterator is included, with functions goFirst, goForth, 
	before, and after */
public class ChainedHashTable280<I> extends HashTable280<I>
{ 
	/**	Array to store linked lists for separate chaining. */
	protected LinkedList280<I>[] hashArray;
  
	/**	Position of the current item in its list. */
	protected LinkedIterator280<I> itemListLocation;

  
	/**	
	 * Create a new hash list for a new chain.
	 * @timing  O(1)  
	 */
	protected LinkedList280<I> newChain()
	{
		return new LinkedList280<I>();
	}

	/**	
	 * Construct hash table strucuture with at least newSize locations. <br>
	 * @timing O(newSize^2)
	 * @param newSize size of the hash table is at least this large.
	 */
	@SuppressWarnings("unchecked")
	public ChainedHashTable280(int newSize)
	{
//		LinkedList280<I> arrayList;
//		arrayList = new LinkedList280<I>();
//
		hashArray = new LinkedList280[newSize];
		count = 0;
		itemListLocation = null;

		// create the chain at the beginning, instead of while inserting it
		for (int i = 0; i < capacity(); i++) {
			hashArray[i] = newChain();

		}

	}

	/**
	 * A method that returns the capacity of the Chained Hash Table
	 * @return the capacity as an integer
	 */
	@Override
	public int capacity()
	{
		return hashArray.length;
	}

	/**
	 * A method that returns the number of times an item appears in the hash table.
	 * @param i The item to be searched.
	 * @return an int that represents the number of times the item appears in the table.
	 */
	@Override
	public int frequency(I i)
	{
		int result = 0;
		boolean saveSearchMode = searchesContinue;
		CursorPosition280 savePos = currentPosition();
		restartSearches();
		search(i);
		resumeSearches();
		while (itemExists())
		{
			result++;
			search(i);
		}
		searchesContinue = saveSearchMode;
		goPosition(savePos);
		return result;
	}


	/**
	 * A method that compares two items.
	 * @param x item to be compared to y
	 * @param y item to be compared to x
	 * @return true if the two items are equal, false otherwise.
	 */
	@Override
    @SuppressWarnings("unchecked")
	public boolean membershipEquals(I x, I y)
	{
			if (x == null || y == null)
				return false;
			else if (x.equals(y))
				return true;
			else
				return false;
	}


	/**
	 * Always returns false because chained hash tables cannot be full
	 * @return false
	 */
	@Override
	public boolean isFull()
	{
		return false;
	}

	/**
	 * A method that determines if an item exists.
	 * @return true if the item exists, and false if it doesn't.
	 */
	@Override
	public boolean itemExists()
	{
		return (itemListLocation!=null) && (itemListLocation.itemExists());
	}

	/**
	 * A method that returns the item at a location.
	 * @return the item I at a location.
	 * @throws NoCurrentItem280Exception
	 */
	@Override
	public I item() throws NoCurrentItem280Exception
	{
		if (!itemExists())
			throw new NoCurrentItem280Exception("Cannot return an item that does not exist.");

		return itemListLocation.item();

	}


	/**
	 * A method that determines if item y is in a linked list.
	 * @param y item whose presence is to be determined.
	 * @return true if the item is in the list, false otherwise.
	 */
	@Override
	public boolean has(I y)
	{


		// this is code using the linked list iterator

		// create the index to search for
		int index;
		index = hashPos(y);

		// Make a hash array
		LinkedList280<I> hashArrayIndex = hashArray[index];

		// Create an iterator for the linked lists
		LinkedIterator280<I> next = new LinkedIterator280<>(hashArrayIndex);

		// if hash is empty, return false
		if (hashArrayIndex.isEmpty()) {
			return false;
		}

		// if not empty, set iterator to first item
		// while an item exists, check the membership
		// if it's in membership, return true, if not go forth

		else {
			// put item first position
			next.goFirst();
			while (next.itemExists()) {
				if (hashArrayIndex.membershipEquals(next.item(), y)) {
					return true;
				}
				next.goForth();
			}
		}

		return false;


	}

	/**
	 * A method to insert an item y in the table.
	 * @param y the item to be inserted.
	 */
	@Override
	public void insert(I y) 
	{

		// instantiate an index
		int index;

		// get hash position for the index
		index = hashPos(y);

		// if the index is null, create a new linked list
		if (hashArray[index] == null || hashArray[index].isEmpty() ) {
			LinkedList280<I> newList = new LinkedList280<>();
			newList.insertFirst(y);
			hashArray[index] = newList;
		}

		// else, insert the new linked list into the chained hash table
		else {
			hashArray[index].insertLast(y);
		}

		// increment count
		count++;




	}

	/**
	 * A method that deletes an item in the table
	 * @param y the item to be deleted.
	 * @throws ItemNotFound280Exception
	 */
	@Override
	public void delete(I y) throws ItemNotFound280Exception {


		// if the CHT has the item, delete it
		try {
			if (has(y)) {
				search(y);
				deleteItem();
			}
		} catch (ItemNotFound280Exception e) {
			System.out.println("Item cannot be deleted because item doesn't exist.");
		}

	}


	/**
	 * A method called by delete() that deletes the item by setting it to null.
	 * @throws NoCurrentItem280Exception
	 */
//	@Override
	public void deleteItem() throws NoCurrentItem280Exception
	{

		// find item index
		// if item doesn't exist, throw exception
		if (!this.itemExists()) {
			throw new NoCurrentItem280Exception("There is no item at the cursor to delete.");
		}


		else {


//			System.out.println("\nThis is where itemListLocation is: " + itemListLocation);

			I deleteThis = itemListLocation.item();
//			System.out.println("Current item to be deleted: " + deleteThis);
//			System.out.println("Current cursor location " + itemListLocation.item());


			// get hash index of item
			// call that linked list's delete

			int index;

			// hash index of item
			index = hashPos(deleteThis);

//			System.out.println("Index of hash position: " + index);

			hashArray[index].delete(deleteThis);

			// reduce size
			count --;
		}
	}

	/**
	 * Puts the cursor on the item it is searching for
	 * @param y
	 */
	@Override
	public void search(I y) {

		// get hash loc
		int itemHashLocation = hashPos(y);

		// go forth to find the item
		if (searchesContinue && itemListLocation!=null)
			goForth();


		else
		{
			if (hashArray[itemHashLocation]==null)
				hashArray[itemHashLocation] = newChain();
			itemListLocation = hashArray[itemHashLocation].iterator();
		}
		while (!itemListLocation.after() && !membershipEquals(y, itemListLocation.item()))
			itemListLocation.goForth();

	}


	/**
	 * Retrieve an item from the dictionary with membershipEquals(item,y).
	 * @precond has(y)
	 * @param y item to obtain from the dictionary
	 * @return y
	 * @throws ItemNotFound280Exception
	 */
	@Override
	public I obtain(I y) throws ItemNotFound280Exception
	{

		// if it has it, return it
		if (has(y)) {
			return y;
		}

		else {
			throw new ItemNotFound280Exception("Item does not exist.");
		}


	}

	@Override
	public boolean before() 
	{
		return (itemListLocation==null) || (itemListLocation.before());
	}
  
	/**	
	 * Are we after the last item in the hash table?.
	 * @timing O(1) 
	 */  
	public boolean after()
	{
		return (itemListLocation!=null) && (itemListLocation.after());
	}

	/**
	 * Method that allows user to go to next position
	 * @throws AfterTheEnd280Exception
	 */
    @Override
	public void goForth() throws AfterTheEnd280Exception
	{

//		if (this.after())
//			throw new AfterTheEnd280Exception("Cannot goForth() when at the end of the table");
//
//
		try {
			if (this.itemListLocation == null || this.itemListLocation.before())
				this.goFirst();
			else {
				int itemHashLocation = this.hashPos(item());
				this.itemListLocation.goForth();
				if (this.itemListLocation.after())
					this.findNextItem(itemHashLocation + 1);
			}
		}
		catch (AfterTheEnd280Exception e) {
			System.out.println("Cannot goForth() when at the end of the table.");
		}
	}

    @Override
	public void goFirst()
	{
		findNextItem(0);
	}

    @Override
	public void goBefore() 
	{
		itemListLocation.goBefore();
	}

    @Override
	public void goAfter() 
	{
		if (hashArray[hashArray.length-1] == null)
			hashArray[hashArray.length-1] = newChain();
		itemListLocation = hashArray[hashArray.length-1].iterator();
		if( !hashArray[hashArray.length-1].isEmpty() ) 
			itemListLocation.goAfter();
	}

	/**
	 * Go to the first item of the first non-empty list
	 * starting at index, or goAfter() if none found.
	 * @timing O(capacity()) - worst case.
	 * @param index first hash value to search to find the next item
	 */
	protected void findNextItem(int index)
	{


		// the hashArray is empty, go before
		if (hashArray.length == 0) {
			goAfter();
		}

		if (itemListLocation.after()) {
			goAfter();
		}

		if (hashArray[index] != null) {
			itemListLocation = hashArray[index].iterator();
		}

		// if the index is null
		else {
			while (hashArray[index]==null) {
				hashArray[index].goForth();
			}
		}

		}


    @Override
	public CursorPosition280 currentPosition()
	{
		/* Return type is CursorPosition280 rather than LinkedIterator280<I> as the iterator
		   returned only iterates through one list rather than the whole container. */
		if (itemListLocation != null) 
			return itemListLocation.clone();
		else
			return null;
	}

    @Override
    @SuppressWarnings("unchecked")
	public void goPosition(CursorPosition280 pos)
	{
		if (pos != null  && !(pos instanceof LinkedIterator280))
			throw new InvalidArgument280Exception("The cursor position parameter" 
					    + " must be a LinkedIterator280<I>");
		if(pos != null) 
			itemListLocation = ((LinkedIterator280<I>) pos).clone();
		else
			itemListLocation = null;
	}

    @Override
	public String toString()
	{
		String result = "";
		for (int i=0; i<capacity(); i++)
			if (hashArray[i] != null)
				result += "\n" + i + ": " + hashArray[i].toString();
		return result;
	}

    @Override
	public void clear()
	{
		for (int i=0; i<capacity(); i++)
			hashArray[i]=null;
		count = 0;
		itemListLocation = null;
	}

	/**
	 * A main method for testing.
	 */
	public static void main(String args[]) {
		
		
		ChainedHashTable280<Double> H = new ChainedHashTable280<Double>(10);
		
		// Test isEmpty on empty table
		if(!H.isEmpty()) System.out.println("Error in isEmpty(): hash table is empty but isEmpty() says otherwise.");

		// Test isFull
		if (H.isFull()) {
			System.out.println("Error in isFull(): isFull should always return false");
		}
		
		// Testing insert() on empty table.
		H.insert(42.0);
		H.hashArray[H.hashPos(42.0)].goFirst();
		if( H.hashArray[H.hashPos(42.0)].item()  != 42.0)
			System.out.println("Error in insert(): item 42 is not in the has table where it should be!");

		// Testing isEmpty() on non-empty table
		if(H.isEmpty()) System.out.println("Error in isEmpty(): hash table is not empty " +
				"but isEmpty(0) says otherwise.");

		// Testing insert() on non-empty table
		H.insert(99.0);
		H.hashArray[H.hashPos(99.0)].goFirst();
		if( H.hashArray[H.hashPos(99.0)].item()  != 99.0)
			System.out.println("Error in insert(): item 99 is not in the has table where it should be!");

		// Insert one more thing:
		H.insert(19.0);
		H.hashArray[H.hashPos(19.0)].goFirst();
		if( H.hashArray[H.hashPos(19.0)].item()  != 19.0)
			System.out.println("Error in insert(): item 19 is not in the has table where it should be!");

		// Test search() on multi-items.
		H.search(42.0);
		if(!H.itemExists() || H.item() != 42.0)
			System.out.println("Error in search(): cursor should be on 42.0 but it isn't!");

		H.goBefore();
		H.goForth();
		if(!H.itemExists() || H.item() != 19.0)
			System.out.println("Error in goForth(): cursor should be on 19.0 but it isn't!");

		// Insert more items to search for
		H.insert(88.0);
		H.insert(66.0);
		H.insert(55.0);
		H.insert(15.0);


		System.out.println("\nHASH ARRAY TABLE H:");
		// Just some code to print out the array
		for (int i = 0; i < H.capacity(); i++) {
			System.out.println("Hash array position " + i + " : " + H.hashArray[i]);
		}

		// Test has() on H
		System.out.println("\nShould return true: " + H.has(55.0));
		System.out.println("Should return true: " + H.has(88.0));
		System.out.println("Should return true: " + H.has(42.0));
		System.out.println("Should return false: " + H.has(11.0));
		System.out.println("Should return false: " + H.has(8.0));


		H.findNextItem(1);

//		 Test search() on multi-items
		H.search(66.0);
		if (!H.itemExists() || H.item() != 66) {
			System.out.println("Error in search(): cursor should be on 66.0 but it isn't!");
			System.out.println("The cursor is on item: " + H.item());
		}

		// Test search() on multi-items.
		H.search(42.0);
		if(!H.itemExists() || H.item() != 42.0)
			System.out.println("Error in search(): cursor should be on 42.0 but it isn't!");

		// Test search() on item that is not in the list.
		H.search(10.0);
		if (H.itemExists()) {
			System.out.println("Error in search(): cursor should be at after()");
		}

		// Test obtain()
		double result;
		result = H.obtain(55.0);
		if (result != 55) {
			System.out.println("Error in obtain(): should have returned 55.");
		}

//		// Test obtain() with item that doesn't exist
//		// uncomment this to see Exception message.
//		H.obtain(63424.0);

		// Test delete/deleteItem(), second item in linked list
		H.delete(55.0);
		boolean result2;
		result2 = H.has(55.0);
		if (result2!=false) {
			System.out.println("Error in delete(): should have returned false");
		}

		// Print list again to make sure 55 was deleted
		System.out.println("\nHASH ARRAY TABLE H AFTER DELETING 55:");
		// Just some code to print out the array
		for (int i = 0; i < H.capacity(); i++) {
			System.out.println("Hash array position " + i + " : " + H.hashArray[i]);
		}

		// Delete first item in a linked list
		H.delete(19.0);
		boolean result3;
		result3 = H.has(19.0);
		if (result3!= false) {
			System.out.println("Error in delete(): should have returned false");
		}

		// Print list again to make sure 19 was delete
		System.out.println("\nHASH ARRAY TABLE H AFTER DELETING 19:");
		// Just some code to print out the array
		for (int i = 0; i < H.capacity(); i++) {
			System.out.println("Hash array position " + i + " : " + H.hashArray[i]);
		}


		// Delete item that is not in the table
		H.delete(100.0);
		boolean result4;
		result4 = H.has(100.0);
		if (result4!=false) {
			System.out.println("Error in delete(): should have returned false");
		}


		// Test goForth
		H.goFirst();
		H.goForth();
		if (H.item() != 15.0) {
			System.out.println("Error in goForth(): item should be 15.0 but it was " + H.item());
		}


		// Test findNextItem()
//		 please note, this does not work at the moment.
//		H.findNextItem(3);
//		System.out.println(H.item());


		// Use iterators to print the items forward and backward.
//		H.goFirst();
//		while(H.itemExists()) {
//			System.out.println(H.item());
//			H.goForth();
//		}

		System.out.println("Regression test complete.");
	}
} 
