package net.milanqiu.mimas.collect;

/**
 * Utilities related to array.
 *
 * <p>Creation Date: 2014-7-25
 * @author Milan Qiu
 */
public class ArrayUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private ArrayUtils() {
    }

    /**
     * Returns <code>true</code> if the specified array contains the specified value.
     * @param array the specified array to be tested
     * @param value the specified value to be tested
     * @return <code>true</code> if the specified array contains the specified value.
     */
    public static boolean contains(byte[] array, byte value) {
        for (byte element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns <code>true</code> if the specified array contains the specified value.
     * @param array the specified array to be tested
     * @param value the specified value to be tested
     * @return <code>true</code> if the specified array contains the specified value.
     */
    public static boolean contains(char[] array, char value) {
        for (char element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns <code>true</code> if the specified array contains the specified value.
     * @param array the specified array to be tested
     * @param value the specified value to be tested
     * @return <code>true</code> if the specified array contains the specified value.
     */
    public static boolean contains(short[] array, short value) {
        for (short element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns <code>true</code> if the specified array contains the specified value.
     * @param array the specified array to be tested
     * @param value the specified value to be tested
     * @return <code>true</code> if the specified array contains the specified value.
     */
    public static boolean contains(int[] array, int value) {
        for (int element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns <code>true</code> if the specified array contains the specified value.
     * @param array the specified array to be tested
     * @param value the specified value to be tested
     * @return <code>true</code> if the specified array contains the specified value.
     */
    public static boolean contains(long[] array, long value) {
        for (long element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns <code>true</code> if the specified array contains the specified value.
     * @param array the specified array to be tested
     * @param value the specified value to be tested
     * @return <code>true</code> if the specified array contains the specified value.
     */
    public static boolean contains(float[] array, float value) {
        for (float element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns <code>true</code> if the specified array contains the specified value.
     * @param array the specified array to be tested
     * @param value the specified value to be tested
     * @return <code>true</code> if the specified array contains the specified value.
     */
    public static boolean contains(double[] array, double value) {
        for (double element : array) {
            if (element == value)
                return true;
        }
        return false;
    }

    /**
     * Returns <code>true</code> if the specified array contains the specified value.
     * More formally, returns true if and only if the specified array contains at least one element such that
     * <code>(value == null ? element == null : value.equals(element))</code>.
     * @param array the specified array to be tested
     * @param value the specified value to be tested
     * @return <code>true</code> if the specified array contains the specified value.
     */
    public static <T> boolean contains(T[] array, Object value) {
        for (T element : array) {
            if (value == null ? element == null : value.equals(element))
                return true;
        }
        return false;
    }
}
