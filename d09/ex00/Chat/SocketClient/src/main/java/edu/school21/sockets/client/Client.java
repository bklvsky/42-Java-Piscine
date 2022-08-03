package edu.school21.sockets.client;

import java.io.*;
import java.net.Socket;

public class Client {
	private static Socket clientSocket;
	private static BufferedReader reader;
	private static BufferedReader in;
	private static BufferedWriter out;

	public static void run(int port) throws IOException {
		try {
			clientSocket = new Socket("localhost", port);
			reader = new BufferedReader(new InputStreamReader(System.in));
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

			String serverInput = in.readLine();
			while (!serverInput.equals("Successful!")) {
				System.out.println(serverInput);
				System.out.print("> ");
				out.write(reader.readLine() + "\n");
				out.flush();
				serverInput = in.readLine();
			}
			System.out.println(serverInput);
		} finally {
			clientSocket.close();
			in.close();
			out.close();
		}
	}
}
