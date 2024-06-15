package src.ch3;

import java.util.Arrays;

public class InsertionSort {
    public static void main(String[] args) {
        char[] arr = {'b','c','d','a','e','h','g','f'};
        // Before sort: [b, c, d, a, e, h, g, f]
        System.out.println("Before sort: "+ Arrays.toString(arr));
        insertionSort(arr);
        // After sort: [a, b, c, d, e, f, g, h]
        System.out.println("After sort: "+ Arrays.toString(arr));
    }

    public static void insertionSort(char[] data) {
        int n = data.length;
        for(int k=1; k<n; k++) {
            // start with second element
            char current = data[k];
            int j = k;
            // while not reached first element
            //   and while prev element is bigger than current element
            while(j > 0 && data[j-1] > current) {
                // swap elements, shifting downwards
                data[j] = data[j-1];
                j--;
            }
            data[j] = current;
        }
    }
}
