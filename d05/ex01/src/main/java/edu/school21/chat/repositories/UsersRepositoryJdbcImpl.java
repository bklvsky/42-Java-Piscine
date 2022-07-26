package edu.school21.chat.repositories;

import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
	private static final String QUERY_STRING = "SELECT * FROM chat.users WHERE id = ";

	private final DataSource ds;

	public UsersRepositoryJdbcImpl(DataSource ds) {
		this.ds = ds;
	}

	public Optional<User> findById(long id) throws SQLException {
		Optional<User> user;
		Connection con = ds.getConnection();
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(QUERY_STRING + id);
		result.next();
		user = Optional.of(new User(
				result.getLong("id"),
				result.getString("login"),
				result.getString("password"),
				null,
				null
		));
		return user;
	}
}
