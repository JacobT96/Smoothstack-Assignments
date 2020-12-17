import java.io.FileWriter;
import java.io.IOException;

public class assignment7 {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Need a txt file in the command line.");
            System.exit(0);
        }

        try (FileWriter out = new FileWriter(args[0], true);) {
            out.append("I have appended to this file.");
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
