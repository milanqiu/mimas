package net.milanqiu.mimas.collect;

import java.util.*;

/**
 * Utilities related to collection.
 *
 * <p>Creation Date: 2014-7-25
 * @author Milan Qiu
 */
public class CollectionUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private CollectionUtils() {
    }

    /**
     * Counts how many times elements occur in an iterable.
     * @param iterable the iterable to be counted
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
     * Compares two iterables ignoring the element order.
     * @param itr1 the first iterable to be compared
     * @param itr2 the second iterable to be compared
     * @return <code>true</code> if elements of two iterables are same
     */
    public static <T> boolean equalsIgnoringOrder(Iterable<? extends T> itr1, Iterable<? extends T> itr2) {
        Map<T, Integer> itr1Elements = countsOccurrence(itr1);
        Map<T, Integer> itr2Elements = countsOccurrence(itr2);
        return itr1Elements.equals(itr2Elements);
    }
}
