package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableSet;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.lang.CollectionUtils;
import net.milanqiu.mimas.lang.MapEntry;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-25
 * @author Milan Qiu
 */
public class BiMapTest {

    /*
        -------------------------------------------------------------------------
        |   Key-Value Map Impl  |   Value-Key Map Impl  |   Corresponding BiMap |
        -------------------------------------------------------------------------
        |   HashMap             |   HashMap             |   HashBiMap           |
        |   ImmutableMap        |   ImmutableMap        |   ImmutableBiMap      |
        |   EnumMap             |   EnumMap             |   EnumBiMap           |
        |   EnumMap             |   HashMap             |   EnumHashBiMap       |
        -------------------------------------------------------------------------
     */

    private BiMap<Integer, String> biMap;

    @Before
    public void setUp() throws Exception {
        biMap = HashBiMap.create();
        biMap.put(INT_0, STR_0);
        biMap.put(INT_1, STR_1);
    }

    @Test
    public void test_inverse() throws Exception {
        BiMap<String, Integer> inversedBiMap = biMap.inverse();
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(inversedBiMap.entrySet(), ImmutableSet.of(
                MapEntry.create(STR_0, INT_0),
                MapEntry.create(STR_1, INT_1)
        )));
    }

    @Test
    public void test_values() throws Exception {
        Assert.assertTrue(biMap.values().equals(ImmutableSet.of(STR_0, STR_1)));
    }

    @Test
    public void test_put() throws Exception {
        Assert.assertEquals(null, biMap.put(INT_2, STR_2));
        Assert.assertEquals(STR_2, biMap.put(INT_2, STR_3));

        try {
            biMap.put(INT_3, STR_0);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(IllegalArgumentException.class, e);
        }
    }

    @Test
    public void test_forcePut() throws Exception {
        Assert.assertEquals(null, biMap.forcePut(INT_3, STR_0));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(biMap.entrySet(), ImmutableSet.of(
                MapEntry.create(INT_3, STR_0),
                MapEntry.create(INT_1, STR_1)
        )));
    }
}
