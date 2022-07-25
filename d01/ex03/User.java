public class User {

	private final Integer IDENTIFIER;
	private String name;
	private Integer balance;
	private TransactionsList transactions;

	public User(String name, Integer balance) {
		this.IDENTIFIER = UserIdsGenerator.getInstance().generateId();
		if (balance < 0) {
			balance = 0;
		}
		this.transactions = new TransactionsLinkedList();
		setName(name);
		setBalance(balance);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getBalance() {
		return balance;
	}

	public void setBalance(Integer balance) {
		if (balance < 0) {
			balance = 0;
		}
		this.balance = balance;
	}

	public Integer getIdentifier() {
		return IDENTIFIER;
	}

	public TransactionsList getTransactions() {
		return transactions;
	}

	public void setTransactions(TransactionsList transactions) {
		this.transactions = transactions;
	}
}
