package Misc;

public class FinalTest {
    public static final int N_ITERATIONS = 10_00_000;
    public static String testFinal() {
        final String a = "a";
        final String b = "b";
        return a + b;
    }
    public static String testNonFinal() {
        String a = "a";
        String b = "b";
        return a + b;
    }
    public static void main(String[] args) {
        long tStart, tElapsed;
        tStart = System.currentTimeMillis();
        for (int i = 0; i < N_ITERATIONS; i++)
            testFinal();
        tElapsed = System.currentTimeMillis() - tStart;
        System.out.println("Method with finals took " + tElapsed + " ms");
        tStart = System.currentTimeMillis();
        for (int i = 0; i < N_ITERATIONS; i++)
            testNonFinal();
        tElapsed = System.currentTimeMillis() - tStart;
        System.out.println("Method without finals took " + tElapsed + " ms");
    }
}