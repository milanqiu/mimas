package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.*;
import net.milanqiu.mimas.instrumentation.DebugUtils;

import java.util.*;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import net.milanqiu.mimas.junit.AssertExt;
import net.milanqiu.mimas.lang.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-10-13
 * @author Milan Qiu
 */
public class IteratorsTest {

    @Test
    public void test_peekingIterator() throws Exception {
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
}
