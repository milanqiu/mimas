package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.AbstractSequentialIterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * Creation Date: 2014-09-09
 * @author Milan Qiu
 */
public class AbstractSequentialIteratorTest {

    private AbstractSequentialIterator<Integer> countOneToFiveItr;
    private AbstractSequentialIterator<Integer> emptyItr;

    @Before
    public void setUp() throws Exception {
        countOneToFiveItr = new AbstractSequentialIterator<Integer>(1) {
            @Override
            protected Integer computeNext(Integer integer) {
                return integer<5 ? integer+1 : null;
            }
        };
        emptyItr = new AbstractSequentialIterator<Integer>(null) {
            @Override
            protected Integer computeNext(Integer integer) {
                return new Random().nextInt();
            }
        };
    }

    @Test
    public void test_next_hasNext() throws Exception {
        Assert.assertTrue(countOneToFiveItr.hasNext());
        Assert.assertEquals(1, (int) countOneToFiveItr.next());
        Assert.assertEquals(2, (int) countOneToFiveItr.next());
        Assert.assertEquals(3, (int) countOneToFiveItr.next());
        Assert.assertEquals(4, (int) countOneToFiveItr.next());
        Assert.assertEquals(5, (int) countOneToFiveItr.next());
        Assert.assertFalse(countOneToFiveItr.hasNext());

        Assert.assertFalse(emptyItr.hasNext());
    }
}
