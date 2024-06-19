package src.main.ch6;

import src.main.ch3.SinglyLinkedList;

// O(1) runtime
public class QueueSinglyLinked<E> implements Queue<E> {
    private SinglyLinkedList<E> list = new SinglyLinkedList<>();
    public QueueSinglyLinked() {}

    public int size() {
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public void enqueue(E element){
        this.list.addLast(element);
    }

    public E first() {
        return this.list.getFirst();
    }

    public E dequeue() {
        return this.list.removeFirst();
    }
    
}
