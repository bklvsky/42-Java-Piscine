public class Sender {
    synchronized public static void send(String msg) {
        System.out.println(msg);
    }
}
