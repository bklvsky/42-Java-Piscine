public class UserIdsGenerator {

	private static Integer lastID;
	private static UserIdsGenerator instance;

	private UserIdsGenerator() {
		lastID = new Integer(0);
	}

	public static UserIdsGenerator getInstance() {
		if (instance == null) {
			instance = new UserIdsGenerator();
		}
		return instance;
	}

	public Integer generateId() {
		lastID += 1;
		return lastID;
	}

}
