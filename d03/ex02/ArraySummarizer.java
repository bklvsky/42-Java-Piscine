public class ArraySummarizer {

    private Summarizer[] summarizers;
    private RandomIntArray arr;

    
    private void printSum(Integer[] arr) {
        int res = 0;
        for (Integer num: arr) {
            res += num;
        }
        System.out.println("Sum: "+ res);
    }

    public ArraySummarizer(int size, int numOfThreads) {
        arr = new RandomIntArray(size);
        summarizers = new Summarizer[numOfThreads];
        int step = (int) Math.ceil((float) size / numOfThreads);
        int low_b = 0;
        int up_b = low_b + step;
        for (int i = 0; i < numOfThreads; i++) {
            summarizers[i] = new Summarizer(i + 1, low_b, up_b, arr.getArr());
            low_b = up_b;
            up_b += step;
            if (up_b > size) {
                up_b = size;
            }
        }
    }

    public void summarize() throws InterruptedException {
        printSum(arr.getArr());
        Thread[] threads = new Thread[summarizers.length];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(summarizers[i]);
            threads[i].start();
        }
        for (int i = 0; i < threads.length; i++) {
            threads[i].join();
        }

        System.out.println("Sum by threads: " + Summarizer.getTotalSum());
    }
    
}
