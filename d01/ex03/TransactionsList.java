import java.util.UUID;

public interface TransactionsList {
	void addTransaction(Transaction tr);
	void removeTransaction(UUID id);
	Transaction[] toArray();
}
