package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.AbstractSequentialIterator;

import java.util.Iterator;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-9-9
 * @author Milan Qiu
 */
public class AbstractSequentialIteratorTest {

    @Test
    public void test_AbstractSequentialIterator() throws Exception {
        Iterator<Integer> countOneToFiveItr = new AbstractSequentialIterator<Integer>(1) {
            @Override
            protected Integer computeNext(Integer integer) {
                return integer<5 ? integer+1 : null;
            }
        };

        Assert.assertEquals(1, (int) countOneToFiveItr.next());
        Assert.assertEquals(2, (int) countOneToFiveItr.next());
        Assert.assertEquals(3, (int) countOneToFiveItr.next());
        Assert.assertEquals(4, (int) countOneToFiveItr.next());
        Assert.assertEquals(5, (int) countOneToFiveItr.next());
        Assert.assertFalse(countOneToFiveItr.hasNext());
    }
}
