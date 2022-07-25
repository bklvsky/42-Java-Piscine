public interface UsersList {
	public void addUser(User user);
	public User getUserByID(Integer identifier);
	public User getUserByIndex(int index);
	public Integer getNumberOfUsers();
}
