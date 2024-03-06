public class Morse {
    public static void main(String[] args) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            result.append(args[i]);
            if (i < args.length - 1) {
                result.append(" Stop. ");
            }
        }
        System.out.println(result.toString());
    }
}
