package school21.spring.service.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcImpl;


public class Main {
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("context.xml");
		UsersRepository usersRepositoryTempl = context.getBean("usersRepositoryJdbcTemplate", UsersRepository.class);
		UsersRepository usersRepositoryjdbc = context.getBean("usersRepositoryJdbc", UsersRepositoryJdbcImpl.class);


		System.out.println("---------------------------------");
		System.out.println("FindAll with JDBC Template:");
		System.out.println(usersRepositoryTempl.findAll());
		System.out.println("----");
		System.out.println("FindAll with JDBC");
		System.out.println(usersRepositoryjdbc.findAll());
		System.out.println("---------------------------------");

		User user = new User(null, "aaaaa@gmail.com");
		usersRepositoryTempl.save(user);
		System.out.println("Added new user:\n" + user);
		System.out.println("---------------------------------");

		user.setEmail("new_email@gmail.com");
		usersRepositoryTempl.update(user);
		System.out.println("Updated this user:\n" + usersRepositoryTempl.findById(user.getId()));
		System.out.println("---------------------------------");

		System.out.println("Finding a user with third_email@gmail.com");
		User thirdUser = usersRepositoryTempl.findByEmail("third_email@gmail.com").get();
		System.out.println(thirdUser);
		System.out.println("---------------------------------");

		System.out.println("Deleting this user");
		usersRepositoryTempl.delete(thirdUser.getId());
		System.out.println("---------------------------------");

		System.out.println("Final database via JDBC Template:");
		System.out.println(usersRepositoryTempl.findAll());
		System.out.println("----");
		System.out.println("Final database via JDBC ");
		System.out.println(usersRepositoryjdbc.findAll());
		System.out.println("---------------------------------");

		usersRepositoryTempl.delete(user.getId());
		usersRepositoryTempl.save(thirdUser);
	}
}
