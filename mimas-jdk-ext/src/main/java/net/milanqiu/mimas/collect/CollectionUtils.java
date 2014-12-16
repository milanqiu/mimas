package net.milanqiu.mimas.collect;

import java.util.HashMap;
import java.util.Map;

/**
 * Utilities related to collection.
 * <p>
 * Creation Date: 2014-7-25
 * @author Milan Qiu
 */
public class CollectionUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private CollectionUtils() {}

    /**
     * Counts how many times each of the different elements occurs in an {@link java.lang.Iterable} object.
     * @param itr the {@link java.lang.Iterable} object to count in
     * @return a map with elements as key and counts as value
     */
    public static <T> Map<T, Integer> countsOccurrence(Iterable<? extends T> itr) {
        Map<T, Integer> result = new HashMap<>();
        for (T element : itr) {
            Integer count = result.get(element);
            if (count == null) {
                result.put(element, 1);
            } else {
                result.put(element, count+1);
            }
        }
        return result;
    }

    /**
     * Compares two {@link java.lang.Iterable} objects ignoring the element order.
     * @param itr1 the first {@link java.lang.Iterable} object to be compared
     * @param itr2 the second {@link java.lang.Iterable} object to be compared
     * @return {@code true} if elements of two {@link java.lang.Iterable} objects are equal ignoring the element order
     */
    public static <T> boolean equalsIgnoringOrder(Iterable<? extends T> itr1, Iterable<? extends T> itr2) {
        Map<T, Integer> itr1Elements = countsOccurrence(itr1);
        Map<T, Integer> itr2Elements = countsOccurrence(itr2);
        return itr1Elements.equals(itr2Elements);
    }

    /**
     * Returns the sum of length of all elements in an {@link java.lang.Iterable} object, whose type parameter is
     * {@link java.lang.String}.
     * @param itr the {@link java.lang.Iterable} object to get sum of length, whose type parameter is {@link java.lang.String}
     * @return the sum of length
     */
    public static int getSumLength(Iterable<String> itr) {
        int result = 0;
        for (String element : itr) {
            if (element != null)
                result += element.length();
        }
        return result;
    }
}
