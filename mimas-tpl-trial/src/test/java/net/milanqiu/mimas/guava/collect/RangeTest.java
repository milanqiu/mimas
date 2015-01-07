package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.BoundType;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2014-12-21
 * @author Milan Qiu
 */
public class RangeTest {

    @Test
    public void test_contains() throws Exception {
        Assert.assertTrue(Range.closed(1, 3).contains(2));
        Assert.assertTrue(Range.atLeast(5).contains(5));
        Assert.assertFalse(Range.downTo(7, BoundType.OPEN).contains(7));
    }

    @Test
    public void test_containsAll() throws Exception {
        Assert.assertTrue(Range.closed(1, 3).containsAll(ImmutableList.of(1, 2, 3)));
        Assert.assertFalse(Range.closed(1, 3).containsAll(ImmutableList.of(1, 3, 5)));
        Assert.assertFalse(Range.closedOpen(1, 3).containsAll(ImmutableList.of(1, 2, 3)));
    }

    @Test
    public void test_hasLowerBound_hasUpperBound() throws Exception {
        Assert.assertTrue(Range.open(1, 3).hasLowerBound());
        Assert.assertTrue(Range.open(1, 3).hasUpperBound());
        Assert.assertFalse(Range.lessThan(5).hasLowerBound());
        Assert.assertFalse(Range.atLeast(5).hasUpperBound());
    }

    @Test
    public void test_lowerBoundType_upperBoundType() throws Exception {
        Assert.assertEquals(BoundType.CLOSED, Range.closedOpen(1, 3).lowerBoundType());
        Assert.assertEquals(BoundType.OPEN,   Range.closedOpen(1, 3).upperBoundType());
        try {
            Range.lessThan(5).lowerBoundType();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void test_lowerEndpoint_upperEndpoint() throws Exception {
        Assert.assertEquals(1, (int) Range.closedOpen(1, 3).lowerEndpoint());
        Assert.assertEquals(3, (int) Range.closedOpen(1, 3).upperEndpoint());
        try {
            Range.lessThan(5).lowerEndpoint();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    public void test_isEmpty() throws Exception {
        Assert.assertTrue(Range.closedOpen(1, 1).isEmpty());
        Assert.assertTrue(Range.openClosed(1, 1).isEmpty());
        Assert.assertFalse(Range.closed(1, 1).isEmpty());
        try {
            Range.open(1, 1);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void test_encloses() throws Exception {
        Assert.assertTrue(Range.closed(3, 6).encloses(Range.closed(4, 5)));
        Assert.assertTrue(Range.open(3, 6).encloses(Range.open(3, 6)));
        Assert.assertTrue(Range.open(3, 6).encloses(Range.openClosed(4, 4)));
        Assert.assertFalse(Range.openClosed(3, 6).encloses(Range.closed(3, 6)));
        Assert.assertFalse(Range.closed(4, 5).encloses(Range.open(3, 6)));
        Assert.assertFalse(Range.closed(4, 5).encloses(Range.openClosed(1, 1)));
    }

    @Test
    public void test_isConnected() throws Exception {
        Assert.assertTrue(Range.closed(3, 5).isConnected(Range.open(5, 10)));
        Assert.assertTrue(Range.closed(0, 9).isConnected(Range.closed(3, 4)));
        Assert.assertTrue(Range.closed(0, 5).isConnected(Range.closed(3, 9)));
        Assert.assertFalse(Range.open(3, 5).isConnected(Range.open(5, 10)));
        Assert.assertFalse(Range.closed(1, 5).isConnected(Range.closed(6, 10)));
    }

    @Test
    public void test_intersection() throws Exception {
        Assert.assertEquals(Range.openClosed(5, 5), Range.closed(3, 5).intersection(Range.open(5, 10)));
        Assert.assertEquals(Range.closed(3, 4),     Range.closed(0, 9).intersection(Range.closed(3, 4)));
        Assert.assertEquals(Range.closed(3, 5),     Range.closed(0, 5).intersection(Range.closed(3, 9)));
        try {
            Range.open(3, 5).intersection(Range.open(5, 10));
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        try {
            Range.closed(1, 5).intersection(Range.closed(6, 10));
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void test_span() throws Exception {
        Assert.assertEquals(Range.closedOpen(3, 10), Range.closed(3, 5).span(Range.open(5, 10)));
        Assert.assertEquals(Range.closed(0, 9),      Range.closed(0, 9).span(Range.closed(3, 4)));
        Assert.assertEquals(Range.closed(0, 9),      Range.closed(0, 5).span(Range.closed(3, 9)));
        Assert.assertEquals(Range.open(3, 10),       Range.open(3, 5).span(Range.open(5, 10)));
        Assert.assertEquals(Range.closed(1, 10),     Range.closed(1, 5).span(Range.closed(6, 10)));
    }

    @Test
    public void test_canonical() throws Exception {
        Assert.assertEquals(Range.closedOpen(3, 6),                  Range.openClosed(2, 5).canonical(DiscreteDomain.integers()));
        Assert.assertEquals(Range.atLeast(8),                        Range.greaterThan(7).canonical(DiscreteDomain.integers()));
        Assert.assertEquals(Range.closedOpen(Integer.MIN_VALUE, 11), Range.atMost(10).canonical(DiscreteDomain.integers()));
    }

    @Test
    public void test_singleton() throws Exception {
        Assert.assertEquals(Range.closed(1, 1), Range.singleton(1));
    }

    @Test
    public void test_encloseAll() throws Exception {
        Assert.assertEquals(Range.closed(1, 5), Range.encloseAll(ImmutableList.of(1, 3, 5)));
    }
}
