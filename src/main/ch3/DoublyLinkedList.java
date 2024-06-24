package src.main.ch3;

public class DoublyLinkedList<E> {

    private Node<E> header; // sentinel
    private Node<E> trailer; // sentinel
    private int size;

    public DoublyLinkedList() {
        this.header = new Node<>(null, null, null);
        this.trailer = new Node<>(null, this.header, null);
        this.header.setNext(this.trailer);
    }

    public int size() {
        return this.size();
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public E first() {
        if (this.isEmpty()) return null;
        return this.header.getNext().getElement();
    }

    public E last() {
        if (this.isEmpty()) return null;
        return this.trailer.getPrev().getElement();
    }

    public void addFirst(E e){
        this.addBetween(e, this.header, this.header.getNext());
    }

    public void addLast(E e){
        this.addBetween(e, this.trailer.getPrev(), this.trailer);
    }

    public E removeFirst() {
        if(this.isEmpty()) return null;
        return this.remove(this.header.getNext());
    }

    public E removeLast() {
        if(this.isEmpty()) return null;
        return this.remove(this.trailer.getPrev());
    }

    private void addBetween(E e, Node<E> predecessor, Node<E> successor){
        Node<E> item = new Node<>(e, predecessor, successor);
        predecessor.setNext(item);
        successor.setPrev(item);
        this.size++;
    }

    private E remove(Node<E> node) {
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        this.size--;
        return node.getElement();
    }

    // nested class
    private static class Node<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;
        
        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        public E getElement(){
            return this.element;
        }

        private Node<E> getPrev(){
            return this.prev;
        }

        public Node<E> getNext() {
            return this.next;
        }

        public void setPrev(Node<E> prev){
            this.prev = prev;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }


    }    
}
