package net.milanqiu.mimas.junit;

import org.junit.Assert;

/**
 * An extension of {@link org.junit.Assert} to provide more utilities.
 * <p>
 * Creation Date: 2014-07-08
 * @author Milan Qiu
 */
public class AssertExt {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private AssertExt() {}

    /**
     * Asserts an actual object is instance of an expected class.
     * @param expectedClazz the expected class
     * @param actualObj the actual object
     */
    public static void assertClassification(Class<?> expectedClazz, Object actualObj) {
        Assert.assertTrue(expectedClazz.isInstance(actualObj));
    }

    /**
     * Asserts the specified boolean value is default.
     * Usually, a default boolean is {@code false}.
     * @param value the boolean value expected to be default
     */
    public static void assertDefault(boolean value) {
        Assert.assertFalse(value);
    }

    /**
     * Asserts the specified character value is default.
     * Usually, a default character is {@code &#92;u0000}.
     * @param value the character value expected to be default
     */
    public static void assertDefault(char value) {
        Assert.assertEquals('\u0000', value);
    }

    /**
     * Asserts the specified integer value is default.
     * Usually, a default integer is {@code 0}.
     * @param value the integer value expected to be default
     */
    public static void assertDefault(int value) {
        Assert.assertEquals(0, value);
    }

    /**
     * Asserts the specified long value is default.
     * Usually, a default long is {@code 0L}.
     * @param value the long value expected to be default
     */
    public static void assertDefault(long value) {
        Assert.assertEquals(0L, value);
    }

    /**
     * Asserts the specified float value is default.
     * Usually, a default float is {@code 0.0f}.
     * @param value the float value expected to be default
     */
    public static void assertDefault(float value) {
        Assert.assertEquals(0.0f, value, 0);
    }

    /**
     * Asserts the specified double value is default.
     * Usually, a default double is {@code 0.0d}.
     * @param value the double value expected to be default
     */
    public static void assertDefault(double value) {
        Assert.assertEquals(0.0d, value, 0);
    }

    /**
     * Asserts the specified string is default.
     * Usually, a default string is {@code null}.
     * @param value the string expected to be default
     */
    public static void assertDefault(String value) {
        Assert.assertNull(value);
    }

    /**
     * Asserts the specified object is default.
     * Usually, a default object is {@code null}.
     * @param value the object expected to be default
     */
    public static void assertDefault(Object value) {
        Assert.assertNull(value);
    }

    /**
     * Asserts the specified string is the empty string.
     * This means it is {@code ""}.
     * @param str the string expected to be empty
     */
    public static void assertEmpty(String str) {
        Assert.assertTrue(str.isEmpty());
    }

    /**
     * Asserts the specified string is null or is the empty string.
     * This means it is {@code null} or {@code ""}.
     * @param str the string expected to be null or empty
     */
    public static void assertNullOrEmpty(String str) {
        Assert.assertTrue(str == null || str.isEmpty());
    }

    /**
     * Asserts the specified object has a custom {@code toString()} method overriding its parent.
     * @param obj the object expected to have custom {@code toString()} method
     * @throws NoSuchMethodException if the object has no {@code toString()} method, which is virtually impossible
     */
    public static void assertHasCustomToString(Object obj) throws NoSuchMethodException {
        Assert.assertTrue(obj.getClass().getMethod("toString").getDeclaringClass().equals(obj.getClass()));
    }
}
