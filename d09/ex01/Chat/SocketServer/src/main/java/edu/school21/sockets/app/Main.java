package edu.school21.sockets.app;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.repositories.UsersRepository;
import edu.school21.sockets.repositories.UsersRepositoryImpl;
import edu.school21.sockets.server.Server;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.CommandLinePropertySource;
import org.springframework.core.env.SimpleCommandLinePropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;

public class Main {

	private static void printUsage() {
		System.out.println("Usage  java -jar target/socket-server.jar --port=[port_number]");
	}

	private static void checkArg(String[] args) {
		Integer port = 0;

		if (args.length != 1
					|| !args[0].startsWith("--port=")) {
			printUsage();
			System.exit(1);
		}
		try {
			port = Integer.parseInt(args[0].substring("--port=".length()));
		} catch (NumberFormatException e) {
			System.err.print("Wrong port formatting");
			printUsage();
			System.exit(1);
		}
	}

	@Component
	private static class ServerStopper implements Runnable {
		private Server server;
		private Thread serverThread;

		@Autowired
		public ServerStopper(Server server, Thread serverThread) {
			this.server = server;
			this.serverThread = serverThread;
		}
		public void run() {
			Scanner sc = new Scanner(System.in);
			while (true) {
				String input = sc.nextLine();
				if (input.equals("stop")) {
					break;
				}
			}
			System.out.println("Stopping server...");
			server.stop();
			serverThread.interrupt();
			for (Thread threads: server.getServerThreads()) {
				threads.interrupt();
			}
		}
	}

	public static void main(String[] args) {
		checkArg(args);
		SimpleCommandLinePropertySource propertySource = new SimpleCommandLinePropertySource(args);
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
		context.register(SocketsApplicationConfig.class);
		context.getEnvironment().getPropertySources().addFirst(propertySource);
		context.refresh();


		Server server = (Server) context.getBean("Server");
		Thread serverThread = new Thread(server);
		serverThread.start();

		Thread serverStopper = new Thread(new ServerStopper(server, serverThread));
		serverStopper.start();

		try {
			serverThread.join();
		} catch (InterruptedException e) {
			serverThread.interrupt();
		}
	}
}
