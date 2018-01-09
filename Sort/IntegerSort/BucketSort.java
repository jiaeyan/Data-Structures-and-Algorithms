package sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/*
 * Stable.
 * 
 * Complexity: O(n)
 */

public class BucketSort {

	int bucketSize = 10;
    int arraySize = 10000;
    
    // Randomly generate an array to be sorted.
    public int[] getArray() {
        int[] arr = new int[arraySize/3];
        Random r = new Random();
        for (int i = 0; i < arraySize/3; i++) {
            arr[i] = r.nextInt(100000);
        }
        return arr;
    }
    
    public void bucketSort(int[] a) {
    	    // Step 1: create buckets.
        List<Integer> bucket[] = new ArrayList[bucketSize];
        
        // Step 2: put elements of array to relative buckets
        //         according to some hash function.
        for (int i = 0; i < a.length; i++) {
            int temp = a[i] / 10000;
            if (bucket[temp] == null) {
                bucket[temp] = new ArrayList<Integer>();
            }
            bucket[temp].add(a[i]);
        }
        
        // Step 3: sort each bucket and print the result.
        for (int j = 0; j < bucketSize; j++) {
            insertSort(bucket[j]);
            printList(bucket[j]);
        }
    }

    public void printList(List<Integer> list) {
        while (list.size() > 0) {
            System.out.println(list.remove(0));
        }
    }

    public void insertSort(List<Integer> list) {
        Collections.sort(list);
    }
    
    public static void main(String[] args) {
        BucketSort bs = new BucketSort();
        int[] array = bs.getArray();
        bs.bucketSort(array);
    }

}
