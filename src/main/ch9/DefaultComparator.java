package ch9;

import java.util.Comparator;

// Relies on natural ordering
// Intended to be used for PriorityQueue keys
public class DefaultComparator<E> implements Comparator<E> {
    @Override
    public int compare(E a, E b) throws ClassCastException {
        return ((Comparable<E>) a).compareTo(b);
    }
}
