import java.io.IOException;
import java.util.Scanner;

public class Program {

	private static final String EXIT = "exit";
	private static final String CUR_FOLDER_FLAG = "--current-folder";

	private static void setFlag(String[] args) {
		if (args.length > 0) {
			String[] parsedFlag = args[0].split("[=]", 2);
			if (!parsedFlag[0].equals(CUR_FOLDER_FLAG)) {
				System.err.println(parsedFlag[0] + ": unknown flag");
			} else if (parsedFlag.length != 2) {
				System.err.println(CUR_FOLDER_FLAG + ": no folder provided");
				System.out.println("Usage: java Program [--current-folder=PATH]");
			} else {
				Executor.getInstance().setCWD(parsedFlag[1]);
				return;
			}
			System.exit(-1);
		}
	}

	public static void main(String[] args) {
		setFlag(args);
		System.out.print("-> ");
		Scanner sc = new Scanner(System.in);
		while (sc.hasNext()) {
			String input = sc.nextLine();
			if (input.equals(EXIT)) {
				break;
			} else if (input.isEmpty()) {
				continue;
			}
			Command command = new Command(input);
			try {
				Executor.getInstance().execute(command);
			} catch (IOException e) {
				System.err.println("Error: " + e.toString());
			}
			System.out.print("-> ");
		}
		sc.close();
	}
}
