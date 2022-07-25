public class Program {

	private static void printUser(User usr) {
        System.out.println(
            "Guy № " + usr.getIdentifier() + " "
            + usr.getName() + " with a balance "
            + usr.getBalance() + "$"
        );
    }

    public static void printTransaction(Transaction tr) {
		if (tr == null) {
			System.out.println("null");
			return;
		}
        System.out.println(
            tr.getCategory() + " Transaction № "
            + tr.getIdentifier()+ "(" + tr.getAmount()
            + "$)"
        );
        System.out.print("From: ");
        printUser(tr.getSender());
        System.out.print("To: ");
        printUser(tr.getRecipient());
    }

	public static void main(String[] args) {
		User boba = new User("Boba", 1000);
		User biba = new User("Buba", 5000);
		TransactionsList trLst = new TransactionsLinkedList();
		
		for (int i = 0; i < 5; i++) {
			trLst.addTransaction(new Transaction(boba, biba, i * 100, 
										Transaction.Category.INCOME));
		}
		Transaction[] trArr = trLst.toArray();
		for (int i = 0; i < 5; i++) {
			System.out.format("[%d] ", i);
			printTransaction(trArr[i]);
			System.out.println("");
		}

		System.out.println("Removing one transaction");
		System.out.println("...");
		trLst.removeTransaction(trArr[2].getIdentifier());
		System.out.println("Trying to remove in again");
		try {
			trLst.removeTransaction(trArr[2].getIdentifier());
		} catch (TransactionNotFoundException e) {
			System.err.println(e.toString());
		}
	}
}
