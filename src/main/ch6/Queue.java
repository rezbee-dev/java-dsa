package ch6;

public interface Queue<E> {
    int size();
    boolean isEmpty();
    // insert element at end of queue
    void enqueue(E e);
    E first();
    // remove and return eelemnt at front of queue
    E dequeue();
}
