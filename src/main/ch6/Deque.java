package ch6;

public interface Deque<E> {
    int size();
    boolean isEmpty();
    E first();
    E last();
    void addFirst(E e); // insert at front
    void addLast(E e);  // insert at back
    E removeFirst();
    E removeLast();
}