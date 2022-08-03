package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Component
public class MessagesRepositoryImpl implements MessagesRepository {

	private static final String FIND_LAST_MESSAGES_QUERY =
			"SELECT * FROM messages ORDER BY sendingtime DESC LIMIT ?";
	private static final String FIND_ALL_QUERY =
			"SELECT * FROM messages";

	private static final String FIND_ALL_LATER_THAN =
			"SELECT * FROM messages WHERE sendingtime > ?";

	private static final String FIND_BY_ID_QUERY =
			"SELECT * FROM messages WHERE id = ";

	private static final String FIND_BY_TEXT_QUERY =
			"SELECT * FROM messages WHERE text = ?";

	private static final String UPDATE_QUERY =
			"UPDATE messages SET text = ? WHERE id = ?";

	private static final String DELETE_QUERY =
			"DELETE FROM messages WHERE id = ";

	private final DataSource dataSource;
	private final JdbcTemplate jdbcTemplate;

	@Autowired
	public MessagesRepositoryImpl(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	private static class MessageMapper implements RowMapper<Message> {

		@Override
		public Message mapRow(ResultSet rs, int rowNum) throws SQLException {
			return new Message(rs.getLong("id"), rs.getString("text"), rs.getTimestamp("sendingTime"));
		}
	}

	@Override
	public Message findById(Long id) {
		return (jdbcTemplate.queryForObject(FIND_BY_ID_QUERY + id, new MessageMapper()));
	}


	@Override
	public List<Message> findAll() {
		return jdbcTemplate.query(FIND_ALL_QUERY, new MessageMapper());
	}

	@Override
	public void save(Message entity) {
		SimpleJdbcInsert insert = new SimpleJdbcInsert(dataSource)
				.withTableName("messages").usingGeneratedKeyColumns("id");
		Map<String, Object> parameter = new HashMap<>();
		parameter.put("text", entity.getText());
		parameter.put("sendingtime", entity.getSendingTime());
		long id = (long) insert.executeAndReturnKey(parameter);
		entity.setId(id);
	}

	@Override
	public void update(Message entity) {
		jdbcTemplate.update(UPDATE_QUERY, entity.getText(), entity.getId());
	}

	@Override
	public void delete(Long id) {
		jdbcTemplate.update(DELETE_QUERY + id);
	}

	@Override
	public Optional<Message> findByText(String text) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_BY_TEXT_QUERY, new Object[] {text}, new MessageMapper()));
		} catch (DataAccessException e) {
			return Optional.empty();
		}
	}

	public List<Message> findLast(int toFind) {
		List<Message> messages = new ArrayList<>();
		return jdbcTemplate.query(FIND_LAST_MESSAGES_QUERY, new MessageMapper(), toFind);
	}

	public List<Message> findAfterLastUpdate(Timestamp lastUpdate){
		ArrayList<Message> messages = new ArrayList<>();
		messages = jdbcTemplate.query(FIND_ALL_LATER_THAN, new MessageMapper(), lastUpdate);
		Collections.reverse(messages);
		return me
	}
}

