package net.milanqiu.mimas.collect;

import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-25
 * @author Milan Qiu
 */
public class ArrayUtilsTest {

    @Test
    public void test_contains() throws Exception {
        // boolean contains(int[] array, int value)
        int[] intArr = new int[] {INT_0, INT_1, INT_0, INT_2};
        Assert.assertTrue(ArrayUtils.contains(intArr, INT_0));
        Assert.assertTrue(ArrayUtils.contains(intArr, INT_1));
        Assert.assertTrue(ArrayUtils.contains(intArr, INT_2));
        Assert.assertFalse(ArrayUtils.contains(intArr, INT_3));

        // <T> boolean contains(T[] array, Object value)
        Object[] objArr = new Object[] {OBJ_0, OBJ_1, OBJ_0, OBJ_2, null};
        Assert.assertTrue(ArrayUtils.contains(objArr, OBJ_0));
        Assert.assertTrue(ArrayUtils.contains(objArr, OBJ_1));
        Assert.assertTrue(ArrayUtils.contains(objArr, OBJ_2));
        Assert.assertFalse(ArrayUtils.contains(objArr, OBJ_3));
        Assert.assertTrue(ArrayUtils.contains(objArr, null));

        Integer[] intObjArr = new Integer[] {INT_0, INT_1, INT_0, INT_2};
        Integer int0Copy = new Integer(INT_0);
        Long int1AsLong = new Long(INT_1);
        Float int2AsFloat = new Float(INT_2);
        Assert.assertTrue(ArrayUtils.contains(intObjArr, int0Copy));
        Assert.assertFalse(ArrayUtils.contains(intObjArr, int1AsLong));
        Assert.assertFalse(ArrayUtils.contains(intObjArr, int2AsFloat));
        Assert.assertFalse(ArrayUtils.contains(intObjArr, null));
    }

    @Test
    public void test_duplicate() throws Exception {
        // int[] duplicate(int value, int count)
        Assert.assertArrayEquals(new int[] {INT_0, INT_0, INT_0}, ArrayUtils.duplicate(INT_0, 3));

        // Object[] duplicate(Object value, int count)
        Assert.assertArrayEquals(new Object[] {OBJ_0, OBJ_0, OBJ_0}, ArrayUtils.duplicate(OBJ_0, 3));
    }

    @Test
    public void test_isNullOrEmpty() throws Exception {
        Assert.assertTrue(ArrayUtils.isNullOrEmpty(null));
        Assert.assertTrue(ArrayUtils.isNullOrEmpty(new Object[] {}));
        Assert.assertFalse(ArrayUtils.isNullOrEmpty(new Object[] {null}));
    }
}
