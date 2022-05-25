
// A Java program for a Server
import java.net.*;
import java.util.HashMap;
import java.io.*;

public class ClientHandler implements Runnable {
	private Socket socket = null;
	private DataInputStream in = null;
	private DataOutputStream out = null;
	// Users Data
	static HashMap<String, String> UserPass = new HashMap<String, String>();

	// constructor with port
	public ClientHandler(Socket uocket) {
		socket = uocket;
	}

	public void run() {
		try {
			// takes input from the client socket
			in = new DataInputStream(
					new BufferedInputStream(socket.getInputStream()));
			// out = new DataOutputStream(
			// new BufferedOutputStream(socket.getOutputStream()));
			out = new DataOutputStream(socket.getOutputStream());

			String line = "";
			String user = null;
			do {
				out.writeUTF("Username?");
				// reads message from client until "Over" is sent
				try {
					user = in.readUTF();
					System.out.println(line);
				} catch (IOException i) {
					System.out.println(i);
				}
			} while (UserPass.get(user) != null);
			out.writeUTF("Password?");
			String pass = null;
			try {
				pass = in.readUTF();
				System.out.println(line);
			} catch (IOException i) {
				System.out.println(i);
			}
			out.writeUTF("Registered");
			UserPass.put(user, pass);
			// Send All users:
			for (String key : UserPass.keySet()) {
				out.writeUTF(key);
			}
			out.writeUTF("Over");
			// Select
			String to = null;
			do {
				out.writeUTF("Who do you wanna chat with?");
				try {
					to = in.readUTF();
					System.out.println(to);
				} catch (IOException i) {
					System.out.println(i);
				}
			} while (UserPass.get(user) != null);
			out.writeUTF("Over");
			
			while (!line.equals("Over")) {
				try {
					line = in.readUTF();
					System.out.println(line);
				} catch (IOException i) {
					System.out.println(i);
				}
			}
			System.out.println("Closing connection");

		} catch (IOException i) {
			System.out.println(i);
		}
		try {
			socket.close();
			in.close();
			out.close();
		} catch (IOException i) {
			System.out.println(i);
		}
	}
}
