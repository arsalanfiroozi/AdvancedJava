import java.io.Console;
import java.util.*;

public class Counter {
    static int count = 0;

    // static void inc() {
    static synchronized void inc() {
        if (count <= 100) {
            try {
            Thread.sleep(10);
            } catch (InterruptedException e) {
            e.printStackTrace();
            }
            count++;
            System.out.println(count);
        }
        if (count > 100) {
            System.out.println("Failed!");
        }
    }

    static void show() {
        System.out.println("Value: " + count);
    }

}
