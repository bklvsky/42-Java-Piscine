import java.util.Scanner;

public class Program {

    private static final int MAX_CHAR_CODE = 65_535;
    private static final int TOP_CHAR_NUM = 10;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String  input = sc.nextLine();
        if (input.length() > 0) {
            char[] parsedChars = countChars(input);
            char[] topTen = getTopTenChars(parsedChars);
            putDiagram(parsedChars, topTen);
        }
    }

    public static char[] countChars(String input) {
        char[] inputArray = input.toCharArray();
        int inputLen = input.length();
        char[] charTable = new char[MAX_CHAR_CODE];
        for (int i = 0; i < inputLen; i++) {
            charTable[inputArray[i]] += 1;
        }
        return charTable;
    }


    public static char[] getTopTenChars(char[] parsedChars) {
        char[] topChars = new char[TOP_CHAR_NUM];

        for (int i = 0; i < MAX_CHAR_CODE; i++) {
            if (parsedChars[i] > 0) {
                char occurence = parsedChars[i];
                for (int j = 0; j < TOP_CHAR_NUM; j++) {
                    if (occurence > parsedChars[topChars[j]])
                    {
                        topChars = insert(topChars, j, (char) i);
                        break;
                    }
                }
            }
        }
        return topChars;
    }

    public static char[] insert(char[] oldArr, int pos, char toInsert) {
        char[] newArr = new char[TOP_CHAR_NUM];
        for (int i = 0; i < pos; i++) {
            newArr[i] = oldArr[i];
        }
        for (int i = pos; i < TOP_CHAR_NUM - 1; i++) {
            newArr[i + 1] = oldArr[i];
        }
        newArr[pos] = toInsert;
        return newArr;
    }

    public static void putDiagram(char[] parsedChars, char[] topTen) {
        double scale = (double) parsedChars[topTen[0]] / TOP_CHAR_NUM;
        int linesToPrint = (int) (parsedChars[topTen[0]] / scale) + 1;
        
        System.out.println("");
        while (linesToPrint > 0) {
            for (int j = 0; j < TOP_CHAR_NUM; j++) {
                int symToPrint = (int) (parsedChars[topTen[j]] / scale);
                if (linesToPrint > symToPrint + 1 || parsedChars[topTen[j]] == 0) {
                    break;
                } else if (symToPrint + 1 == linesToPrint) {
                    System.out.format("%3d", (int) parsedChars[topTen[j]]);
                } else {
                    System.out.format("%3c", '#');
                }
            }
            System.out.println("");
            linesToPrint--;
        }
        for (int i = 0; i < TOP_CHAR_NUM; i++) {
            System.out.format("%3c", topTen[i]);
        }
        System.out.println("");
    }
}
