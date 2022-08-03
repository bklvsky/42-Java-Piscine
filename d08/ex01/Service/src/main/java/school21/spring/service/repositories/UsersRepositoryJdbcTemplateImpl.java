package school21.spring.service.repositories;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import school21.spring.service.models.User;

import javax.sql.DataSource;
import javax.swing.plaf.basic.BasicTreeUI;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UsersRepositoryJdbcTemplateImpl implements UsersRepository {

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
	private final JdbcTemplate jdbcTemplate;

	public UsersRepositoryJdbcTemplateImpl(DataSource ds) {
		this.ds = ds;
		this.jdbcTemplate = new JdbcTemplate(ds);
	}

	private class UserMapper implements RowMapper<User> {

		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new User(rs.getLong("id"), rs.getString("email"));
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
		SimpleJdbcInsert insert = new SimpleJdbcInsert(ds)
				.withTableName("users").usingGeneratedKeyColumns("id");
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("email", entity.getEmail());
		long id = (long) insert.executeAndReturnKey(parameter);
		entity.setId(id);
	}

	@Override
	public void update(User entity) {
		jdbcTemplate.update(UPDATE_QUERY, entity.getEmail(), entity.getId());
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update(DELETE_QUERY + id);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_EMAIL_QUERY, new Object[] {email}, new UserMapper()));
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}
}
