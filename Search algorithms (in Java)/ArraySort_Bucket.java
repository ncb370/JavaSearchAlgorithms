// Nicole Barrington
// 11082099
// NCB370
// CMPT 280



import lib280.list.LinkedList280;

/**
 * A class that performs a bucket sort. Note: this uses ArraySort_Heap class, specifically the heapSort method.
 */
public class ArraySort_Bucket {

    // PROPERTIES
    protected int NumBuckets = 0;


    /**
     * A method that performs a bucket sort on an array with duplicates or an array with no duplicates
     * @param numBuckets the number of buckets for items to be inserted to
     * @param unsortedArray an unsorted array
     * @param minVal the minimum value of items in the array
     * @param maxVal the maximum value of items in the array
     * @return a sorted array
     */
    public int[] bucketSort(int numBuckets, int[] unsortedArray, int minVal, int maxVal) {

        /*
        Properties
         */
        NumBuckets = numBuckets;

        /*
        Creating a linked list of buckets
         */
        LinkedList280[] bucket = new LinkedList280[numBuckets];

        /*
        Creating the key
         */
        int k;

        /*
        Some print statements to show the different input values
         */
        System.out.println("This is the maxVal: " + maxVal);

        System.out.println("This is the minVal: " + minVal);

        System.out.println("This is the number of buckets: " + numBuckets);

        System.out.println("This is the number of items to be sorted: " + unsortedArray.length);

        // Bucket size
        int bucketSize = (int)Math.ceil((double)(maxVal-minVal+1) / numBuckets);

        /*
        A loop that creates keys for each item in the unsorted array
         */
        for (int i = 0; i < unsortedArray.length; i++) {


            // original bucket key
            k = (int)Math.floor((double)((unsortedArray[i]-minVal)/bucketSize));


            /*
            UNCOMMENT THIS TO SEE EACH BUCKET ITEM
             */
//            System.out.println( "item " + i + ": "+ unsortedArray[i]);
//            System.out.println("This is the bucket for item " + i + ": " + k +"\n");

            // create a bucket for each item if it doesn't exist
            if (bucket[k] == null) {
                bucket[k] = new LinkedList280();
//                System.out.println("Bucket created");    // just a validation statement
            }

            // insert each item into the bucket
            bucket[k].insert(unsortedArray[i]);


        }


        /*
        Output to show buckets with items in them
         */
        System.out.println("These are the buckets now: ");
        for (int i = 0; i < bucket.length; i++) {
            System.out.println("Bucket " + i + ": " + bucket[i]);
        }


        // new array to return
        int[] sortedArray = new int[unsortedArray.length];

        // a counter for sorting purposes
        int sortedCounter = 0;

        /*
        A loop that sorts the buckets and puts them into the new sorted array
        Yay, stability.
         */
        for (int v = 0; v < bucket.length; v++) {

            if (bucket[v] != null) {

                // counter to count num items in the bucket
                int counter = 0;

                // counter for length of bucket
                while (!bucket[v].after()) {
                    bucket[v].goForth();
                    counter ++;
                }

                // make a bucketArray for each item in the original linked list
                // this is fugly
                int[] bucketArray = new int[counter - 1];

                // add items to the array
                bucket[v].goFirst();
                for (int j = 0; j < counter - 1; j++) {
                    bucketArray[j] = (int)bucket[v].item();
                    bucket[v].goForth();
                }

                // sort each array
                for (int y = 0; y < counter-1; y++) {
                    ArraySort_Heap newHeap = new ArraySort_Heap();
                   newHeap.heapSort(bucketArray);
                }


                /*
                Concatenating and such
                 */
//                System.out.println("\nThese are the buckets after sorting");
                // print out new bucket
                for (int o = 0; o < bucketArray.length; o++) {
//                    System.out.print(bucketArray[o] + ", ");
                    sortedArray[sortedCounter] = bucketArray[o];
                    sortedCounter ++;

                }

            }


        }


        // Just some code for testing the sorted array
//        System.out.println("\nHere is the sorted array: ");
//        for (int jj = 0; jj < sortedArray.length; jj ++) {
//            System.out.print(sortedArray[jj]+ ", ");
//        }


        return sortedArray;


    }

