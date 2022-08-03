package edu.school21.models;

import java.util.Objects;

public class Product {

	private Long id;
	private final String name;
	private final Integer price;

	public Product(Long id, String name, Integer price) {
		this.id = id;
		this.name = name;
		this.price = price;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || this.getClass() != obj.getClass())
			return false;
		Product msg = (Product) obj;
		return (
				this.id.equals(msg.id)
						&& this.name.equals(msg.name)
						&& this.price.equals(msg.price)
		);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, name, price);
	}

	@Override
	public String toString() {
		return (
				"Message: {\nid=" + id
						+ ",\nname='" + name + "'"
						+ ",\nprice='" + price + "'"
						+ "}"
		);
	}
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getPrice() {
		return price;
	}


	public void setId(long id) { this.id = id; }
}
