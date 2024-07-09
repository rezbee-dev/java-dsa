package ch9;

// Needed to keep track of element (value) and its key in priority queues, even if its relocated in the ADT
public interface Entry<K,V> {
    K getKey();
    V getValue();
}
