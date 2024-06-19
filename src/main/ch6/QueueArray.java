package src.main.ch6;

// Uses modulo to allow for circular indexing of queue's front and end positions for dequeue and enqueue operations
// O(1) runtime for all operations
// O(N) space
public class QueueArray<E> implements Queue<E> {
    public static final int CAPACITY = 1000;
    private E[] data;
    private int frontIndex;
    private int size;

    public QueueArray(int capacity){
        this.data = (E[]) new Object[capacity];
    }

    public QueueArray() {
        this(CAPACITY);
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public void enqueue(E e) throws IllegalStateException {
        if(this.size == data.length) throw new IllegalStateException("Queue is full");
        // finds the next endIndex in circular fashion
        int endIndex = (this.frontIndex + this.size) % this.data.length;
        this.data[endIndex] = e;
        this.size++;
    }

    public E first(){
        if(this.isEmpty()) return null;
        return this.data[this.frontIndex];
    }

    public E dequeue(){
        if(this.isEmpty()) return null;
        // findex the next frontIndex in circular fashion
        E item = this.data[this.frontIndex];
        this.data[this.frontIndex] = null;
        this.frontIndex = (this.frontIndex + 1) % this.data.length;
        this.size--;
        return item;
    }
}
