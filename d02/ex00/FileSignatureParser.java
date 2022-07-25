import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class FileSignatureParser {

	private String signature = new String();
	private File file;

	public FileSignatureParser(String path) {
		this.file = new File(path);
	}

	public String getFileFormat(Map<String,
		ArrayList<Short>> signatures) throws IOException {
		try {
			for (Map.Entry<String, ArrayList<Short>> entry :
												signatures.entrySet()) {
				if (signatureEquals(entry.getValue())) {
					return entry.getKey();
				}
			}
		} catch (FileNotFoundException e) {
			System.err.println("No such file or directory");
		}
		return null;
	}

	private boolean signatureEquals(ArrayList<Short> ref)
												throws IOException {

		FileInputStream inputFile = new FileInputStream(this.file);
		for (int i = 0; i < this.file.length(); i++) {
			if (i == ref.size()) {
				inputFile.close();
				return true;
			}
			Short tmp = new Short((short) inputFile.read());
			if (!tmp.equals(ref.get(i))) {
				break;
			}
		}
		inputFile.close();
		return false;
	}
}
