package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.AbstractIterator;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-9-9
 * @author Milan Qiu
 */
public class AbstractIteratorTest {

    private static Iterator<Integer> countOneToFiveItr() {
        return new AbstractIterator<Integer>() {
            int counter = 0;
            @Override
            protected Integer computeNext() {
                if (counter < 5) {
                    counter++;
                    return counter;
                } else {
                    return endOfData();
                }
            }
        };
    }

    @Test
    public void test_AbstractIterator() throws Exception {
        Iterator<Integer> countOneToFiveItr = countOneToFiveItr();
        Assert.assertEquals(1, (int) countOneToFiveItr.next());
        Assert.assertEquals(2, (int) countOneToFiveItr.next());
        Assert.assertEquals(3, (int) countOneToFiveItr.next());
        Assert.assertEquals(4, (int) countOneToFiveItr.next());
        Assert.assertEquals(5, (int) countOneToFiveItr.next());
        Assert.assertFalse(countOneToFiveItr.hasNext());
    }
}
