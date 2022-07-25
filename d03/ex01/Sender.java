public class Sender {

    private final static Object HEN_DONE = new Object();
    private final static Object EGG_DONE = new Object();

    public static void waitForHen() throws InterruptedException {
        synchronized (HEN_DONE) {
            HEN_DONE.wait();
        }
    }

    public static void notifyForHen() {
        synchronized (HEN_DONE) {
            HEN_DONE.notify();
        }
    }

    public static void waitForEgg() throws InterruptedException {
        synchronized (EGG_DONE) {
            EGG_DONE.wait();
        }
    }

    public static void notifyForEgg() {
        synchronized (EGG_DONE) {
            EGG_DONE.notify();
        }
    }

    synchronized public static void send(String msg) {
        System.out.println(msg);
    }
}
