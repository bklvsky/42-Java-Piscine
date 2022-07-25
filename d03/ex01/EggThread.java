public class EggThread implements Runnable {

    private int count;

    public void run() {
        try {
            for (int i = 0; i < count; i++) {
                Sender.send("Egg");
                Sender.notifyForEgg();
                Sender.waitForHen();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public EggThread(int count) {
        this.count = count;
    }
}
