// Nicole Barrington
// 11082099
// NCB370
// CMPT 280




import lib280.base.CursorPosition280;
import lib280.dictionary.HashTable280;
import lib280.exception.*;

import javax.sound.sampled.Line;

/**
 * A linear hashing function.
 * The linear probe should icrememnt the index in the hash table by p(j) = j
 */
public class LinearHashTable280<I> extends ProbeHashTable<I> {

    // obtain item's key

    /**
     * A constructor for LinearHashTable280
     * @param newSize the size of the hash table.
     */
    public LinearHashTable280(int newSize) {
        super(newSize);
    }

    /**
     * A probing method to determine where an item can be inserted into the hash table,
     * using the linear hashing formula.
     * @param index the initial index
     * @return the index where the item can be inserted.
     */
    @Override
    public int probe(int index, I item) {

        // keep track of the original index
        int originalIndex = index;

//        System.out.println("The original index for the item to be inserted: " + index);


        try {
            // easiest case; where the position in the array has no item and the index can be returned as is
            if (hashArray[index] == null) {
//                System.out.println("The index was empty, so the item was inserted without any further probing.");
                return index;
            }

            // while the hashArray[index] is not null and the index is not at capacity, increment the index
            while (hashArray[index] != null && index < capacity() - 1) {
//                System.out.println("Rats, gotta find a new place to put this item.");
                index++;
            }

            if (index == capacity() - 1 && hashArray[index] == null) {
                return index;
            }

            // if the index is at the capacity, loop around to the front
            if (index == capacity() - 1 && hashArray[index] != null) {
//                System.out.println("Gotta loop around to the front to find a place for insertion.");

                index = 0;

                // while the index is not the original index and the hash array position is not null,
                // continue to increment index.
                while (index < originalIndex && hashArray[index] != null) {
//                    System.out.println("Not quite there, yet.");
                    index++;
                }

                // if the hashArray[index] is not null, then throw exception
                if (hashArray[index] != null) {
                    throw new ContainerFull280Exception("The array is full, item wasn't inserted.");
                }

            }


            // if a position is found, then return it

        }
        catch (Exception e) {
            System.out.println("Container is full!");
        }
        return index;
    }


