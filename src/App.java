import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        // Q1
        // System.out.println("Start....");
        // Q1 obj = new Q1(0);

        // Q2
        Integer i;
        ArrayList<Thread> threads = new ArrayList<Thread>();
        for(i=0;i<101;i++){
            Q2 obj2 = new Q2();
            Thread thread = new Thread(obj2);
            threads.add(thread);
        }
        for(i=0;i<101;i++){
            threads.get(i).start();
        }

    }
}
