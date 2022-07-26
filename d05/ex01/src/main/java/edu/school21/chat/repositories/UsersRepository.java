package edu.school21.chat.repositories;

import edu.school21.chat.models.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UsersRepository {
	Optional<User> findById(long id) throws SQLException;
}
