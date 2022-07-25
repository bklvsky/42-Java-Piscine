import java.util.UUID;

public class Program {

    private static void printUser(User usr) {
        System.out.println(
            "Guy № " + usr.getIdentifier() + " "
            + usr.getName() + " with a balance "
            + usr.getBalance() + "$"
        );
    }

    private static void printTransaction(Transaction tr) {
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

    private static void makeInvalidTransaction(User usr1, User usr2) {
        Transaction invalid = new Transaction(usr1, usr2, -100,
                                        Transaction.Category.INCOME);
        printTransaction(invalid);
    }

    public static void main(String[] args) {
        User johnRich = new User("John Rich", 1000);
        User johnPoor = new User("John Poor", -100);
        Transaction trCredit = new Transaction(johnPoor, johnRich, 100, Transaction.Category.INCOME);
        Transaction trDebit = new Transaction(johnRich, johnPoor, -100, Transaction.Category.OUTCOME);


        johnRich.setIdentifier(1);
        printUser(johnRich);

        johnPoor.setIdentifier(2);
        printUser(johnPoor);

        System.out.println("");

        trCredit.setIdentifier(UUID.randomUUID());
        printTransaction(trCredit);

        System.out.println("");

        trDebit.setIdentifier(UUID.randomUUID());
        printTransaction(trDebit);

        System.out.println("");

        makeInvalidTransaction(johnPoor, johnRich);
    }
}
