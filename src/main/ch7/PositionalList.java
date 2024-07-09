package ch7;

public interface PositionalList<E> {
    int size();
    boolean isEmpty();
    Position<E> first(); // returns positon of first element in list
    Position<E> last();  // returns positon of last element in list
    
    // returns position immediately before Position P (or null if p is first element in list)
    Position<E> before(Position<E> p) throws IllegalArgumentException;
    // returns position immediately after Position P (or null if p is last element in list)
    Position<E> after(Position<E> p) throws IllegalArgumentException;

    // Insert element e at the front of the list and return its new Position
    Position<E> addFirst(E e);
    // Insert element e at the end of the list and return its new Position
    Position<E> addLast(E e);

    // Inserts element e immediately before Position p and returns its new position
    Position<E> addBefore(Position<E> p, E e) throws IllegalArgumentException;
    // Inserts element e immediately after Position p and returns its new position
    Position<E> addAfter(Position<E> p, E e) throws IllegalArgumentException;

    // Replaces element stored at Position p and returns the replaced element
    E set(Position<E> p, E e) throws IllegalArgumentException;
    // Removes the element stored at Position p and returns it (invalidating p)
    E remove(Position<E> p) throws IllegalArgumentException;

    Iterable<Position<E>> positions();
}
