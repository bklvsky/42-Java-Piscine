import java.util.Scanner;

public class Program {

    private static final String END = "42";
    private static final String WEEKSTRING = "Week ";

    private static final void errorExit() {
        System.err.println("IllegalArgument");
        System.exit(-1);
    }

    public static long myPow(int base, int pow) {
        long ret = 1;
        while (pow > 0) {
            ret *= base;
            pow--;
        }
        return ret;
    }

    public static long appendGrade(long allGrades, int minGrade, int weekNum) {
        return (allGrades + minGrade * myPow(10, weekNum - 1));
    }

    public static int getMinGrade(Scanner sc) {
        int min = 9;
        for (int i = 0; i < 5; i++) {
            int current = sc.nextInt();
            if ((current > 9) || (current < 1)) {
                errorExit();
            }
            if (current < min) {
                min = current;
            }
        }
        sc.nextLine();
        return min;
    }

    public static void putGrade(long grade) {
        for (int i = 0; i < grade; i++) {
            System.out.print("=");
        }
        System.out.println(">");
    }

    public static void putResult(int weekNum, long allGrades) {
        for (int i = 1; i <= weekNum; i++) {
            System.out.print(WEEKSTRING);
            System.out.print(i);
            System.out.print(" ");
            putGrade(allGrades % 10);
            allGrades /= 10;
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int weekNum = 0;
        long allGrades = 0;

        while (sc.hasNextLine() && weekNum < 18)
        {
            String inputString = sc.nextLine();
            if (inputString.equals(END))
                break;
            weekNum += 1;
            if (inputString.equals(WEEKSTRING + weekNum)) {
                allGrades = appendGrade(allGrades, getMinGrade(sc), weekNum);
            } else {
                errorExit();
            }
        }
        putResult(weekNum, allGrades);
    }
}
