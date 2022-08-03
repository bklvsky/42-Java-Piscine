package edu.school21.sockets.services;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.repositories.MessagesRepository;
import edu.school21.sockets.repositories.MessagesRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
public class MessagesServiceImpl implements MessageService {

	private MessagesRepository messagesRepository;

	@Autowired
	public MessagesServiceImpl (MessagesRepository messagesRepository) {
		this.messagesRepository = messagesRepository;
	}
	@Override
	public List<Message> findMessagesLaterThan(Timestamp lastUpdate) {
		return this.messagesRepository.findAfterLastUpdate(lastUpdate);
	}

	public void addMessage(String text, String authorUsername) {
		messagesRepository.save(new Message(
				null,
				authorUsername + ": " + text,
				new Timestamp(new Date().getTime())
		));
	}

	public List<Message> findLast(int toFind) {
		return messagesRepository.findLast(toFind);
	}
}
