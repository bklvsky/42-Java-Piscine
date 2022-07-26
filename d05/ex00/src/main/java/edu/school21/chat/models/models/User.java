package edu.school21.chat.models;

import java.util.ArrayList;
import java.util.Objects;

public class User {

	private Long id;
	private String login;
	private String password;
	private ArrayList<Chatroom> createdRooms;
	private ArrayList<Chatroom> chatrooms;

	public User(Long id, String login, String password,
		 ArrayList<Chatroom> createdRooms, ArrayList<Chatroom> rooms) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.createdRooms = createdRooms;
		this.chatrooms = rooms;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		User user = (User) obj;
		return (
				this.id.equals(user.id)
						&& this.login.equals(user.login)
						&& this.password.equals(user.password)
						&& this.createdRooms.equals(user.createdRooms)
						&& this.chatrooms.equals(user.chatrooms)
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, login, password, createdRooms, chatrooms);
	}

	@Override
	public String toString() {
		return (
				"User: {id=" + id
						+ ", login='" + login + "'"
						+ ", createdRooms=" + createdRooms
						+ ", chatrooms=" + chatrooms
						+ "}"
		);
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public ArrayList<Chatroom> getCreatedRooms() {
		return createdRooms;
	}

	public ArrayList<Chatroom> getChatrooms() {
		return chatrooms;
	}
}
