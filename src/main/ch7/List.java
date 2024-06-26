public interface List<E> {
    int size();
    boolean isEmpty();
    E get(int i) throws IndexOutOfBoundsException; // retrieves but does not remove element at index i
    E set(int i, E e) throws IndexOutOfBoundsException; // replaces element at index i, and returned replaced element
    void add(int i, E e) throws IndexOutOfBoundsException; // inserts element at index i, shifting subsequent elements to the right
    E remove(int i); // removes and returns element at index i, and shifts subsequent elements left
}