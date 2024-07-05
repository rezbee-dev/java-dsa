package ch8;

import ch7.Position;

// Supports all tree methods along with adding left, right sibling
public interface BinaryTree<E> extends Tree<E> {
    // returns position of the left child of p, or null if none
    Position<E> left(Position<E> p) throws IllegalArgumentException;
    
    // returns position of the right child of p, or null if none
    Position<E> right(Position<E> p) throws IllegalArgumentException;
    
    // returns position of the sibling child of p, or null if none
    // sibling = node next to p, the "other child" of the parent p
    Position<E> sibling(Position<E> p) throws IllegalArgumentException;

}
