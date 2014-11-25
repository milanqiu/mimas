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
     * Counts how many times each of the different elements occurs in the an {@link java.lang.Iterable} object.
     * @param iterable the {@link java.lang.Iterable} object to count in
     * @return a map with elements as key and counts as value
     */
    public static <T> Map<T, Integer> countsOccurrence(Iterable<? extends T> iterable) {
        Map<T, Integer> result = new HashMap<>();
        for (T element : iterable) {
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
     * @param iterable1 the first {@link java.lang.Iterable} object to be compared
     * @param iterable2 the second {@link java.lang.Iterable} object to be compared
     * @return {@code true} if elements of two {@link java.lang.Iterable} objects are equal ignoring the element order
     */
    public static <T> boolean equalsIgnoringOrder(Iterable<? extends T> iterable1, Iterable<? extends T> iterable2) {
        Map<T, Integer> iterable1Elements = countsOccurrence(iterable1);
        Map<T, Integer> iterable2Elements = countsOccurrence(iterable2);
        return iterable1Elements.equals(iterable2Elements);
    }
}
