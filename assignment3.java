public class assignment3 {
    public static void main(String[] args) {
        
        float[] argsf = new float[args.length];
        try {
            for (int i=0; i<args.length; i++) {
                argsf[i] = Float.parseFloat(args[i]);
            }
        }
        catch (NumberFormatException e) {
            System.out.println("Numbers only");
            System.exit(0);
        }
        
        float sum = 0f;
        for (float num : argsf) {
            sum += num;
        }
        System.out.println("Sum = " + sum);
    }
}
