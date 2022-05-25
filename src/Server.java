
// A Java program for a Server
import java.net.*;
import java.util.HashMap;
import java.io.*;

public class Server implements Runnable {
    // initialize socket and input stream
    private ServerSocket server = null;
    int Port = 0;

    // Users Data
    HashMap<String, String> UserPass = new HashMap<String, String>();

    // constructor with port
    public Server(int port) {
        Port = port;
    }

    public void run() {
        try {
            server = new ServerSocket(Port);
            while (true) {
                Socket socket = null;
                try {

                    System.out.println("Server started");

                    System.out.println("Waiting for a client ...");

                    socket = server.accept();
                    System.out.println("Client accepted");

                    ClientHandler obj = new ClientHandler(socket);
                    Thread thr = new Thread(obj);
                    thr.start();

                } catch (Exception e) {
                    socket.close();
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
