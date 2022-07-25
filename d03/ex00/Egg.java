public class Egg implements Runnable {

    private int count;

    public void run() {
        for (int i = 0; i < count; i++) {
            Sender.send("Egg");
        }
    }
    
    public Egg(int count) {
        this.count = count;
    }
}
