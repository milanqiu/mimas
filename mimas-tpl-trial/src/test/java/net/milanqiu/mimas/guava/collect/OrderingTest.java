package net.milanqiu.mimas.guava.collect;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Ordering;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-18
 * @author Milan Qiu
 */
public class OrderingTest {

    private List<Integer> ints = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        ints.add(5);
        ints.add(30);
        ints.add(1);
        ints.add(4);
        ints.add(2);
    }

    @Test
    public void test_natural() throws Exception {
        /*
            Ordering natural()
            Uses the natural ordering on Comparable types.
         */
        Collections.sort(ints, Ordering.natural());
        Assert.assertEquals(ImmutableList.of(1, 2, 4, 5, 30), ints);
    }

    @Test
    public void test_usingToString() throws Exception {
        /*
            Ordering usingToString()
            Compares objects by the lexicographical ordering of their string representations, as returned by toString().
         */
        Collections.sort(ints, Ordering.usingToString());
        Assert.assertEquals(ImmutableList.of(1, 2, 30, 4, 5), ints);
    }

    @Test
    public void test_from() throws Exception {
        /*
            Ordering from(Comparator)
            Making a preexisting Comparator into an Ordering.
         */
        Collections.sort(ints, Ordering.from(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -o1.compareTo(o2);
            }
        }));
        Assert.assertEquals(ImmutableList.of(30, 5, 4, 2, 1), ints);
    }

    @Test
    public void test_reverse() throws Exception {
        /*
            Ordering reverse()
            Returns the reverse ordering.
         */
        Collections.sort(ints, Ordering.natural().reverse());
        Assert.assertEquals(ImmutableList.of(30, 5, 4, 2, 1), ints);
    }

    @Test
    public void test_nullsFirst() throws Exception {
        /*
            Ordering nullsFirst()
            Returns an Ordering that orders nulls before non-null elements, and otherwise behaves the same as the
            original Ordering.
         */
        ints.add(3, null);
        Collections.sort(ints, Ordering.natural().nullsFirst());
        Assert.assertEquals(Arrays.asList(null, 1, 2, 4, 5, 30), ints);
    }

    @Test
    public void test_nullsLast() throws Exception {
        ints.add(3, null);
        Collections.sort(ints, Ordering.natural().nullsLast());
        Assert.assertEquals(Arrays.asList(1, 2, 4, 5, 30, null), ints);
    }

    @Test
    public void test_compound() throws Exception {
        /*
            Ordering compound(Comparator)
            Returns an Ordering which uses the specified Comparator to "break ties."
         */
        Ordering<Integer> oddsFirstOrdering = new Ordering<Integer>() {
            @Override
            public int compare(Integer integer, Integer integer2) {
                if (integer % 2 == 1 && integer2 % 2 == 0)
                    return -1;
                else if (integer % 2 == 0 && integer2 % 2 == 1)
                    return 1;
                else
                    return 0;
            }
        };
        Collections.sort(ints, oddsFirstOrdering.compound(Ordering.natural()));
        Assert.assertEquals(ImmutableList.of(1, 5, 2, 4, 30), ints);
    }

    @Test
    public void test_onResultOf() throws Exception {
        /*
            Ordering onResultOf(Function)
            Returns an Ordering which orders values by applying the function to them and then comparing the results
            using the original Ordering.
         */
        Collections.sort(ints, Ordering.natural().onResultOf(new Function<Integer, Comparable>() {
            @Override
            public Comparable apply(Integer integer) {
                return integer.toString().replace('4', '9');
            }
        }));
        Assert.assertEquals(ImmutableList.of(1, 2, 30, 5, 4), ints);
    }

    @Test
    public void test_greatestOf() throws Exception {
        /*
            List greatestOf(Iterable iterable, int k)
            Returns the k greatest elements of the specified iterable, according to this ordering, in order from
            greatest to least. Not necessarily stable.
         */
        Assert.assertEquals(ImmutableList.of(30, 5, 4),       Ordering.natural().greatestOf(ints, 3));
        Assert.assertEquals(ImmutableList.of(30, 5, 4, 2, 1), Ordering.natural().greatestOf(ints, 6));
    }

    @Test
    public void test_leastOf() throws Exception {
        Assert.assertEquals(ImmutableList.of(1, 2, 4),        Ordering.natural().leastOf(ints, 3));
        Assert.assertEquals(ImmutableList.of(1, 2, 4, 5, 30), Ordering.natural().leastOf(ints, 6));
    }

    @Test
    public void test_isOrdered() throws Exception {
        /*
            boolean isOrdered(Iterable)
            Tests if the specified Iterable is in nondecreasing order according to this ordering.
         */
        Assert.assertFalse(Ordering.natural().isOrdered(ints));
        Collections.sort(ints, Ordering.natural());
        Assert.assertTrue(Ordering.natural().isOrdered(ints));
        Collections.sort(ints, Ordering.natural().reverse());
        Assert.assertFalse(Ordering.natural().isOrdered(ints));

        ints.add(3, 4);
        Collections.sort(ints, Ordering.natural());
        Assert.assertTrue(Ordering.natural().isOrdered(ints));
    }

    @Test
    public void test_isStrictlyOrdered() throws Exception {
        Assert.assertFalse(Ordering.natural().isStrictlyOrdered(ints));
        Collections.sort(ints, Ordering.natural());
        Assert.assertTrue(Ordering.natural().isStrictlyOrdered(ints));
        Collections.sort(ints, Ordering.natural().reverse());
        Assert.assertFalse(Ordering.natural().isStrictlyOrdered(ints));

        ints.add(3, 4);
        Collections.sort(ints, Ordering.natural());
        Assert.assertFalse(Ordering.natural().isStrictlyOrdered(ints));
    }

    @Test
    public void test_sortedCopy() throws Exception {
        /*
            List sortedCopy(Iterable)
            Returns a sorted copy of the specified elements as a List.
         */
        Assert.assertEquals(ImmutableList.of(1, 2, 4, 5, 30), Ordering.natural().sortedCopy(ints));
        Assert.assertEquals(ImmutableList.of(5, 30, 1, 4, 2), ints);
    }

    @Test
    public void test_immutableSortedCopy() throws Exception {
        Assert.assertEquals(ImmutableList.of(1, 2, 4, 5, 30), Ordering.natural().immutableSortedCopy(ints));
        Assert.assertEquals(ImmutableList.of(5, 30, 1, 4, 2), ints);
    }

    @Test
    public void test_min() throws Exception {
        /*
            E min(E, E)
            Returns the minimum of its two arguments according to this ordering. If the values compare as equal, the
            first argument is returned.
         */
        Assert.assertEquals(100, (int) Ordering.natural().min(200, 100));
        /*
            E min(E, E, E, E...)
            Returns the minimum of its arguments according to this ordering. If there are multiple least values, the
            first is returned.
         */
        Assert.assertEquals(1, (int) Ordering.natural().min(5, 30, 1, 4, 2));
        /*
            E min(Iterable)
            Returns the minimum element of the specified Iterable. Throws a NoSuchElementException if the Iterable is
            empty.
         */
        Assert.assertEquals(1, (int) Ordering.natural().min(ints));
        /*
            E min(Iterator)
         */
        Assert.assertEquals(1, (int) Ordering.natural().min(ints.iterator()));
    }

    @Test
    public void test_max() throws Exception {
        Assert.assertEquals(200, (int) Ordering.natural().max(200, 100));
        Assert.assertEquals(30, (int) Ordering.natural().max(5, 30, 1, 4, 2));
        Assert.assertEquals(30, (int) Ordering.natural().max(ints));
        Assert.assertEquals(30, (int) Ordering.natural().max(ints.iterator()));
    }

    @Test
    public void test_explicit() throws Exception {
        // Ordering<T> explicit(List<T> valuesInOrder)
        {
            Collections.sort(ints, Ordering.explicit(ImmutableList.of(4, 2, 5, 7, 30, 1)));
            Assert.assertEquals(ImmutableList.of(4, 2, 5, 30, 1), ints);

            try {
                Collections.sort(ints, Ordering.explicit(ImmutableList.of(4, 2, 5, 7, 30)));
                DebugUtils.neverGoesHere();
            } catch (Exception e) {
                AssertExt.assertClassification(ClassCastException.class, e);
            }

            try {
                Collections.sort(ints, Ordering.explicit(Arrays.asList(4, 2, 5, 7, 30, 1, null)));
                DebugUtils.neverGoesHere();
            } catch (Exception e) {
                AssertExt.assertClassification(NullPointerException.class, e);
            }
        }

        // Ordering<T> explicit(T leastValue, T... remainingValuesInOrder)
        {
            Collections.sort(ints, Ordering.explicit(4, 2, 5, 7, 30, 1));
            Assert.assertEquals(ImmutableList.of(4, 2, 5, 30, 1), ints);

            try {
                Collections.sort(ints, Ordering.explicit(4, 2, 5, 7, 30));
                DebugUtils.neverGoesHere();
            } catch (Exception e) {
                AssertExt.assertClassification(ClassCastException.class, e);
            }

            try {
                Collections.sort(ints, Ordering.explicit(4, 2, 5, 7, 30, 1, null));
                DebugUtils.neverGoesHere();
            } catch (Exception e) {
                AssertExt.assertClassification(NullPointerException.class, e);
            }
        }
    }

    @Test
    public void test_allEqual() throws Exception {
        Collections.sort(ints, Ordering.allEqual());
        Assert.assertEquals(ImmutableList.of(5, 30, 1, 4, 2), ints);

        List<Object> objs = Arrays.asList(OBJ_0, null, OBJ_1, null, OBJ_2);
        Collections.sort(objs, Ordering.allEqual().nullsLast());
        Assert.assertEquals(Arrays.asList(OBJ_0, OBJ_1, OBJ_2, null, null), objs);
    }

    @Test
    public void test_compare() throws Exception {
        Assert.assertEquals(0, Ordering.natural().compare(1, 1));
        Assert.assertEquals(1, Ordering.natural().compare(2, 1));
        Assert.assertEquals(-1, Ordering.natural().compare(1, 2));
    }

    @Test
    public void test_arbitrary() throws Exception {
        Assert.assertEquals(0, Ordering.arbitrary().compare(OBJ_0, OBJ_0));
        Assert.assertTrue(Arrays.asList(-1, 1).contains(Ordering.arbitrary().compare(OBJ_0, OBJ_1)));

        Assert.assertEquals(0, Ordering.arbitrary().compare(1, 1));
        Assert.assertTrue(Arrays.asList(-1, 1).contains(Ordering.arbitrary().compare(2, 1)));

        // null test
        Assert.assertEquals(1, Ordering.arbitrary().compare(OBJ_0, null));
        Assert.assertEquals(-1, Ordering.arbitrary().compare(null, OBJ_0));
        Assert.assertEquals(0, Ordering.arbitrary().compare(null, null));
    }

    @Test
    public void test_lexicographical() throws Exception {
        Ordering<Iterable<Integer>> o = Ordering.natural().lexicographical();
        Assert.assertEquals(-1, o.compare(ImmutableList.of(),     ImmutableList.of(1)));
        Assert.assertEquals(-1, o.compare(ImmutableList.of(1),    ImmutableList.of(1, 1)));
        Assert.assertEquals(-1, o.compare(ImmutableList.of(1, 1), ImmutableList.of(1, 2)));
        Assert.assertEquals(-1, o.compare(ImmutableList.of(1, 2), ImmutableList.of(2)));

        Assert.assertEquals(1, Ordering.natural().lexicographical().reverse().compare(ImmutableList.of(1), ImmutableList.of(1, 1)));
        Assert.assertEquals(-1, Ordering.natural().reverse().lexicographical().compare(ImmutableList.of(1), ImmutableList.of(1, 1)));
    }

    @Test
    public void test_binarySearch() throws Exception {
        Collections.sort(ints, Ordering.natural());
        Assert.assertEquals(1, Ordering.natural().binarySearch(ints, 2));
        Assert.assertEquals(3, Ordering.natural().binarySearch(ints, 5));
        Assert.assertEquals(-1, Ordering.natural().binarySearch(ints, 0));
        Assert.assertEquals(-6, Ordering.natural().binarySearch(ints, 100));
    }
}
