package edu.school21.chat.repositories;

import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class ChatroomsRepositoryJdbcImpl implements ChatroomsRepository {
	private static final String QUERY_STRING =
			"SELECT room.id, room.name, " +
			"ch_owner.id AS ch_owner_id, " +
			"ch_owner.login AS ch_owner_login, " +
			"ch_owner.password AS ch_owner_password " +
			"from chat.chatrooms room, " +
			"chat.users ch_owner " +
			"WHERE ch_owner.id=room.owner AND " +
			"room.id = ";

	private final DataSource ds;


	public ChatroomsRepositoryJdbcImpl(DataSource ds) {
		this.ds = ds;
	}

	public Optional<Chatroom> findById(long id) throws SQLException {
		Optional<Chatroom> chatroom;
		Connection con = ds.getConnection();
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(QUERY_STRING + id);
		if (!result.next()) {
			return Optional.empty();
		}
		chatroom = Optional.of(
				new Chatroom(
						result.getLong("id"),
						result.getString("name"),
						new User(
								result.getLong("ch_owner_id"),
								result.getString("ch_owner_login"),
								result.getString("ch_owner_password"),
								null, null
						),
						null
				)
		);
		return chatroom;
	}
}
