package edu.school21.sockets.server;

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
public class Server {
	
	private UsersService usersService;
	@Autowired
	private ServerSocket serverSocket;
	private Socket socket;
	private BufferedReader in;
	private BufferedWriter out;

	@Autowired
	public Server(UsersService usersService) throws IOException {
		this.usersService = usersService;
	}


	private Method getCommand(String command) throws IOException {
		try {
			Class<?>[] paramTypes = {String.class, String.class};
			return usersService.getClass().getMethod(command, paramTypes);
		} catch (NoSuchMethodException e) {
			out.write("No such option, please try again\n");
			out.flush();
			return null;
		}
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

	public void run() throws IOException, InvocationTargetException, IllegalAccessException {
		try {
			System.out.println("Server is running");
			this.socket = serverSocket.accept();
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			out.write("Hello from Server!\n");
			out.flush();
			Method serviceMethod = null;
			Object[] parameters;

			while (serviceMethod == null) {
				serviceMethod = getCommand(in.readLine());
			}
			parameters = getParameters(serviceMethod);
			serviceMethod.invoke(usersService, parameters);
			out.write("Successful!\n");
			out.flush();
		} finally {
			socket.close();
			in.close();
			out.close();
			serverSocket.close();
		}
		System.out.println("Server finished successfully!");
	}
}
