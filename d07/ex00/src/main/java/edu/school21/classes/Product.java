package edu.school21.classes;

public class Product {
	private String name;
	private Integer price;
	private Boolean inStock;

	public Product() {
		this.name = "DEFAULT";
		this.price = 0;
		this.inStock = false;
	}

	public Product(String name, Integer price, Boolean inStock) {
		this.name = name;
		this.price = price;
		this.inStock = inStock;
	}

	public Boolean beSold() {
		Boolean res = this.inStock;
		inStock = false;
		return res;
	}

	@Override
	public String toString() {
		return (
				"Product[" +
						"name='" + name + "', " +
						"price=" + price + ", " +
						"inStock=" + inStock + "]"
				);
	}
}
