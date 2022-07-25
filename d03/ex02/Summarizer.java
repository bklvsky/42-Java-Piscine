public class Summarizer implements Runnable {
    
    private int id;
    private int lower_bound;
    private int upper_bound;
    private Integer[] arr;
    private int sum = 0;

    private static int totalSum = 0;

    synchronized static void addToSum(int toAdd) {
        totalSum += toAdd;
    }

    private String makeMessage() {
        return (
            "Thread " + id
            + ": from " + lower_bound
            + " to " + upper_bound
            + " sum is " + sum
        );
    }

    public Summarizer(int id, int lower_bound, int upper_bound, Integer[] arr) {
        this.id = id;
        this.lower_bound = lower_bound;
        this.upper_bound = upper_bound;
        this.arr = arr;
    }

    public void summarize() {

        for (int i = lower_bound; i < upper_bound; i++) {
            sum += arr[i];
        }
        addToSum(this.sum);
    }

    public static int getTotalSum() {
        return totalSum;
    }

    public void run() {
        summarize();
        Sender.send(makeMessage());
    }
    
}
