import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class a3letters {

    public static ArrayList<String> question3(List<String> strs) {
        ArrayList<String> answers = new ArrayList<String>();
        
        strs.forEach((str) -> {
            if (str.charAt(0) == 'a' && str.length() == 3) {
                answers.add(str);
            }
        });

        return answers;
    }

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("ant", "bat", "apricot", "apl");
        ArrayList<String> answers = question3(strings);
        System.out.println(answers);
    }
}
