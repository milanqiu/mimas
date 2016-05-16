package net.milanqiu.mimas.collect;

import java.util.Arrays;

/**
 * Utilities related to array.
 * <p>
 * Creation Date: 2014-07-25
 * @author Milan Qiu
 */
public class ArrayUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private ArrayUtils() {}

    /**
     * Returns whether the specified array contains the specified value.
     * @param array the array to be tested
     * @param value the value to be tested
     * @return {@code true} if the specified array contains the specified value
     */
    public static boolean contains(char[] array, char value) {
        for (char element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns whether the specified array contains the specified value.
     * @param array the array to be tested
     * @param value the value to be tested
     * @return {@code true} if the specified array contains the specified value
     */
    public static boolean contains(byte[] array, byte value) {
        for (byte element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns whether the specified array contains the specified value.
     * @param array the array to be tested
     * @param value the value to be tested
     * @return {@code true} if the specified array contains the specified value
     */
    public static boolean contains(short[] array, short value) {
        for (short element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns whether the specified array contains the specified value.
     * @param array the array to be tested
     * @param value the value to be tested
     * @return {@code true} if the specified array contains the specified value
     */
    public static boolean contains(int[] array, int value) {
        for (int element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns whether the specified array contains the specified value.
     * @param array the array to be tested
     * @param value the value to be tested
     * @return {@code true} if the specified array contains the specified value
     */
    public static boolean contains(long[] array, long value) {
        for (long element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns whether the specified array contains the specified value.
     * @param array the array to be tested
     * @param value the value to be tested
     * @return {@code true} if the specified array contains the specified value
     */
    public static boolean contains(float[] array, float value) {
        for (float element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns whether the specified array contains the specified value.
     * @param array the array to be tested
     * @param value the value to be tested
     * @return {@code true} if the specified array contains the specified value
     */
    public static boolean contains(double[] array, double value) {
        for (double element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns whether the specified array contains the specified value.
     * More formally, returns {@code true} if and only if the specified array contains at least one element such that
     * <code>(value == null ? element == null : value.equals(element))</code>.
     * @param array the array to be tested
     * @param value the value to be tested
     * @param <T> the class of the objects in the array
     * @return {@code true} if the specified array contains the specified value
     */
    public static <T> boolean contains(T[] array, Object value) {
        for (T element : array) {
            if (value == null ? element == null : value.equals(element))
                return true;
        }
        return false;
    }

    /**
     * Creates and returns a character array with the specified length and all elements equal to the specified value.
     * @param value the value to be duplicated, which will be assigned to every element of result array
     * @param count the count of duplication, which is the length of result array
     * @return the result array
     */
    public static char[] duplicate(char value, int count) {
        char[] result = new char[count];
        Arrays.fill(result, value);
        return result;
    }

    /**
     * Creates and returns a byte array with the specified length and all elements equal to the specified value.
     * @param value the value to be duplicated, which will be assigned to every element of result array
     * @param count the count of duplication, which is the length of result array
     * @return the result array
     */
    public static byte[] duplicate(byte value, int count) {
        byte[] result = new byte[count];
        Arrays.fill(result, value);
        return result;
    }

    /**
     * Creates and returns a short array with the specified length and all elements equal to the specified value.
     * @param value the value to be duplicated, which will be assigned to every element of result array
     * @param count the count of duplication, which is the length of result array
     * @return the result array
     */
    public static short[] duplicate(short value, int count) {
        short[] result = new short[count];
        Arrays.fill(result, value);
        return result;
    }

    /**
     * Creates and returns an integer array with the specified length and all elements equal to the specified value.
     * @param value the value to be duplicated, which will be assigned to every element of result array
     * @param count the count of duplication, which is the length of result array
     * @return the result array
     */
    public static int[] duplicate(int value, int count) {
        int[] result = new int[count];
        Arrays.fill(result, value);
        return result;
    }

    /**
     * Creates and returns a long array with the specified length and all elements equal to the specified value.
     * @param value the value to be duplicated, which will be assigned to every element of result array
     * @param count the count of duplication, which is the length of result array
     * @return the result array
     */
    public static long[] duplicate(long value, int count) {
        long[] result = new long[count];
        Arrays.fill(result, value);
        return result;
    }

    /**
     * Creates and returns a float array with the specified length and all elements equal to the specified value.
     * @param value the value to be duplicated, which will be assigned to every element of result array
     * @param count the count of duplication, which is the length of result array
     * @return the result array
     */
    public static float[] duplicate(float value, int count) {
        float[] result = new float[count];
        Arrays.fill(result, value);
        return result;
    }

    /**
     * Creates and returns a double array with the specified length and all elements equal to the specified value.
     * @param value the value to be duplicated, which will be assigned to every element of result array
     * @param count the count of duplication, which is the length of result array
     * @return the result array
     */
    public static double[] duplicate(double value, int count) {
        double[] result = new double[count];
        Arrays.fill(result, value);
        return result;
    }

    /**
     * Creates and returns an object array with the specified length and all elements equal to the specified value.
     * @param value the value to be duplicated, which will be assigned to every element of result array
     * @param count the count of duplication, which is the length of result array
     * @return the result array
     */
    public static Object[] duplicate(Object value, int count) {
        Object[] result = new Object[count];
        Arrays.fill(result, value);
        return result;
    }

    /**
     * Returns whether the specified array is null or empty.
     * @param array the array to be tested
     * @param <T> the class of the objects in the array
     * @return {@code true} if the specified array is null or empty
     */
    public static <T> boolean isNullOrEmpty(T[] array) {
        return array == null || array.length == 0;
    }
}
