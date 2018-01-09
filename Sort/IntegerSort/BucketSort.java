package sort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class BucketSort {

	int bucketSize = 10;
    int arraySize = 10000;

    public static void main(String[] args) {
        BucketSort bs = new BucketSort();
        int[] array = bs.getArray();
        bs.bucketSort(array);
    }

    public int[] getArray() {
        int[] arr = new int[arraySize/3];
        Random r = new Random();
        for (int i = 0; i < arraySize/3; i++) {
            arr[i] = r.nextInt(100000);
        }
        return arr;
    }

    public void bucketSort(int[] a) {
        List<Integer> bucket[] = new ArrayList[bucketSize];
        for (int i = 0; i < a.length; i++) {
            int temp = a[i] / 10000;
            if (bucket[temp] == null) {
                bucket[temp] = new ArrayList<Integer>();
            }
            bucket[temp].add(a[i]);
        }
        for (int j = 0; j < bucketSize; j++) {
            insertSort(bucket[j]);
            printList(bucket[j]);
        }
    }

    public void printList(List<Integer> list) {
        while (list.size() > 0) {
            System.out.print(list.remove(0) + "\t");
        }
    }

    public void insertSort(List<Integer> list) {
        Collections.sort(list);
    }

}
