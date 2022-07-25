public class HenThread implements Runnable {

    private int count;

    public void run() {
		try {
			for (int i = 0; i < count; i++) {
				Sender.waitForEgg();
				Sender.send("Hen");
				Sender.notifyForHen();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();;
		}
    }
    
    public HenThread(int count) {
        this.count = count;
    }
}
