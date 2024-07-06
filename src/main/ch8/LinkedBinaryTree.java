package ch8;

import ch7.Position;

// mostly O(1), except for height method - O(N)
// O(n) spacetime
public class LinkedBinaryTree<E> extends AbstractBinaryTree<E> {

    protected Node<E> root;
    private int size; // number of nodes in the tree

    public LinkedBinaryTree() {}

    public int size() {
        return this.size;
    }

    public Position<E> root(){
        return this.root;
    }

    // implement abstract binary tree methods
    @Override
    public Position<E> parent(Position<E> p) throws IllegalArgumentException {
        Node<E> node = this.validate(p);
        return node.getParent();
    }

    @Override
    public Position<E> left(Position<E> p) throws IllegalArgumentException {
        Node<E> node = this.validate(p);
        return node.getLeft();
    }

    @Override
    public Position<E> right(Position<E> p) throws IllegalArgumentException {
        Node<E> node = this.validate(p);
        return node.getRight();
    }

    // Update methods

    // creates root for empty tree, and returns position of the root. Error if tree not empty
    public Position<E> addRoot(E e) throws IllegalStateException {
        if (!this.isEmpty()) throw new IllegalStateException("tree is not empty");
        this.root = this.createNode(e, null, null, null);
        this.size = 1;
        return root;
    }

    // creates left child of p, and returns node. Error if p already has left child
    public Position<E> addLeft(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> parent = this.validate(p);
        if(parent.getLeft() != null) throw new IllegalArgumentException("p already has a left child");
        
        Node<E> child = this.createNode(e, parent, null, null);
        parent.setLeft(child);

        this.size++;
        return child;
    }

    // creates right child of p, and returns node. Error if p already has right child
    public Position<E> addRight(Position<E> p, E e) throws IllegalArgumentException{
        Node<E> parent = this.validate(p);
        if(parent.getRight() != null) throw new IllegalArgumentException("p already has a right child");
        
        Node<E> child = this.createNode(e, parent, null, null);
        parent.setRight(child);

        this.size++;
        return child;
    }

    // replaces element stored at p, with e, and returns the replaced e
    public E set(Position<E> p, E e) throws IllegalArgumentException {
        Node<E> node = this.validate(p);
        
        E temp = node.getElement();
        node.setElement(e);

        return temp;
    }

    // attaches the internal structures (nodes) of T1 and T2 as the left and right subtrees of leaf p
    //   Error if p is not a leaf
    public void attach(Position<E> p, LinkedBinaryTree<E> t1, LinkedBinaryTree<E> t2) throws IllegalArgumentException {
        Node<E> node = this.validate(p);
        if(this.isInternal(p)) throw new IllegalArgumentException("p must be a leaf");
        
        this.size += t1.size() + t2.size();
        if(!t1.isEmpty()){
            t1.root.setParent(node);
            node.setLeft(t1.root);
            t1.root = null;
            t1.size = 0;
        }
        if(!t2.isEmpty()){
            t2.root.setParent(node);
            node.setRight(t2.root);
            t2.root = null;
            t2.size = 0;
        }
    }

    // Removes node at p, replacing it with its child (if any) and returns the replaced element
    //   Error if p has two children
    public E remove(Position<E> p) throws IllegalArgumentException {
        Node<E> node = this.validate(p);
        if(this.numChildren(p) == 2) throw new IllegalArgumentException("p has two children");

        Node<E> child = node.getLeft() != null ? node.getLeft(): node.getRight();

        if(child != null) child.setParent(node.getParent());
        if(node == root) root = child; // child becomes root
        else {
            Node<E> parent = node.getParent();
            if(node == parent.getLeft()) parent.setLeft(child);
            else parent.setRight(child);
        }

        this.size--;
        E temp = node.getElement();
        node.setElement(null);
        node.setLeft(null);
        node.setRight(null);
        node.setParent(node); // convention for defunt node

        return temp;
    }

    // internal method for validation
    protected Node<E> validate(Position<E> p) throws IllegalArgumentException {
        if(!(p instanceof Node)) throw new IllegalArgumentException("Not a valid position type");
        
        Node<E> node = (Node<E>) p;
        // checking if node is invalid; nodes are invalidated via setting parent of node = node
        if(node.getParent() == node)throw new IllegalArgumentException("p is no longer in the tree");

        return node;
    }

    // Factory function for creating new nodes
    protected Node<E> createNode(E e, Node<E> parent, Node<E> left, Node<E> right) {
        return new Node<E>(e, parent, left, right);
    }

    // Nested Node class
    protected static class Node<E> implements Position<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E e, Node<E> parent, Node<E> left, Node<E> right) {
            this.element = e;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        // getters & setters
        public E getElement() {
            return this.element;
        }

        public Node<E> getParent() {
            return this.parent;
        }

        public Node<E> getLeft() {
            return this.left;
        }

        public Node<E> getRight() {
            return this.right;
        }

        public void setElement(E e) {
            this.element = e;
        }

        public void setParent(Node<E> parent){
            this.parent = parent;
        }

        public void setLeft(Node<E> left){
            this.left = left;
        }

        public void setRight(Node<E> right){
            this.right = right;
        }
    }
}