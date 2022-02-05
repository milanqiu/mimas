package net.milanqiu.mimas.guava.collect;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import net.milanqiu.mimas.collect.CollectionUtils;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-30
 * @author Milan Qiu
 */
public class IterablesTest {

    @Test
    public void test_concat() throws Exception {
        /*
            Iterable concat(Iterable...)
            Iterable concat(Iterable<Iterable>)
            Returns a lazy view of the concatenation of several iterables.
            ps: The actual type of returned value is an inner class inheriting abstract class FluentIterable.
         */
        List<String> list1 = ImmutableList.of(STR_0, STR_1);
        List<String> list2 = ImmutableList.of(STR_1, STR_2, STR_2);
        Set<String> set3 = ImmutableSet.of(STR_2, STR_3);
        FluentIterable<String> expected = FluentIterable.of(new String[]{STR_0, STR_1, STR_1, STR_2, STR_2, STR_2, STR_3});
        Assert.assertTrue(Iterables.elementsEqual(expected, Iterables.concat(list1, list2, set3)));
        Assert.assertTrue(Iterables.elementsEqual(expected, Iterables.concat(ImmutableList.of(list1, list2, set3))));
    }

    @Test
    public void test_frequency() throws Exception {
        /*
            int frequency(Iterable, Object)
            Returns the number of occurrences of the object.
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_1, STR_1, STR_2, STR_2);
        Assert.assertEquals(1, Iterables.frequency(list, STR_0));
        Assert.assertEquals(3, Iterables.frequency(list, STR_1));
        Assert.assertEquals(2, Iterables.frequency(list, STR_2));
        Assert.assertEquals(0, Iterables.frequency(list, STR_3));
    }

    @Test
    public void test_partition() throws Exception {
        /*
            Iterable<List> partition(Iterable, int)
            Returns an unmodifiable view of the iterable partitioned into chunks of the specified size.
            ps: The actual type of returned value is an inner class inheriting abstract class FluentIterable.
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_1, STR_2, STR_2, STR_2, STR_3);
        Iterable<List<String>> result = Iterables.partition(list, 3);
        Iterator<List<String>> resultIterator = result.iterator();
        Assert.assertEquals(ImmutableList.of(STR_0, STR_1, STR_1), resultIterator.next());
        Assert.assertEquals(ImmutableList.of(STR_2, STR_2, STR_2), resultIterator.next());
        Assert.assertEquals(ImmutableList.of(STR_3),               resultIterator.next());
    }

    @Test
    public void test_paddedPartition() throws Exception {
        /*
            Iterable<List> paddedPartition(Iterable, int)
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_1, STR_2, STR_2, STR_2, STR_3);
        Iterable<List<String>> result = Iterables.paddedPartition(list, 3);
        Iterator<List<String>> resultIterator = result.iterator();
        Assert.assertEquals(ImmutableList.of(STR_0, STR_1, STR_1), resultIterator.next());
        Assert.assertEquals(ImmutableList.of(STR_2, STR_2, STR_2), resultIterator.next());
        Assert.assertEquals(Lists.newArrayList(STR_3, null, null), resultIterator.next());
    }

    @Test
    public void test_getFirst() throws Exception {
        /*
            T getFirst(Iterable, T default)
            Returns the first element of the iterable, or the default value if empty.
         */
        Assert.assertEquals(STR_0, Iterables.getFirst(ImmutableList.of(STR_0, STR_1, STR_1, STR_2), STR_4));
        Assert.assertEquals(STR_4, Iterables.getFirst(ImmutableList.of(), STR_4));
    }

    @Test
    public void test_getLast() throws Exception {
        /*
            T getLast(Iterable)
            Returns the last element of the iterable, or fails fast with a NoSuchElementException if it's empty.
         */
        Assert.assertEquals(STR_2, Iterables.getLast(ImmutableList.of(STR_0, STR_1, STR_1, STR_2)));
        AssertExt.assertExceptionThrown(() -> Iterables.getLast(ImmutableList.of()), NoSuchElementException.class);

        /*
            T getLast(Iterable, T default)
         */
        Assert.assertEquals(STR_2, Iterables.getLast(ImmutableList.of(STR_0, STR_1, STR_1, STR_2), STR_4));
        Assert.assertEquals(STR_4, Iterables.getLast(ImmutableList.of(), STR_4));
    }

