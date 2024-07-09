package ch9;

import java.util.Comparator;

public abstract class AbstractPriorityQueue<K,V> implements PriorityQueue<K,V> {

    // Defines the ordering of keys in PQ
    private Comparator<K> comp;

    protected AbstractPriorityQueue(Comparator<K> comp) {
        this.comp = comp;
    }

    protected AbstractPriorityQueue() {
        this(new DefaultComparator<K>());
    }

    // Method for comparing two entries according to key
    protected int compare(Entry<K,V> a, Entry<K,V> b) {
        return this.comp.compare(a.getKey(), b.getKey());
    }

    // Determines whether a key is valid
    protected boolean checkKey(K key) throws IllegalArgumentException {
        try {
            // see if key can be compared with itself
            return (this.comp.compare(key, key) == 0);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Incompatible Key");
        }
    }

    public boolean isEmpty() {
        return this.size() == 0;
    }

    // Implements Entry Interface
    protected static class PQEntry<K,V> implements Entry<K,V> {
        private K k;
        private V v;
        
        public PQEntry(K key, V value) {
            k = key;
            v = value;
        }

        @Override
        public K getKey() {
            return this.k;
        }

        @Override
        public V getValue() {
            return this.v;
        }

        protected void setKey(K key) {
            this.k = key;
        }

        protected void setValue(V value) {
            this.v = value;
        }
    }
}
