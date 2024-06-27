// Uses DoublyLinkedList implementation (sort of)
// All operations are O(1) runtime, O(n) spacetime
public class PositionalLinkedList<E> implements PositionalList<E> {

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
}
