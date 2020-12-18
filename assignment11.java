import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

class Producer extends Thread {
    private final Queue buf;

    public Producer(Queue buf) {
        this.buf = buf;
    }

    public void run() {
        Random rand = new Random();

        while (true) {
            synchronized (buf) {
                int item = rand.nextInt();
                buf.add(item);
                System.out.println("Producer added " + item);
            }
            try {
                Thread.sleep(500);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer extends Thread {
    private final Queue buf;

    public Consumer(Queue buf) {
        this.buf = buf;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (buf) {
                int item = (int) buf.remove();
                System.out.println("Consumer ate " + item);
            }
        }
    }
}

public class assignment11 {
    public static void main(String[] args) {
        Queue buffer = new LinkedList<Integer>();
        Producer prod = new Producer(buffer);
        Consumer cons = new Consumer(buffer);
        System.out.println("Start");
        prod.start();
        cons.start();
    }
}
