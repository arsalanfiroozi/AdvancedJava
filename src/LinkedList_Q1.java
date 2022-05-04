import java.util.*;

public class LinkedList_Q1 {
    Double[] L = null;

    public LinkedList_Q1() {
        System.out.println("LinkedList_Q1 is created!");
        this.L = new Double[] { 1.0, 2.0, 3.0 };
    }

    public LinkedList_Q1(Double[] T) {
        System.out.println("LinkedList_Q1 is created!");
        this.L = T;
    }

    public void addItem(Double x, Integer Pos) throws Exception {
        Double[] T = new Double[this.L.length + 1];
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
        Double[] T = new Double[this.L.length - 1];
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

    public Double getItem(Integer Pos) throws Exception {
        return this.L[Pos];
    }

}