    /**
     * A method to create an array with no duplicates
     * @param numItems the number of items allowed in the ND array
     * @param maxValue the maximum value of the ND array
     * @return
     */
    public int[] makeArrayND(int numItems, int maxValue) {

        /*
        Create a new array of integer items with no duplicates
         */
        int[] arrND = new int[numItems];

        /*
        Make an array of items from 1 to max value
         */
        for (int i = 0; i < arrND.length; i++) {
            int rand = (int) (Math.random() * (maxValue) + 1);
            arrND[i] = rand;

            for (int a = 0; a < i; a++) {
                if (arrND[i] == arrND[a]) {
                    i--;
                }
            }
        }

        return arrND;

    }

    /**
     * A method to create an array with duplicates
     * @param numItems the number of items for the array
     * @param maxValue the max value of the number of items
     * @return
     */
    public int[] makeArrayD(int numItems, int maxValue) {
        /*
        Make an array of integers items with duplicates
         */
        int[] arrD = new int[numItems];

        /*
        Fill array with values from 1 to max value
         */
        for (int v = 0; v < arrD.length; v++) {
            arrD[v] = (int) (Math.random() * (maxValue) + 1);
            System.out.print(arrD[v] + ", ");
        }

        return arrD;
    }

    /**
     * A static method to print an Array
     * @param anArray an array to be sorted
     */
    public static void printArray(int[] anArray) {
        for (int i = 0; i < anArray.length; i ++) {
            System.out.print(anArray[i] + ", ");
        }
        System.out.println("\n");
    }


