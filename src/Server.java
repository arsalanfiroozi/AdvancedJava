
// A Java program for a Server
import java.net.*;
import java.util.HashMap;
import java.util.Scanner;
import java.io.*;

public class Server implements Runnable {
    // initialize socket and input stream
    private ServerSocket server = null;
    int Port = 0;
    String command = null;
    boolean newCommand = false;

    // Users Data
    HashMap<String, String> UserPass = new HashMap<String, String>();

    // constructor with port
    public Server(int port) {
        Port = port;
    }

    public void run() {
        System.out.println("Server started");
        try {
            server = new ServerSocket(Port);
            listenForCommands();
            while (!server.isClosed()) {
                Socket socket = null;
                socket = server.accept();
                System.out.println("Client accepted: " + socket);
                ClientHandler obj = new ClientHandler(this, socket);
                Thread thr = new Thread(obj);
                thr.start();
            }
        } catch (Exception e) {
            CloseServer();
        }
    }

    public void CloseServer(){
        try {
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void listenForCommands() throws Exception {
		new Thread(new Runnable() {
			public void run() {
                try (Scanner scanner = new Scanner(System.in)) {
                    while (!server.isClosed()) {
                        command = scanner.nextLine();
                        if(!(command==null))
                            newCommand = true;
                    }
                }
			}
		}).start();
	}
}
