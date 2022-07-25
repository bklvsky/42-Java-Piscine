import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Set;
import java.lang.Math;

public class Comparator {
    
    private Set<String> dict;
    private ArrayList<Integer> occVect1;
    private ArrayList<Integer> occVect2;

    public Comparator() {
        dict = new TreeSet<String>();
        this.occVect1 = new ArrayList<Integer>();
        this.occVect2 = new ArrayList<Integer>();
    }

    public void addFiles(ComparableFile file1, ComparableFile file2) {
        dict.addAll(file1.getWords().keySet());
        dict.addAll(file2.getWords().keySet());
        for (String word : dict) {
            occVect1.add(file1.getWords().getOrDefault(word, 0));
            occVect2.add(file2.getWords().getOrDefault(word, 0));
        }
    }

    public double compare(ComparableFile file1, ComparableFile file2) {
        double res = 0;
        double numerator = 0;
        double denominatorFirst = 0;
        double denominatorSecond = 0;

        for (int i = 0; i < occVect1.size(); i++) {
            numerator += (double) (occVect1.get(i) * occVect2.get(i));
            denominatorFirst += Math.pow(occVect1.get(i), 2);
            denominatorSecond += Math.pow(occVect2.get(i), 2);
        }
        if (denominatorFirst == 0 || denominatorSecond == 0) {
            return 0;
        }
        res = numerator / (Math.sqrt(denominatorFirst) * 
                                        Math.sqrt(denominatorSecond));
        return res;
    }

    public Set<String> getDict() {
        return this.dict;
    }

}
