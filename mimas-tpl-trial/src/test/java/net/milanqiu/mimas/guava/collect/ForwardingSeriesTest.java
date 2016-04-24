package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.ImmutableList;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-09-05
 * @author Milan Qiu
 */
public class ForwardingSeriesTest {

    /*
        -----------------------------------------------------
        |   Interface	    |   Forwarding Decorator        |
        -----------------------------------------------------
        |   Collection	    |   ForwardingCollection        |
        |   List	        |   ForwardingList              |
        |   Set	            |   ForwardingSet               |
        |   SortedSet	    |   ForwardingSortedSet         |
        |   Map	            |   ForwardingMap               |
        |   SortedMap	    |   ForwardingSortedMap         |
        |   ConcurrentMap	|   ForwardingConcurrentMap     |
        |   Map.Entry	    |   ForwardingMapEntry          |
        |   Queue	        |   ForwardingQueue             |
        |   Iterator	    |   ForwardingIterator          |
        |   ListIterator	|   ForwardingListIterator      |
        |   Multiset	    |   ForwardingMultiset          |
        |   Multimap	    |   ForwardingMultimap          |
        |   ListMultimap    |   ForwardingListMultimap      |
        |   SetMultimap	    |   ForwardingSetMultimap       |
        -----------------------------------------------------
     */

    private class AddTenIntList extends ForwardingList<Integer> {

        List<Integer> delegator = new ArrayList<>();

        @Override
        protected List<Integer> delegate() {
            return delegator;
        }

        @Override
        public void add(int index, Integer element) {
            super.add(index, element+10);
            /*
                Remember, by default, all methods forward directly to the delegate,
                so overriding ForwardingList.add(int, E) will not change the behavior of
                ForwardingList.add(E) and ForwardingList.addAll(Collection<E>)
             */
        }

        @Override
        public boolean add(Integer element) {
            return standardAdd(element);
        }

        @SuppressWarnings("NullableProblems")
        @Override
        public boolean addAll(Collection<? extends Integer> collection) {
            return standardAddAll(collection);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Integer> elements) {
            // standardAddAll(int, Iterable<E>) doesn't invoke add(int, E) internally, maybe it is a bug
            return standardAddAll(index, elements);
        }
    }

    @Test
    public void test_ForwardingList() throws Exception {
        AddTenIntList list = new AddTenIntList();

        Assert.assertTrue(list.add(INT_1));
        Assert.assertEquals(INT_1+10, (int) list.get(0));

        list.add(0, INT_2);
        Assert.assertEquals(INT_2+10, (int) list.get(0));
        Assert.assertEquals(INT_1+10, (int) list.get(1));

        Assert.assertTrue(list.addAll(ImmutableList.of(INT_3, INT_4)));
        Assert.assertEquals(INT_2+10, (int) list.get(0));
        Assert.assertEquals(INT_1+10, (int) list.get(1));
        Assert.assertEquals(INT_3+10, (int) list.get(2));
        Assert.assertEquals(INT_4+10, (int) list.get(3));

        Assert.assertTrue(list.addAll(2, ImmutableList.of(INT_0)));
        Assert.assertEquals(INT_2+10, (int) list.get(0));
        Assert.assertEquals(INT_1+10, (int) list.get(1));
        Assert.assertEquals(INT_0,    (int) list.get(2));
        Assert.assertEquals(INT_3+10, (int) list.get(3));
        Assert.assertEquals(INT_4+10, (int) list.get(4));
    }
}
