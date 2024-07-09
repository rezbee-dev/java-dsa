package ch9;

import java.util.Comparator;

import ch7.Position;
import ch7.PositionalLinkedList;
import ch7.PositionalList;

// O(1) for all methods except removeMin which is O(n)
public class UnsortedPriorityQueue<K,V> extends AbstractPriorityQueue<K,V> {
    private PositionalList<Entry<K,V>> list = new PositionalLinkedList<>();
    
    // Creates empty priority queue based on natural ordering of its keys
    public UnsortedPriorityQueue() {
        super();
    }

    public UnsortedPriorityQueue(Comparator<K> comp) {
        super(comp);
    }

    // Returns the Position of entry with minimal key
    private Position<Entry<K,V>> findMin() {
        Position<Entry<K,V>> small = this.list.first();
        
        for(Position<Entry<K,V>> walk : this.list.positions()) {
            if(this.compare(walk.getElement(), small.getElement()) < 0) {
                small = walk;
            }
        }

        return small;
    }

    // Inserts key-value pair and returns the entry created
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
        this.checkKey(key);
        Entry<K,V> newest = new PQEntry<>(key, value);
        this.list.addLast(newest);
        return newest;
    }

    public Entry<K,V> min() {
        if(this.list.isEmpty()) return null;
        return this.findMin().getElement();
    }

    public Entry<K,V> removeMin() {
        if(this.list.isEmpty()) return null;
        return this.list.remove(this.findMin());
    }

    public int size() {
        return this.list.size();
    }
}
