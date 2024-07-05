package ch6;

import ch3.SinglyLinkedList;

public class StackSinglyLinked<E> implements Stack<E>{
    // Using existing class as hidden field rather 
    //  than modifying it while retaining some of the original methods is
    //  known as Adapater Pattern
    private SinglyLinkedList<E> list = new SinglyLinkedList<>();
    public StackSinglyLinked() {}
    
    public int size(){
        return this.list.size();
    }

    public boolean isEmpty() {
        return this.list.isEmpty();
    }

    public void push(E element){
        this.list.addFirst(element);
    }

    public E top() {
        return this.list.getFirst();
    }

    public E pop() {
        return this.list.removeFirst();
    }

}
