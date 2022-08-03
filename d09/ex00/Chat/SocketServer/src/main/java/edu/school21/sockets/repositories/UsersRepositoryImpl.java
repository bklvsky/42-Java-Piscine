package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class UsersRepositoryImpl implements UsersRepository {

	private static final String FIND_ALL_QUERY =
			"SELECT * FROM users";

	private static final String FIND_BY_ID_QUERY =
			"SELECT * FROM users WHERE id = ";

	private static final String FIND_BY_USERNAME_QUERY =
			"SELECT * FROM users WHERE username = ?";

	private static final String UPDATE_QUERY =
			"UPDATE users SET username = ? WHERE id = ?";

	private static final String DELETE_QUERY =
			"DELETE FROM users WHERE id = ";

	private final DataSource dataSource;
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public UsersRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private class UserMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new User(rs.getLong("id"), rs.getString("username"), rs.getString("password"));
		}
	}

	@Override
	public User findById(Long id) {
		return (jdbcTemplate.queryForObject(FIND_BY_ID_QUERY + id, new UserMapper()));
	}


	@Override
	public List<User> findAll() {
		return jdbcTemplate.query(FIND_ALL_QUERY, new UserMapper());
	}

	@Override
	public void save(User entity) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource)
				.withTableName("users").usingGeneratedKeyColumns("id");
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("username", entity.getUsername());
		parameter.put("password", entity.getPassword());
		long id = (long) insert.executeAndReturnKey(parameter);
		entity.setId(id);
	}

	@Override
	public void update(User entity) {
		jdbcTemplate.update(UPDATE_QUERY, entity.getUsername(), entity.getId());
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update(DELETE_QUERY + id);
	}

	@Override
	public Optional<User> findByUsername(String username) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_USERNAME_QUERY, new Object[] {username}, new UserMapper()));
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}
}
