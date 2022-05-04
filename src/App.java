import java.io.Console;

public class App {
    public static void main(String[] args) throws Exception {
        // Test LinkedList_Q1
        // LinkedList_Q1 Ins = new LinkedList_Q1();
        // System.out.println(Ins.getItem(1));
        // Ins.addItem(99.0, 1);
        // System.out.println(Ins.getItem(1));
        // System.out.println(Ins.getItem(2));
        // Ins.removeItem(2);
        // System.out.println(Ins.getItem(1));
        // System.out.println(Ins.getItem(2));

        // Test LinkedList_Q2
        LinkedList_Q2 Ins2 = new LinkedList_Q2(new Double[] { 1.0, 2.0, 3.0 });
        System.out.println(Ins2.getItem(1));
        Ins2.addItem(99.0, 1);
        System.out.println(Ins2.getItem(1));
        System.out.println(Ins2.getItem(2));
        Ins2.removeItem(2);
        System.out.println(Ins2.getItem(1));
        System.out.println(Ins2.getItem(2));

    }
}
