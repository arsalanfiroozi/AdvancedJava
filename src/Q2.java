import java.util.*;

public class Q2 implements Runnable {
    
    public Q2() {
    }

    public void run() {
        Counter cnt = new Counter();
        cnt.inc();
    }
}
