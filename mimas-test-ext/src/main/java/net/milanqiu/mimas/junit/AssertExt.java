package net.milanqiu.mimas.junit;

import org.junit.Assert;

/**
 * An extension of {@link org.junit.Assert} to provide more utilities.
 * <p>
 * Creation Date: 2014-7-8
 * @author Milan Qiu
 */
public class AssertExt {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private AssertExt() {
    }

    /**
     * Asserts an actual object is instance of an expected class.
     * @param expectedClazz the expected class
     * @param actualObj the actual object
     */
    public static void assertClassification(Class<?> expectedClazz, Object actualObj) {
        Assert.assertTrue(expectedClazz.isInstance(actualObj));
    }

    /**
     * Asserts a boolean is unassigned and default.
     * In usual JVM, it is <i>false</i>.
     * @param value the boolean expected to be default
     */
    public static void assertDefault(boolean value) {
        Assert.assertFalse(value);
    }

    /**
     * Asserts a character is unassigned and default.
     * In usual JVM, it is <i>'\u0000'</i>.
     * @param value the character expected to be default
     */
    public static void assertDefault(char value) {
        Assert.assertEquals('\u0000', value);
    }

    /**
     * Asserts a long is unassigned and default.
     * In usual JVM, it is <i>0</i>.
     * @param value the long expected to be default
     */
    public static void assertDefault(long value) {
        Assert.assertEquals(0L, value);
    }

    /**
     * Asserts a float is unassigned and default.
     * In usual JVM, it is <i>0.0</i>.
     * @param value the float expected to be default
     */
    public static void assertDefault(float value) {
        Assert.assertEquals(0.0F, value, 0);
    }

    /**
     * Asserts a double is unassigned and default.
     * In usual JVM, it is <i>0.0</i>.
     * @param value the double expected to be default
     */
    public static void assertDefault(double value) {
        Assert.assertEquals(0.0D, value, 0);
    }

    /**
     * Asserts a string is unassigned and default.
     * In usual JVM, it is <i>null</i>.
     * @param value the string expected to be default
     */
    public static void assertDefault(String value) {
        Assert.assertNull(value);
    }

    /**
     * Asserts an object is unassigned and default.
     * In usual JVM, it is <i>null</i>.
     * @param value the object expected to be default
     */
    public static void assertDefault(Object value) {
        Assert.assertNull(value);
    }

    /**
     * Asserts a string is null or empty.
     * This means it is <code>null</code> or <i>""</i>.
     * @param str the string expected to be null or empty
     */
    public static void assertNullOrEmpty(String str) {
        Assert.assertTrue(str == null || str.isEmpty());
    }

    /**
     * Asserts a string is empty.
     * This means it is <i>""</i>.
     * @param str the string expected to be empty
     */
    public static void assertEmpty(String str) {
        Assert.assertTrue(str.isEmpty());
    }

    /**
     * Asserts an object has a default <code>toString()</code> member.
     * This means its <code>toString()</code> has never been overrided, or the override method is led by <code>Object.toString()</code>.
     * @param obj the object expected to have default <code>toString()</code> member
     */
    public static void assertHasDefaultToString(Object obj) {
        Assert.assertTrue(obj.toString().startsWith(obj.getClass().getName() + "@"));
    }
}
