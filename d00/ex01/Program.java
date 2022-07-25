import java.util.Scanner;

public class Program {

    public static int mySqrt(int num) {
        int sqrt = 2;
        while (sqrt < num / sqrt)
            sqrt++;
        return sqrt;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int steps = 0;
        boolean isPrime = true;
        
        if (sc.hasNextLine() && sc.hasNextInt()) {
            int num = sc.nextInt();
            if (num <= 1) {
                System.err.println("IllegalArgument");
                System.exit(-1);
            }
            else if (num == 2)
                steps = 1;
            else {
                int sqrt = mySqrt(num);
                for (int i = 2; i <= sqrt; i++) {
                    steps++;
                    if (num % i == 0)
                        isPrime = false;
                }
            }
            System.out.println(isPrime + " " + steps);
        }
        else {
            System.err.println("IllegalArgument");
            System.exit(-1);
        }
    }
}
