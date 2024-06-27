// used for Node in PotionalList ADTs
// Used to encapsulate Node details
// users should only be concerned with the getting the element from a position
// Needed to be able to identify locations/ positions of elements
public interface Position<E> {
    // Returns element stored at this position
    E getElement() throws IllegalStateException;
}