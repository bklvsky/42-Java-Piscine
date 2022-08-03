package edu.school21.sockets.server;

import edu.school21.sockets.exceptions.UserException;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.services.MessageService;
import edu.school21.sockets.services.UsersService;
import edu.school21.sockets.services.UsersServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.net.ServerSocket;
import java.net.Socket;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component("Server")
public class Server implements Runnable {

	// I SHOULD MAKE COMMANDLINE RUNNER TO PARSE ARGS
	// THEN MAKE ALL THIS SHIT BELOW BEANS IN SOCKETS_APPLICATION_CONFIG
	// AND THEEEEEN AUTOWIRE IT HERE!
	// MAKE EVERYTHING IN SERVER STATIC I THINK MAYBE
	// MAYBE NOT
	// WELL THAT'S IT BABY
	// https://stackoverflow.com/questions/53487768/how-to-provide-a-parameterized-component-in-spring

	private Boolean isStopped = false;
	private List<Thread> serverThreads;
	private UsersService usersService;
	private MessageService messageService;
	@Autowired
	private ServerSocket serverSocket;

	private final int port;


	@Autowired
	public Server(UsersService usersService, MessageService messageService, int port) {
		serverThreads = new ArrayList<>();
		this.usersService = usersService;
		this.messageService = messageService;
		this.port = port;
	}

	public void run() {
		System.out.println("Server is running");
		try {
			while (!isStopped) {
				Socket socket = serverSocket.accept();
				if (isStopped == true) {
					break;
				}
				System.out.println("Client connected to the server");
				Thread thread = new Thread(new ServerThread(socket, usersService, messageService));
				thread.start();
				serverThreads.add(thread);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				serverSocket.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println("Server finished successfully!");
	}

	public void stop() {isStopped = true;}
	public List<Thread> getServerThreads() { return serverThreads; }
}
