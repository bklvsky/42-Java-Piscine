package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.ChatroomsRepository;
import edu.school21.chat.repositories.ChatroomsRepositoryJdbcImpl;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
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


	public static void main(String[] args) {
		long id;
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl("jdbc:postgresql://localhost:5432/" + DB_NAME);
		ds.setUsername(USER_NAME);
		ds.setPassword(SQL_PASSWORD);

		MessagesRepository msgRepo = new MessagesRepositoryJdbcImpl(ds);
		ChatroomsRepository roomRepo = new ChatroomsRepositoryJdbcImpl(ds);

		try (Scanner sc = new Scanner(System.in)) {
			initDB(ds);
			System.out.println("-> Enter a message ID");
			System.out.print("-> ");
			id = sc.nextLong();
			System.out.println("Looking for a message {"+ id + "}");
			System.out.println("...");
			Message msg = msgRepo.findById(id).orElse(null);
			System.out.println(msg);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
