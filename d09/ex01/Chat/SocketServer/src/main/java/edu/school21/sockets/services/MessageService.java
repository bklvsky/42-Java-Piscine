package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;

@Component
public interface MessageService {
	List<Message> findMessagesLaterThan(Timestamp lastUpdate);
	void addMessage(String text, String authorUsername);
	public List<Message> findLast(int toFind);
}
