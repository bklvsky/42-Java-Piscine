package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.*;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.Scanner;

public class Program {
	private static final String DATA_SQL = "/resources/data.sql";

	private static final String USER_NAME = "chatadmin";
	private static final String DB_NAME = "chat";

	private static final String SQL_PASSWORD = "123";

	private static void runQueriesFromFile
			(HikariDataSource ds,
			 File file) throws FileNotFoundException {
		try (Scanner sc = new Scanner(file).useDelimiter(";")) {
			Connection con = ds.getConnection();
			Statement statement = con.createStatement();
			while (sc.hasNext()) {
				statement.execute(sc.next());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void initDB(HikariDataSource ds) throws FileNotFoundException, URISyntaxException
	{
		File data = new File(System.getProperty("user.dir") + "/src/main/" + DATA_SQL);
		runQueriesFromFile(ds, data);
	}

	private static User getUser(DataSource ds, Scanner sc) throws SQLException {
		UsersRepository userRepo = new UsersRepositoryJdbcImpl(ds);
		Optional<User> user;
		while (true) {
			System.out.println("");
			System.out.println("Enter a user ID");
			System.out.print("-> ");
			long id = sc.nextLong();
			System.out.println("Looking for user{" + id + "}");
			System.out.println("...");
			user = userRepo.findById(id);
			if (user.isPresent()) {
				System.out.println("SUCCESS");
				break;
			}
			System.out.println("No such User in the database.");
		}
		return user.get();
	}

	private static Chatroom getChatroom(DataSource ds, Scanner sc) throws SQLException {
		ChatroomsRepository roomRepo = new ChatroomsRepositoryJdbcImpl(ds);
		Optional<Chatroom> chatroom;
		while (true) {
			System.out.println("");
			System.out.println("Enter a chatroom ID");
			System.out.print("-> ");
			long id = sc.nextLong();
			System.out.println("Looking for chatroom{" + id + "}");
			System.out.println("...");
			chatroom = roomRepo.findById(id);
			if (chatroom.isPresent()) {
				System.out.println("SUCCESS");
				break;
			}
			System.out.println("No such chatroom in the database.");
		}
		return chatroom.get();
	}

	public static void main(String[] args) {
		long id;
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl("jdbc:postgresql://localhost:5432/" + DB_NAME);
		ds.setUsername(USER_NAME);
		ds.setPassword(SQL_PASSWORD);

		MessagesRepository msgRepo = new MessagesRepositoryJdbcImpl(ds);

		try (Scanner sc = new Scanner(System.in)) {
			initDB(ds);
			String text;
			User author = getUser(ds, sc);
			Chatroom room = getChatroom(ds, sc);

			System.out.println("");
			System.out.println("Enter a message text");
			System.out.print("-> ");
			sc.nextLine();
			text = sc.nextLine();

			Message message = new Message(null, author, room, text, new Timestamp(new Date().getTime()));
			msgRepo.save(message);
			System.out.println("Message was successfully saved!");
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			System.out.println("");
			System.out.println("---------------------------------------------");
			System.out.println("Trying to save a message with an invalid user");
			System.out.println("---------------------------------------------");

			User author = new User(7L, "lol", "123", null, null);
			Chatroom room = new Chatroom(8L, "null", null, null);
			Message message = new Message(
					null,
					author,
					room,
					"It won't be sent",
					new Timestamp(new Date().getTime())
			);
			msgRepo.save(message);

		} catch (NotSavedSubEntityException e) {
			System.out.println(e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
