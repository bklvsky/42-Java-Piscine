package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
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

	public void signUp(String username, String password) {
		usersRepository.save(new User(null, username, passwordEncoder.encode(password)));
	}
}
