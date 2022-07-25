public class Program {

    private static void printUsage() {
        System.out.println("Usage: java Program --count=[NUM]");
    }

    private static int getCount(String arg) {
        int count;
        String[] values = arg.split("[=]");
        if (values.length != 2) {
            printUsage();
            System.exit(-1);
        }
        count = Integer.parseInt(values[1]);
        return (count);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            printUsage();
            return;
        }

        int count = getCount(args[0]);
        Thread egg = new Thread(new Egg(count));
        Thread hen = new Thread(new Hen(count));

        egg.start();
        hen.start();
        try {
            egg.join();
            hen.join();
        } catch (InterruptedException e) {
            System.err.println(e.getMessage());
        }
        for (int i = 0; i < count; i++) {
            System.out.println("Human");
        }
    }
}
