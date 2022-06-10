
// A Java program for a Server
import java.net.*;
import java.util.HashMap;
import java.io.*;
import java.util.concurrent.TimeUnit;

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
			m = "Username?";
			sendMessage(2, m);
			m = in.readLine();
			while (UserPass.get(m) != null) {
				m = "This username is already taken. Please choose another.";
				sendMessage(2, m);
				m = in.readLine();
			}
			user = m;

			m = "Pass?";
			sendMessage(2, m);
			m = in.readLine();

			// Add user to hash map
			UserPass.put(user, m);
			UserSocket.put(user, this);
			System.out.println("\nNew User: " + user + "\n");

			//select recipient
			m = "Who do you wanna chat with? You can also wait for incoming users! \n current users:";
			sendMessage(2, m);
			//print list of current users
			for (String key : UserPass.keySet()) {
				if (!key.equals(user))
					sendMessage(2, key);
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
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//System.out.print("W");
			//m = to;
			//sendMessage(1, m);
			// System.out.println(to);
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
		m = "User " + text + " wants to chat with you. U Ok [y/n]?";
		sendMessage(2, m);
		try {
			m = in.readLine();
		} catch (IOException e) {
			errorFunc();
		}
		// System.out.println(m);
		if (m.equals("y")) {
			to = text;
			System.out.println("User " + user + " accepted user " + text);
			return true;
		}
		System.out.println("User " + user + " rejected user " + text);
		return false;
	}

	public void sendMessage(int who, String text) {
		ClientHandler c;
		// if (who == 1)
		// 	c = UserSocket.get(user);
		// else if (who == 0)
		// 	c = UserSocket.get(to);
		// else
		// 	c = this;
		String serverDestName=user;
		if (who == 0){
			c = UserSocket.get(to);
			System.out.println("Message sent from " + user + " \t to " + to + ":" + text);
		} else {
			c = this;
			if (serverDestName==null) {
			    serverDestName = " the new client without username.\n";
			} else {
			    serverDestName = user;
			}
			System.out.println("Message sent from Server to " + serverDestName + ":" + text);
		}

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
