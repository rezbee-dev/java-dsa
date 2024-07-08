package ch8;

import java.util.ArrayList;
import java.util.List;

import ch6.Queue;
import ch6.QueueSinglyLinked;
import ch7.Position;

public abstract class AbstractTree<E> implements Tree<E> {
    // Internal nodes have 1 or more children elements
    public boolean isInternal(Position<E> p) {
        return this.numChildren(p) > 0;
    }
    
    // External nodes have 0 children elements
    public boolean isExternal(Position<E> p) {
        return this.numChildren(p) == 0;
    }

    public boolean isRoot(Position<E> p) {
        return p == this.root();
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    // O(N)
    // returns number of parents of p
    public int depth(Position<E> p){
        if(this.isRoot(p)) return 0;
        else return 1 + this.depth(this.parent(p));
    }

    // O(N^2)
    // Returns height of tree
    // Better implementation of this is provided elsewhere using recursive operations, hence this is private
    private int heightBad(){
        int h = 0;
        // Considers only leaf nodes
        // Saves the leaf node with maximum depth
        // positions() -> O(n), depth() -> O(n) = O(n^2)
        for(Position<E> p: this.positions()) {
            if(this.isExternal(p))
                h = Math.max(h, this.depth(p));
        }
        return h;
    }

    // O(N)
    public int height(Position<E> p) {
        int h=0;
        for (Position<E> c: this.children(p)) {
            h = Math.max(h, 1 + this.height(c));
        }
        return h;
    }

    @Override
    public Iterable<Position<E>> positions() {
        return this.preorder();
    }

    // Adds positions of the subtree rooted at Position p, to the given snapshot List
    private void preorderSubtree(Position<E> p, List<Position<E>> snapshot){
        snapshot.add(p); // add p before exploring subtrees
        for (Position<E> c: this.children(p)){
            preorderSubtree(c, snapshot);
        }
    }

    // Returns an iterable collection of positions of the tree, reported in preorder
    public Iterable<Position<E>> preorder() {
        List<Position<E>> snapshot = new ArrayList<>();
        if(this.isEmpty()) this.preorderSubtree(this.root(), snapshot);
        return snapshot;
    }

    // Adds positions of subtree rooted at Position p to the given snapshot
    private void postorderSubtree(Position<E> p, List<Position<E>> snapshot){
        for(Position<E> c: this.children(p)) postorderSubtree(c, snapshot);
        snapshot.add(p); // add p after exploring subtrees
    }

    // Returns iterable collection of positions of the tree, reported in postorder
    public Iterable<Position<E>> postorder(){
        List<Position<E>> snapshot = new ArrayList<>();
        if(!this.isEmpty()) this.postorderSubtree(this.root(), snapshot);
        return snapshot;
    }

    // Returns an iterable collection of positions of the tree in breadth-first order
    public Iterable<Position<E>> breadthFirst() {
        List<Position<E>> snapshot = new ArrayList<>();
        
        if(!this.isEmpty()){
            Queue<Position<E>> fringe = new QueueSinglyLinked<>();
            fringe.enqueue(this.root()); // start with root
            while(!fringe.isEmpty()){
                Position<E> p = fringe.dequeue(); // remove from front of the queue
                snapshot.add(p);                  // report current position
                for(Position<E> c: this.children(p)){
                    fringe.enqueue(c);            // add children to back of queue
                }
            }
        }

        return snapshot;
    }
}
