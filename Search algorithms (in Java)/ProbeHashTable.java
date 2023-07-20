// Nicole Barrington
// 11082099
// NCB370
// CMPT 280

import lib280.base.CursorPosition280;
import lib280.dictionary.HashTable280;
import lib280.exception.AfterTheEnd280Exception;
import lib280.exception.InvalidArgument280Exception;
import lib280.exception.ItemNotFound280Exception;
import lib280.exception.NoCurrentItem280Exception;
import lib280.list.ArrayedListIterator280;
import lib280.list.LinkedIterator280;
import lib280.list.LinkedList280;

public abstract class ProbeHashTable<I> extends HashTable280<I> {
    /**	Array to store linked lists for separate chaining. */

    protected I [] hashArray;

    protected int hashIndex;

    protected static int afterPos = -1;

    protected static int beforePos = -2;

    protected static int firstPos = 0;

//    /**	Position of the current item in its list. */
//    protected LinkedIterator280<I> itemListLocation;

    /**
     * Create a new hash list for a new chain.
     * @timing  O(1)
     */
    protected LinkedList280<I> newChain()
    {
        return new LinkedList280<I>();
    }

    /**
     * Construct hash table structure with at least newSize locations. <br>
     * @timing O(newSize^2)
     * @param newSize size of the hash table is at least this large
     */
    @SuppressWarnings("unchecked")
    public ProbeHashTable(int newSize)
    {
        hashArray = (I[]) new Object[newSize];
        count = 0;
        hashIndex = 0;

}


    @Override
    public int capacity()
    {
        return hashArray.length;
    }


