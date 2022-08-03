package edu.school21.sockets.services;

import edu.school21.sockets.exceptions.UserException;
import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UsersServiceImpl implements UsersService {
	private UsersRepository usersRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;


	@Autowired
	public UsersServiceImpl(UsersRepository usersRepository) {
	this.usersRepository = usersRepository;
	}

	public void signUp(String username, String password) throws UserException {
		if (usersRepository.findByUsername(username).isPresent()) {
			throw new UserException("The user already exists");
		}
		usersRepository.save(new User(null, username, passwordEncoder.encode(password)));
	}

	public User signIn(String username, String password) throws UserException {
		User userByUsername = usersRepository.findByUsername(username).get();
		if (userByUsername == null) {
			throw new UserException("User doesn't exist");
		}
		if (!passwordEncoder.matches(password, userByUsername.getPassword())) {
			throw new UserException("Wrong credentials");
		}
		return userByUsername;
	}
}
