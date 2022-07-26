package edu.school21.chat.validator;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Validator {

	private static final String USER_QUERY = "SELECT * from chat.users WHERE id = ";
	private static final String MESSAGE_QUERY = "SELECT * from chat.messages WHERE id = ";
	private static final String CHATROOM_QUERY = "SELECT * from chat.chatrooms WHERE id = ";
	private DataSource ds;
	private static Validator instance = null;

	private Validator(DataSource ds) {
		this.ds = ds;
	}

	public static Validator getInstance(DataSource ds) {
		if (instance == null) {
			instance = new Validator(ds);
		}
		return instance;
	}

	public boolean validateUser(User user) throws SQLException {
		Connection con = ds.getConnection();
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(USER_QUERY + user.getId());
		return result.next();
	}

	public boolean validateMessage(Message message) throws SQLException {
		Connection con = ds.getConnection();
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(MESSAGE_QUERY + message.getId());
		return result.next();
	}

	public boolean validateChatroom(Chatroom chatroom) throws SQLException {
		Connection con = ds.getConnection();
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(CHATROOM_QUERY + chatroom.getId());
		return result.next();
	}
}
