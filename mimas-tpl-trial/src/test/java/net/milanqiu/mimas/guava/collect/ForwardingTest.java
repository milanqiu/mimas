package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.ForwardingList;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-9-5
 * @author Milan Qiu
 */
public class ForwardingTest {

    private class AddOneIntList extends ForwardingList<Integer> {

        List<Integer> delegator = new ArrayList<>();

        @Override
        protected List<Integer> delegate() {
            return delegator;
        }

        @Override
        public void add(int index, Integer element) {
            super.add(index, element+1);
        }

        @Override
        public boolean add(Integer element) {
            return standardAdd(element);
        }

        @Override
        public boolean addAll(Collection<? extends Integer> collection) {
            return standardAddAll(collection);
        }

        @Override
        public boolean addAll(int index, Collection<? extends Integer> elements) {
            // standardAddAll(int, Collection<E>) doesn't call add(int, E) internally, maybe it is a bug
            return standardAddAll(index, elements);
        }
    }

    @Test
    public void test_ForwardingList() throws Exception {
        AddOneIntList list = new AddOneIntList();

        list.add(INT_0);
        Assert.assertEquals(INT_0 + 1, (int) list.get(0));

        list.add(0, INT_1);
        Assert.assertEquals(INT_1 + 1, (int) list.get(0));
        Assert.assertEquals(INT_0 + 1, (int) list.get(1));

        list.addAll(ImmutableList.of(INT_2, INT_3));
        Assert.assertEquals(INT_1 + 1, (int) list.get(0));
        Assert.assertEquals(INT_0 + 1, (int) list.get(1));
        Assert.assertEquals(INT_2 + 1, (int) list.get(2));
        Assert.assertEquals(INT_3 + 1, (int) list.get(3));

        list.addAll(2, ImmutableList.of(INT_4));
        Assert.assertEquals(INT_1 + 1, (int) list.get(0));
        Assert.assertEquals(INT_0 + 1, (int) list.get(1));
        Assert.assertEquals(INT_4,     (int) list.get(2));
        Assert.assertEquals(INT_2 + 1, (int) list.get(3));
        Assert.assertEquals(INT_3 + 1, (int) list.get(4));
    }
}
