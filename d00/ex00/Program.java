public class Program {
    public static void main(String[] args) {
        int number =  479598;
        int sum = 0;
        sum += number % 10;
        number /= 10;
        sum += number % 10;
        number /= 10;
        sum += number % 10;
        number /= 10;
        sum += number % 10;
        number /= 10;
        sum += number % 10;
        number /= 10;
        sum += number;
        System.out.println(sum);
    }
}