    /**
     * A main method for testing purposes.
     */
    public static void main(String[] args) {

        ///////////////////////////////////////////////////////////////////////
        // TEST 1 /////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////

        System.out.println("\n|------------------------------------------------------------------------------" +
                "------------------------------------------------------------|\n");
        System.out.println("\nTEST 1: ARRAY OF 25 ITEMS NO DUPLICATES, " +
                "VALUE RANGE 1-1000, " +
                "NUM OF BUCKETS 25");

        // variables
        int numItems = 1000;
        int maxVal = 1000;
        int minVal = 1;
        int numBuckets = 25;

        // TEST 1 OBJECT
       ArraySort_Bucket test1 = new ArraySort_Bucket();

       // TEST 1 ARRAY
       int[] newArr1;
       newArr1 = test1.makeArrayND(numItems, maxVal);

        System.out.println("Here are the buckets before sorting: ");
        printArray(newArr1);

        // TEST 1 RETURNED ARRAY
       int []returnedArr1;
       returnedArr1 = test1.bucketSort(numBuckets, newArr1, minVal, maxVal);

        System.out.println("\nHere are the sorted returned buckets: ");
        printArray(returnedArr1);


        ///////////////////////////////////////////////////////////////////////
        // TEST 2 /////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////

        System.out.println("\n|------------------------------------------------------------------------------" +
                "------------------------------------------------------------|\n");
        System.out.println("\nTEST 2: ARRAY OF 100 ITEMS NO DUPLICATES, " +
                "VALUE RANGE 1-100, " +
                "NUM OF BUCKETS 100");

        // variables
        int numItems2 = 100;
        int maxVal2 = 100;
        int minVal2 = 1;
        int numBuckets2 = 100;

        // TEST 2 OBJECT
        ArraySort_Bucket test2 = new ArraySort_Bucket();

        // TEST 2 ARRAY
        int[] newArr2;
        newArr2 = test2.makeArrayND(numItems2, maxVal2);

        System.out.println("Here are the buckets before sorting: ");
        printArray(newArr2);

        // TEST 2 RETURNED ARRAY
        int []returnedArr2;
        returnedArr2 = test2.bucketSort(numBuckets2, newArr2, minVal2, maxVal2);

        System.out.println("\nHere are the sorted returned buckets: ");
        printArray(returnedArr2);

        ///////////////////////////////////////////////////////////////////////
        // TEST 3 /////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////

        System.out.println("\n|------------------------------------------------------------------------------" +
                "------------------------------------------------------------|\n");
        System.out.println("\nTEST 3: ARRAY OF 1500 ITEMS NO DUPLICATES, " +
                "VALUE RANGE 1-2000, " +
                "NUM OF BUCKETS 2000");

        // variables
        int numItems3 = 1500;
        int maxVal3 = 2000;
        int minVal3 = 1;
        int numBuckets3 = 4000;

        // TEST 3 OBJECT
        ArraySort_Bucket test3 = new ArraySort_Bucket();

        // TEST 3 ARRAY
        int[] newArr3;
        newArr3 = test3.makeArrayND(numItems3, maxVal3);

        System.out.println("Here are the buckets before sorting: ");
        printArray(newArr3);

        // TEST 3 RETURNED ARRAY
        int []returnedArr3;
        returnedArr3 = test3.bucketSort(numBuckets3, newArr3, minVal3, maxVal3);

        System.out.println("\nHere are the sorted returned buckets: ");
        printArray(returnedArr3);

        ///////////////////////////////////////////////////////////////////////
        // TEST 4 /////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////

        System.out.println("\n|------------------------------------------------------------------------------" +
                "------------------------------------------------------------|\n");
        System.out.println("\nTEST 4: ARRAY OF 50 ITEMS NO DUPLICATES, " +
                "VALUE RANGE 1-100, " +
                "NUM OF BUCKETS 100");

        // variables
        int numItems4 = 50;
        int maxVal4 = 100;
        int minVal4 = 1;
        int numBuckets4 = 100;

        // TEST 4 OBJECT
        ArraySort_Bucket test4 = new ArraySort_Bucket();

        // TEST 4 ARRAY
        int[] newArr4;
        newArr4 = test4.makeArrayND(numItems4, maxVal4);

        System.out.println("Here are the buckets before sorting: ");
        printArray(newArr4);

        // TEST 4 RETURNED ARRAY
        int []returnedArr4;
        returnedArr4 = test4.bucketSort(numBuckets4, newArr4, minVal4, maxVal4);

        System.out.println("\nHere are the sorted returned buckets: ");
        printArray(returnedArr4);

        ///////////////////////////////////////////////////////////////////////
        // TEST 5 /////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////

        System.out.println("\n|------------------------------------------------------------------------------" +
                "------------------------------------------------------------|\n");
        System.out.println("\nTEST 5: ARRAY OF 100 ITEMS, HAS DUPLICATES, " +
                "VALUE RANGE 1-100, " +
                "NUM OF BUCKETS 100");

        // variables
        int numItems5 = 100;
        int maxVal5 = 100;
        int minVal5 = 1;
        int numBuckets5 = 100;

        // TEST 5 OBJECT
        ArraySort_Bucket test5 = new ArraySort_Bucket();

        // TEST 5 ARRAY
        int[] newArr5;
        newArr5 = test5.makeArrayD(numItems5, maxVal5);

        System.out.println("Here are the buckets before sorting: ");
        printArray(newArr5);

        // TEST 5 RETURNED ARRAY
        int []returnedArr5;
        returnedArr5 = test5.bucketSort(numBuckets5, newArr5, minVal5, maxVal5);

        System.out.println("\nHere are the sorted returned buckets: ");
        printArray(returnedArr5);

        ///////////////////////////////////////////////////////////////////////
        // TEST 6 /////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////

        System.out.println("\n|------------------------------------------------------------------------------" +
                "------------------------------------------------------------|\n");
        System.out.println("\nTEST 6: ARRAY OF 1000 ITEMS, HAS DUPLICATES, " +
                "VALUE RANGE 1-100, " +
                "NUM OF BUCKETS 25");

        // variables
        int numItems6 = 1000;
        int maxVal6 = 100;
        int minVal6 = 1;
        int numBuckets6 = 25;

        // TEST 6 OBJECT
        ArraySort_Bucket test6 = new ArraySort_Bucket();

        // TEST 6 ARRAY
        int[] newArr6;
        newArr6 = test6.makeArrayD(numItems6, maxVal6);

        System.out.println("Here are the buckets before sorting: ");
        printArray(newArr6);

        // TEST 6 RETURNED ARRAY
        int []returnedArr6;
        returnedArr6 = test6.bucketSort(numBuckets6, newArr6, minVal6, maxVal6);

        System.out.println("\nHere are the sorted returned buckets: ");
        printArray(returnedArr6);

        ///////////////////////////////////////////////////////////////////////
        // TEST 7 /////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////

        System.out.println("\n|------------------------------------------------------------------------------" +
                "------------------------------------------------------------|\n");
        System.out.println("\nTEST 7: ARRAY OF 2000 ITEMS, HAS DUPLICATES, " +
                "VALUE RANGE 1-100, " +
                "NUM OF BUCKETS 1000");

        // variables
        int numItems7 = 2000;
        int maxVal7 = 100;
        int minVal7 = 1;
        int numBuckets7 = 1000;

        // TEST 7 OBJECT
        ArraySort_Bucket test7 = new ArraySort_Bucket();

        // TEST 7 ARRAY
        int[] newArr7;
        newArr7 = test7.makeArrayD(numItems7, maxVal7);

        System.out.println("Here are the buckets before sorting: ");
        printArray(newArr7);

        // TEST 7 RETURNED ARRAY
        int []returnedArr7;
        returnedArr7 = test7.bucketSort(numBuckets7, newArr7, minVal7, maxVal7);

        System.out.println("\nHere are the sorted returned buckets: ");
        printArray(returnedArr7);


        ///////////////////////////////////////////////////////////////////////
        // TEST 8 /////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////

        System.out.println("\n|------------------------------------------------------------------------------" +
                "------------------------------------------------------------|\n");
        System.out.println("\nTEST 8: ARRAY OF 10 ITEMS, HAS DUPLICATES, " +
                "VALUE RANGE 1-1000, " +
                "NUM OF BUCKETS 10");

        // variables
        int numItems8 = 10;
        int maxVal8 = 1000;
        int minVal8 = 1;
        int numBuckets8 = 10;

        // TEST 8 OBJECT
        ArraySort_Bucket test8 = new ArraySort_Bucket();

        // TEST 8 ARRAY
        int[] newArr8;
        newArr8 = test8.makeArrayD(numItems8, maxVal8);

        System.out.println("Here are the buckets before sorting: ");
        printArray(newArr8);

        // TEST 8 RETURNED ARRAY
        int []returnedArr8;
        returnedArr8 = test8.bucketSort(numBuckets8, newArr8, minVal8, maxVal8);

        System.out.println("\nHere are the sorted returned buckets: ");
        printArray(returnedArr8);

        ///////////////////////////////////////////////////////////////////////
        // TEST 9 /////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////

        System.out.println("\n|------------------------------------------------------------------------------" +
                "------------------------------------------------------------|\n");
        System.out.println("\nTEST 9 IT'S OVER 9000: ARRAY OF 9001 ITEMS, HAS DUPLICATES, " +
                "VALUE RANGE 1-9001, " +
                "NUM OF BUCKETS 9001");

        // variables
        int numItems9 = 9001;
        int maxVal9 = 9001;
        int minVal9 = 1;
        int numBuckets9 = 9001;

        // TEST 9 OBJECT
        ArraySort_Bucket test9 = new ArraySort_Bucket();

        // TEST 9 ARRAY
        int[] newArr9;
        newArr9 = test9.makeArrayD(numItems9, maxVal9);

        System.out.println("Here are the buckets before sorting: ");
        printArray(newArr9);

        // TEST 9 RETURNED ARRAY
        int []returnedArr9;
        returnedArr9 = test9.bucketSort(numBuckets9, newArr9, minVal9, maxVal9);

        System.out.println("\nHere are the sorted returned buckets: ");
        printArray(returnedArr9);
    }

}




