package edu.school21.sockets.services;

import edu.school21.sockets.exceptions.UserException;
import edu.school21.sockets.models.User;
import org.springframework.stereotype.Component;

@Component
public interface UsersService {
	void signUp(String username, String password) throws UserException;
	User signIn(String username, String password) throws UserException;
}


