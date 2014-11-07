package net.milanqiu.mimas.guava.collect;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.collect.CollectionUtils;

import java.util.*;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-30
 * @author Milan Qiu
 */
public class IterablesTest {

    @Test
    public void test_concat() throws Exception {
        /*
            concat(Iterable...)
            concat(Iterable<Iterable>)
            Returns a lazy view of the concatenation of several iterables.
         */
        List<String> list1 = ImmutableList.of(STR_0, STR_1);
        List<String> list2 = ImmutableList.of(STR_1, STR_2, STR_2);
        Set<String> set3 = ImmutableSet.of(STR_2, STR_3);
        List<String> expectedListResult = ImmutableList.of(STR_0, STR_1, STR_1, STR_2, STR_2, STR_2, STR_3);
        Assert.assertEquals(expectedListResult, ((FluentIterable) Iterables.concat(list1, list2, set3)).toList());
        Assert.assertEquals(expectedListResult, ((FluentIterable) Iterables.concat(ImmutableList.of(list1, list2, set3))).toList());
    }

    @Test
    public void test_frequency() throws Exception {
        /*
            frequency(Iterable, Object)
            Returns the number of occurrences of the object.
         */
        List list = ImmutableList.of(STR_0, STR_1, STR_1, STR_1, STR_2, STR_2);
        Assert.assertEquals(1, Iterables.frequency(list, STR_0));
        Assert.assertEquals(3, Iterables.frequency(list, STR_1));
        Assert.assertEquals(2, Iterables.frequency(list, STR_2));
        Assert.assertEquals(0, Iterables.frequency(list, STR_3));
    }

    @Test
    public void test_partition() throws Exception {
        /*
            partition(Iterable, int)
            Returns an unmodifiable view of the iterable partitioned into chunks of the specified size.
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_1, STR_2, STR_2, STR_2, STR_3);
        List<List<String>> result = ((FluentIterable<List<String>>) Iterables.partition(list, 3)).toList();
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(ImmutableList.of(STR_0, STR_1, STR_1), result.get(0));
        Assert.assertEquals(ImmutableList.of(STR_2, STR_2, STR_2), result.get(1));
        Assert.assertEquals(ImmutableList.of(STR_3), result.get(2));
    }

    @Test
    public void test_paddedPartition() throws Exception {
        /*
            paddedPartition(Iterable, int)
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_1, STR_2, STR_2, STR_2, STR_3);
        List<List<String>> result = ((FluentIterable<List<String>>) Iterables.paddedPartition(list, 3)).toList();
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(ImmutableList.of(STR_0, STR_1, STR_1), result.get(0));
        Assert.assertEquals(ImmutableList.of(STR_2, STR_2, STR_2), result.get(1));
        Assert.assertEquals(Lists.newArrayList(STR_3, null, null), result.get(2));
    }

    @Test
    public void test_getFirst() throws Exception {
        /*
            getFirst(Iterable, T default)
            Returns the first element of the iterable, or the default value if empty.
         */
        Assert.assertEquals(STR_0, Iterables.getFirst(ImmutableList.of(STR_0, STR_1, STR_1, STR_2), STR_4));
        Assert.assertEquals(STR_4, Iterables.getFirst(ImmutableList.of(), STR_4));
    }

    @Test
    public void test_getLast() throws Exception {
        /*
            getLast(Iterable)
            Returns the last element of the iterable, or fails fast with a NoSuchElementException if it's empty.
         */
        Assert.assertEquals(STR_2, Iterables.getLast(ImmutableList.of(STR_0, STR_1, STR_1, STR_2)));
        try {
            Iterables.getLast(ImmutableList.of());
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(NoSuchElementException.class, e);
        }

        /*
            getLast(Iterable, T default)
         */
        Assert.assertEquals(STR_2, Iterables.getLast(ImmutableList.of(STR_0, STR_1, STR_1, STR_2), STR_4));
        Assert.assertEquals(STR_4, Iterables.getLast(ImmutableList.of(), STR_4));
    }

