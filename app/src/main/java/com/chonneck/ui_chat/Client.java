package com.chonneck.ui_chat;
// A Java program for a Client
import android.os.Build;
import android.widget.EditText;

// A Java program for a Client
import androidx.annotation.RequiresApi;

import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.Base64;
import java.io.*;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class Client {
	private Socket socket = null;
	private BufferedReader in = null;
	private BufferedWriter out = null;

	private int encryptionType = 0;
	private PrivateKey privateKeySend;
	private PublicKey publicKeySend;
	private PublicKey publicKeyReceived;
	private boolean waitnigForPublicKey = false;

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
	@RequiresApi(api = Build.VERSION_CODES.O)
	public void sendMessage(EditText textView, String m) throws Exception {
		try {
			switch (encryptionType) {
				//plain text
				case 0:
					out.write(m);
					out.newLine();
					out.flush();
					textView.setText("Outgoing: " + m +  "\n" + textView.getText() );
					break;
				//base 64 coding
				case 1:
					byte[] mscanBytes = m.getBytes(StandardCharsets.UTF_8);
					String mscanEncoded = Base64.getEncoder().encodeToString(mscanBytes);
					out.write(mscanEncoded);
					out.newLine();
					out.flush();
					textView.setText("Outgoing: " + m +  "\n" + textView.getText() );
					break;

				//asymmetric encryption
				case 2:
					try{
						String mscanEncrypted = AsymmetricEncrypt(m);
						out.write(mscanEncrypted);
						out.newLine();
						out.flush();
						textView.setText("Outgoing: " + m +  "\n" + textView.getText() );
					} catch (Exception e) {
						encryptionType = 0;
					}
					break;

					default:
						break;
			}
			//commands
			switch (m) {
				case "Enable Plain Text":
					encryptionType = 0;
					break;

				case "Enable Base64 Coding":
					encryptionType = 1;
					break;

				case "Enable Asymmetric Encryption":
					waitnigForPublicKey = true;
					enableAsymmetricEncryption();
					//sending public key
					out.write(encodePublicKeySend());
					out.newLine();
					out.flush();
					textView.setText("Outgoing: " + m +  "\n" + textView.getText() );

					encryptionType = 2;
					break;

				default:
					break;
			}
		} catch (IOException e) {
			errorFunc();
		}
	}

	public void listenForMessage(EditText textView) throws Exception {
		new Thread(new Runnable() {
			@RequiresApi(api = Build.VERSION_CODES.O)
			public void run() {
				String m;
				while (socket.isConnected()) {
					try {
						m = in.readLine();
						if(m==null){
							m="sign out";
						}
						if(waitnigForPublicKey) {
							try {
								decodePublicKeyReceived(m);
								waitnigForPublicKey = false;
							} catch(Exception e) {

							}
						} else {
							switch (encryptionType) {
								//plain text
								case 0:
									System.out.println(m);
									textView.setText("Incoming: " + m +  "\n" + textView.getText());
									break;

								//base 64 coding
								case 1:
									try{
										byte[] mDecoded = Base64.getDecoder().decode(m);
										m = new String(mDecoded);
										System.out.println(m);
										textView.setText("Incoming: " + m +  "\n" + textView.getText());
									} catch (Exception e) {
										System.out.println(m);
										textView.setText("Incoming: " + m +  "\n" + textView.getText());
									}
									break;

								//asymmetric encryption
								case 2:
									try{
										m = AsymmetricDecrypt(m);
										System.out.println(m);
										textView.setText("Incoming: " + m +  "\n" + textView.getText());
									} catch (Exception e) {
										System.out.println(m);
										textView.setText("Incoming: " + m +  "\n" + textView.getText());
										encryptionType = 0;
									}
									break;

								default:
									break;
							}
							//commands
							switch (m) {
								case "Enable Plain Text":
									encryptionType = 0;
									break;

								case "Enable Base64 Coding":
									encryptionType = 1;
									break;

								case "Enable Asymmetric Encryption":
									try{
										m = in.readLine();
										enableAsymmetricEncryption();
										decodePublicKeyReceived(m);
									} catch (Exception e) {
										System.out.println(m);
										textView.setText("Incoming: " + m +  "\n" + textView.getText());
									}
									//sending public key
									out.write(encodePublicKeySend());
									out.newLine();
									out.flush();

									encryptionType = 2;
									break;

								case "sign out":
									out.write("sign out");
									out.newLine();
									out.flush();
									break;

								default:
									break;
							}
						}

					} catch (IOException e) {
						errorFunc();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public String receiveMessage() {
		String m = null;
		try {
			m = in.readLine();
			if(m==null){
				m="sign out";
			}
			if(waitnigForPublicKey) {
				try {
					decodePublicKeyReceived(m);
					waitnigForPublicKey = false;
				} catch(Exception e) {
				}
			} else {
				switch (encryptionType) {
					//plain text
					case 0:
						System.out.println(m);
						break;
					//base 64 coding
					case 1:
						try {
							byte[] mDecoded = Base64.getDecoder().decode(m);
							m = new String(mDecoded);
							System.out.println(m);
						} catch (Exception e) {
							System.out.println(m);
						}
						break;
					//asymmetric encryption
					case 2:
						try {
							m = AsymmetricDecrypt(m);
							System.out.println(m);
						} catch (Exception e) {
							System.out.println(m);
							encryptionType = 0;
						}
						break;

					default:
						break;
				}
				return m;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return m;
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
	public void enableAsymmetricEncryption() throws Exception{
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(2048);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		privateKeySend = keyPair.getPrivate();
		publicKeySend = keyPair.getPublic();
		//System.out.println("your private key: " + privateKeySend);
		//System.out.println("your public  key: " + publicKeySend);

	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public String AsymmetricEncrypt(String message) throws Exception {
		Cipher encryptCipher  = Cipher.getInstance("RSA");
		encryptCipher.init(Cipher.ENCRYPT_MODE, publicKeyReceived);
		byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
		byte[] messageBytesEncrypted = encryptCipher.doFinal(messageBytes);
		String messageEncoded = Base64.getEncoder().encodeToString(messageBytesEncrypted);

		return messageEncoded;
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public String AsymmetricDecrypt(String message) throws Exception {
		Cipher decryptCipher  = Cipher.getInstance("RSA");
		decryptCipher.init(Cipher.DECRYPT_MODE, privateKeySend);
		byte[] decodedMessageBytes = Base64.getDecoder().decode(message);
		byte[] decryptedMessageBytes = decryptCipher.doFinal(decodedMessageBytes);
		String decryptedMessageString = new String(decryptedMessageBytes, StandardCharsets.UTF_8);

		return decryptedMessageString;
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public String encodePublicKeySend() throws Exception {
		byte[] publicKeyByte = publicKeySend.getEncoded();
		String publicKeyEncoded = Base64.getEncoder().encodeToString(publicKeyByte);

		return publicKeyEncoded;
	}

	@RequiresApi(api = Build.VERSION_CODES.O)
	public void decodePublicKeyReceived(String publicKeyEncoded) throws Exception {
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		byte[] publicKeyByte = Base64.getDecoder().decode(publicKeyEncoded);
		EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyByte);
		publicKeyReceived = keyFactory.generatePublic(publicKeySpec);

		//return publicKeyReceived;
	}
}
