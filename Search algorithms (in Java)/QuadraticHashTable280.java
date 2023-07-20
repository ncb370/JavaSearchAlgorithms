// Nicole Barrington
// 11082099
// NCB370
// CMPT 280

import lib280.base.CursorPosition280;
import lib280.dictionary.HashTable280;
import lib280.exception.*;

/**
 * A quadratic hash table.
 * The quadratic probe should increment the index using the formula p(j) = (-1)^(i-1) * ((i+1)/2)^2
 */
public class QuadraticHashTable280<I> extends ProbeHashTable<I> {

    /**
     * A constructor for QuadraticHashTable280
     * @param newSize the size of the hash table
     */
    public QuadraticHashTable280(int newSize) {
        super(newSize);
    }

    /**
     * A probing method to determine where an item can be inserted into the hash table,
     * using the quadratic hashing formula.
     * @param index the initial index
     * @return the index where the item can be inserted.
     */
    public int probe(int index, I item) {


        // original index to be probed
        int originalIndex = index;

        // offset
        int offset;


        try {
//            System.out.println("The original index for the item to be inserted: " + index);


            // easiest case; where the position in the array has no item and the index can be returned as is
            if (hashArray[index] == null) {
//                System.out.println("The index was empty, so the item was inserted without any further probing.");
                return index;
            }

            // loop through the array until you find a position. The quadratic formula is based on
            // iterations, so use a for-loop
            // the starting point in the array is the index
            for (int i = 0; i < hashArray.length; i++) {


                // if the hash position is null, insert the thing
                if (hashArray[index] == null) {
//                    System.out.println("Position was found: " + index);
                    return index;
                }

                // if the index position isn't at the end of the list,
                // but the index isn't full, increment the index
                if (hashArray[index] != null) {

//                    System.out.println("Position " + index + " was full.");

                    // calculate the offset

                    offset = (int)Math.pow(-1, (i-1)) * (int)Math.pow((i+1)/2, 2);

//                    System.out.println("Offset is " + offset + " and the index is " + index);

                    index = (originalIndex + offset) % capacity();


                    if (index < 0){
                        index += capacity();
                    }

                }
            }

        }
        catch (Exception e) {
            System.out.println("Container is full!");
        }

        return index;
    }

