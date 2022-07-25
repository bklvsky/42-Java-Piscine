public class UsersArrayList implements UsersList {

	private Integer numberOfUsers;
	private Integer size;
	private User[] userArray;

	public UsersArrayList() {
		this.numberOfUsers = 0;
		this.size = 10;
		userArray = new User[size];
	}

	public void addUser(User user) {
		if (numberOfUsers + 1 == size) {
			reallocateUsers();
		}
		this.userArray[numberOfUsers] = user;
		this.numberOfUsers += 1;
	}

	public User getUserByID(Integer identifier) {
		for (int i = 0; i < this.numberOfUsers; i++) {
			if (this.userArray[i].getIdentifier() == identifier) {
				return this.userArray[i];
			}
		}
		throw new UserNotFoundException("ID [" + identifier + "] invalid");
	}

	public User getUserByIndex(int index) {
		if (this.userArray[index] == null) {
			throw new UserNotFoundException("index [" + index + "] invalid");
		}
		return this.userArray[index];
	}
	public Integer getNumberOfUsers() {
		return this.numberOfUsers;
	}
	
	private void reallocateUsers() {
		Integer newSize = new Integer((int) (this.size * 1.5 + 1));
		User[] newArr = new User[newSize];
		for (int i = 0; i < size; i++) {
			newArr[i] = this.userArray[i];
		}
		this.size = newSize;
		this.userArray = newArr;
	}
}
