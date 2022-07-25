import java.util.HashMap;
import java.util.Map;

public class Program {

	private static final String ARG_SIZE = "--arraySize";
	private static final String ARG_THREAD = "--threadCount";
	private static final String[] ARGS = {ARG_SIZE, ARG_THREAD};

	private static void printUsage() {
		System.out.println("Usage: java Program "
						+ "--arraySize=[NUM]"
						+ " --threadCount=[NUM]");
		System.exit(-1);
	}

	private static void exitError(String error) {
		System.err.println(error);
		System.exit(-1);
	}

	private static void getKw(String[]args, Map<String, Integer> kw) {
		for (String arg: args) {
			String[] values = arg.split("[=]");
			if (values.length != 2) {
				printUsage();
			}
			if (!kw.containsKey(values[0])) {
				exitError(values[0] + ": uknown key");
			}
			kw.put(values[0], Integer.parseInt(values[1]));
		}
	}

	private static void validateArgs(Map<String, Integer>kw) {
		if (kw.get(ARG_SIZE) <= 0 || kw.get(ARG_THREAD) <= 0) {
			exitError("Arguments should be positive");
		}
		if (kw.get(ARG_SIZE) < kw.get(ARG_THREAD)) {
			exitError(ARG_SIZE + " can't be smaller than " + ARG_THREAD);
		}
		if (kw.get(ARG_SIZE) > 2_000_000) {
			exitError(ARG_SIZE + " is too big. Should be not more than 2.000.000");
		}
	}

	private static Map<String, Integer> parseArgs(String[] args) {
		if (args.length != 2) {
			printUsage();
		}
		Map<String, Integer> kw = new HashMap<String, Integer>();
		for (String arg : ARGS) {
			kw.put(arg, 0);
		}
		getKw(args, kw);
		validateArgs(kw);
		return kw;
	}

	public static void main(String[] args) {
		Map<String, Integer> kw= parseArgs(args);
		ArraySummarizer summarizer = new ArraySummarizer(kw.get(ARG_SIZE), kw.get(ARG_THREAD));
		try {
			summarizer.summarize();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
