package edu.school21.chat.models;

import java.sql.Timestamp;
import java.util.Objects;

public class Message {

	private Long id;
	private final User author;
	private final Chatroom room;
	private final String text;
	private final Timestamp timestamp;

	public Message(Long id, User author, Chatroom room, String text, Timestamp timestamp) {
		this.id = id;
		this.author = author;
		this.room = room;
		this.text = text;
		this.timestamp = timestamp;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		Message msg = (Message) obj;
		return (
				this.id.equals(msg.id)
						&& this.author.equals(msg.author)
						&& this.room.equals(msg.room)
						&& this.text.equals(msg.text)
						&& this.timestamp.equals(msg.timestamp)
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, author, room, text, timestamp);
	}

	@Override
	public String toString() {
		return (
				"Message: {id=" + id
						+ ", author='" + author + "'"
						+ ", room='" + room + "'"
						+ ", text=" + text
						+ ", dateTime=" + timestamp
						+ "}"
		);
	}
	public Long getId() {
		return id;
	}

	public User getAuthor() {
		return author;
	}

	public Chatroom getRoom() {
		return room;
	}

	public String getText() {
		return text;
	}

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setId(long id) {
		this.id = id;
	}

}
