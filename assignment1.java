public class assignment1 {
    public static void main(String[] args) {
        int dots = 9;
        System.out.println("1)");
        for (int i=1; i<5; i++) {
            for (int j=0; j<i; j++) {
                System.out.print("*");
            }
            System.out.println();
        }
        for (int i=0; i<dots; i++) {
            System.out.print(".");
        }
        System.out.println();
        dots++;

        System.out.println();
        System.out.println("2)");
        for (int i=0; i<dots; i++) {
            System.out.print(".");
        }
        System.out.println();
        dots++;
        for (int i=4; i>0; i--) {
            for (int j=i; j>0; j--) {
                System.out.print("*");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("3)");

        int spaces = 5;
        for (int i=1; i<9; i+=2) {
            for (int j=0; j<spaces; j++) {
                System.out.print(" ");
            }
            for (int j=0; j<i; j++) {
                System.out.print("*");
            }
            spaces--;
            System.out.println();
        }

        for (int i=0; i<dots; i++) {
            System.out.print(".");
        }
        System.out.println();
        dots++;

        System.out.println();
        System.out.println("4)");
        for (int i=0; i<dots; i++) {
            System.out.print(".");
        }
        System.out.println();
        
        spaces = 2;
        for (int i=7; i>0; i-=2) {
            for (int j=0; j<spaces; j++) {
                System.out.print(" ");
            }
            for (int j=0; j<i; j++) {
                System.out.print("*");
            }
            System.out.println();
            spaces++;
        }
    }
}