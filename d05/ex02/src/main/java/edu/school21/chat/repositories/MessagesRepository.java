package edu.school21.chat.repositories;

import edu.school21.chat.exceptions.NotSavedSubEntityException;
import edu.school21.chat.models.Message;

import java.sql.SQLException;
import java.util.Optional;

public interface MessagesRepository {
	public Optional<Message> findById(long id) throws SQLException;
	public void save(Message message) throws NotSavedSubEntityException, SQLException;
}
