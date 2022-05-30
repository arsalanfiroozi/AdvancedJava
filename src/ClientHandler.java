
// A Java program for a Server
import java.net.*;
import java.util.HashMap;
import java.io.*;

public class ClientHandler implements Runnable {
	public Socket socket = null;
	public BufferedReader in = null;
	public BufferedWriter out = null;
	// Users Data
	public static HashMap<String, String> UserPass = new HashMap<String, String>();
	public static HashMap<String, ClientHandler> UserSocket = new HashMap<String, ClientHandler>();
	// From, To
	String user = null;
	String to = null;

	// constructor with port
	public ClientHandler(Socket socket) {
		String m;
		try {
			this.socket = socket;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			// Login
			m = "User?";
			sendMessage(2, m);
			do {
				m = in.readLine();
			} while (UserPass.get(m) != null);
			user = m;

			m = "Pass?";
			sendMessage(2, m);
			m = in.readLine();

			// Add user to hash map
			UserPass.put(user, m);
			UserSocket.put(user, this);
			m = "Who do you wanna chat with? You can also wait for incoming users!";
			sendMessage(1, m);
			for (String key : UserPass.keySet()) {
				if (!key.equals(user))
					sendMessage(1, key);
			}

			// Make Connection
			Boolean r = false;
			do {
				do {
					m = in.readLine();
				} while (UserPass.get(m) == null && !m.equals("wait"));
				to = m;

				if (!m.equals("wait")) {
					r = UserSocket.get(to).askPermission(user);
					if (!r && !m.equals("wait")) {
						m = "Refused, Try again.";
						sendMessage(2, m);
					}
				}
			} while (!r && !m.equals("wait"));

		} catch (IOException e) {
			e.printStackTrace();
			errorFunc();
		}
	}

	public void run() {
		String m;
		while (to.equals("wait")) {
			// System.out.println("Waiting...");
			System.out.println(to);
			// Nothing
		}

		while (!socket.isClosed()) {
			try {
				m = in.readLine();
				sendMessage(0, m);
			} catch (IOException e) {
				errorFunc();
				break;
			}
		}
	}

	public Boolean askPermission(String text) {
		String m;
		m = "User " + text + " wants to chat with you. U Ok?";
		sendMessage(2, m);
		try {
			m = in.readLine();
		} catch (IOException e) {
			errorFunc();
		}
		// System.out.println(m);
		if (m.equals("1") || m.equals("y") || m.equals("yes")) {
			to = text;
			System.out.println("U accepted.");
			return true;
		}
		System.out.println("U rejected.");
		return false;
	}

	public void sendMessage(int who, String text) {
		ClientHandler c;
		if (who == 1)
			c = UserSocket.get(user);
		else if (who == 0)
			c = UserSocket.get(to);
		else
			c = this;
		System.out.println(to);
		try {
			c.out.write(text);
			c.out.newLine();
			c.out.flush();
		} catch (IOException e) {
			errorFunc();
		}
	}

	public void errorFunc() {
		UserPass.remove(user);
		UserSocket.remove(user);
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
