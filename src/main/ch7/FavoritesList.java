import src.main.ch7.PositionalLinkedList;

public class FavoritesList<E> {
    PositionalList<Item<E>> list = new PositionalLinkedList<>();

    public FavoritesList(){}

    // Shorthand for retrieving user's element stored at position p
    protected E value(Position<Item<E>> p){
        return p.getElement().getValue();
    }

    // Shorthand for retrieving count of item stored at position p
    protected int count(Position<Item<E>> p) {
        return p.getElement().getCount();
    }

    // Returns position having element equal to e (or null)
    protected Position<Item<E>> findPosition(E e){
        Position<Item<E>> walk = this.list.first();
        while(walk != null && !e.equals(this.value(walk))){
            walk = this.list.after(walk);
        }
        return walk;
    }

    // todo: finish writing chp 7.7

    // For storing element and its access count
    protected static class Item<E> {
        private E value;
        private int count;

        public Item(E value){
            this.value = value;
        }

        public int getCount(){
            return this.count;
        }

        public E getValue() {
            return this.value;
        }

        public void increment() {
            this.count++;
        }
    }
}
