public class User {

	private final Integer IDENTIFIER;
	private String name;
	private Integer balance;

	public User(String name, Integer balance) {
		this.IDENTIFIER = UserIdsGenerator.getInstance().generateId();
		if (balance < 0) {
			balance = 0;
		}
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
}
