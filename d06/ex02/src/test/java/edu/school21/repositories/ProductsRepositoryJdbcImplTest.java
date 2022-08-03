package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class ProductsRepositoryJdbcImplTest {

	private EmbeddedDatabase ds;
	private ProductsRepository repo;
	private List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
			new Product(1L, "apple", 100),
			new Product(2L, "pineapple", 250),
			new Product(3L, "cake", 600),
			new Product(4L, "bread", 50),
			new Product(5L, "coffee", 1500)
	);

	final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(
			2L, "pineapple", 250
	);

	final Product EXPECTED_UPDATED_PRODUCT = new Product(
			3L, "chocolate cake", 850
	);

	final Product EXPECTED_SAVED_PRODUCT = new Product(
			6L, "potatoes", 40
	);

	@BeforeEach
	void init() {
		ds = new EmbeddedDatabaseBuilder()
				.setType(EmbeddedDatabaseType.HSQL)
				.addScript("schema.sql")
				.addScript("data.sql")
				.build();
		repo = new ProductsRepositoryJdbcImpl(ds);
	}

	@Test
	void testFindAll() throws SQLException {
		Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, repo.findAll());
	}

	@Test
	void testFindById() throws SQLException {
		Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, repo.findById(2).get());
	}

	@Test
	void testSave() throws SQLException {
		repo.save(EXPECTED_SAVED_PRODUCT);
		Assertions.assertEquals(
				EXPECTED_SAVED_PRODUCT,
				repo.findById(EXPECTED_SAVED_PRODUCT.getId()).get()
		);
	}

	@Test
	void testUpdate() throws SQLException {
		repo.update(EXPECTED_UPDATED_PRODUCT);
		Assertions.assertEquals(
				EXPECTED_UPDATED_PRODUCT,
				repo.findById(EXPECTED_UPDATED_PRODUCT.getId()).get()
		);
	}

	@Test
	void testDelete() throws SQLException {
		repo.delete(1L);
		Assertions.assertFalse(repo.findById(1L).isPresent());
	}

	@AfterEach
	void shutdown() {
		ds.shutdown();
	}
}
