package school21.spring.service.models;

import java.util.Objects;

public class User {

	private Long id;
	private String email;

	public User(Long id, String email) {
		this.id = id;
		this.email = email;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
						&& this.email.equals(user.email)
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, email);
	}

	@Override
	public String toString() {
		return (
				"User: {id=" + id
						+ ", email='" + email + "'}"
		);
	}
}
