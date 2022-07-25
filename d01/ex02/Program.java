public class Program {

	private final static int TEST_ID = 3;
	private final static int TEST_INDEX = 10;

	public static void main(String[] args) {
		UsersList usrlst = new UsersArrayList();
		for (int i = 0; i < 20; i++) {
			if (i % 2 == 0) {
				usrlst.addUser(new User("ODD", 1000));
			} else {
				usrlst.addUser(new User("EVEN", 2000));
			}
		}
		System.out.println("Users in list = " + usrlst.getNumberOfUsers());
		System.out.println("User by ID " + TEST_ID +" is " 
						+ usrlst.getUserByID(TEST_ID).getName() + " ["
						+ usrlst.getUserByID(TEST_ID).getIdentifier()
						+ "]");
		System.out.println("User by index " + TEST_INDEX + " is " 
						+ usrlst.getUserByIndex(TEST_INDEX).getName() + " ["
						+ usrlst.getUserByIndex(TEST_INDEX).getIdentifier()
						+ "]");

		System.out.println("");
		try {
			System.out.println("Trying to access User by ID 100");
			System.out.println("User by ID 100 is " 
						+ usrlst.getUserByID(100).getName());
		} catch (UserNotFoundException e) {
			System.err.println(e.toString());
			System.err.println("");
		}
		try {
			System.out.println("Trying to access User by index 21");
			System.out.println("User by ID 21 is " 
						+ usrlst.getUserByIndex(21).getName());
					} catch (UserNotFoundException e) {
			System.err.println(e.toString());
			System.err.println("");
		}
		try {
			System.out.println("Trying to access User by Index 100");
			System.out.println("User by Index 100 is " 
			+ usrlst.getUserByIndex(100).getName());
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}
}
