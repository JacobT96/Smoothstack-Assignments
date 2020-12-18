class Singleton {

    private static volatile Singleton instance = null;

    private Singleton() {}

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                instance = new Singleton();
            }
        }

        return instance;
    }
}

//Make a singleton and show that both objects are the same instance.
public class assignment9 {
    public static void main(String[] args) {
        Singleton var = Singleton.getInstance();
        Singleton var2 = Singleton.getInstance();

        if (var == var2) {
            System.out.println("Equal");
        }
        else {
            System.out.println("Not equal");
        }
    }
}