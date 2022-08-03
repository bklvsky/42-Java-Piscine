package edu.school21.sockets.models;

import org.springframework.stereotype.Component;

import java.util.Objects;

public class User {

	private Long id;
	private String username;
	private String password;

	public User(Long id, String username, String password) {
		this.id = id;
		this.username = username;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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
						&& this.username.equals(user.username)
						&& this.password.equals(user.password)
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, password);
	}

	@Override
	public String toString() {
		return (
				"User: {id=" + id
						+ ", username='" + username + "'}"
		);
	}
}
