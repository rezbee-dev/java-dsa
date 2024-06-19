package src.main.ch6;

public interface Stack<E> {
    int size();
    boolean isEmpty();
    void push(E e); // inserts element at top of stack
    E top(); // views element at top
    E pop(); // remove element at top
}