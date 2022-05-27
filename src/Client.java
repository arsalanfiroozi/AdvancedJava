
// A Java program for a Client
import java.net.*;
import java.io.*;

public class Client implements Runnable {
	// initialize socket and input output streams
	private Socket socket = null;
	private DataInputStream input = null;
	private DataOutputStream out = null;
	private DataInputStream in = null;
	String Address = null;
	int Port = 0;

	// constructor to put ip address and port
	public Client(String address, int port) {
		Address = address;
		Port = port;
	}

	public static void main(String args[]) {
		Client client = new Client("127.0.0.1", 5000);
	}

	public void run() {
		// establish a connection
		try {
			socket = new Socket(Address, Port);
			System.out.println("Connected");

			// takes input from terminal
			input = new DataInputStream(System.in);

			// sends output to the socket
			out = new DataOutputStream(socket.getOutputStream());
			in = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
		} catch (UnknownHostException u) {
			System.out.println(u);
		} catch (IOException i) {
			System.out.println(i);
		}

		// string to read message from input
		String line = "";
		// keep reading until "Over" is input
		try {
			line = in.readUTF();
			System.out.println(line);
		} catch (IOException i) {
			System.out.println(i);
		}
		while (!line.equals("Password?")) {
			String line2;
			try {
				line2 = input.readLine();
				out.writeUTF(line2);
			} catch (IOException i) {
				System.out.println(i);
			}
			try {
				line = in.readUTF();
				System.out.println(line);
			} catch (IOException i) {
				System.out.println(i);
			}
		}
		try {
			String line2 = input.readLine();
			out.writeUTF(line2);
		} catch (IOException i) {
			System.out.println(i);
		}
		try {
			line = in.readUTF();
			System.out.println(line);
		} catch (IOException i) {
			System.out.println(i);
		}
		// Show users:
		line = "Available Users:";
		while (!line.equals("Over")) {
			System.out.println(line);
			try {
				line = in.readUTF();
			} catch (IOException i) {
				System.out.println(i);
			}
		}
		// Select
		try {
			line = in.readUTF();
		} catch (IOException i) {
			System.out.println(i);
		}
		while (!line.equals("Over")) {
			System.out.println(line);
			try {
				String line2 = input.readLine();
				out.writeUTF(line2);
			} catch (IOException i) {
				System.out.println(i);
			}
			try {
				line = in.readUTF();
			} catch (IOException i) {
				System.out.println(i);
			}
		}
		System.out.println("Session Created. You can talk:");
		
		// close the connection
		try {
			input.close();
			out.close();
			socket.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}
}
