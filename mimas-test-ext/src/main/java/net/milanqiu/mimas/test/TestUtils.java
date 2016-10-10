package net.milanqiu.mimas.test;

/**
 * Utilities related to test.
 * <p>
 * Creation Date: 2016-10-10
 * @author Milan Qiu
 */
public class TestUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private TestUtils() {}

    /**
     * Returns a message of comparison, which includes the expected value and the actual value.
     * @param expected the expected value
     * @param actual the actual value
     * @return a message of comparison
     */
    public static String comparisonMsg(String expected, String actual) {
        return System.lineSeparator() +
                "Expected :" + expected + System.lineSeparator() +
                "Actual   :" + actual + System.lineSeparator();
    }

    /**
     * Returns a message of class comparison, which includes the expected class and the actual class.
     * @param expectedClass the expected class
     * @param actualClass the actual class
     * @return a message of class comparison
     */
    public static String comparisonMsg(Class<?> expectedClass, Class<?> actualClass) {
        return System.lineSeparator() +
                "Expected class :" + expectedClass.getName() + System.lineSeparator() +
                "Actual class   :" + actualClass.getName() + System.lineSeparator();
    }
}
