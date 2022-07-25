import java.util.Scanner;

public class Program {

    public static int sumDigits(long num) {
        int sum = 0;
        while (num > 0) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }

    public static int mySqrt(int num) {
        int sqrt = 2;
        while (sqrt < num / sqrt)
            sqrt++;
        return sqrt;
    }

    public static boolean isPrime(int num) {

        if (num == 2)
            return true;
        int sqrt = mySqrt(num);
        for (int i = 2; i <= sqrt; i++) {
            if (num % i == 0)
                return false;
        }
        return true;
    }

    public static boolean isACoffeeQuery(long query) {
        return isPrime(sumDigits(query));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int coffeeQueries = 0;
        while (sc.hasNextLine()) {
            long query = sc.nextLong();
            if (query == 42) {
                break;
            }
            if (isACoffeeQuery(query)) {
                coffeeQueries += 1;
            }
        }
        System.out.println("Count of coffee-request - " + coffeeQueries);
    }
    
}
