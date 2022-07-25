import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.InputStream;

public class SignaturesConfig {
	private Map<String, ArrayList<Short>> signatures;

	public SignaturesConfig(String path) throws IOException{
		FileInputStream configFile = new FileInputStream(path);
		signatures = new HashMap<String, ArrayList<Short>>();
		readConfig(configFile);
		configFile.close();
	}

	private void readConfig(InputStream configFile) {
		Scanner sc = new Scanner(configFile);
		while (sc.hasNext()) {
			String input = sc.nextLine();
			Scanner scLine = new Scanner(input).useDelimiter("[\\s,]");
			String key = scLine.next();
			ArrayList<Short> value = new ArrayList<>();
			while (scLine.hasNext()) {
				String tmp = scLine.next();
				if (!(tmp.isEmpty()))
					value.add(Short.parseShort(tmp, 16));
			}
			this.signatures.put(key, value);
			scLine.close();
		}
		sc.close();
	}

	public Map<String, ArrayList<Short>> getSignatures() {
		return signatures;
	}
}