    /**
     * A main method to test the linear hash table
     * @param args
     */
    public static void main(String[] args) {

        // create an instance of QuadraticHashTable280
        QuadraticHashTable280<Double> QuadraticTable = new QuadraticHashTable280<>(40);

        // Test isFull on an empty table
        boolean result;
        result = QuadraticTable.isFull();
        if (result!=false) {
            System.out.println("Error in isFull(): should've returned false.");
        }
        // test insert on 20 items into a Quadratic table with 100 items
        System.out.println(QuadraticTable);


        // insert each item into their hash table
        System.out.println("----------------------------------------------------------------");

        // Insertion with items that don't have to probe
        System.out.println("\nInserting first item, does not require quadratic probing: ");
        System.out.println("     Inserting 2.0");
        QuadraticTable.insert(2.0);
        if (!QuadraticTable.toString().equals("\n24: 2.0")) {
            System.out.println("Error in probe(): Item was not successfully inserted.\n");
        }

        System.out.println("QuadraticTable should have 1 item: " + QuadraticTable);
        System.out.println("----------------------------------------------------------------");

        // Insertion with items
        System.out.println("\nInserting items that do not require quadratic probing: ");
        for (int i = 3; i < 5; i++) {
            System.out.println("     Inserting " + i);
            QuadraticTable.insert((double)i);
        }
        if (!QuadraticTable.toString().equals("\n0: 4.0\n24: 2.0\n32: 3.0")){
            System.out.println("Error in probe(): items were not successfully inserted.\n");
        }


        // Insertion with items that require one or two probes
        System.out.println("Quadratic table should have 3 items: " +QuadraticTable);
        System.out.println("----------------------------------------------------------------");

        System.out.println("\nInserting items that require at least 1 probe: ");
        for (int i = 10; i < 15; i++) {
            System.out.println("     Inserting " + i);
            QuadraticTable.insert((double)i);
        }
        if (!QuadraticTable.toString().equals("\n0: 4.0\n1: 10.0\n8: 14.0\n16: 13.0" +
                "\n24: 2.0\n25: 12.0\n32: 3.0\n33: 11.0")){
            System.out.println("Error in probe(): items were not successfully probed.\n");
        }

        // Insertion with items that require one or two probes
        System.out.println("Quadratic table should have 8 items: " + QuadraticTable);
        System.out.println("----------------------------------------------------------------");

        System.out.println("\nInserting items that require 2 or more probes: ");
        for (int i = 15; i < 22; i++) {
            System.out.println("     Inserting " + i);
            QuadraticTable.insert((double)i);
        }

        if (!QuadraticTable.toString().equals("\n0: 4.0\n1: 10.0\n4: 19.0\n8: 14.0\n9: 17.0" +
                "\n16: 13.0\n17: 20.0\n23: 18.0\n24: 2.0\n25: 12.0\n31: 16.0" +
                "\n32: 3.0\n33: 11.0\n36: 21.0\n39: 15.0")) {
            System.out.println("Error in probe(): items were not successfully probed.\n");
        }

        System.out.println("Quadratic table should have 15 items: "+ QuadraticTable);
        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTest to make sure duplicate items cannot be submitted: ");

        // test to make sure duplicate items cannot be inserted
        QuadraticTable.insert(5.0);
        if (QuadraticTable.frequency(5.0) > 1) {
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
        QuadraticTable.delete(15.0);
        if (QuadraticTable.has(15.0)) {
            System.out.println("Error in delete(): item not deleted");
        }
        else {
            System.out.println("And it was deleted successfully: " + QuadraticTable);
        }

        System.out.println("----------------------------------------------------------------");

        // Try to delete an item that doesn't exist
        System.out.println("\nAttempt to delete item that doesn't exist: ");
        QuadraticTable.delete(77777.0);
        System.out.println("Test was ran successfully and exceptions were caught.");

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting search()");

        System.out.println("Searching for 21 in table: ");
        QuadraticTable.search(21.0);
        if (QuadraticTable.item() != 21.0) {
            System.out.println("Error in search(): cursor not on correct item.");
        }
        System.out.println("Cursor is currently on this item: "+ QuadraticTable.item());

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting search()");
        System.out.println("Searching for an item that doesn't exist in table");
        QuadraticTable.search(4444.0);
        System.out.println("Test was ran successfully and exceptions were caught.");


        System.out.println("----------------------------------------------------------------");

        // Test for frequency
        System.out.println("\nTesting frequency()");

        System.out.println("Testing frequency on item that doesn't exist in the table");
        int result1;
        result1 = QuadraticTable.frequency(1231.0);
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
        result2 = QuadraticTable.frequency(3.0);
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
        result3 = QuadraticTable.obtain(11.0);
        if (!result3.equals(11.0)) {
            System.out.println("Error in obtain: expected item '11.0' and " + result3 + "was returned.");
        }
        else {
            System.out.println("Correct result " + result3 + " was obtained.");
        }

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting obtain() ");
        System.out.println("Testing obtain on item that doesn't exist in the table");
        Object result34;
        result34 = QuadraticTable.obtain(7.0);
        System.out.println("Should return null: " + result34);

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting goAfter() ");
        QuadraticTable.goAfter();
        if (!QuadraticTable.after()) {
            System.out.println("Error in goAfter(): was not set to after position.");
        }
        else {
            System.out.println("Was successfully set to after position.");
        }

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting goBefore() ");
        QuadraticTable.goBefore();
        if (!QuadraticTable.before()) {
            System.out.println("Error in goBefore(): was not set to before position.");
        }
        else {
            System.out.println("Was successfully set to before position.");
        }

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting goFirst() ");
        QuadraticTable.goFirst();
        if (QuadraticTable.item() != 4.0) {
            System.out.println("Error in goFirst(): hashIndex is not at first item");
        }
        else{
            System.out.println("Was successfully set to first position.");
        }

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting findNextItem() with empty index");
        QuadraticTable.findNextItem(3);
        if (QuadraticTable.item() != 19.0) {
            System.out.println("Error in findNextItem(): not finding the next item");
        }
        else {
            System.out.println("Next item was found successfully.");
        }






    }


}
