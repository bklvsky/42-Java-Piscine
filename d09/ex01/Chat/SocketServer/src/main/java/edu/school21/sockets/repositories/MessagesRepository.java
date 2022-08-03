package edu.school21.sockets.repositories;

import edu.school21.sockets.models.Message;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Component
public interface MessagesRepository extends CrudRepository<Message> {
	Optional<Message> findByText(String text);
	List<Message> findAfterLastUpdate(Timestamp lastUpdate);
	List<Message> findLast(int toFind);
}
