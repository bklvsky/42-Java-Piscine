package edu.school21.sockets.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Timestamp;
import java.util.Date;

@Configuration
@PropertySource("db.properties")
@ComponentScan({
		"edu.school21.sockets.repositories",
		"edu.school21.sockets.services",
		"edu.school21.sockets.server",
		"edu.school21.sockets.models"})
public class SocketsApplicationConfig {

	@Value("${db.driver.name}")
	private String driverClassName;

	@Value("${db.url}")
	private String jdbcUrl;

	@Value("${db.user}")
	private String username;

	@Value("${ds.password}")
	private String password;

	@Value("${port}")
	private String port;

	@Bean
	public DataSource dataSource() {
		HikariDataSource ds = new HikariDataSource();
		ds.setDriverClassName(driverClassName);
		ds.setJdbcUrl(jdbcUrl);
		ds.setUsername(username);
		ds.setPassword(password);
		return ds;
	}


	@Bean
	public int port() {
		return Integer.parseInt(this.port);
	}

	@Bean
	@Autowired
	public ServerSocket serverSocket(int port) throws IOException {
		return new ServerSocket(port);
	}

	@Bean
	public PasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Timestamp lastUpdate() {
		return new Timestamp(new Date().getTime());
	}
}