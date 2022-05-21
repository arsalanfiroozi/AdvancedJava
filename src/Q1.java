import java.util.*;

public class Q1 implements Runnable {
    Integer i;
    Thread thread = null;

    public Q1(Integer j) {
        i = j;
        if (i != 50) {
            j = j + 1;
            Q1 obj = new Q1(j);
            thread = new Thread(obj);
            thread.start();
        }
    }

    public void run() {
        while(thread.isAlive()){
            // Nothing!
        }
        System.out.println("Hello from thread " + i);
    }

}
