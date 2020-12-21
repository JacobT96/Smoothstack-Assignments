import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class functional1 {
    
    public static List<Integer> rightMost(List<Integer> ints) {
        return ints.stream()
            .map(i -> {
                i %= 100;
                i %= 10;
                return i;
            }).collect(Collectors.toList());
    }

    public static List<Integer> doubling(List<Integer> ints) {
        return ints.stream()
            .map(i -> {
                return i * 2;
            }).collect(Collectors.toList());
    }

    public static List<String> noX(List<String> strings) {
        return strings.stream()
            .map(s -> {
                return s.replace("x", "");
            }).collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<Integer> nums1 = Arrays.asList(1, 22, 93);
        nums1 = rightMost(nums1);
        System.out.println(nums1);

        List<Integer> nums2 = Arrays.asList(16, 8, 886, 8, 1);
        nums2 = rightMost(nums2);
        System.out.println(nums2);

        List<Integer> nums3 = Arrays.asList(10, 0);
        nums3 = rightMost(nums3);
        System.out.println(nums3);

        System.out.println();

        List<Integer> nums4 = Arrays.asList(1, 2, 3);
        List<Integer> nums5 = Arrays.asList(6, 8, 6, 8, -1);
        List<Integer> nums6 = new ArrayList<Integer>();

        System.out.println(doubling(nums4));
        System.out.println(doubling(nums5));
        System.out.println(doubling(nums6));
        
        System.out.println();

        List<String> str1 = Arrays.asList("ax", "bb", "cx");
        List<String> str2 = Arrays.asList("xxax", "xbxbx", "xxcx");
        List<String> str3 = Arrays.asList("x");

        System.out.println(noX(str1));
        System.out.println(noX(str2));
        System.out.println(noX(str3));
    }
}
