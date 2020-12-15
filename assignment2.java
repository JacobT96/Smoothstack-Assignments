import java.util.Random;
import java.util.Scanner;

public class assignment2 {
    public static void main(String[] args) {
        Random rand = new Random();
        int number = rand.nextInt(100) + 1;
        Scanner sc = new Scanner(System.in);
        System.out.println("Guess a number between 1 and 100!");

        for (int i=0; i<5; i++) {
            int guess = sc.nextInt();
            int diff = Math.abs(guess - number);
            
            if (diff <= 10) {
                System.out.println("Congratulations! The number is " + number);
                sc.close();
                System.exit(0);
            }
            else if (i < 4) {
                System.out.println("Keep trying!");
            }
        }
        sc.close();
        System.out.println("Sorry. The number was " + number);
    }
}