    /**
     * A main method to test the linear hash table
     */
    public static void main(String[] args) {

        // create an instance of LinearHashTable280 that contains doubles
        LinearHashTable280<Double> LinearTable = new LinearHashTable280<>(40);


        // Test isFull on an empty table
        boolean result;
        result = LinearTable.isFull();
        if (result!=false) {
            System.out.println("Error in isFull(): should've returned false.");
        }
        // test insert on 20 items into a Quadratic table with 100 items
        System.out.println(LinearTable);


        // insert each item into their hash table
        System.out.println("----------------------------------------------------------------");

        // Insertion with items that don't have to probe
        System.out.println("\nInserting first item: ");
        System.out.println("     Inserting 2.0");
        LinearTable.insert(2.0);
        if (!LinearTable.toString().equals("\n24: 2.0")) {
            System.out.println("Error in probe(): Item was not successfully inserted.\n");
        }

        System.out.println("Linear should have 1 item: " + LinearTable);
        System.out.println("----------------------------------------------------------------");

        // Insertion with items
        System.out.println("\nInserting items that do not require linear probing: ");
        for (int i = 3; i < 5; i++) {
            System.out.println("     Inserting " + i);
            LinearTable.insert((double)i);
        }
        if (!LinearTable.toString().equals("\n0: 4.0\n24: 2.0\n32: 3.0")){
            System.out.println("Error in probe(): items were not successfully inserted.\n");
        }


        // Insertion with items that require one or two probes
        System.out.println("Linear table should have 3 items: " +LinearTable);
        System.out.println("----------------------------------------------------------------");

        System.out.println("\nInserting items that require at least 1 probe: ");
        for (int i = 10; i < 15; i++) {
            System.out.println("     Inserting " + i);
            LinearTable.insert((double)i);
        }
        if (!LinearTable.toString().equals("\n0: 4.0\n1: 10.0\n8: 14.0\n16: 13.0" +
                "\n24: 2.0\n25: 12.0\n32: 3.0\n33: 11.0")){
            System.out.println("Error in probe(): items were not successfully probed.\n");
        }

        // Insertion with items that require one or two probes
        System.out.println("Linear table should have 8 items: " + LinearTable);
        System.out.println("----------------------------------------------------------------");

        System.out.println("\nInserting items that require 2 or more probes: ");
        for (int i = 15; i < 22; i++) {
            System.out.println("     Inserting " + i);
            LinearTable.insert((double)i);
        }

        if (!LinearTable.toString().equals("\n0: 4.0\n1: 10.0\n2: 15.0\n3: 19.0\n8: 14.0\n" +
                "9: 17.0\n16: 13.0\n17: 20.0\n24: 2.0\n25: 12.0\n26: 18.0\n32: 3.0\n" +
                "33: 11.0\n34: 16.0\n35: 21.0")) {
            System.out.println("Error in probe(): items were not successfully probed.\n");
        }

        System.out.println("Linear table should have 15 items: "+ LinearTable);
        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTest to make sure duplicate items cannot be submitted: ");

        // test to make sure duplicate items cannot be inserted
        LinearTable.insert(5.0);
        if (LinearTable.frequency(5.0) > 1) {
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
        LinearTable.delete(15.0);
        if (LinearTable.has(15.0)) {
            System.out.println("Error in delete(): item not deleted");
        }
        else {
            System.out.println("And it was deleted successfully: " + LinearTable);
        }

        System.out.println("----------------------------------------------------------------");

        // Try to delete an item that doesn't exist
        System.out.println("\nAttempt to delete item that doesn't exist: ");
        LinearTable.delete(77777.0);
        System.out.println("Test was ran successfully and exceptions were caught.");

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting search()");

        System.out.println("Searching for 21 in table: ");
        LinearTable.search(21.0);
        if (LinearTable.item() != 21.0) {
            System.out.println("Error in search(): cursor not on correct item.");
        }
        System.out.println("Cursor is currently on this item: "+ LinearTable.item());

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting search()");
        System.out.println("Searching for an item that doesn't exist in table");
        LinearTable.search(4444.0);
        System.out.println("Test was ran successfully and exceptions were caught.");


        System.out.println("----------------------------------------------------------------");

        // Test for frequency
        System.out.println("\nTesting frequency()");

        System.out.println("Testing frequency on item that doesn't exist in the table");
        int result1;
        result1 = LinearTable.frequency(1231.0);
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
        result2 = LinearTable.frequency(3.0);
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
        result3 = LinearTable.obtain(11.0);
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
        result34 = LinearTable.obtain(7.0);
        System.out.println("Should return null: " + result34);

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting goAfter() ");
        LinearTable.goAfter();
        if (!LinearTable.after()) {
            System.out.println("Error in goAfter(): was not set to after position.");
        }
        else {
            System.out.println("Was successfully set to after position.");
        }

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting goBefore() ");
        LinearTable.goBefore();
        if (!LinearTable.before()) {
            System.out.println("Error in goBefore(): was not set to before position.");
        }
        else {
            System.out.println("Was successfully set to before position.");
        }

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting goFirst() ");
        LinearTable.goFirst();
        if (LinearTable.item() != 4.0) {
            System.out.println("Error in goFirst(): hashIndex is not at first item");
        }
        else{
            System.out.println("Was successfully set to first position.");
        }

        System.out.println("----------------------------------------------------------------");

        System.out.println("\nTesting findNextItem() with empty index");
        LinearTable.findNextItem(3);
        if (LinearTable.item() != 19.0) {
            System.out.println("Error in findNextItem(): not finding the next item");
        }
        else {
            System.out.println("Next item was found successfully.");
        }



    }
}
