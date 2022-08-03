package edu.school21.classes;

public class User {
	private String firstName;
	private String lastName;
	private String login;
	private Integer age;

	public User() {
		this.firstName = "DEFAULT";
		this.lastName = "DEFAULT";
		this.login = "DEFAULT";
		this.age = 0;
	}

	public User(String firstName, String lastName, Integer age, String login) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.login = login;
		this.age = age;
	}

	public Integer growOlder(int years) {
		this.age += years;
		return this.age;
	}

	@Override
	public String toString() {
		return (
				"User[" +
						"firstName='" + firstName + "', " +
						"lastName='" + lastName + "', " +
						"login='" + login + "', " +
						"age=" + age + "]"
				);
	}
}
