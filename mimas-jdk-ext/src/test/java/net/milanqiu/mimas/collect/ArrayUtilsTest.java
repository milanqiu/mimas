package net.milanqiu.mimas.collect;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

import net.milanqiu.mimas.collect.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-25
 * @author Milan Qiu
 */
public class ArrayUtilsTest {

    @Test
    public void test_contains() throws Exception {
        // variant of int parameter
        int[] intArray = new int[]{INT_0, INT_1, INT_0, INT_2};
        Assert.assertTrue(ArrayUtils.contains(intArray, INT_0));
        Assert.assertTrue(ArrayUtils.contains(intArray, INT_1));
        Assert.assertTrue(ArrayUtils.contains(intArray, INT_2));
        Assert.assertFalse(ArrayUtils.contains(intArray, INT_3));

        // variant of generic parameter
        Object[] objArray = new Object[]{OBJ_0, OBJ_1, OBJ_0, OBJ_2, null};
        Assert.assertTrue(ArrayUtils.contains(objArray, OBJ_0));
        Assert.assertTrue(ArrayUtils.contains(objArray, OBJ_1));
        Assert.assertTrue(ArrayUtils.contains(objArray, OBJ_2));
        Assert.assertFalse(ArrayUtils.contains(objArray, OBJ_3));
        Assert.assertTrue(ArrayUtils.contains(objArray, null));

        Integer[] intObjArray = new Integer[]{INT_0, INT_1, INT_0, INT_2};
        Integer int0Copy = new Integer(INT_0);
        Long int1AsLong = new Long(INT_1);
        Float int2AsFloat = new Float(INT_2);
        Assert.assertTrue(ArrayUtils.contains(intObjArray, int0Copy));
        Assert.assertFalse(ArrayUtils.contains(intObjArray, int1AsLong));
        Assert.assertFalse(ArrayUtils.contains(intObjArray, int2AsFloat));
        Assert.assertFalse(ArrayUtils.contains(intObjArray, null));
    }
}
