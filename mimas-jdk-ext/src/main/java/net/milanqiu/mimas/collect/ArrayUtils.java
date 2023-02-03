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
     * Concatenates two character arrays and returns the result array.
     * @param array1 the first array to be concatenated
     * @param array2 the second array to be concatenated
     * @return the result array
     */
    public static char[] concat(char[] array1, char[] array2) {
        char[] result = new char[array1.length+array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenates two byte arrays and returns the result array.
     * @param array1 the first array to be concatenated
     * @param array2 the second array to be concatenated
     * @return the result array
     */
    public static byte[] concat(byte[] array1, byte[] array2) {
        byte[] result = new byte[array1.length+array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenates two short arrays and returns the result array.
     * @param array1 the first array to be concatenated
     * @param array2 the second array to be concatenated
     * @return the result array
     */
    public static short[] concat(short[] array1, short[] array2) {
        short[] result = new short[array1.length+array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenates two integer arrays and returns the result array.
     * @param array1 the first array to be concatenated
     * @param array2 the second array to be concatenated
     * @return the result array
     */
    public static int[] concat(int[] array1, int[] array2) {
        int[] result = new int[array1.length+array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenates two long arrays and returns the result array.
     * @param array1 the first array to be concatenated
     * @param array2 the second array to be concatenated
     * @return the result array
     */
    public static long[] concat(long[] array1, long[] array2) {
        long[] result = new long[array1.length+array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenates two float arrays and returns the result array.
     * @param array1 the first array to be concatenated
     * @param array2 the second array to be concatenated
     * @return the result array
     */
    public static float[] concat(float[] array1, float[] array2) {
        float[] result = new float[array1.length+array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenates two double arrays and returns the result array.
     * @param array1 the first array to be concatenated
     * @param array2 the second array to be concatenated
     * @return the result array
     */
    public static double[] concat(double[] array1, double[] array2) {
        double[] result = new double[array1.length+array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
        return result;
    }

    /**
     * Concatenates two object arrays and returns the result array.
     * @param array1 the first array to be concatenated
     * @param array2 the second array to be concatenated
     * @return the result array
     */
    public static Object[] concat(Object[] array1, Object[] array2) {
        Object[] result = new Object[array1.length+array2.length];
        System.arraycopy(array1, 0, result, 0, array1.length);
        System.arraycopy(array2, 0, result, array1.length, array2.length);
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
