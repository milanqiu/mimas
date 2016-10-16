package net.milanqiu.mimas.junit;

import net.milanqiu.mimas.test.TestUtils;
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
        Assert.assertTrue(TestUtils.comparisonMsg(expectedClazz, actualObj.getClass()), expectedClazz.isInstance(actualObj));
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
        Assert.assertTrue("expected empty, but was:<" + str + ">", str.isEmpty());
    }

    /**
     * Asserts the specified string is null or is the empty string.
     * This means it is {@code null} or {@code ""}.
     * @param str the string expected to be null or empty
     */
    public static void assertNullOrEmpty(String str) {
        Assert.assertTrue("expected null or empty, but was:<" + str + ">", str == null || str.isEmpty());
    }

    /**
     * Asserts the specified object has a custom {@code toString()} method overriding its parent.
     * @param obj the object expected to have custom {@code toString()} method
     * @throws NoSuchMethodException if the object has no {@code toString()} method, which is virtually impossible
     */
    public static void assertHasCustomToString(Object obj) throws NoSuchMethodException {
        Assert.assertTrue(obj.getClass().getMethod("toString").getDeclaringClass().equals(obj.getClass()));
    }

    /**
     * Likes {@link java.lang.Runnable}, but may throw an exception when running.
     */
    @FunctionalInterface
    public interface RunnableWithException {
        /**
         * May take any action whatsoever, allowing exception thrown.
         */
        void run() throws Exception;
    }

    /**
     * Asserts the running of the specified code will throw an expected exception.
     * @param runnable the runnable code expected to throw exception
     * @param exceptionClazz the expected exception class
     */
    public static void assertExceptionThrown(RunnableWithException runnable, Class<? extends Throwable> exceptionClazz) {
        try {
            runnable.run();
            Assert.fail("Expected exception not thrown");
        } catch (Exception e) {
            Assert.assertTrue(TestUtils.comparisonMsg(exceptionClazz, e.getClass()), exceptionClazz.isInstance(e));
        }
    }

    /**
     * Asserts the running of the specified code will throw an expected exception with expected message.
     * @param runnable the runnable code expected to throw exception
     * @param exceptionClazz the expected exception class
     * @param exceptionMessage the expected exception message
     */
    public static void assertExceptionThrown(RunnableWithException runnable, Class<? extends Throwable> exceptionClazz,
                                             String exceptionMessage) {
        try {
            runnable.run();
            Assert.fail("Expected exception not thrown");
        } catch (Exception e) {
            Assert.assertTrue(TestUtils.comparisonMsg(exceptionClazz, e.getClass()), exceptionClazz.isInstance(e));
            Assert.assertEquals(exceptionMessage, e.getMessage());
        }
    }

    /**
     * Asserts the running of the specified code will throw an expected exception with expected message and cause.
     * @param runnable the runnable code expected to throw exception
     * @param exceptionClazz the expected exception class
     * @param exceptionMessage the expected exception message
     * @param exceptionCause the expected exception cause
     */
    public static void assertExceptionThrown(RunnableWithException runnable, Class<? extends Throwable> exceptionClazz,
                                             String exceptionMessage, Throwable exceptionCause) {
        try {
            runnable.run();
            Assert.fail("Expected exception not thrown");
        } catch (Exception e) {
            Assert.assertTrue(TestUtils.comparisonMsg(exceptionClazz, e.getClass()), exceptionClazz.isInstance(e));
            Assert.assertEquals(exceptionMessage, e.getMessage());
            Assert.assertSame(exceptionCause, e.getCause());
        }
    }

    /**
     * Asserts the running of the specified code will throw an expected exception with expected cause.
     * @param runnable the runnable code expected to throw exception
     * @param exceptionClazz the expected exception class
     * @param exceptionCause the expected exception cause
     */
    public static void assertExceptionThrown(RunnableWithException runnable, Class<? extends Throwable> exceptionClazz,
                                             Throwable exceptionCause) {
        try {
            runnable.run();
            Assert.fail("Expected exception not thrown");
        } catch (Exception e) {
            Assert.assertTrue(TestUtils.comparisonMsg(exceptionClazz, e.getClass()), exceptionClazz.isInstance(e));
            Assert.assertSame(exceptionCause, e.getCause());
        }
    }
}
