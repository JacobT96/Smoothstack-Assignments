import java.util.Arrays;

public class lambda1 {
    
    public static int eComparator(String w1, String w2) {
        if (w1.contains("e")) return -1;
        else if (w2.contains("e")) return 1;
        else return 0;
    }

    public static void main(String[] args) {
        String[] words = {"hello", "hi", "how are you", "aaaa"};
        Arrays.sort(words, (i, j) -> {
            return i.length() - j.length();
        });

        for (String s : words) {
            System.out.println(s);
        }

        Arrays.sort(words, (i, j) -> {
            return j.length() - i.length();
        });

        System.out.println();
        for (String s : words) {
            System.out.println(s);
        }

        Arrays.sort(words, (i, j) -> {
            return i.charAt(0) - j.charAt(0);
        });

        System.out.println();
        for (String s : words) {
            System.out.println(s);
        }

        Arrays.sort(words, (i, j) -> {
            if (i.contains("e")) return -1;
            else if (j.contains("e")) return 1;
            else return 0;
        });

        System.out.println();
        for (String s : words) {
            System.out.println(s);
        }

        Arrays.sort(words, (i, j) -> {
            return eComparator(i, j);
        });

        System.out.println();
        for (String s : words) {
            System.out.println(s);
        }
    }
}