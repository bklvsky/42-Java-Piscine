import java.util.UUID;

public class Transaction {

	private UUID identifier;

	private User recipient;
	private User sender;
	private Integer amount;

	public enum Category {

		INCOME,
		OUTCOME;
	}

	private Category category;


	public Transaction(User recipient, User sender, Integer amount, Category category) {
		if ((category == Category.INCOME) && (amount < 0)) {
			System.err.println("Invalid transaction");
			System.exit(-1);
		} else if ((category == Category.OUTCOME) && (amount > 0)) {
			System.err.println("Invalid transaction");
			System.exit(-1);
		}
		this.identifier = UUID.randomUUID();
		setCategory(category);
		setRecipient(recipient);
		setSender(sender);
		setAmount(amount);
	}

	public UUID getIdentifier() {
		return identifier;
	}

	public User getRecipient() {
		return recipient;
	}

	public void setRecipient(User recipient) {
		this.recipient = recipient;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
