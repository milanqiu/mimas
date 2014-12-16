package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.*;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-7-25
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

    private BiMap<Integer, String> hashBiMap;

    @Before
    public void setUp() throws Exception {
        hashBiMap = HashBiMap.create();
        hashBiMap.put(INT_0, STR_0);
        hashBiMap.put(INT_1, STR_1);
    }

    @Test
    public void test_inverse() throws Exception {
        Assert.assertEquals(ImmutableBiMap.of(
                STR_0, INT_0,
                STR_1, INT_1), hashBiMap.inverse());
    }

    @Test
    public void test_values() throws Exception {
        Assert.assertEquals(ImmutableSet.of(STR_0, STR_1), hashBiMap.values());
    }

    @Test
    public void test_put() throws Exception {
        Assert.assertEquals(null, hashBiMap.put(INT_2, STR_2));
        Assert.assertEquals(STR_2, hashBiMap.put(INT_2, STR_3));
        Assert.assertEquals(ImmutableBiMap.of(
                INT_0, STR_0,
                INT_1, STR_1,
                INT_2, STR_3), hashBiMap);

        try {
            hashBiMap.put(INT_3, STR_0);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void test_forcePut() throws Exception {
        Assert.assertEquals(null, hashBiMap.forcePut(INT_3, STR_0));
        Assert.assertEquals(ImmutableBiMap.of(
                INT_3, STR_0,
                INT_1, STR_1), hashBiMap);
    }

    @Test
    public void test_putAll() throws Exception {
        hashBiMap.putAll(ImmutableMap.of(INT_2, STR_2, INT_1, STR_3));
        Assert.assertEquals(ImmutableBiMap.of(
                INT_0, STR_0,
                INT_1, STR_3,
                INT_2, STR_2), hashBiMap);

        try {
            hashBiMap.putAll(ImmutableMap.of(INT_4, STR_0));
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }
}
