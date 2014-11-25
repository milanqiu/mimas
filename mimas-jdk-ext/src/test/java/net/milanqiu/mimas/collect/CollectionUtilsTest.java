package net.milanqiu.mimas.collect;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-7-25
 * @author Milan Qiu
 */
public class CollectionUtilsTest {

    @Test
    public void test_countsOccurrence() throws Exception {
        Map<Object, Integer> counts = CollectionUtils.countsOccurrence(Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0));
        Map<Object, Integer> countsExpected = new HashMap<>();
        countsExpected.put(OBJ_0, 2);
        countsExpected.put(OBJ_1, 1);
        countsExpected.put(OBJ_2, 1);
        Assert.assertTrue(counts.equals(countsExpected));

        // null test
        Map<Object, Integer> countsWithNull = CollectionUtils.countsOccurrence(Arrays.asList(OBJ_0, null, null));
        Map<Object, Integer> countsWithNullExpected = new HashMap<>();
        countsWithNullExpected.put(OBJ_0, 1);
        countsWithNullExpected.put(null, 2);
        Assert.assertTrue(countsWithNull.equals(countsWithNullExpected));
    }

    @Test
    public void test_equalsIgnoringOrder() throws Exception {
        List<Object> list = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0);
        List<Object> list2 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0);
        List<Object> list3 = Arrays.asList(OBJ_2, OBJ_0, OBJ_0, OBJ_1);
        List<Object> list4 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_2);
        List<Object> list5 = Arrays.asList(OBJ_0, OBJ_1, OBJ_3, OBJ_0);
        List<Object> list6 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2);
        List<Object> list7 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0, OBJ_0);
        
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(list, list2));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(list, list3));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(list, list4));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(list, list5));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(list, list6));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(list, list7));
        
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(list2, list));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(list3, list));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(list4, list));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(list5, list));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(list6, list));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(list7, list));

        // null test
        List<Object> list10 = Arrays.asList(OBJ_0, null);
        List<Object> list11 = Arrays.asList(null, OBJ_0);
        List<Object> list12 = Arrays.asList(OBJ_0);
        List<Object> list13 = Arrays.asList(OBJ_0, null, null);
        
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(list10, list11));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(list10, list12));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(list10, list13));
        
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(list11, list10));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(list12, list10));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(list13, list10));
    }
}
