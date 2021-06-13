package net.milanqiu.mimas.collect;

import java.util.*;

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
     * Converts the specified iterable of subtype to iterable of supertype.
     * @param iterableSubtype the iterable of subtype to be converted
     * @param <T> the supertype to be converted to
     * @return the result iterable of supertype
     */
    public static <T> Iterable<T> convertIterable(Iterable<? extends T> iterableSubtype) {
        return new Iterable<T>() {
            @Override
            public Iterator<T> iterator() {
                return new Iterator<T>() {
                    private Iterator<? extends T> iteratorSubtype = iterableSubtype.iterator();

                    @Override
                    public boolean hasNext() {
                        return iteratorSubtype.hasNext();
                    }

                    @Override
                    public T next() {
                        return iteratorSubtype.next();
                    }
                };
            }
        };
    }

    /**
     * Converts the specified iterable to a set.
     * @param itr the iterable to be converted
     * @param <T> the base class of the objects in the iterable
     * @return the result set
     */
    public static <T> Set<T> iterableToSet(Iterable<? extends T> itr) {
        Set<T> result = new HashSet<>();
        for (T element : itr) {
            result.add(element);
        }
        return result;
    }

    /**
     * Counts how many times each of the different elements occurs in an {@link java.lang.Iterable} object.
     * @param itr the {@link java.lang.Iterable} object to count in
     * @param <T> the base class of the objects in the iterable
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
     * Same as <code>com.google.common.collect.Iterables.elementsEqual(Iterable, Iterable)</code> of guava.
     * @param itr1 the first {@link java.lang.Iterable} object to be compared
     * @param itr2 the second {@link java.lang.Iterable} object to be compared
     * @param <T> the base class of the objects in the iterables
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
     * Compares two {@link java.lang.Iterable} objects ignoring the element order, regardless of their actual types.
     * @param itr1 the first {@link java.lang.Iterable} object to be compared
     * @param itr2 the second {@link java.lang.Iterable} object to be compared
     * @param <T> the base class of the objects in the iterables
     * @return {@code true} if size of two {@link java.lang.Iterable} objects are equal and their elements are equal ignoring the element order
     */
    public static <T> boolean equalsIgnoringOrder(Iterable<? extends T> itr1, Iterable<? extends T> itr2) {
        Map<T, Integer> itr1Elements = countsOccurrence(itr1);
        Map<T, Integer> itr2Elements = countsOccurrence(itr2);
        return itr1Elements.equals(itr2Elements);
    }

    /**
     * Compares two {@link java.lang.Iterable} objects, regardless of their actual types.
     * Other than {@link #equals(Iterable, Iterable)}, during the comparison, it will call {@link Iterator#hasNext()} of the
     * two {@link java.lang.Iterable} objects repeatedly, even if it has no real effect.
     * Usually used for testing custom {@link java.lang.Iterable} implementation.
     * @param itr1 the first {@link java.lang.Iterable} object to be compared
     * @param itr2 the second {@link java.lang.Iterable} object to be compared
     * @param <T> the base class of the objects in the iterables
     * @return {@code true} if size of two {@link java.lang.Iterable} objects are equal and their elements are equal one by one
     */
    public static <T> boolean equalsCallingHasNextRepeatedly(Iterable<? extends T> itr1, Iterable<? extends T> itr2) {
        Iterator itor1 = itr1.iterator();
        Iterator itor2 = itr2.iterator();
        while (itor1.hasNext() && itor2.hasNext()) {
            itor1.hasNext();
            itor2.hasNext();
            if (!Objects.equals(itor1.next(), itor2.next()))
                return false;
        }
        return (!itor1.hasNext() && !itor2.hasNext());
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

    /**
     * Returns the union of two {@link java.lang.Iterable} objects.
     * Notice the element order will be according to the first, then the second.
     * @param itr1 the first {@link java.lang.Iterable} object
     * @param itr2 the second {@link java.lang.Iterable} object
     * @param <T> the base class of the objects in the iterables
     * @return the union of two specified {@link java.lang.Iterable} objects.
     */
    public static <T> List<T> union(Iterable<? extends T> itr1, Iterable<? extends T> itr2) {
        List<T> result = new ArrayList<>();
        Set<T> set1 = iterableToSet(itr1);
        for (T element : itr1) {
            result.add(element);
        }
        for (T element : itr2) {
            if (!set1.contains(element))
                result.add(element);
        }
        return result;
    }

    /**
     * Returns the intersection of two {@link java.lang.Iterable} objects.
     * Notice the element order will be according to the first.
     * @param itr1 the first {@link java.lang.Iterable} object
     * @param itr2 the second {@link java.lang.Iterable} object
     * @param <T> the base class of the objects in the iterables
     * @return the union of two specified {@link java.lang.Iterable} objects.
     */
    public static <T> List<T> intersection(Iterable<? extends T> itr1, Iterable<? extends T> itr2) {
        List<T> result = new ArrayList<>();
        Set<T> set2 = iterableToSet(itr2);
        for (T element : itr1) {
            if (set2.contains(element))
                result.add(element);
        }
        return result;
    }

    /**
     * Returns the difference of two {@link java.lang.Iterable} objects.
     * Notice the element order will be according to the first.
     * @param itr1 the first {@link java.lang.Iterable} object
     * @param itr2 the second {@link java.lang.Iterable} object
     * @param <T> the base class of the objects in the iterables
     * @return the union of two specified {@link java.lang.Iterable} objects.
     */
    public static <T> List<T> difference(Iterable<? extends T> itr1, Iterable<? extends T> itr2) {
        List<T> result = new ArrayList<>();
        Set<T> set2 = iterableToSet(itr2);
        for (T element : itr1) {
            if (!set2.contains(element))
                result.add(element);
        }
        return result;
    }

    /**
     * Returns the symmetric difference of two {@link java.lang.Iterable} objects.
     * Notice the element order will be according to the first, then the second.
     * @param itr1 the first {@link java.lang.Iterable} object
     * @param itr2 the second {@link java.lang.Iterable} object
     * @param <T> the base class of the objects in the iterables
     * @return the union of two specified {@link java.lang.Iterable} objects.
     */
    public static <T> List<T> symmetricDifference(Iterable<? extends T> itr1, Iterable<? extends T> itr2) {
        List<T> result = new ArrayList<>();
        Set<T> set1 = iterableToSet(itr1);
        Set<T> set2 = iterableToSet(itr2);
        for (T element : itr1) {
            if (!set2.contains(element))
                result.add(element);
        }
        for (T element : itr2) {
            if (!set1.contains(element))
                result.add(element);
        }
        return result;
    }
}
