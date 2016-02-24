package net.milanqiu.mimas.collect;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;

/**
 * Utilities related to collection.
 * <p>
 * Creation Date: 2014-07-25
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
     * Compares two {@link java.lang.Iterable} objects, regardless of their actual types.
     * @param itr1 the first {@link java.lang.Iterable} object to be compared
     * @param itr2 the second {@link java.lang.Iterable} object to be compared
     * @return {@code true} if size of two {@link java.lang.Iterable} objects are equal and their elements are equal one by one
     */
    public static <T> boolean equals(Iterable<? extends T> itr1, Iterable<? extends T> itr2) {
        Iterator itor1 = itr1.iterator();
        Iterator itor2 = itr2.iterator();
        while (itor1.hasNext() && itor2.hasNext()) {
            if (!Objects.equals(itor1.next(), itor2.next()))
                return false;
        }
        return (!itor1.hasNext() && !itor2.hasNext());
    }

    /**
     * Compares two {@link java.lang.Iterable} objects ignoring the element order, regardless of their actual types..
     * @param itr1 the first {@link java.lang.Iterable} object to be compared
     * @param itr2 the second {@link java.lang.Iterable} object to be compared
     * @return {@code true} if size of two {@link java.lang.Iterable} objects are equal and their elements are equal ignoring the element order
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
        return StreamUtils.parallelStreamOf(itr).filter(e -> e!=null).mapToInt(String::length).sum();
    }
}
