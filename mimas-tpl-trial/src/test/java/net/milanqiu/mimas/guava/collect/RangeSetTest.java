package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Creation Date: 2014-07-26
 * @author Milan Qiu
 */
public class RangeSetTest {

    /*
        A RangeSet describes a set of disconnected, nonempty ranges. When adding a range to a mutable RangeSet, any
        connected ranges are merged together, and empty ranges are ignored.
     */

    private RangeSet<Integer> rangeSet;

    @Before
    public void setUp() throws Exception {
        rangeSet = TreeRangeSet.create();
    }

    @Test
    public void test_add_remove_asRanges() throws Exception {
        /*
            asRanges(): views the RangeSet as a Set<Range<C>> which can be iterated over.
         */
        rangeSet.add(Range.closed(1, 10)); // {[1, 10]}
        Assert.assertEquals(ImmutableSet.of(Range.closed(1, 10)), rangeSet.asRanges());

        rangeSet.add(Range.closedOpen(11, 15)); // disconnected range: {[1, 10], [11, 15)}
        Assert.assertEquals(ImmutableSet.of(Range.closed(1, 10), Range.closedOpen(11, 15)), rangeSet.asRanges());

        rangeSet.add(Range.range(15, BoundType.CLOSED, 20, BoundType.OPEN)); // connected range; {[1, 10], [11, 20)}
        Assert.assertEquals(ImmutableSet.of(Range.closed(1, 10), Range.closedOpen(11, 20)), rangeSet.asRanges());

        rangeSet.add(Range.openClosed(0, 0)); // empty range; {[1, 10], [11, 20)}
        Assert.assertEquals(ImmutableSet.of(Range.closed(1, 10), Range.closedOpen(11, 20)), rangeSet.asRanges());

        rangeSet.remove(Range.open(5, 10)); // splits [1, 10]; {[1, 5], [10, 10], [11, 20)}
        Assert.assertEquals(ImmutableSet.of(Range.closed(1, 5), Range.singleton(10), Range.closedOpen(11, 20)), rangeSet.asRanges());

        /*
            Note that to merge ranges like Range.closed(1, 10) and Range.closedOpen(11, 15), you must first preprocess
            ranges with Range.canonical(DiscreteDomain), e.g. with DiscreteDomain.integers().
         */
        rangeSet.clear();
        rangeSet.add(Range.closed(1, 10).canonical(DiscreteDomain.integers())); // {[1, 10]}
        rangeSet.add(Range.closedOpen(11, 15)); // merged range: {[1, 15)}
        Assert.assertEquals(ImmutableSet.of(Range.closedOpen(1, 15)), rangeSet.asRanges());
    }

    @Test
    public void test_complement() throws Exception {
        /*
            complement(): views the complement of the RangeSet. complement is also a RangeSet, as it contains
            disconnected, nonempty ranges.
         */
        rangeSet.add(Range.closedOpen(1, 10));
        rangeSet.add(Range.closedOpen(20, 30));
        Assert.assertEquals(ImmutableSet.of(Range.lessThan(1), Range.closedOpen(10, 20), Range.atLeast(30)),
                rangeSet.complement().asRanges());
    }

    @Test
    public void test_subRangeSet() throws Exception {
        /*
            subRangeSet(Range<C>): returns a view of the intersection of the RangeSet with the specified Range. This
            generalizes the headSet, subSet, and tailSet views of traditional sorted collections.
         */
        rangeSet.add(Range.closedOpen(1, 10));
        rangeSet.add(Range.openClosed(20, 30));
        Assert.assertEquals(ImmutableSet.of(Range.closedOpen(5, 10), Range.openClosed(20, 25)),
                rangeSet.subRangeSet(Range.closed(5, 25)).asRanges());
    }

    @Test
    public void test_contains() throws Exception {
        /*
            contains(C): the most fundamental operation on a RangeSet, querying if any range in the RangeSet contains
            the specified element.
         */
        rangeSet.add(Range.closedOpen(1, 10));
        rangeSet.add(Range.openClosed(20, 30));
        Assert.assertTrue(rangeSet.contains(1));
        Assert.assertTrue(rangeSet.contains(9));
        Assert.assertTrue(rangeSet.contains(21));
        Assert.assertTrue(rangeSet.contains(30));
        Assert.assertFalse(rangeSet.contains(10));
        Assert.assertFalse(rangeSet.contains(15));
        Assert.assertFalse(rangeSet.contains(20));
    }

    @Test
    public void test_rangeContaining() throws Exception {
        /*
            rangeContaining(C): returns the Range which encloses the specified element, or null if there is none.
         */
        rangeSet.add(Range.closedOpen(1, 10));
        rangeSet.add(Range.openClosed(20, 30));
        Assert.assertEquals(Range.closedOpen(1, 10), rangeSet.rangeContaining(5));
        Assert.assertEquals(Range.openClosed(20, 30), rangeSet.rangeContaining(25));
        Assert.assertNull(rangeSet.rangeContaining(15));
    }

    @Test
    public void test_encloses() throws Exception {
        /*
            encloses(Range<C>): straightforwardly enough, tests if any Range in the RangeSet encloses the specified
            range.
         */
        rangeSet.add(Range.closedOpen(1, 10));
        rangeSet.add(Range.openClosed(20, 30));
        Assert.assertTrue(rangeSet.encloses(Range.closedOpen(1, 10)));
        Assert.assertTrue(rangeSet.encloses(Range.open(1, 10)));
        Assert.assertTrue(rangeSet.encloses(Range.closed(22, 24)));
        Assert.assertFalse(rangeSet.encloses(Range.closed(1, 10)));
        Assert.assertFalse(rangeSet.encloses(Range.closed(20, 20)));
    }

    @Test
    public void test_span() throws Exception {
        /*
            span(): returns the minimal Range that encloses every range in this RangeSet.
         */
        rangeSet.add(Range.closedOpen(1, 10));
        rangeSet.add(Range.openClosed(20, 30));
        Assert.assertEquals(Range.closed(1, 30), rangeSet.span());
    }
}
