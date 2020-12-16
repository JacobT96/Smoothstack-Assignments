import java.util.Random;

public class assignment4 {

    private static void printMatrix(int[][] arr) {
        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr[i].length; j++) {
                if (arr[i][j] < 10) System.out.print(" " + arr[i][j] + " ");
                else System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] arr = new int[10][10];
        Random rand = new Random();
        
        for (int i=0; i<10; i++) {
            for (int j=0; j<10; j++) {
                arr[i][j] = rand.nextInt(81);
            }
        }
        printMatrix(arr);
        System.out.println("\n\n");

        int maxNum = 0;
        int xPos = 0;
        int yPos = 0;

        for (int i=0; i<arr.length; i++) {
            for (int j=0; j<arr[i].length; j++) {
                int thisNum = arr[i][j];
                if (thisNum > maxNum) {
                    maxNum = thisNum;
                    xPos = i;
                    yPos = j;
                }
            }
        }

        System.out.println("Max number is " + maxNum + " at row " + xPos + ", column " + yPos + ".");
    }
}
