import java.io.IOException;
import java.util.Scanner;

public class Program {

	public static final String SIGNATURES_TXT = "./signatures.txt";
	public static final String EO_INPUT = "42";

	public static void main(String[] args) {
		try {
			SignaturesConfig cnfg = new SignaturesConfig(SIGNATURES_TXT);
			Scanner sc = new Scanner(System.in);
			while (sc.hasNext()) {
				String input = sc.nextLine();
				String result = new String();
				if (input.equals(EO_INPUT)) {
					break;
				}
				FileSignatureParser parser = new FileSignatureParser(input);
				result = parser.getFileFormat(cnfg.getSignatures());
				Logger.getInstance().logResult(result);
			}

		} catch (IOException e) {
			System.err.println(e.toString());
			System.exit(-1);
		}
	}    
}

