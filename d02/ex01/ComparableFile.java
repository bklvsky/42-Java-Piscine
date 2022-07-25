import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.TreeMap;
import java.util.Scanner;

public class ComparableFile {
    private TreeMap<String, Integer> words;

    
    public ComparableFile(File file) throws IOException {
        words = new TreeMap<String, Integer>();
        FileReader fr = new FileReader(file);
        Scanner sc = new Scanner(fr).useDelimiter("[\\s$]");
        
        while (sc.hasNext()) {
            String tmp = sc.next();
            if (!tmp.isEmpty()) {
                if (!words.containsKey(tmp)) {
                    words.put(tmp, 1);
                } else {
                    words.put(tmp, words.get(tmp) + 1);
                }
            }
        }
        sc.close();
        fr.close();
    }
    
    public TreeMap<String, Integer> getWords() {
        return words;
    }
    
}