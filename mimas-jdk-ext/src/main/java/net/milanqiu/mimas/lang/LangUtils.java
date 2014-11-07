package net.milanqiu.mimas.lang;

import java.util.Comparator;

/**
 * Utilities related to language.
 *
 * <p>Creation Date: 2014-7-25
 * @author Milan Qiu
 */
public class LangUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private LangUtils() {
    }

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
     * Usually, a default character is {@code '\u0000'}.
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
     * Usually, a default float is {@code 0.0F}.
     * @param value the float value to be tested
     * @return {@code true} if the specified float value is default
     */
    public static boolean isDefault(float value) {
        return value == 0.0F;
    }

    /**
     * Returns whether the specified double value is default.
     * Usually, a default double is {@code 0.0D}.
     * @param value the double value to be tested
     * @return {@code true} if the specified double value is default
     */
    public static boolean isDefault(double value) {
        return value == 0.0D;
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
     * Returns whether the specified object has a default {@code toString()} member.
     * This means its <code>toString()</code> has never been overrided, or the override method is led by <code>Object.toString()</code>.
     * @param obj the object expected to have default <code>toString()</code> member
     */
    public static boolean hasDefaultToString(Object obj) {
        return obj.toString().startsWith(obj.getClass().getName() + "@");
    }

    /**
     * A constant comparator to compare two objects.
     * It casts <code>equals()</code> at first and return <code>0</code> if these two objects are equal. Otherwise,
     * it would compare <code>hashCode()</code> and then <code>toString()</code>.
     * Anyway, <code>null</code> is the least if any of them is <code>null</code>.
     */
    public static final Comparator<Object> OBJECT_COMPARATOR = new Comparator<Object>() {
        @Override
        public int compare(Object o1, Object o2) {
            if (o1 == o2)
                return 0;
            else if (o1 == null)
                return -1;
            else if (o2 == null)
                return 1;
            else if (o1.equals(o2))
                return 0;
            else {
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
        }
    };
}
