package edu.school21.sockets.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Objects;

public class Message {
	private Long id;
	private String text;
	private Timestamp sendingTime;

	public Message(Long id, String text, Timestamp sendingTime) {
		this.id = id;
		this.text = text;
		this.sendingTime = sendingTime;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Timestamp getSendingTime() {
		return sendingTime;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		Message message = (Message) obj;
		return (
				this.id.equals(message.id)
						&& this.text.equals(message.text)
						&& this.sendingTime.equals(message.sendingTime)
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, text, sendingTime);
	}

	@Override
	public String toString() {
		return (
				"Message: {id=" + id
						+ ", text='" + text + "'}"
		);
	}
}
