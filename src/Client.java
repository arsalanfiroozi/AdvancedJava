
// A Java program for a Client
import java.net.*;
import java.util.Scanner;
import java.io.*;

public class Client {
	// initialize socket and input output streams
	private Socket socket = null;
	private BufferedReader in = null;
	private BufferedWriter out = null;

	// Connection
	String Address = null;
	int Port = 0;

	// User
	String user;

	// constructor to put ip address and port
	public Client(String address, int port) {
		Address = address;
		Port = port;
		try {
			socket = new Socket(Address, Port);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			System.out.println("Connected");
		} catch (IOException e) {
			errorFunc();
		}
	}

	public void sendMessage() {
		try {
			Scanner scanner = new Scanner(System.in);
			while (socket.isConnected()) {
				String m = scanner.nextLine();
				out.write(m);
				out.newLine();
				out.flush();
			}
		} catch (IOException e) {
			errorFunc();
		}
	}

	public void listenForMessage() {
		new Thread(new Runnable() {
			public void run() {
				String m;
				while (socket.isConnected()) {
					try {
						m = in.readLine();
						System.out.println(m);
					} catch (IOException e) {
						errorFunc();
					}
				}
			}
		}).start();
	}

	public void errorFunc() {
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
