import java.util.*;

public class LinkedList_Q2 {
    Double val = null;
    LinkedList_Q2 next = null;
    LinkedList_Q2 before = null;
    Integer length = 0;

    public LinkedList_Q2() {
        // System.out.println("LinkedList_Q2 is created!");
    }

    public LinkedList_Q2(Double[] T) {
        LinkedList_Q2 p = this;
        LinkedList_Q2 pb = null;
        for (int i = 0; i < T.length; i++) {
            p.length = T.length - i;
            p.val = T[i];
            p.next = new LinkedList_Q2();
            p.before = pb;
            pb = p;
            p = p.next;
        }
    }

    public void addItem(Double x, Integer Pos) throws Exception {
        LinkedList_Q2 p = this;
        for (int i = 0; i < Pos; i++) {
            p.length = p.length + 1;
            p = p.next;
        }
        LinkedList_Q2 pn = p;
        LinkedList_Q2 pb = p.before;
        p = new LinkedList_Q2();
        p.length = pb.length + 1;
        p.val = x;
        p.before = pb;
        p.next = pn;
        p.next.before = p;
        p.before.next = p;
    }

    public void removeItem(Integer Pos) throws Exception {
        LinkedList_Q2 p = this;
        for (int i = 0; i < Pos; i++) {
            p.length = p.length - 1;
            p = p.next;
        }
        p.next.before = p.before;
        p.before.next = p.next;
    }

    public Integer Length(Integer Pos) throws Exception {
        return this.length;
    }

    public Double getItem(Integer Pos) throws Exception {
        LinkedList_Q2 p = this;
        for (int i = 0; i < Pos; i++) {
            p = p.next;
        }
        return p.val;
    }

}
