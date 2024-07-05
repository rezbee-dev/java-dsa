package ch6;

// A better implementation of Stack<E> is provided in
// Pros: Simple and very efficient time-wise, O(1) for all operations
// Cons: relies on fixed-capacity which is space inefficient O(n) and difficult (can't resize)
public class StackArray<E> implements Stack<E> {
    public static final int CAPACITY = 1000;
    private E[] data;
    private int topIndex = -1; // -1 because can't use 0 to indicate empty
    
    public StackArray(int capacity){
        this.data = (E[]) new Object[capacity];
    }

    public StackArray() {
        this(CAPACITY);
    }

    public int size() {
        return this.topIndex++;
    }

    public boolean isEmpty() {
        return this.topIndex == -1;
    }

    public void push(E e) throws IllegalStateException {
        if(this.size() == this.data.length) throw new IllegalStateException("Stack is full");
        this.data[++topIndex] = e;
    }

    public E top() {
        if(this.isEmpty()) return null;
        return this.data[this.topIndex];
    }

    public E pop() {
        if(this.isEmpty()) return null;
        E item = this.data[this.topIndex];
        this.data[this.topIndex] = null;
        this.topIndex--;
        return item;
    }
}
