package socialnetwork.domain;

import java.util.Objects;


/**
 * Define a Tuple o generic type entities
 * @param <E> - tuple entity type
 */
public class Tuple<E extends Comparable<E>> {
    private E e1;
    private E e2;

    public Tuple(E e1, E e2) {
        this.e1 = e1;
        this.e2 = e2;
    }

    public E getLeft() {
        return e1;
    }

    public void setLeft(E e1) {
        this.e1 = e1;
    }

    public E getRight() {
        return e2;
    }

    public void setRight(E e2) {
        this.e2 = e2;
    }

    @Override
    public String toString() {
        return "" + e1 + "," + e2;

    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null){
            return  false;
        }
        if (!(obj instanceof Tuple)){
            return false;
        }
        Tuple tuple = (Tuple) obj;
        return (Objects.equals(e1,tuple.getRight()) && Objects.equals(tuple.getLeft(), e2)) ||
                (Objects.equals(e2,tuple.getRight()) && Objects.equals(tuple.getLeft(), e1));

    }

    @Override
    public int hashCode() {
        if(e1 == null){
            return Objects.hash(e1,e2);
        }
        if(e1.compareTo(e2) < 0){
            return Objects.hash(e1,e2);
        }
        return Objects.hash(e2,e1);
    }
}