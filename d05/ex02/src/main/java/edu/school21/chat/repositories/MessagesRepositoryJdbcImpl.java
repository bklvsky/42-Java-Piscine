package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.validator.Validator;

import javax.sql.DataSource;
import java.sql.*;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {

	private static final String QUERY_STRING =
			"SELECT messages.id, messages.author, " + "messages.room, messages.text, messages.timestamp, " +
					"users.login AS auth_login, users.password AS author_password, " +
					"chatrooms.name AS  chatroom_name, " +
					"ch_owner.id AS ch_owner_id, " +
					"ch_owner.login AS ch_owner_login, ch_owner.password AS ch_owner_password " +
					"FROM chat.messages messages, " +
					"chat.users users, " +
					"chat.chatrooms chatrooms, " +
					"chat.users ch_owner " +
					"WHERE users.id = messages.author AND " +
					"chatrooms.id=messages.room AND " +
					"ch_owner.id=chatrooms.owner AND " +
					"messages.id = ";

	private static final String SAVE_QUERY =
			"INSERT INTO chat.messages (author, room, text, timestamp) " +
					"VALUES (?, ?, ?, ?)";
	private final DataSource ds;

	public MessagesRepositoryJdbcImpl(DataSource ds) {
		this.ds = ds;
	}

	public Optional<Message> findById(long id) throws SQLException {
		Optional<Message> message;
		Connection con = ds.getConnection();
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(QUERY_STRING + id);
		if (!result.next()) {
			return Optional.empty();
		}


		message = Optional.of(
				new Message(
						result.getLong("id"),
						new User(
								result.getLong("author"),
								result.getString("auth_login"),
								result.getString("author_password"),
								null, null
						),
						new Chatroom(
								result.getLong("room"),
								result.getString("chatroom_name"),
								new User(
										result.getLong("ch_owner_id"),
										result.getString("ch_owner_login"),
										result.getString("ch_owner_password"),
										null, null
								),
								null
						),
						result.getString("text"),
						result.getTimestamp("timestamp")
				)
		);
		return message;
	}


	public void save(Message message) throws NotSavedSubEntityException, SQLException {

		User author = message.getAuthor();
		Chatroom room = message.getRoom();
		Validator vl = Validator.getInstance(ds);
		if (!vl.validateUser(author) || !vl.validateChatroom(room)) {
			throw new NotSavedSubEntityException();
		}
		try (Connection con = ds.getConnection()) {
			PreparedStatement statement = con.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
			ResultSet res;

			statement.setLong(1, author.getId());
			statement.setLong(2, room.getId());
			statement.setString(3, message.getText());
			statement.setTimestamp(4, message.getTimestamp());
			statement.executeUpdate();
			res = statement.getGeneratedKeys();
			if (res.next()) {
				message.setId(res.getLong(1));
			} else {
				throw new NotSavedSubEntityException();
			}
		}
	}
}
