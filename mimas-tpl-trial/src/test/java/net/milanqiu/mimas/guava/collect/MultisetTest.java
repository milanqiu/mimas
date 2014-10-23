package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multiset;
import net.milanqiu.mimas.instrumentation.DebugUtils;

import java.util.Set;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-24
 * @author Milan Qiu
 */
public class MultisetTest {

    /*
        Wikipedia defines a multiset, in mathematics, as "a generalization of the notion of set in which members are
        allowed to appear more than once...In multisets, as in sets and in contrast to tuples, the order of elements
        is irrelevant: The multisets {a, a, b} and {a, b, a} are equal."

        There are two main ways of looking at this:
         - This is like an ArrayList<E> without an ordering constraint: ordering does not matter.
         - This is like a Map<E, Integer>, with elements and counts.

         ----------------------------------------------------
         |  HashMap             |   HashMultiset            |
         |  TreeMap             |   TreeMultiset            |
         |  LinkedHashMap       |   LinkedHashMultiset      |
         |  ConcurrentHashMap   |   ConcurrentHashMultiset  |
         |  ImmutableMap        |   ImmutableMultiset       |
         ----------------------------------------------------
     */

    private Multiset<String> multiset;

    @Before
    public void setUp() throws Exception {
        multiset = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_0));
    }

    @Test
    public void test_count() throws Exception {
        /*
            int count(E)
            Count the number of occurrences of an element that have been added to this multiset.
         */

        Assert.assertEquals(3, multiset.count(STR_0));
        Assert.assertEquals(1, multiset.count(STR_1));
        Assert.assertEquals(2, multiset.count(STR_2));
        Assert.assertEquals(0, multiset.count(STR_3));
    }

    @Test
    public void test_elementSet() throws Exception {
        /*
            Set<E> elementSet()
            View the distinct elements of a Multiset<E> as a Set<E>.
         */
        Assert.assertTrue(multiset.elementSet().equals(ImmutableSet.of(STR_0, STR_1, STR_2)));
    }

    @Test
    public void test_entrySet() throws Exception {
        /*
            Set<Multiset.Entry<E>> entrySet()
            Similar to Map.entrySet(), returns a Set<Multiset.Entry<E>>, containing entries supporting getElement()
            and getCount().
         */
        Set<Multiset.Entry<String>> entrySet = multiset.entrySet();
        Assert.assertEquals(3, entrySet.size());
        for (Multiset.Entry<String> entry : entrySet) {
            if (entry.getElement().equals(STR_0))
                Assert.assertEquals(3, entry.getCount());
            else if (entry.getElement().equals(STR_1))
                Assert.assertEquals(1, entry.getCount());
            else if (entry.getElement().equals(STR_2))
                Assert.assertEquals(2, entry.getCount());
            else
                DebugUtils.neverGoesHere();
        }
    }

    @Test
    public void test_add() throws Exception {
        /*
            boolean add(E)
         */
        Assert.assertTrue(multiset.add(STR_1));
        Assert.assertEquals(3, multiset.count(STR_0));
        Assert.assertEquals(2, multiset.count(STR_1));
        Assert.assertEquals(2, multiset.count(STR_2));
        Assert.assertEquals(0, multiset.count(STR_3));

        /*
            int add(E, int)
            Adds the specified number of occurrences of the specified element.
         */
        Assert.assertEquals(0, multiset.add(STR_3, 4));
        Assert.assertEquals(3, multiset.count(STR_0));
        Assert.assertEquals(2, multiset.count(STR_1));
        Assert.assertEquals(2, multiset.count(STR_2));
        Assert.assertEquals(4, multiset.count(STR_3));
    }

    @Test
    public void test_remove() throws Exception {
        /*
            boolean remove(E)
         */
        Assert.assertTrue(multiset.remove(STR_2));
        Assert.assertEquals(3, multiset.count(STR_0));
        Assert.assertEquals(1, multiset.count(STR_1));
        Assert.assertEquals(1, multiset.count(STR_2));
        Assert.assertEquals(0, multiset.count(STR_3));

        /*
            int remove(E, int)
            Removes the specified number of occurrences of the specified element.
         */
        Assert.assertEquals(3, multiset.remove(STR_0, 3));
        Assert.assertEquals(0, multiset.count(STR_0));
        Assert.assertEquals(1, multiset.count(STR_1));
        Assert.assertEquals(1, multiset.count(STR_2));
        Assert.assertEquals(0, multiset.count(STR_3));
    }

    @Test
    public void test_setCount() throws Exception {
        /*
            int setCount(E, int)
            Sets the occurrence count of the specified element to the specified nonnegative value.
         */
        Assert.assertEquals(1, multiset.setCount(STR_1, 4));
        Assert.assertEquals(3, multiset.count(STR_0));
        Assert.assertEquals(4, multiset.count(STR_1));
        Assert.assertEquals(2, multiset.count(STR_2));
        Assert.assertEquals(0, multiset.count(STR_3));

        Assert.assertEquals(2, multiset.setCount(STR_2, 0));
        Assert.assertEquals(3, multiset.count(STR_0));
        Assert.assertEquals(4, multiset.count(STR_1));
        Assert.assertEquals(0, multiset.count(STR_2));
        Assert.assertEquals(0, multiset.count(STR_3));

        /*
            boolean setCount(E, int, int)
            Conditionally sets the count of an element to a new value.
         */
        Assert.assertFalse(multiset.setCount(STR_0, 1, 2));
        Assert.assertEquals(3, multiset.count(STR_0));
        Assert.assertEquals(4, multiset.count(STR_1));
        Assert.assertEquals(0, multiset.count(STR_2));
        Assert.assertEquals(0, multiset.count(STR_3));

        Assert.assertTrue(multiset.setCount(STR_0, 3, 2));
        Assert.assertEquals(2, multiset.count(STR_0));
        Assert.assertEquals(4, multiset.count(STR_1));
        Assert.assertEquals(0, multiset.count(STR_2));
        Assert.assertEquals(0, multiset.count(STR_3));
    }

    @Test
    public void test_size() throws Exception {
        /*
            int size()
            Returns the total number of occurrences of all elements in the Multiset.
         */
        Assert.assertEquals(6, multiset.size());
    }

    @Test
    public void test_addAll() throws Exception {
        multiset.addAll(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_0));
        Assert.assertEquals(6, multiset.count(STR_0));
        Assert.assertEquals(2, multiset.count(STR_1));
        Assert.assertEquals(4, multiset.count(STR_2));
        Assert.assertEquals(0, multiset.count(STR_3));
    }
}
