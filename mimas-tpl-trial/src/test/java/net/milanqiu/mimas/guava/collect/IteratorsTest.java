package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.*;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-10-13
 * @author Milan Qiu
 */
public class IteratorsTest {

    /*
        Every method in Iterables has an analogous corresponding method in Iterators.
     */

    @Test
    public void test_peekingIterator_PeekingIterator_peek_next() throws Exception {
        List<String> source = Lists.newArrayList(STR_0, STR_1, STR_2);
        PeekingIterator<String> peeking = Iterators.peekingIterator(source.iterator());
        Assert.assertTrue(peeking.hasNext());
        Assert.assertEquals(STR_0, peeking.peek());
        Assert.assertEquals(STR_0, peeking.peek());
        Assert.assertEquals(STR_0, peeking.next());
        Assert.assertEquals(STR_1, peeking.peek());
        Assert.assertEquals(STR_1, peeking.peek());
        Assert.assertEquals(STR_1, peeking.next());
    }

    @Test
    public void test_get() throws Exception {
        // T get(Iterator<T> iterator, int position)
        {
            List<String> list = Lists.newArrayList(STR_0, STR_1, STR_2);
            Assert.assertEquals(STR_0, Iterators.get(list.iterator(), 0));
            Assert.assertEquals(STR_1, Iterators.get(list.iterator(), 1));
            Assert.assertEquals(STR_2, Iterators.get(list.iterator(), 2));

            Iterator<String> listIterator = list.iterator();
            Assert.assertEquals(STR_2, Iterators.get(listIterator, 2));
            Assert.assertFalse(listIterator.hasNext());
            AssertExt.assertExceptionThrown(() -> Iterators.get(listIterator, 1), IndexOutOfBoundsException.class);
        }

        // T get(Iterator<T> iterator, int position, T defaultValue)
        {
            List<String> list = Lists.newArrayList(STR_0, STR_1, STR_2);
            Assert.assertEquals(STR_0, Iterators.get(list.iterator(), 0, STR_4));
            Assert.assertEquals(STR_1, Iterators.get(list.iterator(), 1, STR_4));
            Assert.assertEquals(STR_2, Iterators.get(list.iterator(), 2, STR_4));

            Iterator<String> listIterator = list.iterator();
            Assert.assertEquals(STR_2, Iterators.get(listIterator, 2, STR_4));
            Assert.assertFalse(listIterator.hasNext());
            Assert.assertEquals(STR_4, Iterators.get(listIterator, 1, STR_4));
        }
    }

    @Test
    public void test_advance() throws Exception {
        List<String> list = Lists.newArrayList(STR_0, STR_1, STR_2);

        Iterator<String> listIterator1 = list.iterator();
        Assert.assertEquals(2, Iterators.advance(listIterator1, 2));
        Assert.assertTrue(listIterator1.hasNext());
        Assert.assertEquals(STR_2, listIterator1.next());

        Iterator<String> listIterator2 = list.iterator();
        Assert.assertEquals(3, Iterators.advance(listIterator2, 4));
        Assert.assertFalse(listIterator2.hasNext());
    }

    @Test
    public void test_forArray() throws Exception {
        Assert.assertTrue(Iterators.elementsEqual(ImmutableList.of(STR_0, STR_1, STR_2).iterator(),
                Iterators.forArray(STR_0, STR_1, STR_2)));

        String[] strArray = new String[]{STR_0, STR_1, STR_2};
        UnmodifiableIterator strArrayItr = Iterators.forArray(strArray);
        strArray[1] = STR_4;
        Assert.assertTrue(Iterators.elementsEqual(ImmutableList.of(STR_0, STR_4, STR_2).iterator(), strArrayItr));
    }

    @Test
    public void test_singletonIterator() throws Exception {
        UnmodifiableIterator itr = Iterators.singletonIterator(STR_0);
        Assert.assertEquals(STR_0, itr.next());
        Assert.assertFalse(itr.hasNext());
    }
}
