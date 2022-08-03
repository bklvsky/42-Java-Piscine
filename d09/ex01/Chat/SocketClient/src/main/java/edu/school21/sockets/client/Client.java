package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;

public class Client {
	private static Boolean isStopped = false;
	private static Socket clientSocket;
	private static BufferedReader reader;
	private static BufferedReader in;
	private static BufferedWriter out;

	private static void userSignIn() throws IOException {
		String serverInput;
		while (!isStopped) {
			serverInput = in.readLine();
			if (serverInput.equals("")) {
				break;
			}
			System.out.println(serverInput);
			System.out.print("> ");
			out.write(reader.readLine() + "\n");
			out.flush();
		}
		System.out.println("Now you can message.");
	}

	private static void getMessages() throws IOException {
		String serverInput;
		while ((serverInput = in.readLine()) != null &&
				!serverInput.isEmpty()) {
			System.out.println(serverInput);
		}
	}

	private static void writeMessage() throws IOException {
		System.out.print("> ");
		String message = reader.readLine();
		if (message.equals("Exit")) {
			isStopped = true;
		}
		out.write(message + "\n");
		out.flush();
	}

	public static void run(int port) throws IOException {
		try {
			clientSocket = new Socket("localhost", port);
			reader = new BufferedReader(new InputStreamReader(System.in));
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

			userSignIn();
			while (!isStopped) {
				getMessages();
				writeMessage();
			}
			System.out.println("You have left the chat.");
		} finally {
			clientSocket.close();
			in.close();
			out.close();
		}
	}
}
