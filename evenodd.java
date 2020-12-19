import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class evenodd {

    private static String str = "";

    public static String eo(List<Integer> nums) {
        str = "";
        nums.forEach((num) -> {
            if (num % 2 == 0) {
                str = str.concat("e" + num);
            }
            else {
                str = str.concat("o" + num);
            }
            if (nums.indexOf(num) < nums.size()-1) str = str.concat(",");
        });

        return str;
    }

    public static void main(String[] args) {
        List<Integer> ints = Arrays.asList( 1, 2, 3, 4, 5 );
        System.out.println(eo(ints));
    }
}
