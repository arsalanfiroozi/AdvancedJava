import java.util.*;

public class Generic_LinkedList_Q2<TypeN> {
    Double val = null;
    Generic_LinkedList_Q2<TypeN> next = null;
    Generic_LinkedList_Q2<TypeN> before = null;
    Integer length = 0;

    public Generic_LinkedList_Q2() {
        // System.out.println("LinkedList_Q2 is created!");
    }

    public Generic_LinkedList_Q2(Double[] T) {
        Generic_LinkedList_Q2<TypeN> p = this;
        Generic_LinkedList_Q2<TypeN> pb = null;
        for (int i = 0; i < T.length; i++) {
            p.length = T.length - i;
            p.val = T[i];
            p.next = new Generic_LinkedList_Q2<TypeN>();
            p.before = pb;
            pb = p;
            p = p.next;
        }
    }

    public void addItem(Double x, Integer Pos) throws Exception {
        Generic_LinkedList_Q2<TypeN> p = this;
        for (int i = 0; i < Pos; i++) {
            p.length = p.length + 1;
            p = p.next;
        }
        Generic_LinkedList_Q2<TypeN> pn = p;
        Generic_LinkedList_Q2<TypeN> pb = p.before;
        p = new Generic_LinkedList_Q2<TypeN>();
        p.length = pb.length + 1;
        p.val = x;
        p.before = pb;
        p.next = pn;
        p.next.before = p;
        p.before.next = p;
    }

    public void removeItem(Integer Pos) throws Exception {
        Generic_LinkedList_Q2<TypeN> p = this;
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
        Generic_LinkedList_Q2<TypeN> p = this;
        for (int i = 0; i < Pos; i++) {
            p = p.next;
        }
        return p.val;
    }

}
