package ch3;

public class SinglyLinkedList<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    public SinglyLinkedList(){}

    public boolean isEmpty() {
        return this.size == 0;
    }

    public E getFirst(){
        if(this.isEmpty()) return null;
        return this.head.getElement();
    }

    public E getLast(){
        if(this.isEmpty()) return null;
        return this.tail.getElement();
    }

    public E removeFirst(){
        if(this.isEmpty()) return null;
        E item = this.head.getElement();
        this.head = this.head.getNext();
        this.size--;
        if(this.isEmpty()) this.tail = null;
        return item;
    }

    public void addFirst(E e){
        this.head = new Node<>(e, this.head);
        if(this.isEmpty()) this.tail = this.head;
        this.size++;
    }

    public void addLast(E e){
        var newItem = new Node<>(e, null);
        if(this.isEmpty()) this.head = newItem;
        else this.tail.setNext(newItem);
        size++;
    }

    public int size(){
        return this.size;
    }

    // nested class
    private static class Node<E> {
        private E element;
        private Node<E> next;
        
        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }

        public E getElement(){
            return this.element;
        }

        public Node<E> getNext() {
            return this.next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }
}