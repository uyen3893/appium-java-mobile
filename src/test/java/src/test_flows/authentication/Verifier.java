package src.test_flows.authentication;

public class Verifier {

    public static void equals(String actual, String expected) {
        if (!actual.equalsIgnoreCase(expected)) {
            throw new RuntimeException("....");
        }
    }

    public static void equals(int actual, int expected) {
        if(actual == expected) {
            throw new RuntimeException("...");
        }
    }
}
