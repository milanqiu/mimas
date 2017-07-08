package net.milanqiu.mimas.collect;

import net.milanqiu.mimas.string.StrUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-25
 * @author Milan Qiu
 */
public class CollectionUtilsTest {

    @Test
    public void test_convertIterable() throws Exception {
        List<Integer> list = Arrays.asList(INT_0, INT_1, INT_2);
        Iterable<Number> itr = CollectionUtils.convertIterable(list);
        for (int i = 0; i < 2; i++) {
            int cursor = 0;
            for (Number element : itr) {
                Assert.assertEquals(list.get(cursor++), element);
            }
            Assert.assertEquals(list.size(), cursor);
        }
    }

    @Test
    public void test_countsOccurrence() throws Exception {
        Map<Object, Integer> counts = CollectionUtils.countsOccurrence(Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0));
        Map<Object, Integer> countsExpected = new HashMap<>();
        countsExpected.put(OBJ_0, 2);
        countsExpected.put(OBJ_1, 1);
        countsExpected.put(OBJ_2, 1);
        Assert.assertEquals(countsExpected, counts);

        // same test but change element type to int
        Map<Integer, Integer> countsInt = CollectionUtils.countsOccurrence(Arrays.asList(INT_0, INT_1, INT_2, INT_0));
        Map<Integer, Integer> countsIntExpected = new HashMap<>();
        countsIntExpected.put(INT_0, 2);
        countsIntExpected.put(INT_1, 1);
        countsIntExpected.put(INT_2, 1);
        Assert.assertEquals(countsIntExpected, countsInt);

