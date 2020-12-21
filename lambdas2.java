import java.util.function.Function;

public class lambdas2 {
    
    public static Function<Integer, Boolean> isOdd() {
        return (num) -> {
            return num % 2 != 0;
        };
    }

    public static Function<Integer, Boolean> isPrime() {
        return (num) -> {
            if (num == 0 || num == 1) return false;
            if (num == 2) return true;

            if (num % 2 == 0) return false;

            int p = 3;
            while (p < num) { //divide by every odd number up to the given number
                if (num % p == 0) return false;
                p += 2;
            }

            return true;
        };
    }

    public static Function<Integer, Boolean> isPalindrome() {
        return (num) -> {
            String str = Integer.toString(Math.abs(num)); //get the string representation for all the work
            int len = str.length();
            for (int i=0; i<(len/2)+1; i++) {
                if (str.charAt(i) != str.charAt(len-(i+1))) return false;
            }

            return true;
        };
    }

    public static void main(String[] args) {
        System.out.println(isOdd().apply(1));
        System.out.println(isOdd().apply(2));
        System.out.println(isOdd().apply(0));
        System.out.println();

        System.out.println(isPrime().apply(0));
        System.out.println(isPrime().apply(1));
        System.out.println(isPrime().apply(2));
        System.out.println(isPrime().apply(3));
        System.out.println(isPrime().apply(4));
        System.out.println(isPrime().apply(5));
        System.out.println(isPrime().apply(439));
        System.out.println(isPrime().apply(440));
        System.out.println();

        System.out.println(isPalindrome().apply(1221));
        System.out.println(isPalindrome().apply(1));
        System.out.println(isPalindrome().apply(12321));
        System.out.println(isPalindrome().apply(22));
        System.out.println(isPalindrome().apply(1222));
        System.out.println(isPalindrome().apply(12311));
    }
}