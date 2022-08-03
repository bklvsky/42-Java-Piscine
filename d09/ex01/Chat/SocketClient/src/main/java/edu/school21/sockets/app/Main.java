package edu.school21.sockets.app;

import edu.school21.sockets.client.Client;

import java.io.IOException;

public class Main {

	private static void printUsage() {
		System.out.println("Usage  java -jar target/socket-server.jar --server-port=[port_number]");
	}

	private static int getPort(String[] args) {
		int port = 0;

		if (args.length != 1
					|| !args[0].startsWith("--server-port=")) {
			printUsage();
			System.exit(1);
		}
		try {
			port = Integer.parseInt(args[0].substring("--server-port=".length()));
		} catch (NumberFormatException e) {
			System.err.print("Wrong port formatting");
			printUsage();
			System.exit(1);
		}
		return port;
	}

	public static void main(String[] args) {
		int port = getPort(args);
		try {
			Client.run(port);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