    @Test
    public void test_elementsEqual() throws Exception {
        /*
            boolean elementsEqual(Iterable, Iterable)
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
            Iterable unmodifiableIterable(Iterable)
            Returns an unmodifiable view of the iterable.
            ps: The actual type of returned value is a private class inheriting abstract class FluentIterable.
         */
        List<String> modifiableList = new ArrayList<>();
        modifiableList.add(STR_0);
        modifiableList.add(STR_1);
        modifiableList.add(STR_2);
        FluentIterable<String> expected = FluentIterable.of(new String[]{STR_0, STR_1, STR_2});
        Assert.assertTrue(Iterables.elementsEqual(expected, Iterables.unmodifiableIterable(modifiableList)));
    }

    @Test
    public void test_limit() throws Exception {
        /*
            Iterable limit(Iterable, int)
            Returns an Iterable returning at most the specified number of elements.
            ps: The actual type of returned value is an inner class inheriting abstract class FluentIterable.
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_2, STR_2, STR_3);
        FluentIterable<String> expected = FluentIterable.of(new String[]{STR_0, STR_1, STR_2});
        Assert.assertTrue(Iterables.elementsEqual(expected, Iterables.limit(list, 3)));
    }

    @Test
    public void test_getOnlyElement() throws Exception {
        /*
            T getOnlyElement(Iterable)
            Returns the only element in Iterable. Fails fast if the iterable is empty or has multiple elements.
         */
        Assert.assertEquals(STR_2, Iterables.getOnlyElement(ImmutableList.of(STR_2)));
        AssertExt.assertExceptionThrown(() -> Iterables.getOnlyElement(ImmutableList.of()), NoSuchElementException.class);
        AssertExt.assertExceptionThrown(() -> Iterables.getOnlyElement(ImmutableList.of(STR_0, STR_1, STR_1, STR_2)), IllegalArgumentException.class);

        /*
            T getOnlyElement(Iterable, T default)
         */
        Assert.assertEquals(STR_2, Iterables.getOnlyElement(ImmutableList.of(STR_2), STR_4));
        Assert.assertEquals(STR_4, Iterables.getOnlyElement(ImmutableList.of(), STR_4));
        AssertExt.assertExceptionThrown(() -> Iterables.getOnlyElement(ImmutableList.of(STR_0, STR_1, STR_1, STR_2), STR_4), IllegalArgumentException.class);
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
        AssertExt.assertExceptionThrown(() -> {
            Iterables.find(list, new Predicate<String>() {
                @Override
                public boolean apply(String s) {
                    return s.equals(STR_3);
                }
            });
        }, NoSuchElementException.class);

        /*
            T find(Iterable, Predicate, T default)
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
        List<String> list = Lists.newArrayList(STR_0, STR_1, STR_2, STR_1);
        Assert.assertTrue(Iterables.removeIf(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_1) || s.equals(STR_3);
            }
        }));
        Assert.assertEquals(ImmutableList.of(STR_0, STR_2), list);
        Assert.assertFalse(Iterables.removeIf(list, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_1) || s.equals(STR_3);
            }
        }));
        Assert.assertEquals(ImmutableList.of(STR_0, STR_2), list);
    }

    @Test
    public void test_transform() throws Exception {
        /*
            Also implemented in FluentIterable, Iterators, Collections2, Lists.
            A transform operation for Set is omitted, since an efficient contains(Object) operation could not be
            supported. Instead, use Sets.newHashSet(Collections2.transform(set, function)) to create a copy of a
            transformed set.
         */
        List<Integer> from = ImmutableList.of(INT_0, INT_1, INT_2, INT_2);
        Iterable<String> to = Iterables.transform(from, new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return integer.toString();
            }
        });
        Assert.assertTrue(Iterables.elementsEqual(to, ImmutableList.of(
                STR_OF_INT_0, STR_OF_INT_1, STR_OF_INT_2, STR_OF_INT_2)));
    }

    @Test
    public void test_toString() throws Exception {
        List<Integer> list = ImmutableList.of(INT_0, INT_1, INT_2, INT_2);
        Assert.assertEquals("["+STR_OF_INT_0+", "+STR_OF_INT_1+", "+STR_OF_INT_2+", "+STR_OF_INT_2+"]",
                Iterables.toString(list));
    }

    @Test
    public void test_cycle() throws Exception {
        // Iterable<T> cycle(Iterable<T> iterable)
        {
            List<String> list = ImmutableList.of(STR_0, STR_1, STR_2, STR_2);
            Iterable<String> result = Iterables.cycle(list);
            Iterator<String> resultIterator = result.iterator();
            int counter = 0;
            while (counter < 100) {
                switch (counter++ % 4) {
                    case 0:
                        Assert.assertEquals(STR_0, resultIterator.next());
                        break;
                    case 1:
                        Assert.assertEquals(STR_1, resultIterator.next());
                        break;
                    case 2:
                        Assert.assertEquals(STR_2, resultIterator.next());
                        break;
                    case 3:
                        Assert.assertEquals(STR_2, resultIterator.next());
                        break;
                }
            }
        }

        // Iterable<T> cycle(T... elements)
        {
            Iterable<String> result = Iterables.cycle(STR_0, STR_1, STR_2, STR_2);
            Iterator<String> resultIterator = result.iterator();
            int counter = 0;
            while (counter < 100) {
                switch (counter++ % 4) {
                    case 0:
                        Assert.assertEquals(STR_0, resultIterator.next());
                        break;
                    case 1:
                        Assert.assertEquals(STR_1, resultIterator.next());
                        break;
                    case 2:
                        Assert.assertEquals(STR_2, resultIterator.next());
                        break;
                    case 3:
                        Assert.assertEquals(STR_2, resultIterator.next());
                        break;
                }
            }
        }
    }

    @Test
    public void test_skip() throws Exception {
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_2, STR_2);
        Assert.assertTrue(Iterables.elementsEqual(Iterables.skip(list, 2), ImmutableList.of(STR_2, STR_2)));
        Assert.assertTrue(Iterables.elementsEqual(Iterables.skip(list, 4), ImmutableList.of()));
    }

    @Test
    public void test_consumingIterable() throws Exception {
        List<String> list = Lists.newArrayList(STR_0, STR_1, STR_2, STR_2);
        Iterable<String> consuming = Iterables.consumingIterable(list);
        Assert.assertEquals(ImmutableList.of(STR_0, STR_1, STR_2, STR_2), list);

        Assert.assertEquals(STR_0, consuming.iterator().next());
        Assert.assertEquals(ImmutableList.of(STR_1, STR_2, STR_2), list);

        Assert.assertEquals(STR_1, consuming.iterator().next());
        Assert.assertEquals(ImmutableList.of(STR_2, STR_2), list);

        Assert.assertEquals("["+STR_2+", "+STR_2+"]", Iterables.toString(consuming));
        Assert.assertTrue(list.isEmpty());
        Assert.assertFalse(consuming.iterator().hasNext());
    }

    @Test
    public void test_isEmpty() throws Exception {
        Assert.assertTrue(Iterables.isEmpty(ImmutableList.of()));
        Assert.assertFalse(Iterables.isEmpty(ImmutableList.of(STR_0, STR_1, STR_2, STR_2)));
    }

    @Test
    public void test_mergeSorted() throws Exception {
        List<Integer> list1 = ImmutableList.of(1, 3, 5);
        List<Integer> list2 = ImmutableList.of(2, 4);
        List<Integer> list3 = ImmutableList.of(1, 2, 3);
        List<Integer> list4 = ImmutableList.of(3, 2, 1);
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of(1, 1, 2, 2, 3, 3, 4, 5),
                Iterables.mergeSorted(ImmutableList.of(list1, list2, list3), Ordering.natural())));
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of(1, 2, 3, 3, 2, 1, 4, 5),
                Iterables.mergeSorted(ImmutableList.of(list1, list2, list4), Ordering.natural())));
    }
}
