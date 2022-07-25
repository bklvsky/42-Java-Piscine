import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;

public class Program {

	private static String DICTIONARY_TXT = "dictionary.txt";

	private static void writeDict(Set<String> dict) throws IOException{
		FileWriter outFile = new FileWriter(DICTIONARY_TXT);
		for (String word : dict) {
			outFile.append(word);
			outFile.append('\n');
		}
		outFile.close();
	}

	public static void main(String[] args) throws Exception {

		if (args.length < 2) {
			System.out.println("Usage: java Program [file1] [file2]");
			return;
		}
		
		File file1 = new File(args[0]);
		File file2 = new File(args[1]);
		
		ComparableFile cf1;
		ComparableFile cf2;
		
		try {
			cf1 = new ComparableFile(file1);
			cf2 = new ComparableFile(file2);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
			return;
		}
		Comparator comparator = new Comparator();

		comparator.addFiles(cf1, cf2);
		System.out.println(
			"Similarity = " + Math.floor(comparator.compare(cf1, cf2) * 100) / 100);
		writeDict(comparator.getDict());
	}
}
