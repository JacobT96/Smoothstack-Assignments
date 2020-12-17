import java.io.File;

public class assignment6 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Need a directory in the command line.");
            System.exit(0);
        }

        File folder = new File(args[0]);
        File[] list = folder.listFiles();
        for (File file : list) {
            System.out.println(file.toString());
        }
    }
}