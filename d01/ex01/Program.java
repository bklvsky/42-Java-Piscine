public class Program {
	public static void main(String[] args) {
		for (int i = 0; i < 9; i++) {
			User tmp;
			if (i % 2 == 0) {
				tmp = new User("RICH", 10000);
			} else {
				tmp = new User("POOR", 10);
			}
			System.out.format("[%d] %s USER with an ID %d\n", 
							i, tmp.getName(), tmp.getIdentifier().intValue());
		}
	}
}
