import java.util.*;

public class App {
    public static void main(String[] args) throws Exception {
        System.out.println("Starting Test:");
        Server ser = new Server(8000);
        Thread thread = new Thread(ser);
        thread.start();
    }
}
