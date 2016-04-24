package net.milanqiu.mimas.lang;

import java.util.Comparator;

/**
 * Utilities related to types,
 * mainly to primitive types and major reference types({@link java.lang.Object} and {@link java.lang.String}).
 * <p>
 * Creation Date: 2014-07-25
 * @author Milan Qiu
 */
public class TypeUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private TypeUtils() {}

    /**
     * Returns whether the specified boolean value is default.
     * Usually, a default boolean is {@code false}.
     * @param value the boolean value to be tested
     * @return {@code true} if the specified boolean value is default
     */
    public static boolean isDefault(boolean value) {
        return !value;
    }

    /**
     * Returns whether the specified character value is default.
     * Usually, a default character is {@code '&#92;u0000'}.
     * @param value the character value to be tested
     * @return {@code true} if the specified character value is default
     */
    public static boolean isDefault(char value) {
        return value == '\u0000';
    }

    /**
     * Returns whether the specified integer value is default.
     * Usually, a default integer is {@code 0}.
     * @param value the integer value to be tested
     * @return {@code true} if the specified integer value is default
     */
    public static boolean isDefault(int value) {
        return value == 0;
    }

    /**
     * Returns whether the specified long value is default.
     * Usually, a default long is {@code 0L}.
     * @param value the long value to be tested
     * @return {@code true} if the specified long value is default
     */
    public static boolean isDefault(long value) {
        return value == 0L;
    }

    /**
     * Returns whether the specified float value is default.
     * Usually, a default float is {@code 0.0f}.
     * @param value the float value to be tested
     * @return {@code true} if the specified float value is default
     */
    public static boolean isDefault(float value) {
        return value == 0.0f;
    }

    /**
     * Returns whether the specified double value is default.
     * Usually, a default double is {@code 0.0d}.
     * @param value the double value to be tested
     * @return {@code true} if the specified double value is default
     */
    public static boolean isDefault(double value) {
        return value == 0.0d;
    }

    /**
     * Returns whether the specified string value is default.
     * Usually, a default string is {@code null}.
     * @param value the string value to be tested
     * @return {@code true} if the specified string value is default
     */
    public static boolean isDefault(String value) {
        return value == null;
    }

    /**
     * Returns whether the specified object value is default.
     * Usually, a default object is {@code null}.
     * @param value the object value to be tested
     * @return {@code true} if the specified object value is default
     */
    public static boolean isDefault(Object value) {
        return value == null;
    }

    /**
     * A constant comparator to compare two objects.
     * It invokes {@link java.lang.Object#equals(Object)} at first and return {@code 0} if these two objects are equal.
     * Otherwise, it will compare their {@link java.lang.Object#hashCode()} and then {@link java.lang.Object#toString()}.
     * Anyway, {@code null} is the least if any of them is {@code null}.
     */
    public static final Comparator<Object> OBJECT_COMPARATOR = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            if (o1 == o2)
                return 0;
            if (o1 == null)
                return -1;
            if (o2 == null)
                return 1;
            if (o1.equals(o2))
                return 0;

            int o1HashCode = o1.hashCode();
            int o2HashCode = o2.hashCode();
            if (o1HashCode > o2HashCode)
                return 1;
            else if (o1HashCode < o2HashCode)
                return -1;
            else {
                return o1.toString().compareTo(o2.toString());
            }
        }
    };

    /**
     * A tiny float. Usually used to compare two floats as delta.
     */
    public static final float PARTICLE_FLOAT = 0.000_000_000_1f;
    /**
     * A tiny double. Usually used to compare two doubles as delta.
     */
    public static final double PARTICLE_DOUBLE = 0.000_000_000_1;

    /**
     * A constant holding the minimum value a unsigned byte can have, which equals to 0.
     */
    public static final int UNSIGNED_BYTE_MIN_VALUE = 0x00;
    /**
     * A constant holding the maximum value a unsigned byte can have, which equals to 2<sup>8</sup>-1.
     */
    public static final int UNSIGNED_BYTE_MAX_VALUE = 0xFF;
    /**
     * A constant holding the minimum value a unsigned short can have, which equals to 0.
     */
    public static final int UNSIGNED_SHORT_MIN_VALUE = 0x0000;
    /**
     * A constant holding the maximum value a unsigned short can have, which equals to 2<sup>16</sup>-1.
     */
    public static final int UNSIGNED_SHORT_MAX_VALUE = 0xFFFF;

    /**
     * The count of all character values.
     */
    public static final int CHAR_VALUE_COUNT           = Character.MAX_VALUE - Character.MIN_VALUE + 1;
    /**
     * The count of all byte values.
     */
    public static final int BYTE_VALUE_COUNT           = Byte.MAX_VALUE - Byte.MIN_VALUE + 1;
    /**
     * The count of all unsigned byte values.
     */
    public static final int UNSIGNED_BYTE_VALUE_COUNT  = UNSIGNED_BYTE_MAX_VALUE - UNSIGNED_BYTE_MIN_VALUE + 1;
    /**
     * The count of all short values.
     */
    public static final int SHORT_VALUE_COUNT          = Short.MAX_VALUE - Short.MIN_VALUE + 1;
    /**
     * The count of all unsigned short values.
     */
    public static final int UNSIGNED_SHORT_VALUE_COUNT = UNSIGNED_SHORT_MAX_VALUE - UNSIGNED_SHORT_MIN_VALUE + 1;

    /**
     * Returns an array holding all character values in ascending order.
     * The production of array costs some time. So if need to use the array repeatedly, create a variable to cache it.
     * @return an array holding all character values in ascending order
     */
    public static char[] getAllCharValues() {
        char[] result = new char[CHAR_VALUE_COUNT];
        int index = 0;
        for (int i = Character.MIN_VALUE; i <= Character.MAX_VALUE; i++) {
            result[index++] = (char) i;
        }
        return result;
    }

    /**
     * Returns an array holding all byte values in ascending order.
     * The production of array costs some time. So if need to use the array repeatedly, create a variable to cache it.
     * @return an array holding all byte values in ascending order
     */
    public static byte[] getAllByteValues() {
        byte[] result = new byte[BYTE_VALUE_COUNT];
        int index = 0;
        for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
            result[index++] = (byte) i;
        }
        return result;
    }

    /**
     * Returns an array holding all unsigned byte values in ascending order.
     * The production of array costs some time. So if need to use the array repeatedly, create a variable to cache it.
     * @return an array holding all unsigned byte values in ascending order
     */
    public static int[] getAllUnsignedByteValues() {
        int[] result = new int[UNSIGNED_BYTE_VALUE_COUNT];
        int index = 0;
        for (int i = UNSIGNED_BYTE_MIN_VALUE; i <= UNSIGNED_BYTE_MAX_VALUE; i++) {
            result[index++] = i;
        }
        return result;
    }

    /**
     * Returns an array holding all short values in ascending order.
     * The production of array costs some time. So if need to use the array repeatedly, create a variable to cache it.
     * @return an array holding all short values in ascending order
     */
    public static short[] getAllShortValues() {
        short[] result = new short[SHORT_VALUE_COUNT];
        int index = 0;
        for (int i = Short.MIN_VALUE; i <= Short.MAX_VALUE; i++) {
            result[index++] = (short) i;
        }
        return result;
    }

    /**
     * Returns an array holding all unsigned short values in ascending order.
     * The production of array costs some time. So if need to use the array repeatedly, create a variable to cache it.
     * @return an array holding all unsigned short values in ascending order
     */
    public static int[] getAllUnsignedShortValues() {
        int[] result = new int[UNSIGNED_SHORT_VALUE_COUNT];
        int index = 0;
        for (int i = UNSIGNED_SHORT_MIN_VALUE; i <= UNSIGNED_SHORT_MAX_VALUE; i++) {
            result[index++] = i;
        }
        return result;
    }

    /**
     * Traverses all character values in ascending order.
     * @param action the action to be taken in each visit
     */
    public static void traverseCharValues(RunnableWithParam.WithChar action) {
        for (int i = Character.MIN_VALUE; i <= Character.MAX_VALUE; i++) {
            action.run((char) i);
        }
    }

    /**
     * Traverses all byte values in ascending order.
     * @param action the action to be taken in each visit
     */
    public static void traverseByteValues(RunnableWithParam.WithByte action) {
        for (int i = Byte.MIN_VALUE; i <= Byte.MAX_VALUE; i++) {
            action.run((byte) i);
        }
    }

    /**
     * Traverses all unsigned byte values in ascending order.
     * @param action the action to be taken in each visit
     */
    public static void traverseUnsignedByteValues(RunnableWithParam.WithInt action) {
        for (int i = UNSIGNED_BYTE_MIN_VALUE; i <= UNSIGNED_BYTE_MAX_VALUE; i++) {
            action.run(i);
        }
    }

    /**
     * Traverses all short values in ascending order.
     * @param action the action to be taken in each visit
     */
    public static void traverseShortValues(RunnableWithParam.WithShort action) {
        for (int i = Short.MIN_VALUE; i <= Short.MAX_VALUE; i++) {
            action.run((short) i);
        }
    }

    /**
     * Traverses all unsigned short values in ascending order.
     * @param action the action to be taken in each visit
     */
    public static void traverseUnsignedShortValues(RunnableWithParam.WithInt action) {
        for (int i = UNSIGNED_SHORT_MIN_VALUE; i <= UNSIGNED_SHORT_MAX_VALUE; i++) {
            action.run(i);
        }
    }

    /**
     * Returns a string holding all character values in ascending order.
     * The production of string costs some time. So if need to use the string repeatedly, create a variable to cache it.
     * @return a string holding all character values in ascending order
     */
    public static String getAllCharValuesString() {
        return new String(getAllCharValues());
    }
}
