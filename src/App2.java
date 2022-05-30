import java.io.DataInputStream;
import java.util.*;

public class App2 {
    public static void main(String[] args) throws Exception {
        // System.out.println("Starting Test:");
        // Server ser = new Server(5000);
        // Thread thread = new Thread(ser);
        // thread.start();

        System.out.println("Starting Client:");
        Client cli1 = new Client("localhost", 8000);
        cli1.listenForMessage();
        cli1.sendMessage();
    }
}
