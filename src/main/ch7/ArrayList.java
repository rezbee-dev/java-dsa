package ch7;

// For non-dynamic array, All operations are O(1) time complexity except for add & remove which is O(n)
// For dynamic array, runtimes are still the same; see ch7.2 for discussions on amortizations regarding the resizing
// Adding ArrayIterator & Iterator from 7.4

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayList<E> implements List<E>, Iterable<E> {
    // default array capacity
    public static final int CAPACITY = 16;
    private E[] data;
    private int size;

    public ArrayList() {
        this(CAPACITY);
    }

    public ArrayList(int capacity){
        this.data = (E[]) new Object[capacity];
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public E get(int i) throws IndexOutOfBoundsException {
        this.checkIndex(i, this.size);
        return this.data[i];
    }

    public E set(int i, E e) throws IndexOutOfBoundsException {
        this.checkIndex(i, this.size);
        E temp = this.data[i];
        this.data[i] = e;
        return temp;
    }

    // Without dynamic array capabilities
    // public void add(int i, E e) throws IndexOutOfBoundsException, IllegalStateException {
    //     this.checkIndex(i, this.size + 1);
        
    //     if (this.size == this.data.length)
    //         throw new IllegalStateException("Array is full");
        
    //     for(int k=this.size-1; k >= i; k--){
    //         this.data[k+1] = this.data[k]; // shift rightwards
    //     }

    //     this.data[i] = e;
    //     this.size++;
    // }

    // Dynamic array
    // Note that to ensure efficient space usage, array should be shrinked when element are removed
    public void add(int i, E e) throws IndexOutOfBoundsException {
        this.checkIndex(i, this.size + 1);
        
        if (this.size == this.data.length)
            this.resize(2 * this.data.length); // double capacity of the internal array
        
        for(int k=this.size-1; k >= i; k--){
            this.data[k+1] = this.data[k]; // shift rightwards
        }

        this.data[i] = e;
        this.size++;
    }

    public E remove(int i) throws IndexOutOfBoundsException {
        this.checkIndex(i, this.size);
        E temp = this.data[i];
        for(int k=i; k<this.size-1; k++) {
            this.data[k] = this.data[k+1]; // shift leftwards
        }
        this.data[size-1] = null;
        this.size--;
        return temp;
    }

    protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
        if (i < 0 || i >= n) throw new IndexOutOfBoundsException("Illegal index: " + i);
    }

    // Dynamic array - Resizes internal array to have given capacity >= size
    protected void resize(int capacity){
        E[] temp = (E[]) new Object[capacity];
        for(int k=0; k<this.size; k++){
            temp[k] = this.data[k]; // deep copy
        }
        this.data = temp; // set internal array to new resized array
    }

    public Iterator<E> iterator(){
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<E> {
        private int j; // index of the next element to report
        private boolean removable = false; // denotes whether remove() can be called at this time

        public boolean hasNext() {
            return this.j < size; // size field in outer class
        }

        public E next() throws NoSuchElementException {
            if(this.j == size) throw new NoSuchElementException("No next element");
            this.removable = true;
            // data field of outer class
            // post increment j so its ready for future next call
            return data[this.j++]; 
        }

        // removes the element returned by most recent call to next
        public void remove() throws IllegalStateException {
            if(!this.removable) throw new IllegalStateException("nothing to remove");
            ArrayList.this.remove(j-1); // remove the last element that was returned from next()
            this.j--; // decrement because removal will shift leftwards
            this.removable = false; // do not allow remove again until next is called 
        }
    }
}
