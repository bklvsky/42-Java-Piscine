package edu.school21.chat.exceptions;

public class NotSavedSubEntityException extends RuntimeException{
	public NotSavedSubEntityException() {
		super("Invalid input data. The entity was not saved.");
	}
}
