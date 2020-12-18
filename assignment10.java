//Create a deadlock
public class assignment10 {
    public static void main(String[] args) {
        assignment10 i1 = new assignment10();
        assignment10 i2 = new assignment10();

        System.out.println("Start");
        Runnable t1 = new Runnable() {
            public void run() {
                try {
                    synchronized (i1) {
                        Thread.sleep(100);
                        synchronized (i2) {
                            System.out.println("Thread 1 woke up.");
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        Runnable t2 = new Runnable() {
            public void run() {
                try {
                    synchronized (i2) {
                        Thread.sleep(100);
                        synchronized (i1) {
                            System.out.println("Thread 2 woke up.");
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        new Thread(t1).start();
        new Thread(t2).start();

        System.out.println("End");
    }
}
