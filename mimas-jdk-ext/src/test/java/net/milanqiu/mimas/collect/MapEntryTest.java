package net.milanqiu.mimas.collect;

import java.util.HashMap;
import java.util.Map;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

import net.milanqiu.mimas.collect.MapEntry;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-25
 * @author Milan Qiu
 */
public class MapEntryTest {

    private MapEntry<Integer, String> mapEntry;

    @Before
    public void setUp() throws Exception {
        mapEntry = MapEntry.create(INT_0, STR_0);
    }

    @Test
    public void test_getKey() throws Exception {
        Assert.assertEquals(INT_0, (int) mapEntry.getKey());
    }

    @Test
    public void test_getValue() throws Exception {
        Assert.assertEquals(STR_0, mapEntry.getValue());
    }

    @Test
    public void test_setValue() throws Exception {
        Assert.assertEquals(STR_0, mapEntry.setValue(STR_1));
        Assert.assertEquals(STR_1, mapEntry.getValue());
}

    @Test
    public void test_equals() throws Exception {
        Assert.assertTrue(mapEntry.equals(MapEntry.create(INT_0, STR_0)));
        Assert.assertFalse(mapEntry.equals(MapEntry.create(STR_0, INT_0)));
        Assert.assertFalse(mapEntry.equals(MapEntry.create(INT_0, null)));
        Assert.assertFalse(mapEntry.equals(MapEntry.create(null, STR_0)));
        Assert.assertFalse(mapEntry.equals(MapEntry.create(null, null)));

        Map<Integer, String> map = new HashMap<>();
        map.put(INT_0, STR_0);
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            Assert.assertTrue(mapEntry.equals(entry));
            Assert.assertTrue(entry.equals(mapEntry));
        }
    }

    @Test
    public void test_hashCode() throws Exception {
        Map<Integer, String> map = new HashMap<>();
        map.put(INT_0, STR_0);
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            Assert.assertEquals(entry.hashCode(), mapEntry.hashCode());
        }
    }

    @Test
    public void test_toString() throws Exception {
        Assert.assertEquals(INT_0+"="+STR_0, mapEntry.toString());
    }
}
