// Uses DoublyLinkedList implementation (sort of)
// All operations are O(1) runtime, O(n) spacetime

import java.util.Iterator;
import java.util.NoSuchElementException;

public class PositionalLinkedList<E> implements PositionalList<E>, Iterable<E> {

    private Node<E> header;
    private Node<E> trailer;
    private int size;

    public PositionalLinkedList(){
        this.header = new Node<>(null, null, null);
        this.trailer = new Node<>(null, this.header, null);
        this.header.setNext(this.trailer);
    }

    // utility method for validating the position and returns it as a node
    private Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if(!(p instanceof Node)) throw new IllegalArgumentException("Invalid position");
        Node<E> node = (Node<E>) p;
        // convention for defunct node
        if(node.getNext() == null) throw new IllegalArgumentException("Position is no longer in the list");
        return node;
    }

    // Utility method for returning position or null if sentinel
    // primary purpose to is prevent exposing sentinel nodes to user
    private Position<E> position(Node<E> node){
        if(node == this.header || node == this.trailer) return null;
        return node;
    }

    public int size() {
        return this.size;
    }

    public boolean isEmpty(){
        return this.size == 0;
    }

    public Position<E> first() {
        return this.position(this.header.getNext());
    }

    public Position<E> last() {
        return this.position(this.trailer.getPrev());
    }

    public Position<E> before(Position<E> p) throws IllegalArgumentException {
        Node<E> node = this.validate(p);
        return this.position(node.getPrev());
    }

    public Position<E> after(Position<E> p) throws IllegalArgumentException {
        Node<E> node = this.validate(p);
        return this.position(node.getNext());
    }

    // Adds element e in between the given nodes
    private Position<E> addBetween(E e, Node<E> pred, Node<E> succ){
        Node<E> item = new Node<>(e, pred, succ);
        pred.setNext(item);
        succ.setPrev(item);
        this.size++;
        return item;
    }

    public Position<E> addFirst(E e) {
        return this.addBetween(e, this.header, this.header.getNext());
    }

    public Position<E> addLast(E e) {
        return this.addBetween(e, this.trailer.getPrev(), this.trailer);
    }

    public Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = this.validate(p);
        return this.addBetween(e, node.getPrev(), node);
    }

    public Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = this.validate(p);
        return this.addBetween(e, node, node.getNext());
    }

    // replaces element at position p and returns it
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = this.validate(p);
        E item = node.getElement();
        node.setElement(e);
        return item;
    }

    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = this.validate(p);
        Node<E> predecessor = node.getPrev();
        Node<E> successor = node.getNext();
        
        predecessor.setNext(successor);
        successor.setPrev(predecessor);
        this.size--;

        E item = node.getElement();
        node.setElement(null);
        node.setNext(null); // add convention for defunct node
        node.setPrev(null);
        
        return item;
    }

    public Iterator<E> iterator() {
        return new ElementIterator();
    }

    // returns iterable representation of list's positions
    public Iterable<Position<E>> positions() {
        return new PositionIterable();
    }

    // Nested Node class
    private static class Node<E> implements Position<E> {
        private E element;
        private Node<E> prev;
        private Node<E> next;
        
        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }

        public E getElement() throws IllegalStateException {
            // convention for defunct node (?)
            if(this.next == null)
                throw new IllegalStateException("Position no longer valid");
            return this.element;
        }

        public Node<E> getPrev(){
            return this.prev;
        }

        public Node<E> getNext(){
            return this.next;
        }

        public void setElement(E e){
            this.element = e;
        }

        public void setPrev(Node<E> p){
            this.prev = p;
        }

        public void setNext(Node<E> n){
            this.next = n;
        }
    }
    
    // Maintains position of the next element to be returned, not just index
    private class PositionIterator implements Iterator<Position<E>>{
        private Position<E> cursor = PositionalLinkedList.this.first(); // position of the next element
        private Position<E> recent = null; // position of last next element

        public boolean hasNext() {
            return this.cursor != null;
        }

        public Position<E> next() throws NoSuchElementException {
            if (this.cursor == null) throw new NoSuchElementException("no more elements");
            this.recent = this.cursor; // set up for removal
            this.cursor = PositionalLinkedList.this.after(this.cursor); // set up for next element
            return this.recent;
        }

        public void remove() throws IllegalStateException {
            if(this.recent == null) throw new IllegalStateException("nothing to remove");
            PositionalLinkedList.this.remove(this.recent);
            this.recent = null; // do not allow remove again until next() is called
        }
    }

    // Constructs PositionIterator object each time its iterator() is called
    // See outer class's positions()
    private class PositionIterable implements Iterable<Position<E>> {
        public Iterator<Position<E>> iterator() {
            return new PositionIterator();
        }
    }

    // Adapts the iteration produced by positions to return elements (ex: simplified for-each expressions)
    private class ElementIterator implements Iterator<E> {
        Iterator<Position<E>> posIterator = new PositionIterator();
        public boolean hasNext() {
            return this.posIterator.hasNext();
        }
        public E next() {
            return this.posIterator.next().getElement();
        }

        public void remove() {
            this.posIterator.remove();
        }
    }
}
