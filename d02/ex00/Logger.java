import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Logger {
	private static final String RESULT_TXT = "result.txt";
	private static final String SUCCESS = "PROCESSED\n";
	private static final String FAIL = "UNDEFINED\n";

	private FileOutputStream outFile = null;
	private static Logger instance = null;

	private Logger() throws IOException {
		File file = new File(RESULT_TXT);
		if (!file.createNewFile()) {
			FileOutputStream tmp = new FileOutputStream(file);
			tmp.close();
		}
		outFile = new FileOutputStream(file, true);
	}

	public static Logger getInstance() throws IOException {
		if (instance == null) {
			instance = new Logger();
		}
		return instance;
	}

	public void logResult(String result) throws IOException {
		if (result != null) {
			outFile.write((result + "\n").getBytes());
			System.out.print(SUCCESS);
		} else {
			outFile.write(FAIL.getBytes());
			System.out.print(FAIL);

		}
	}

}