        // null test
        Map<Object, Integer> countsWithNull = CollectionUtils.countsOccurrence(Arrays.asList(OBJ_0, null, null));
        Map<Object, Integer> countsWithNullExpected = new HashMap<>();
        countsWithNullExpected.put(OBJ_0, 1);
        countsWithNullExpected.put(null, 2);
        Assert.assertEquals(countsWithNullExpected, countsWithNull);
    }

    @Test
    public void test_equals() throws Exception {
        List<Object> list = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0);
        List<Object> list2 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0);
        List<Object> list3 = Arrays.asList(OBJ_2, OBJ_0, OBJ_0, OBJ_1);
        List<Object> list4 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_2);
        List<Object> list5 = Arrays.asList(OBJ_0, OBJ_1, OBJ_3, OBJ_0);
        List<Object> list6 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2);
        List<Object> list7 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0, OBJ_0);

        Assert.assertTrue(CollectionUtils.equals(list, list2));
        Assert.assertFalse(CollectionUtils.equals(list, list3));
        Assert.assertFalse(CollectionUtils.equals(list, list4));
        Assert.assertFalse(CollectionUtils.equals(list, list5));
        Assert.assertFalse(CollectionUtils.equals(list, list6));
        Assert.assertFalse(CollectionUtils.equals(list, list7));

        Assert.assertTrue(CollectionUtils.equals(list2, list));
        Assert.assertFalse(CollectionUtils.equals(list3, list));
        Assert.assertFalse(CollectionUtils.equals(list4, list));
        Assert.assertFalse(CollectionUtils.equals(list5, list));
        Assert.assertFalse(CollectionUtils.equals(list6, list));
        Assert.assertFalse(CollectionUtils.equals(list7, list));

        // same test but change element type to int
        List<Integer> listInt = Arrays.asList(INT_0, INT_1, INT_2, INT_0);
        List<Integer> listInt2 = Arrays.asList(INT_0, INT_1, INT_2, INT_0);
        List<Integer> listInt3 = Arrays.asList(INT_2, INT_0, INT_0, INT_1);
        List<Integer> listInt4 = Arrays.asList(INT_0, INT_1, INT_2, INT_2);
        List<Integer> listInt5 = Arrays.asList(INT_0, INT_1, INT_3, INT_0);
        List<Integer> listInt6 = Arrays.asList(INT_0, INT_1, INT_2);
        List<Integer> listInt7 = Arrays.asList(INT_0, INT_1, INT_2, INT_0, INT_0);

        Assert.assertTrue(CollectionUtils.equals(listInt, listInt2));
        Assert.assertFalse(CollectionUtils.equals(listInt, listInt3));
        Assert.assertFalse(CollectionUtils.equals(listInt, listInt4));
        Assert.assertFalse(CollectionUtils.equals(listInt, listInt5));
        Assert.assertFalse(CollectionUtils.equals(listInt, listInt6));
        Assert.assertFalse(CollectionUtils.equals(listInt, listInt7));

        Assert.assertTrue(CollectionUtils.equals(listInt2, listInt));
        Assert.assertFalse(CollectionUtils.equals(listInt3, listInt));
        Assert.assertFalse(CollectionUtils.equals(listInt4, listInt));
        Assert.assertFalse(CollectionUtils.equals(listInt5, listInt));
        Assert.assertFalse(CollectionUtils.equals(listInt6, listInt));
        Assert.assertFalse(CollectionUtils.equals(listInt7, listInt));

        // compare list and set
        List<Object> listS = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0);
        List<Object> listS2 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2);
        Set<Object> set = new LinkedHashSet<>(Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0));
        Set<Object> set2 = new LinkedHashSet<>(Arrays.asList(OBJ_0, OBJ_1, OBJ_2));
        Set<Object> set3 = new LinkedHashSet<>(Arrays.asList(OBJ_2, OBJ_1, OBJ_0));

        Assert.assertFalse(CollectionUtils.equals(listS, listS2));
        Assert.assertFalse(CollectionUtils.equals(listS, set));
        Assert.assertFalse(CollectionUtils.equals(listS, set2));
        Assert.assertFalse(CollectionUtils.equals(listS, set3));
        Assert.assertFalse(CollectionUtils.equals(listS2, listS));
        Assert.assertTrue(CollectionUtils.equals(listS2, set));
        Assert.assertTrue(CollectionUtils.equals(listS2, set2));
        Assert.assertFalse(CollectionUtils.equals(listS2, set3));
        Assert.assertFalse(CollectionUtils.equals(set, listS));
        Assert.assertTrue(CollectionUtils.equals(set, listS2));
        Assert.assertTrue(CollectionUtils.equals(set, set2));
        Assert.assertFalse(CollectionUtils.equals(set, set3));
        Assert.assertFalse(CollectionUtils.equals(set2, listS));
        Assert.assertTrue(CollectionUtils.equals(set2, listS2));
        Assert.assertTrue(CollectionUtils.equals(set2, set));
        Assert.assertFalse(CollectionUtils.equals(set2, set3));
        Assert.assertFalse(CollectionUtils.equals(set3, listS));
        Assert.assertFalse(CollectionUtils.equals(set3, listS2));
        Assert.assertFalse(CollectionUtils.equals(set3, set));
        Assert.assertFalse(CollectionUtils.equals(set3, set2));

        // null test
        List<Object> listWithNull = Arrays.asList(OBJ_0, null, null);
        List<Object> listWithNull2 = Arrays.asList(OBJ_0, null, null);
        List<Object> listWithNull3 = Arrays.asList(null, null, OBJ_0);
        List<Object> listWithNull4 = Arrays.asList(OBJ_0, null);
        List<Object> listWithNull5 = Arrays.asList(OBJ_0, null, null, null);

        Assert.assertTrue(CollectionUtils.equals(listWithNull, listWithNull2));
        Assert.assertFalse(CollectionUtils.equals(listWithNull, listWithNull3));
        Assert.assertFalse(CollectionUtils.equals(listWithNull, listWithNull4));
        Assert.assertFalse(CollectionUtils.equals(listWithNull, listWithNull5));

        Assert.assertTrue(CollectionUtils.equals(listWithNull2, listWithNull));
        Assert.assertFalse(CollectionUtils.equals(listWithNull3, listWithNull));
        Assert.assertFalse(CollectionUtils.equals(listWithNull4, listWithNull));
        Assert.assertFalse(CollectionUtils.equals(listWithNull5, listWithNull));
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

        // same test but change element type to int
        List<Integer> listInt = Arrays.asList(INT_0, INT_1, INT_2, INT_0);
        List<Integer> listInt2 = Arrays.asList(INT_0, INT_1, INT_2, INT_0);
        List<Integer> listInt3 = Arrays.asList(INT_2, INT_0, INT_0, INT_1);
        List<Integer> listInt4 = Arrays.asList(INT_0, INT_1, INT_2, INT_2);
        List<Integer> listInt5 = Arrays.asList(INT_0, INT_1, INT_3, INT_0);
        List<Integer> listInt6 = Arrays.asList(INT_0, INT_1, INT_2);
        List<Integer> listInt7 = Arrays.asList(INT_0, INT_1, INT_2, INT_0, INT_0);

        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listInt, listInt2));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listInt, listInt3));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listInt, listInt4));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listInt, listInt5));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listInt, listInt6));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listInt, listInt7));

        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listInt2, listInt));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listInt3, listInt));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listInt4, listInt));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listInt5, listInt));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listInt6, listInt));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listInt7, listInt));

        // compare list and set
        List<Object> listS = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0);
        List<Object> listS2 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2);
        Set<Object> set = new LinkedHashSet<>(Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0));
        Set<Object> set2 = new LinkedHashSet<>(Arrays.asList(OBJ_0, OBJ_1, OBJ_2));
        Set<Object> set3 = new LinkedHashSet<>(Arrays.asList(OBJ_2, OBJ_1, OBJ_0));

        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listS, listS2));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listS, set));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listS, set2));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listS, set3));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listS2, listS));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listS2, set));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listS2, set2));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listS2, set3));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(set, listS));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(set, listS2));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(set, set2));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(set, set3));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(set2, listS));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(set2, listS2));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(set2, set));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(set2, set3));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(set3, listS));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(set3, listS2));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(set3, set));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(set3, set2));

        // null test
        List<Object> listWithNull = Arrays.asList(OBJ_0, null, null);
        List<Object> listWithNull2 = Arrays.asList(OBJ_0, null, null);
        List<Object> listWithNull3 = Arrays.asList(null, null, OBJ_0);
        List<Object> listWithNull4 = Arrays.asList(OBJ_0, null);
        List<Object> listWithNull5 = Arrays.asList(OBJ_0, null, null, null);
        
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listWithNull, listWithNull2));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listWithNull, listWithNull3));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listWithNull, listWithNull4));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listWithNull, listWithNull5));

        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listWithNull2, listWithNull));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listWithNull3, listWithNull));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listWithNull4, listWithNull));
        Assert.assertFalse(CollectionUtils.equalsIgnoringOrder(listWithNull5, listWithNull));
    }

    @Test
    public void test_equalsCallingHasNextRepeatedly() throws Exception {
        List<Object> list = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0);
        List<Object> list2 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0);
        List<Object> list3 = Arrays.asList(OBJ_2, OBJ_0, OBJ_0, OBJ_1);
        List<Object> list4 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_2);
        List<Object> list5 = Arrays.asList(OBJ_0, OBJ_1, OBJ_3, OBJ_0);
        List<Object> list6 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2);
        List<Object> list7 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0, OBJ_0);

        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(list, list2));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(list, list3));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(list, list4));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(list, list5));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(list, list6));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(list, list7));

        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(list2, list));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(list3, list));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(list4, list));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(list5, list));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(list6, list));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(list7, list));

        // same test but change element type to int
        List<Integer> listInt = Arrays.asList(INT_0, INT_1, INT_2, INT_0);
        List<Integer> listInt2 = Arrays.asList(INT_0, INT_1, INT_2, INT_0);
        List<Integer> listInt3 = Arrays.asList(INT_2, INT_0, INT_0, INT_1);
        List<Integer> listInt4 = Arrays.asList(INT_0, INT_1, INT_2, INT_2);
        List<Integer> listInt5 = Arrays.asList(INT_0, INT_1, INT_3, INT_0);
        List<Integer> listInt6 = Arrays.asList(INT_0, INT_1, INT_2);
        List<Integer> listInt7 = Arrays.asList(INT_0, INT_1, INT_2, INT_0, INT_0);

        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(listInt, listInt2));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listInt, listInt3));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listInt, listInt4));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listInt, listInt5));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listInt, listInt6));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listInt, listInt7));

        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(listInt2, listInt));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listInt3, listInt));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listInt4, listInt));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listInt5, listInt));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listInt6, listInt));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listInt7, listInt));

        // compare list and set
        List<Object> listS = Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0);
        List<Object> listS2 = Arrays.asList(OBJ_0, OBJ_1, OBJ_2);
        Set<Object> set = new LinkedHashSet<>(Arrays.asList(OBJ_0, OBJ_1, OBJ_2, OBJ_0));
        Set<Object> set2 = new LinkedHashSet<>(Arrays.asList(OBJ_0, OBJ_1, OBJ_2));
        Set<Object> set3 = new LinkedHashSet<>(Arrays.asList(OBJ_2, OBJ_1, OBJ_0));

        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listS, listS2));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listS, set));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listS, set2));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listS, set3));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listS2, listS));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(listS2, set));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(listS2, set2));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listS2, set3));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(set, listS));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(set, listS2));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(set, set2));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(set, set3));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(set2, listS));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(set2, listS2));
        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(set2, set));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(set2, set3));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(set3, listS));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(set3, listS2));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(set3, set));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(set3, set2));

        // null test
        List<Object> listWithNull = Arrays.asList(OBJ_0, null, null);
        List<Object> listWithNull2 = Arrays.asList(OBJ_0, null, null);
        List<Object> listWithNull3 = Arrays.asList(null, null, OBJ_0);
        List<Object> listWithNull4 = Arrays.asList(OBJ_0, null);
        List<Object> listWithNull5 = Arrays.asList(OBJ_0, null, null, null);

        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(listWithNull, listWithNull2));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listWithNull, listWithNull3));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listWithNull, listWithNull4));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listWithNull, listWithNull5));

        Assert.assertTrue(CollectionUtils.equalsCallingHasNextRepeatedly(listWithNull2, listWithNull));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listWithNull3, listWithNull));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listWithNull4, listWithNull));
        Assert.assertFalse(CollectionUtils.equalsCallingHasNextRepeatedly(listWithNull5, listWithNull));
    }

    @Test
    public void test_getSumLength() throws Exception {
        List<String> list = Arrays.asList(STR_0, STR_1, STR_2);
        Assert.assertEquals(STR_0.length()+STR_1.length()+STR_2.length(), CollectionUtils.getSumLength(list));

        // null test
        list = Arrays.asList(STR_0, STR_1, STR_2, null, StrUtils.STR_EMPTY);
        Assert.assertEquals(STR_0.length()+STR_1.length()+STR_2.length(), CollectionUtils.getSumLength(list));
    }
}