    @Override
    public int frequency(I i)
    {
//        System.out.println("Looking for item " + i);

        int result = 0;

        for (int v = 0; v < hashArray.length; v ++) {
//            System.out.println(hashArray[v]);

            // case where there is no item at the position
            if (hashArray[v] == null) {
//                System.out.println("pos is null");
            }

            // case where there is an item at the position
            else if (hashArray[v].equals(i)) {
                result ++;
            }

        }

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
     * Alwways returns false because chained hash tables cannot be full
     * @return false
     */
    @Override
    public boolean isFull()
    {
        for (int i = 0; i < hashArray.length; i++) {
            if (hashArray[i] != null) {
                this.goForth();
            }

            if (this.after()) {
                return true;
            }

            else {
                return false;
            }
        }
        return true;

    }

    /**
     * A method that determines if an item exists.
     * @return true if the item exists, and false if it doesn't.
     */
    @Override
    public boolean itemExists()
    {
        return (hashArray[hashIndex]!=null);
    }

    /**
     * A method that returns the item at a location.
     * @return the item I at a location.
     * @throws NoCurrentItem280Exception
     */
    @Override
    public I item() throws NoCurrentItem280Exception
    {

        try{
            if (itemExists()) {
                return hashArray[hashIndex];
            }
        }

        catch (NoCurrentItem280Exception e){
        if (!itemExists())
            System.out.println("Cannot return an item that does not exist.");

    }
        return hashArray[hashIndex];
    }


    /**
     * A method that determines if item y is in an array
     * @param y item whose presence is to be determined
     * @return true if the item is in the list, false otherwise
     */
    @Override
    public boolean has(I y)
    {

        for (int i = 0; i < hashArray.length; i ++) {
            if (y.equals(hashArray[i])) {
                return true;
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


        if (this.isFull()) {
//            System.out.println("Cannot insert item, container is full");
        }

        else if (this.has(y)) {
//            System.out.println("Item already exists, cannot be inserted.");
        }

        else {
//            System.out.println("\nItem to be inserted: " + y);
            // place where item will be inserted
            int insertHere;

            // get the initial hash position of insert
            int index = hashPos(y);

            // probe the index to make sure it's available for insertion
            insertHere = probe(index, y);

            if (hashArray[insertHere] != null) {
//                System.out.println("Couldn't be inserted.");
            }

            // the returned int from probe(index)
            // will be the index where item can be inserted
            // add the value at that index
            else {hashArray[insertHere] = y;}
        }

    }

    /**
     * Just a method that can be overridden in each of the subclasses...
     * @param index the original index where it can be inserted
     * @return the index where it will be inserted.
     */
    public int probe(int index, I y) {
        return -1;
    }

    /**
     * A method that deletes an item in the table
     * @param y the item to be deleted.
     * @throws ItemNotFound280Exception
     */
    @Override
    public void delete(I y) throws ItemNotFound280Exception {

        try {
            if (has(y)) {
                search(y);
                deleteItem();
            }
        }
        catch (ItemNotFound280Exception e) {
            System.out.println("Item cannot be deleted because item doesn't exist");
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
        //. if item doesn't exist, throw exception
        if (!this.itemExists()) {
            throw new NoCurrentItem280Exception("There is no item at the cursor to delete.");
        }


        // if item exists, a few different cases

        // only item in the linked list
        // first item in the linked list
        // middle item in the linked list
        // last item in the linked list

        else {


//            System.out.println("\nThis is where itemListLocation is: " + hashIndex);
//			System.out.println("This is the index value " + index);

            // get hash index of item
            hashArray[hashIndex] = null;

//			System.out.println(hashArray[index]);

//			System.out.println(index);

//			System.out.println(hashArray[index].item());

//			System.out.println(currentPosition());
////			goPosition(itemListLocation);
//			System.out.println(currentPosition())

            count --;
        }
    }

    /**
     * Puts the cursor on the item it is searching for
     * @param y
     */
    @Override
    public void search(I y) {


//        int itemHashLocation = hashPos(y);



        int currentIndex = 0;

        if (!this.has(y)) {
//            System.out.println("Item is not in the table.");
            return;
        }

        // item matches what's in the current index, set the hashIndex to the current Index
        if (y.equals(hashArray[currentIndex])) {
            hashIndex = currentIndex;
            return;
        }

        // else, while item is not equal to the current item, and the currentIndex is less than
        // the length of the hashArray, increment the current Index and the hashIndex.

        while (!y.equals(hashArray[currentIndex]) && currentIndex < hashArray.length) {
            currentIndex ++;
        }

        hashIndex = currentIndex;



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


        try {
            if (has(y)) {
                return y;
            }
        }

        catch (ItemNotFound280Exception e)
        {
            throw new ItemNotFound280Exception("There is no item that matches y");
        }

        catch (NullPointerException e) {
            System.out.println("Item not in the table.");
        }
        return null;
    }

    @Override
    public boolean before()
    {
        return (this.hashIndex == beforePos);
    }

    /**
     * Are we after the last item in the hash table?.
     * @timing O(1)
     */
    public boolean after()
    {
        return (this.hashIndex == afterPos);
    }

    @Override
    public void goForth() throws AfterTheEnd280Exception
    {

        try {
            if (this.before())
                this.goFirst();

            else {
                hashIndex ++;
            }
        }
        catch (AfterTheEnd280Exception e) {
            System.out.println("Cannot goForth() when ar the end of the table.");
        }

    }

    @Override
    public void goFirst()
    {
        this.hashIndex = firstPos;
    }

    @Override
    public void goBefore()
    {
        this.hashIndex = beforePos;

    }

    @Override
    public void goAfter()
    {
        this.hashIndex = afterPos;
    }

    /**
     * Go to the first item of the first non-empty list
     * starting at index, or goAfter() if none found.
     * @timing O(capacity()) - worst case.
     * @param index first hash value to search to find the next item
     */
    protected void findNextItem(int index)
    {

        hashIndex = index;

        // the hashArray is empty, go before
        if (hashArray.length == 0) {
            goAfter();
        }

        if (this.after()) {
            goAfter();
        }

        if (hashArray[index + 1] != null) {
            hashIndex ++;
        }

        // if the index is null
        else {

            int ListLength = 0;
            while (hashArray[index] == null && ListLength < capacity()) {
                hashIndex ++;
                ListLength ++;
            }
        }

    }


    @Override
    public CursorPosition280 currentPosition()
    {
        return null;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void goPosition(CursorPosition280 pos) { }

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
        hashIndex = 0;
    }

}
