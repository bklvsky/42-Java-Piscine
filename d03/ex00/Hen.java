public class Hen implements Runnable {

    private int count;

    public void run() {
        for (int i = 0; i < count; i++) {
            Sender.send("Hen");
        }
    }
    
    public Hen(int count) {
        this.count = count;
    }
}
