import java.util.Arrays;
import java.util.List;

public class recursion1 {

    // find the sum of the entire array
    private static int sum(int[] arr, int sum) {
        if (arr.length == 0) return sum;

        return arr[0] + sum(Arrays.copyOfRange(arr, 1, arr.length), sum);
    }

    // join 2 arrays. The first must end with a trail of zeros.
    private static int[] join(int[] arr1, int[] arr2, int index) {
        if (index == arr1.length) return arr1;
        arr1[index] = arr2[0];
        arr2 = Arrays.copyOfRange(arr2, 1, arr2.length);
        return join(arr1, arr2, index+1);
    }

    // return the max value in the array and its index
    private static int[] max(int[] arr, int max, int index, int indexOf) {
        if (index == arr.length) return new int[] {max, indexOf};

        if (arr[index] > max) {
            max = arr[index];
            indexOf = index;
        }

        return max(arr, max, index+1, indexOf);
    }

    public static boolean groupSumClump(int currentsum, int[] arr, int sum) {
        if (currentsum == sum) return true;
        if (currentsum + sum(arr, 0) == sum) return true;
        if (arr.length <= 1) return false;

        // find the index range of identical values
        int startI = -1;
        int endI = 0;
        for (int i=0; i<arr.length-1; i++) {
            if (arr[i] == arr[i+1]) {
                if (startI == -1) startI = i;
            }
            else if (startI > -1) {
                endI = i;
                break;
            }
        }

        // if identical values exist, find the sum of them
        if (startI > -1) {
            int sumI = sum(Arrays.copyOfRange(arr, startI, endI+1), 0);

            // get the array without these values
            int[] arr1 = Arrays.copyOfRange(arr, 0, startI);
            int[] arr2 = Arrays.copyOfRange(arr, endI+1, arr.length);

            int[] newArr = Arrays.copyOf(arr1, arr1.length + arr2.length);
            newArr = join(newArr, arr2, arr1.length);

            // if the sum of these values does not exceed the target, try to use them
            if (sumI + currentsum <= sum) {
                if (groupSumClump(currentsum+sumI, newArr, sum)) return true;
            }

            // then, proceed without them
            return groupSumClump(currentsum, newArr, sum);
        }

        // find the largest number in this array and its position
        int[] maxI = max(arr, 0, 0, 0);
        int[] arr1 = Arrays.copyOfRange(arr, 0, maxI[1]);
        int[] arr2 = Arrays.copyOfRange(arr, maxI[1]+1, arr.length);
        int[] newArr = Arrays.copyOf(arr1, arr1.length + arr2.length);
        newArr = join(newArr, arr2, arr1.length);

        // try to use it
        if (currentsum + maxI[0] <= sum) {
            if (groupSumClump(currentsum+maxI[0], newArr, sum)) return true;
        }

        // proceed without it
        return groupSumClump(currentsum, newArr, sum);
    }

    public static void main(String[] args) {
        int[] ints1 = {2, 4, 8};
        int[] ints2 = {1, 2, 4, 8, 1};
        int[] ints3 = {2, 4, 4, 8};

        System.out.println(groupSumClump(0, ints1, 10));
        System.out.println(groupSumClump(0, ints2, 14));
        System.out.println(groupSumClump(0, ints3, 14));
    }
}
