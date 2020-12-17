import java.io.FileReader;
import java.io.IOException;

public class assignment8 {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Need a file and a character in the command line.");
            System.exit(0);
        }

        try (FileReader reader = new FileReader(args[0]);) {
            String car = args[1];
            int occurences = 0;
            while (reader.ready()) {
                int character = reader.read();
                if ((char) character == car.charAt(0)) //have to cast and index for these to be compatible types
                    occurences++;
            }
        reader.close();
        System.out.println("Character '" + car + "' appeared " + occurences + " times.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
