package ch9;

import java.util.Comparator;

import ch7.Position;
import ch7.PositionalLinkedList;
import ch7.PositionalList;

// O(1) except isnert which is O(n)
public class SortedPriorityQueue <K,V> extends AbstractPriorityQueue<K,V> {
    private PositionalList<Entry<K,V>> list = new PositionalLinkedList<>();

    public SortedPriorityQueue() {
        super();
    }

    public SortedPriorityQueue(Comparator<K> comp) {
        super(comp);
    }

    // O(n)
    public Entry<K,V> insert(K key, V value) throws IllegalArgumentException {
        this.checkKey(key);

        Entry<K,V> newest = new PQEntry<>(key, value);
        Position<Entry<K,V>> walk = this.list.last();

        // walk backward, looking for smaller key
        while (walk != null && this.compare(newest, walk.getElement()) < 0) {
            walk = this.list.before(walk);
        }

        // new key is smallest
        if(walk == null) list.addFirst(newest);
        // newest goes after walk
        else this.list.addAfter(walk, newest); 

        return newest;
    }

    public Entry<K,V> min() {
        if(this.list.isEmpty()) return null;
        return this.list.first().getElement();
    }

    public Entry<K,V> removeMin() {
        if(this.list.isEmpty()) return null;
        return list.remove(list.first());
    }

    public int size(){
        return list.size();
    }
}
