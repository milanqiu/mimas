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
