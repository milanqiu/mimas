package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.AbstractIterator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Creation Date: 2014-9-9
 * @author Milan Qiu
 */
public class AbstractIteratorTest {

    private AbstractIterator<Integer> countOneToFiveItr;

    @Before
    public void setUp() throws Exception {
        countOneToFiveItr = new AbstractIterator<Integer>() {
            int counter = 0;
            @Override
            protected Integer computeNext() {
                if (counter < 5) {
                    return ++counter;
                } else {
                    return endOfData();
                }
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
    }

    @Test
    public void test_peek() throws Exception {
        Assert.assertEquals(1, (int) countOneToFiveItr.peek());
        Assert.assertEquals(1, (int) countOneToFiveItr.peek());
        Assert.assertEquals(1, (int) countOneToFiveItr.peek());
    }
}