    @Test
    public void test_elementsEqual() throws Exception {
        /*
            elementsEqual(Iterable, Iterable)
            Returns true if the iterables have the same elements in the same order.
         */
        List<String> list1 = Lists.newArrayList(STR_0, STR_1, STR_2);
        List<String> list2 = Lists.newLinkedList(ImmutableList.of(STR_0, STR_1, STR_2));
        List<String> list3 = ImmutableList.of(STR_0, STR_1, STR_2);
        Set<String> set1 = Sets.newLinkedHashSet(ImmutableList.of(STR_0, STR_1, STR_2));
        Set<String> set2 = Sets.newHashSet(STR_0, STR_1, STR_2);
        Assert.assertTrue(Iterables.elementsEqual(list1, list2));
        Assert.assertTrue(Iterables.elementsEqual(list1, list3));
        Assert.assertTrue(Iterables.elementsEqual(list1, set1));
        Assert.assertFalse(Iterables.elementsEqual(list1, set2));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(list1, set2));
    }

    @Test
    public void test_unmodifiableIterable() throws Exception {
        /*
            unmodifiableIterable(Iterable)
            Returns an unmodifiable view of the iterable.
         */
        List<String> modifiableList = new ArrayList<>();
        modifiableList.add(STR_0);
        modifiableList.add(STR_1);
        modifiableList.add(STR_2);
        FluentIterable<String> unmodifiableList = (FluentIterable<String>) Iterables.unmodifiableIterable(modifiableList);
        Assert.assertTrue(Iterables.elementsEqual(unmodifiableList, modifiableList));
    }

    @Test
    public void test_limit() throws Exception {
        /*
            limit(Iterable, int)
            Returns an Iterable returning at most the specified number of elements.
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_2, STR_2, STR_3);
        List<String> result = ((FluentIterable<String>) Iterables.limit(list, 3)).toList();
        Assert.assertEquals(ImmutableList.of(STR_0, STR_1, STR_2), result);
    }

    @Test
    public void test_getOnlyElement() throws Exception {
        /*
            getOnlyElement(Iterable)
            Returns the only element in Iterable. Fails fast if the iterable is empty or has multiple elements.
         */
        Assert.assertEquals(STR_2, Iterables.getOnlyElement(ImmutableList.of(STR_2)));
        try {
            Iterables.getOnlyElement(ImmutableList.of());
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(NoSuchElementException.class, e);
        }
        try {
            Iterables.getOnlyElement(ImmutableList.of(STR_0, STR_1, STR_1, STR_2));
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(IllegalArgumentException.class, e);
        }

        /*
            getOnlyElement(Iterable, T default)
         */
        Assert.assertEquals(STR_2, Iterables.getOnlyElement(ImmutableList.of(STR_2), STR_4));
        Assert.assertEquals(STR_4, Iterables.getOnlyElement(ImmutableList.of(), STR_4));
        try {
            Iterables.getOnlyElement(ImmutableList.of(STR_0, STR_1, STR_1, STR_2), STR_4);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(IllegalArgumentException.class, e);
        }
    }

    /*
        [Collection-Like]

        Typically, collections support these operations naturally on other collections, but not on iterables.

        Each of these operations delegates to the corresponding Collection interface method when the input is actually
        a Collection. For example, if Iterables.size is passed a Collection, it will call the Collection.size method
        instead of walking through the iterator.

        -------------------------------------------------------------------------------------------------
        |   Method                                              |   Analogous Collection method         |
        -------------------------------------------------------------------------------------------------
        |   addAll(Collection addTo, Iterable toAdd)            |   Collection.addAll(Collection)       |
        |   contains(Iterable, Object)                          |   Collection.contains(Object)         |
        |   removeAll(Iterable removeFrom, Collection toRemove) |   Collection.removeAll(Collection)    |
        |   retainAll(Iterable removeFrom, Collection toRetain) |   Collection.retainAll(Collection)    |
        |   size(Iterable)                                      |   Collection.size()                   |
        |   toArray(Iterable, Class)                            |   Collection.toArray(T[])             |
        |   isEmpty(Iterable)                                   |   Collection.isEmpty()                |
        |   get(Iterable, int)                                  |   List.get(int)                       |
        |   toString(Iterable)                                  |   Collection.toString()               |
        -------------------------------------------------------------------------------------------------
     */

    @Test
    public void test_filter() throws Exception {
        /*
            Also implemented in FluentIterable, Iterators, Collections2, Sets.
            But a filtered List view is omitted, because operations such as get(int) could not be supported efficiently.
            Instead, use Lists.newArrayList(Collections2.filter(list, predicate)) to make a copy.
         */
        List<String> unfiltered = ImmutableList.of(STR_0, STR_1, STR_1, STR_1, STR_2, STR_2);
        Iterable<String> filtered = Iterables.filter(unfiltered, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_0) || s.equals(STR_2);
            }
        });
        Assert.assertTrue(Iterables.elementsEqual(filtered, ImmutableList.of(STR_0, STR_2, STR_2)));
    }

    @Test
    public void test_all() throws Exception {
        /*
            boolean all(Iterable, Predicate)
            Do all the elements satisfy the predicate? Lazy: if it finds an element failing the predicate, doesn't
            iterate further.
            Also implemented in FluentIterable, Iterators.
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_2, STR_2);
        Assert.assertTrue(Iterables.all(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_0) || s.equals(STR_1) || s.equals(STR_2);
            }
        }));
        Assert.assertFalse(Iterables.all(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_0) || s.equals(STR_1);
            }
        }));
    }

    @Test
    public void test_any() throws Exception {
        /*
            boolean any(Iterable, Predicate)
            Do any of the elements satisfy the predicate? Lazy: only iterates until it finds an element satisfying the
            predicate.
            Also implemented in FluentIterable, Iterators.
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_2, STR_2);
        Assert.assertTrue(Iterables.any(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_2) || s.equals(STR_3);
            }
        }));
        Assert.assertFalse(Iterables.any(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_3);
            }
        }));
    }

    @Test
    public void test_find() throws Exception {
        /*
            T find(Iterable, Predicate)
            Finds and returns an element satisfying the predicate, or throws a NoSuchElementException.
            Also implemented in Iterators.
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_2, STR_2);
        Assert.assertEquals(STR_1, Iterables.find(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_1) || s.equals(STR_3);
            }
        }));
        try {
            Iterables.find(list, new Predicate<String>() {
                @Override
                public boolean apply(String s) {
                    return s.equals(STR_3);
                }
            });
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(NoSuchElementException.class, e);
        }

        /*
            T find(Ite/rable, Predicate, T default)
         */
        Assert.assertEquals(STR_4, Iterables.find(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_3);
            }
        }, STR_4));
    }

    @Test
    public void test_tryFind() throws Exception {
        /*
            Optional<T> tryFind(Iterable, Predicate)
            Returns an element satisfying the predicate, or Optional.absent().
            Also implemented in Iterators.
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_2, STR_2);
        Assert.assertEquals(Optional.of(STR_1), Iterables.tryFind(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_1) || s.equals(STR_3);
            }
        }));
        Assert.assertEquals(Optional.absent(), Iterables.tryFind(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_3);
            }
        }));
    }

    @Test
    public void test_indexOf() throws Exception {
        /*
            indexOf(Iterable, Predicate)
            Returns the index of the first element of the iterable satisfying the predicate, or -1 if no such element
            could be found.
            Also implemented in Iterators.
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_2, STR_2);
        Assert.assertEquals(1, Iterables.indexOf(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_1) || s.equals(STR_3);
            }
        }));
        Assert.assertEquals(-1, Iterables.indexOf(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_3);
            }
        }));
    }

    @Test
    public void test_removeIf() throws Exception {
        /*
            removeIf(Iterable, Predicate)
            Removes all elements satisfying the predicate, using the Iterator.remove() method.
            Also implemented in Iterators.
         */
        List<String> list = Lists.newArrayList(STR_0, STR_1, STR_2, STR_2);
        Assert.assertTrue(Iterables.removeIf(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_1) || s.equals(STR_3);
            }
        }));
        Assert.assertEquals(ImmutableList.of(STR_0, STR_2, STR_2), list);
        Assert.assertFalse(Iterables.removeIf(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_1) || s.equals(STR_3);
            }
        }));
    }

    @Test
    public void test_transform() throws Exception {
        /*
            Also implemented in FluentIterable, Iterators, Collections2, Lists.
            A transform operation for Set is omitted, since an efficient contains(Object) operation could not be
            supported. Instead, use Sets.newHashSet(Collections2.transform(set, function)) to create a copy of a
            transformed set.
         */
        List<Integer> from = ImmutableList.of(INT_0, INT_1, INT_1, INT_1, INT_2, INT_2);
        Iterable<String> to = Iterables.transform(from, new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return integer.toString();
            }
        });
        Assert.assertTrue(Iterables.elementsEqual(to, ImmutableList.of(
                STR_OF_INT_0, STR_OF_INT_1, STR_OF_INT_1, STR_OF_INT_1, STR_OF_INT_2, STR_OF_INT_2)));
    }
}
