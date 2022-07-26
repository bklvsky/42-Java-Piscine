package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.Objects;

public class Chatroom {

	private Long id;
	private String name;
	private User owner;
	private ArrayList<Message> messages;

	public Chatroom(Long id, String name, User owner, ArrayList<Message> messages) {
		this.id = id;
		this.name = name;
		this.owner = owner;
		this.messages = messages;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		Chatroom room = (Chatroom) obj;
		return (
				this.id.equals(room.id)
						&& this.name.equals(room.name)
						&& this.owner.equals(room.owner)
						&& this.messages.equals(room.messages)
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, owner, messages);
	}

	@Override
	public String toString() {
		return (
				"Chatroom: {id=" + id
						+ ", name='" + name + "'"
						+ ", owner='" + owner + "'"
						+ ", messages=" + messages
						+ "}"
		);
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public User getOwner() {
		return owner;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}
}
