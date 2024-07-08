package ch8;

import java.util.ArrayList;
import java.util.List;

import ch7.Position;

public abstract class AbstractBinaryTree<E> extends AbstractTree<E> implements BinaryTree<E> {
    public Position<E> sibling(Position<E> p){
        Position<E> parent = this.parent(p);
        if (parent == null) return null; // p is root
        if (p == this.left(parent)) return this.right(parent);
        else return this.left(parent);
    }

    // Returns number of children of Position p
    public int numChildren(Position<E> p) {
        int count = 0;
        if(this.left(p) != null) { 
            count++;
        }

        if(this.right(p) != null) {
            count++;
        }

        return count;
    }

    // Returns an iterable collection of the positions representing p's children
    // snapshot
    public Iterable<Position<E>> children(Position<E> p) {
        List<Position<E>> snapshot = new ArrayList<>(2); // max capacity 2
        
        if(this.left(p) != null) snapshot.add(this.left(p));
        if(this.right(p) != null) snapshot.add(this.right(p));
        
        return snapshot;
    }

    // Adds positiosn of subtree rooted at p to the given snapshot
    // Note: only applicable to binary trees
    private void inorderSubtree(Position<E> p, List<Position<E>> snapshot){
        if(this.left(p) != null) inorderSubtree(this.left(p), snapshot);
        snapshot.add(p);
        if(this.right(p) != null) inorderSubtree(this.right(p), snapshot);
    }

    // Returns an iterable collection of positions of the tree, reported in inorder
    public Iterable<Position<E>> inorder(){
        List<Position<E>> snapshot = new ArrayList<>();
        if(!this.isEmpty()) this.inorderSubtree(this.root(), snapshot);
        return snapshot;
    }

    @Override
    public Iterable<Position<E>> positions() {
        return this.inorder();
    }
}
