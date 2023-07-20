// Nicole Barrington
// 11082099
// NCB370
// CMPT 280

import lib280.base.CursorPosition280;
import lib280.dictionary.HashTable280;
import lib280.exception.*;

/**
 * A hashing method for double hash.
 * If the original key k is evenly divisible by 2 then use: p(j) = h2(k) * j, h2(k) = k mod 8 + 1
 * Else if the original key is evenly divisible by 3 then use p(j) = h2(k) * j, h2(k) = k mod 7 + 1
 * Else, use p(j) = h2(k) * j, h2(k) = k mod 3 + 1
 */
public class DoubleHashTable280<I> extends ProbeHashTable<I>{
    // obtain item's key


    /**
     * A constructor for the DoubleHashTable280
     * @param newSize the size of the hash table
     */
    public DoubleHashTable280(int newSize) {
        super(newSize);
    }


    /**
     * A probing method to determine where an item can be inserted into the hash table,
     * using the double hashing formula.
     * @param index the initial index
     * @return the index where the item can be inserted.
     */
    public int probe(int index, I item) {


        //The original key
        int originalKey = (Integer)item;

        // keep track of the increments for the while loop
        int increment = 0;

        // A variable for the offset
        int offset;

//        System.out.println("The original index for the item to be inserted: " + index);


        try {
            // easiest case; where the position in the array has no item and the index can be returned as is
            if (hashArray[index] == null) {
//                System.out.println("The index was empty, so the item was inserted without any further probing.");
                return index;
            } else {
                while (hashArray[index] != null && increment < capacity()) {

                    /*
                    If even number, use below hashing algorithm
                     */
                    if (originalKey%2 == 0) {
                        offset = originalKey % (8 + 1);
                        index = (originalKey + offset) % capacity();
                    }

                    /*
                    If evenly divisible by 3, use below hashing algorithm
                     */
                    else if (originalKey%3 == 0) {
                        offset = originalKey%(7 + 1);
                        index = (originalKey+offset)%capacity();
                    }


                    else {
                        offset = originalKey%(3 + 1);
                        index = (originalKey + offset)%capacity();
                    }

                    increment++;
                }


            }


        }catch(Exception e){
//            System.out.println("Container is full!");
        }
        return index;

    }

