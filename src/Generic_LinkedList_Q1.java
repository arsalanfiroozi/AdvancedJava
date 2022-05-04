import java.util.*;

public class Generic_LinkedList_Q1<TypeN> {
    TypeN[] L = null;

    public Generic_LinkedList_Q1() {
        System.out.println("LinkedList_Q1 is created!");
    }

    public Generic_LinkedList_Q1(TypeN[] T) {
        System.out.println("LinkedList_Q1 is created!");
        this.L = T;
    }

    public void addItem(TypeN x, Integer Pos) throws Exception {
        TypeN[] T = (TypeN[]) new Object[this.L.length + 1];
        for (int i = 0; i < Pos; i++) {
            T[i] = this.L[i];
        }
        T[Pos] = x;
        for (int i = Pos; i < this.L.length; i++) {
            T[i + 1] = this.L[i];
        }
        this.L = T;
    }

    public void removeItem(Integer Pos) throws Exception {
        TypeN[] T = (TypeN[]) new Object[this.L.length - 1];
        for (int i = 0; i < Pos; i++) {
            T[i] = this.L[i];
        }
        for (int i = Pos + 1; i < this.L.length; i++) {
            T[i - 1] = this.L[i];
        }
        this.L = T;
    }

    public Integer Length(Integer Pos) throws Exception {
        return this.L.length;
    }

    public TypeN getItem(Integer Pos) throws Exception {
        return this.L[Pos];
    }

}
