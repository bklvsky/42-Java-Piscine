package school21.spring.service.repositories;

import school21.spring.service.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {

	private static final String FIND_ALL_QUERY =
			"SELECT * FROM users";

	private static final String FIND_BY_ID_QUERY =
			"SELECT * FROM users WHERE id = ";

	private static final String FIND_BY_EMAIL_QUERY =
			"SELECT * FROM users WHERE email = ?";

	private static final String SAVE_QUERY =
			"INSERT INTO users (email) " +
					"VALUES (?)";

	private static final String UPDATE_QUERY =
			"UPDATE users SET email = ? WHERE id = ?";

	private static final String DELETE_QUERY =
			"DELETE FROM users WHERE id = ";
	private final DataSource ds;

	public UsersRepositoryJdbcImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public User findById(Long id) {
		try {
			Connection con = ds.getConnection();
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(FIND_BY_EMAIL_QUERY + id);

			return new User(
							result.getLong("id"),
							result.getString("email")
					);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<User> findAll() {
		List<User> lst = new ArrayList<>();

		try {
			Connection con = ds.getConnection();
			Statement statement = con.createStatement();
			ResultSet result = statement.executeQuery(FIND_ALL_QUERY);
			while (result.next()) {
				long id = result.getLong("id");
				String email = result.getString("email");

				lst.add(new User(id, email));
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		return lst;
	}

	@Override
	public void save(User entity) {
		try (Connection con = ds.getConnection()) {
			PreparedStatement statement = con.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
			ResultSet res;

			statement.setString(1, entity.getEmail());
			statement.executeUpdate();
			res = statement.getGeneratedKeys();
			if (res.next()) {
				entity.setId(res.getLong(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void update(User entity) {
		try (Connection con = ds.getConnection()) {
			PreparedStatement statement = con.prepareStatement(UPDATE_QUERY);
			statement.setString(1, entity.getEmail());
			statement.setLong(2, entity.getId());
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(Long id) {
		try (Connection con = ds.getConnection()) {
			Statement statement = con.createStatement();
			statement.executeUpdate(DELETE_QUERY + id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Optional<User> findByEmail(String email) {
		ResultSet result;
		try {
			Connection con = ds.getConnection();
			PreparedStatement statement = con.prepareStatement(FIND_BY_EMAIL_QUERY);
			statement.setString(1, email);
			result = statement.executeQuery();

			return Optional.of(new User(
							result.getLong("id"),
							result.getString("email")
							));
		} catch (SQLException e) {
			return Optional.empty();
		}
	}
}
