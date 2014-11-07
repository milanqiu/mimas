package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.*;
import net.milanqiu.mimas.collect.CollectionUtils;
import net.milanqiu.mimas.collect.MapEntry;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-26
 * @author Milan Qiu
 */
public class RangeMapTest {

    /*
        RangeMap is a collection type describing a mapping from disjoint, nonempty ranges to values. Unlike RangeSet,
        RangeMap never "coalesces" adjacent mappings, even if adjacent ranges are mapped to the same values.
     */

    private RangeMap<Integer, String> rangeMap;

    @Before
    public void setUp() throws Exception {
        rangeMap = TreeRangeMap.create();
    }

    @Test
    public void test_put_remove_asMapOfRanges() throws Exception {
        /*
            asMapOfRanges(): views the RangeMap as a Map<Range<K>, V>. This can be used, for example, to iterate over
            the RangeMap.
         */
        rangeMap.put(Range.closed(1, 10), STR_0); // {[1, 10] => STR_0}
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(rangeMap.asMapOfRanges().entrySet(), ImmutableSet.of(
                MapEntry.create(Range.closed(1, 10), STR_0)
        )));

        rangeMap.put(Range.open(3, 6), STR_1); // {[1, 3] => STR_0, (3, 6) => STR_1, [6, 10] => STR_0}
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(rangeMap.asMapOfRanges().entrySet(), ImmutableSet.of(
                MapEntry.create(Range.closed(1, 3), STR_0),
                MapEntry.create(Range.open(3, 6), STR_1),
                MapEntry.create(Range.closed(6, 10), STR_0)
        )));

        rangeMap.put(Range.open(10, 20), STR_0); // {[1, 3] => STR_0, (3, 6) => STR_1, [6, 10] => STR_0, (10, 20) => STR_0}
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(rangeMap.asMapOfRanges().entrySet(), ImmutableSet.of(
                MapEntry.create(Range.closed(1, 3), STR_0),
                MapEntry.create(Range.open(3, 6), STR_1),
                MapEntry.create(Range.closed(6, 10), STR_0),
                MapEntry.create(Range.open(10, 20), STR_0)
        )));

        rangeMap.remove(Range.closed(5, 11)); // {[1, 3] => STR_0, (3, 5) => STR_1, (11, 20) => STR_0}
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(rangeMap.asMapOfRanges().entrySet(), ImmutableSet.of(
                MapEntry.create(Range.closed(1, 3), STR_0),
                MapEntry.create(Range.open(3, 5), STR_1),
                MapEntry.create(Range.open(11, 20), STR_0)
        )));
    }

    @Test
    public void test_subRangeMap() throws Exception {
        /*
            subRangeMap(Range<K>) views the intersection of the RangeMap with the specified Range as a RangeMap. This
            generalizes the traditional headMap, subMap, and tailMap operations.
         */
        rangeMap.put(Range.closed(1, 3), STR_0);
        rangeMap.put(Range.open(3, 6), STR_1);
        rangeMap.put(Range.closed(6, 8), STR_0);
        rangeMap.put(Range.openClosed(8, 10), STR_0);
        RangeMap<Integer, String> subRangeMap = rangeMap.subRangeMap(Range.openClosed(2, 9));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(subRangeMap.asMapOfRanges().entrySet(), ImmutableSet.of(
                MapEntry.create(Range.openClosed(2, 3), STR_0),
                MapEntry.create(Range.open(3, 6), STR_1),
                MapEntry.create(Range.closed(6, 8), STR_0),
                MapEntry.create(Range.openClosed(8, 9), STR_0)
        )));
    }
}
