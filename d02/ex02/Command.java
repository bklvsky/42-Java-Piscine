public class Command {

	private static final String[] types = {
		"cd",
		"ls",
		"mv"
	};

	private int type = -1;
	private String[] args;

	public Command(String input) {
		String[] args = input.split("[\\s]");
		this.setType(args[0]);
		this.setArgs(args);
	}

	private void setType(String arg) {
		for (int i = 0; i < types.length; i++) {
			if (arg.equals(types[i])) {
				this.type = i;
				break;
			}
		}
	}

	private void setArgs(String[] args) {
		this.args = new String[args.length - 1];
		for (int i = 1; i < args.length; i++) {
			this.args[i - 1] = args[i];
		}
	}

	public int getType() {
		return this.type;
	}

	public String[] getArgs() {
		return this.args;
	}
}