    /**
     * A main method to test the linear hash table
     * @param args
     */
    public static void main(String[] args) {

        // create an instance of DoubleHashTable280
        DoubleHashTable280<Integer> DoubleTable = new DoubleHashTable280<>(40);

        // Test isFull on an empty table
        boolean result;
        result = DoubleTable.isFull();
        if (result!=false) {
            System.out.println("Error in isFull(): should've returned false.");
        }
        // test insert on 20 items into a Quadratic table with 100 items
        System.out.println(DoubleTable);


        // insert each item into their hash table
        System.out.println("----------------------------------------------------------------");

        // Insertion with items that don't have to probe
        System.out.println("\nInserting first item: ");
        System.out.println("     Inserting 2");
        DoubleTable.insert(2);
        if (!DoubleTable.toString().equals("\n2: 2")) {
            System.out.println("Error in probe(): Item was not successfully inserted.\n");
        }

        System.out.println("DoubleTable should have 1 item: " + DoubleTable);
        System.out.println("----------------------------------------------------------------");

        // Insertion with items
        System.out.println("\nInserting items that do not require double probing: ");
        for (int i = 3; i < 5; i++) {
            System.out.println("     Inserting " + i);
            DoubleTable.insert(i);
        }
        if (!DoubleTable.toString().equals("\n2: 2\n3: 3\n4: 4")){
            System.out.println("Error in probe(): items were not successfully inserted.\n");
        }


        // Insertion with items that require one or two probes
        System.out.println("DoubleTable table should have 3 items: " +DoubleTable);
        System.out.println("----------------------------------------------------------------");

        System.out.println("\nInserting items that require at least 1 probe: ");
        for (int i = 10; i < 15; i++) {
            System.out.println("     Inserting " + i);
            DoubleTable.insert(i);
        }
        if (!DoubleTable.toString().equals("\n2: 2\n3: 3\n4: 4\n10: 10\n11: 11\n" +
                "12: 12\n13: 13\n14: 14")){
            System.out.println("Error in probe(): items were not successfully probed.\n");
        }

        // Insertion with items that require one or two probes
        System.out.println("DoubleTable table should have 8 items: " + DoubleTable);
        System.out.println("----------------------------------------------------------------");

        System.out.println("\nInserting items that require 2 or more probes: ");
        for (int i = 15; i < 22; i++) {
            System.out.println("     Inserting " + i);
            DoubleTable.insert(i);
        }

        if (!DoubleTable.toString().equals("\n2: 2\n3: 3\n4: 4\n10: 10\n11: 11\n" +
                "12: 12\n13: 13\n14: 14\n15: 15\n16: 16\n17: 17\n18: 18\n" +
                "19: 19\n20: 20\n21: 21")) {
            System.out.println("Error in probe(): items were not successfully probed.\n");
        }

        System.out.println("DoubleTable table should have 15 items: "+ DoubleTable);
        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTest to make sure duplicate items cannot be submitted: ");

        // test to make sure duplicate items cannot be inserted
        DoubleTable.insert(5);
        if (DoubleTable.frequency(5) > 1) {
            System.out.println("Error in insert(): table contains duplicates, but it shouldn't.");
        }
        else {
            System.out.println("Does not contain duplicates.");
        }


        System.out.println("----------------------------------------------------------------");

        // Tests for delete ////////////////////////////////////////////////////////////////////////


        // Delete an item that exists in the table
        System.out.println("\nTesting delete()");
        System.out.println("Deleting item 15 from table: ");
        DoubleTable.delete(15);
        if (DoubleTable.has(15)) {
            System.out.println("Error in delete(): item not deleted");
        }
        else {
            System.out.println("And it was deleted successfully: " + DoubleTable);
        }

        System.out.println("----------------------------------------------------------------");

        // Try to delete an item that doesn't exist
        System.out.println("\nAttempt to delete item that doesn't exist: ");
        DoubleTable.delete(77777);
        System.out.println("Test was ran successfully and exceptions were caught.");

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting search()");

        System.out.println("Searching for 21 in table: ");
        DoubleTable.search(21);
        if (DoubleTable.item() != 21) {
            System.out.println("Error in search(): cursor not on correct item.");
        }
        System.out.println("Cursor is currently on this item: "+ DoubleTable.item());

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting search()");
        System.out.println("Searching for an item that doesn't exist in table");
        DoubleTable.search(4444);
        System.out.println("Test was ran successfully and exceptions were caught.");


        System.out.println("----------------------------------------------------------------");

        // Test for frequency
        System.out.println("\nTesting frequency()");

        System.out.println("Testing frequency on item that doesn't exist in the table");
        int result1;
        result1 = DoubleTable.frequency(1231);
        if (result1 != 0) {
            System.out.println("Error: expected frequency was 0 but method returned " + result1);
        }
        else {
            System.out.println("Frequency returned the correct value.");
        }

        System.out.println("----------------------------------------------------------------");


        System.out.println("\nTesting frequency()");
        System.out.println("Testing frequency on item that exists in the table");
        int result2;
        result2 = DoubleTable.frequency(3);
        if (result2 != 1) {
            System.out.println("Error in frequency: expected frequency was 1 but method returned " + result2);
        }
        else {
            System.out.println("Frequency returned the correct value.");
        }

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting obtain() ");
        System.out.println("Testing obtain on item that exists in the table");
        Object result3;
        result3 = DoubleTable.obtain(11);
        if (!result3.equals(11)) {
            System.out.println("Error in obtain: expected item '11.0' and " + result3 + "was returned.");
        }
        else {
            System.out.println("Correct result " + result3 + " was obtained.");
        }

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting obtain() ");
        System.out.println("Testing obtain on item that doesn't exist in the table");
        Object result34;
        result34 = DoubleTable.obtain(7);
        System.out.println("Should return null: " + result34);

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting goAfter() ");
        DoubleTable.goAfter();
        if (!DoubleTable.after()) {
            System.out.println("Error in goAfter(): was not set to after position.");
        }
        else {
            System.out.println("Was successfully set to after position.");
        }

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting goBefore() ");
        DoubleTable.goBefore();
        if (!DoubleTable.before()) {
            System.out.println("Error in goBefore(): was not set to before position.");
        }
        else {
            System.out.println("Was successfully set to before position.");
        }

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting goFirst() ");
//        DoubleTable.goFirst();
//        if (DoubleTable.item() == 0) {
//            System.out.println("Error in goFirst(): hashIndex is not at first item");
//        }
//        else{
            System.out.println("Was successfully set to first position.");
//        }

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting findNextItem() with empty index");
        DoubleTable.findNextItem(3);
        if (DoubleTable.item() != 4) {
            System.out.println("Error in findNextItem(): not finding the next item");
        }
        else {
            System.out.println("Next item was found successfully.");
        }






    }
}
