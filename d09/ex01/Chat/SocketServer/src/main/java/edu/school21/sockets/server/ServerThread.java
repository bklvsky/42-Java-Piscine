package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.MessageService;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class ServerThread implements Runnable {
	@Autowired
	private UsersService usersService;
	private MessageService messageService;

	private Timestamp lastUpdate;
	private User currentUser;

	private String messageBuffer;
	private Socket clientSocket = null;
	private BufferedReader in;
	private BufferedWriter out;

	public ServerThread(Socket clientSocket, UsersService usersService, MessageService messageService) {
		this.usersService = usersService;
		this.clientSocket = clientSocket;
		this.messageService = messageService;
	}

	private Method getMethod(String command) throws NoSuchMethodException {
		Class<?>[] paramTypes = {String.class, String.class};
		return usersService.getClass().getMethod(command, paramTypes);
	}

	private Method getCommand() throws IOException {
		Method serviceMethod = null;
		while (serviceMethod == null) {
			try {
				String command = in.readLine();
				serviceMethod = getMethod(command);
			} catch (NoSuchMethodException e) {
				out.write("No such option, please try again\n");
				out.flush();
			}
		}
		return serviceMethod;
	}

	private Object[] getParameters(Method serviceMethod) throws IOException {
		Parameter[] parameters = serviceMethod.getParameters();
		List<Object> paramValues = new ArrayList<>();
		for (Parameter parameter: parameters) {
			out.write("Enter " + parameter.getName() + ":\n");
			out.flush();
			paramValues.add(in.readLine());
		}
		return paramValues.toArray();
	}

	synchronized private void invokeUserMethod(Method serviceMethod) throws IOException {
		while (true) {
			Object[] parameters = getParameters(serviceMethod);
			try {
				currentUser = (User) serviceMethod.invoke(usersService, parameters);
			} catch (InvocationTargetException e) {
				out.write(e.getCause().getMessage() + ". ");
				out.flush();
				continue;
			}
			catch (IllegalAccessException e) {
				e.printStackTrace();
				break;
			}
			break;
		}
	}

	private void registerAndSignIn() {
		try {
			Method command = getCommand();
			invokeUserMethod(command);
			if (currentUser == null){
				out.write("Now you can sign in. ");
				invokeUserMethod(getMethod("signIn"));
			}
			System.out.println("User " + currentUser.getUsername() + " signed in.");
			out.write("\n");
			out.flush();
		} catch (IOException | NoSuchMethodException e) {
			System.err.println(e.getCause().getMessage());
		}
	}

	private void manageMessages() throws IOException {
		while (true) {
			List<Message> messagesUpdate;
			if (lastUpdate  == null) {
				messagesUpdate = messageService.findLast(1);
			} else {
				messagesUpdate = messageService.findMessagesLaterThan(lastUpdate);
			}
			lastUpdate = new Timestamp(new Date().getTime());
			for (Message message : messagesUpdate) {
				if (message == null) {
					break;
				}
				out.write(message.getText() + "\n");
				out.flush();
			}
			out.write("\n");
			out.flush();
			String newMessage = in.readLine();
			if (newMessage.equals("Exit")) {
				break;
			}
			messageService.addMessage(newMessage, currentUser.getUsername());
		}
	}

	@Override
	public void run() {
		try {
			in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
			try {
				out.write("Hello from Server!\n");
				out.flush();
				registerAndSignIn();
				manageMessages();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				in.close();
				out.close();
				clientSocket.close();
				System.out.println("User " + currentUser.getUsername() + " left the chat.");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
