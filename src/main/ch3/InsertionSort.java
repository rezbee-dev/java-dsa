package src.main.ch3;

import java.util.Arrays;

import src.main.ch7.PositionalLinkedList;
import src.main.ch7.Position;


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

    // added from ch7
    public static void insertSort(PositionalLinkedList<Integer> list){
    // last position known to be sorted
    Position<Integer> marker = list.first();

        while(marker != list.last()){
            Position<Integer> pivot = list.after(marker);
            // element to be sorted
            int value = pivot.getElement();
            if (value > marker.getElement())
                marker = pivot;
            else {
            // to find leftmost item greater than value
            Position<Integer> walk = marker;
            while (walk != list.first() && list.before(walk).getElement() > value){
                walk = list.before(walk);
            }
            list.remove(pivot);
            list.addBefore(walk, value);
            }
        }
    }
}
