package ch8;

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
}
