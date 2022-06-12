
// A Java program for a Server
import java.net.*;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.temporal.IsoFields;
import java.util.Base64;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.io.*;

public class ClientHandler implements Runnable {
	public Socket socket = null;
	public BufferedReader in = null;
	public BufferedWriter out = null;
	
	// Users Data
	public static HashMap<String, String> UserPass = new HashMap<String, String>();
	public static HashMap<String, ClientHandler> UserSocket = new HashMap<String, ClientHandler>();
	public static HashMap<String, Boolean> isOnline = new HashMap<String, Boolean>();

	// From, To
	String user = null;
	String to = null;

	boolean connectionRequested = false;
	
	boolean notSignedIn = true;
	boolean decodeBase64 = false;
	boolean stopCheckingBase64 = false;
	boolean commandReceived = false;
	Server server;


	// constructor with port
	public ClientHandler(Server server, Socket socket) {

		try {
			this.socket = socket;
			this.server = server;
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

			
		} catch (IOException e) {
			e.printStackTrace();
			errorFunc();
		}
	}

	public void run() {
		String m;
		// Login
		while(!socket.isClosed()){
			try {
				while(notSignedIn) {
					m = "Username?";
					sendMessage(2, m);
					m = in.readLine();
					if(m.equals("sign out")){
						m = in.readLine();
					}
					user = m;
					if(UserPass.get(user) == null) {  //new user
						m = "Pass?";
						sendMessage(2, m);
						m = in.readLine();

						// Add user to hash map
						UserPass.put(user, m);
						UserSocket.put(user, this);
						isOnline.put(user,true);

						System.out.println("\nNew User: " + user + "\n");
						notSignedIn = false;

					} else { //old user login
						if(isOnline.get(user)){
							m = "Logged in in other session. ";
							sendMessage(2, m);
						} else {
							m = "Pass?";
							sendMessage(2, m);
							m = in.readLine();

							//check pass
							if(m.equals(UserPass.get(user))){
								UserSocket.put(user, this);
								isOnline.put(user,true);
								notSignedIn = false;
							} else {
								m = "Wrong password.";
								sendMessage(2, m);
							}
						}
					}
				}
			} catch (Exception e) {}
		

			//select recipient
			m = "Who do you wanna chat with? You can also wait for incoming users! \n current users:";
			sendMessage(2, m);
			//print list of current users
			String onlineStatus = null;
			for (String key : UserPass.keySet()) {
				if (!key.equals(user)){
					if(isOnline.get(key).equals(true)){
						onlineStatus = "(online)";
					} else {
						onlineStatus = "(offline)";
					}
					sendMessage(2, key + "\t" + onlineStatus);
				}
			}

			// Make Connection
			try {
				Boolean recipientAccepted = false;
				while (!recipientAccepted) {
					while((UserPass.get(m) == null)&&(to==null)) {
						int x = 5;
						long startTime = System.currentTimeMillis();
						while ((System.currentTimeMillis() - startTime) < x * 1000 && !in.ready()) {}

						if (in.ready()&&(to==null)) {
							m = in.readLine();
							if(UserPass.get(m) == null){
								m = "Who do you wanna chat with? You can also wait for incoming users! \n current users:";
								sendMessage(2, m);
								//print list of current users
								for (String key : UserPass.keySet()) {
									if (!(key==user)){
										if(isOnline.get(key).equals(true)){
											onlineStatus = "(online)";
										} else {
											onlineStatus = "(offline)";
										}
										sendMessage(2, key + "\t" + onlineStatus);
									}
								}
							}
						}
					}
					// do {
					// 	m = in.readLine();
					// } while ((UserPass.get(m) == null)&&(to.equals(null)));


					if(!(UserPass.get(m) == null)){ //requesting user "m"
						to = m;
						recipientAccepted = UserSocket.get(to).askPermission(user);
						if (!recipientAccepted) {
							m = "Refused, Try again.";
							sendMessage(2, m);
							to = null;
						}
					} else { 						//accepting someone else request 
						recipientAccepted = true;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}




			while (!notSignedIn) {
				try {
					m = in.readLine();
					if(m.equals("sign out")){
						signOut();
					}
					if(!(to==null)){
						sendMessage(0, m);
					}
				} catch (IOException e) {
					errorFunc();
					break;
				}
			}

			UserPass.put(user, null);
			UserSocket.put(user, null);
			isOnline.put(user, false);
			// isOnline.get(user);
			// System.out.println(isOnline.get(to));
			// // server.isOnline.put(user,false);
			// //server.isOnline.remove(user);
			// for (String key : UserPass.keySet()) {
			// 	if (true){
			// 		if(isOnline.get(user).equals(true)){
			// 			onlineStatus = "(online)";
			// 		} else {
			// 			onlineStatus = "(offline)";
			// 		}
			// 		sendMessage(2, key + "\t" + onlineStatus);
			// 	}
			// }


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
			connectionRequested = true;
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
			boolean checkBase64 = true;
			//detect RSA with Base64
			try {
				KeyFactory keyFactory = KeyFactory.getInstance("RSA");
				byte[] textByte = Base64.getDecoder().decode(text);
				EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(textByte);
				PublicKey publicKeyReceived = keyFactory.generatePublic(publicKeySpec);
				
				System.out.println("----RSA Public Key!----");
				checkBase64 = false;
				// this.out.write("-RSA Public Key. waiting for server approval...");
				// this.out.newLine();
				// this.out.flush();
				System.out.println("remove users? [y/n]");

				while(server.newCommand==false){
					TimeUnit.MILLISECONDS.sleep(50);
				}
				server.newCommand = false;
				if (server.command.equals("y")) {
					this.out.write("You were signed out. ");
					this.out.newLine();
					this.out.flush();
					c.out.write("You were signed out. ");
					c.out.newLine();
					c.out.flush();					
					this.out.write("sign out");
					this.out.newLine();
					this.out.flush();
					c.out.write("sign out");
					c.out.newLine();
					c.out.flush();


				} else {
					// this.out.write("-approved");
					// this.out.newLine();
					// this.out.flush();
				}
			} catch (Exception e) {}


			//detect Base64
			if(checkBase64){
				
				if(!stopCheckingBase64){
					try {
						byte[] decodedMessageByte = Base64.getDecoder().decode(text);
						if(!decodeBase64){
							this.out.write("-Base64 encoded message. waiting for server approval...");
							this.out.newLine();
							this.out.flush();

							System.out.println("Base64 encoded message! Decode? [y/n]");

							while(server.newCommand==false){
								TimeUnit.MILLISECONDS.sleep(50);
							}
							server.newCommand = false;
							this.out.write("-approved");
							this.out.newLine();
							this.out.flush();
							if (server.command.equals("y")) {
								decodeBase64 = true;
								System.out.println("decoded: " + new String(decodedMessageByte));
							} else {
								stopCheckingBase64 = true;
							}

						} else {
							System.out.println("decoded: " + new String(decodedMessageByte));
						}

					} catch (Exception e) {}
				}
			}


		} else {
			c = this;
			if (serverDestName==null) {
			    serverDestName = " the new client without username.\n";
			} else {
			    serverDestName = user;
			}
			System.out.println("Message sent from Server to " + serverDestName + ": \"" + text + "\"");
		}

		try {
			c.out.write(text);
			c.out.newLine();
			c.out.flush();
		} catch (Exception e) {
			errorFunc();
		}
		
		/*
		Scanner scanner = new Scanner(System.in); 
		try {
			byte[] mDecoded = Base64.getDecoder().decode(text);
			isBase64 = true;
			System.out.println("Base64 encoded message!");
			//String ans = scanner.nextLine();
			//if(ans.equals("y")) {
				String mDecodedString = new String(mDecoded);
				System.out.println("decoded: " + mDecodedString);
			//}
		} catch (Exception e) {

		}
		scanner.close();
		*/
	}

	public void signOut() {
		notSignedIn = true;
		System.out.println("user " + user + " signed out.");
	}

	

	public void errorFunc() {
		// UserPass.remove(user);
		// UserSocket.remove(user);
		// try {
		// 	in.close();
		// 	out.close();
		// 	socket.close();
		// } catch (IOException e) {
		// 	e.printStackTrace();
		// }
	}

	
}
