package edu.school21.chat.app;

import com.zaxxer.hikari.HikariDataSource;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Program {

	private static final String SCHEMA_SQL = "/resources/schema.sql";
	private static final String DATA_SQL = "/resources/data.sql";

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
		File schema = new File(System.getProperty("user.dir") + "/src/main/" + SCHEMA_SQL);
		runQueriesFromFile(ds, schema);
		File data = new File(System.getProperty("user.dir") + "/src/main/" + DATA_SQL);
		runQueriesFromFile(ds, data);
	}


	public static void main(String[] args) {
		long id;
		HikariDataSource ds = new HikariDataSource();
		ds.setJdbcUrl("jdbc:postgresql://localhost:5432/ex01");
		ds.setUsername("skach");
		ds.setPassword("123");

		MessagesRepository msgRepo = new MessagesRepositoryJdbcImpl(ds);
		ChatroomsRepository roomRepo = new ChatroomsRepositoryJdbcImpl(ds);

		try (Scanner sc = new Scanner(System.in)) {
			initDB(ds);
			System.out.println("-> Enter a message ID");
			System.out.print("-> ");
			id = sc.nextLong();
			Message msg = msgRepo.findById(id).orElse(null);
			System.out.println(msg);
			System.out.println("-> Enter a chatroom ID");
			System.out.print("-> ");
			id = sc.nextLong();
			System.out.println("Looking for a chat room "+ id);
			System.out.println("...");

			Chatroom room = roomRepo.findById(id).orElse(null);
			System.out.println(room);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
