package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {

	private static final String FIND_ALL_QUERY =
			" SELECT * FROM \"products\"";
	private static final String FIND_BY_ID_QUERY =
			"SELECT * FROM \"products\" WHERE ID = ";

	private static final String SAVE_QUERY =
			"INSERT INTO \"products\" (NAME, PRICE) " +
					"VALUES (?, ?)";

	private static final String UPDATE_QUERY =
			"UPDATE \"products\" SET NAME = ?, PRICE = ? WHERE ID = ?";

	private static final String DELETE_QUERY =
			"DELETE FROM \"products\" WHERE ID = ";

	private final DataSource ds;

	public ProductsRepositoryJdbcImpl(DataSource ds) {
		this.ds = ds;
	}

	@Override
	public List<Product> findAll() throws SQLException{
		List<Product> lst = new ArrayList<>();

		Connection con = ds.getConnection();
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(FIND_ALL_QUERY);
		while (result.next()) {
			long id = result.getLong("ID");
			String name = result.getString("NAME");
			Integer price = result.getInt("PRICE");

			lst.add(new Product(id, name, price));
		}
		return lst;
	}

	public Optional<Product> findById(long id) throws SQLException {
		Optional<Product> message;
		Connection con = ds.getConnection();
		Statement statement = con.createStatement();
		ResultSet result = statement.executeQuery(FIND_BY_ID_QUERY + id);

		if (!result.next()) {
			return Optional.empty();
		}

		message = Optional.of(
				new Product(
						result.getLong("ID"),
						result.getString("NAME"),
						result.getInt("PRICE")
				)
		);
		return message;
	}


	public void save(Product product) throws SQLException {

		try (Connection con = ds.getConnection()) {
			PreparedStatement statement = con.prepareStatement(SAVE_QUERY, Statement.RETURN_GENERATED_KEYS);
			ResultSet res;

			statement.setString(1, product.getName());
			statement.setInt(2, product.getPrice());
			statement.executeUpdate();
			res = statement.getGeneratedKeys();
			if (res.next()) {
				product.setId(res.getLong(1));
			}
		}
	}

	@Override
	public void update(Product product) throws SQLException {
		try (Connection con = ds.getConnection()) {
			PreparedStatement statement = con.prepareStatement(UPDATE_QUERY);
			statement.setString(1, product.getName());
			statement.setInt(2, product.getPrice());
			statement.setLong(3, product.getId());
			statement.executeUpdate();
		}
	}

	@Override
	public void delete(Long id) throws SQLException {
		try (Connection con = ds.getConnection()) {
			Statement statement = con.createStatement();
			statement.executeUpdate(DELETE_QUERY + id);
		}
	}
}
