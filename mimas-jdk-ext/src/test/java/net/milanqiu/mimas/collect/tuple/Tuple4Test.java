package net.milanqiu.mimas.collect.tuple;

import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-02-08
 * @author Milan Qiu
 */
public class Tuple4Test {

    private Tuple4<Integer, Integer, Integer, String> tuple;
    private Tuple4<Integer, Integer, Integer, String> anotherTuple;
    private Tuple4<String, String, String, String> anotherTupleWithDifferentType;

    @Test
    public void test_setAll() throws Exception {
        tuple = new Tuple4<>();
        tuple.setAll(INT_0, INT_1, INT_2, STR_0);
        Assert.assertEquals(INT_0, (int) tuple.getA());
        Assert.assertEquals(INT_1, (int) tuple.getB());
        Assert.assertEquals(INT_2, (int) tuple.getC());
        Assert.assertEquals(STR_0, tuple.getD());

        // null test
        tuple.setAll(null, null, null, null);
        Assert.assertNull(tuple.getA());
        Assert.assertNull(tuple.getB());
        Assert.assertNull(tuple.getC());
        Assert.assertNull(tuple.getD());
    }

    @Test
    public void test_Constructor() throws Exception {
        // Tuple4()
        tuple = new Tuple4<>();
        Assert.assertNull(tuple.getA());
        Assert.assertNull(tuple.getB());
        Assert.assertNull(tuple.getC());
        Assert.assertNull(tuple.getD());

        // Tuple4(TA a, TB b, TC c, TD d)
        tuple = new Tuple4<>(INT_0, INT_1, INT_3, STR_0);
        Assert.assertEquals(INT_0, (int) tuple.getA());
        Assert.assertEquals(INT_1, (int) tuple.getB());
        Assert.assertEquals(INT_3, (int) tuple.getC());
        Assert.assertEquals(STR_0, tuple.getD());

        // null test
        tuple = new Tuple4<>(null, null, null, null);
        Assert.assertNull(tuple.getA());
        Assert.assertNull(tuple.getB());
        Assert.assertNull(tuple.getC());
        Assert.assertNull(tuple.getD());
    }

    @Test
    public void test_equals() throws Exception {
        tuple = new Tuple4<>(INT_0, INT_1, INT_2, STR_0);
        anotherTuple = new Tuple4<>(INT_0, INT_1, INT_2, STR_0);
        Assert.assertTrue(tuple.equals(anotherTuple));
        Assert.assertTrue(anotherTuple.equals(tuple));

        anotherTuple.setAll(INT_0, INT_1, INT_2, STR_1);
        Assert.assertFalse(tuple.equals(anotherTuple));
        Assert.assertFalse(anotherTuple.equals(tuple));

        anotherTuple.setAll(INT_0, INT_1, INT_3, STR_0);
        Assert.assertFalse(tuple.equals(anotherTuple));
        Assert.assertFalse(anotherTuple.equals(tuple));

        anotherTupleWithDifferentType = new Tuple4<>(STR_OF_INT_0, STR_OF_INT_1, STR_OF_INT_2, STR_0);
        Assert.assertFalse(tuple.equals(anotherTupleWithDifferentType));
        Assert.assertFalse(anotherTupleWithDifferentType.equals(tuple));

        // null test
        anotherTuple.setAll(null, null, null, null);
        Assert.assertFalse(tuple.equals(anotherTuple));
        Assert.assertFalse(anotherTuple.equals(tuple));

        tuple.setAll(null, null, null, null);
        Assert.assertTrue(tuple.equals(anotherTuple));
        Assert.assertTrue(anotherTuple.equals(tuple));
    }

    @Test
    public void test_hashCode() throws Exception {
        tuple = new Tuple4<>(INT_0, INT_1, INT_2, STR_0);
        anotherTuple = new Tuple4<>(INT_0, INT_1, INT_2, STR_0);
        Assert.assertEquals(tuple.hashCode(), anotherTuple.hashCode());

        anotherTuple.setAll(INT_0, INT_1, INT_2, STR_1);
        Assert.assertNotEquals(tuple.hashCode(), anotherTuple.hashCode());

        anotherTuple.setAll(INT_0, INT_1, INT_3, STR_0);
        Assert.assertNotEquals(tuple.hashCode(), anotherTuple.hashCode());

        anotherTupleWithDifferentType = new Tuple4<>(STR_OF_INT_0, STR_OF_INT_1, STR_OF_INT_2, STR_0);
        Assert.assertNotEquals(tuple.hashCode(), anotherTupleWithDifferentType.hashCode());

        // null test
        anotherTuple.setAll(null, null, null, null);
        Assert.assertNotEquals(tuple.hashCode(), anotherTuple.hashCode());

        tuple.setAll(null, null, null, null);
        Assert.assertEquals(tuple.hashCode(), anotherTuple.hashCode());
    }

    @Test
    public void test_clone() throws Exception {
        tuple = new Tuple4<>(INT_0, INT_1, INT_2, STR_0);
        anotherTuple = tuple.clone();
        Assert.assertEquals(INT_0, (int) anotherTuple.getA());
        Assert.assertEquals(INT_1, (int) anotherTuple.getB());
        Assert.assertEquals(INT_2, (int) anotherTuple.getC());
        Assert.assertEquals(STR_0, anotherTuple.getD());
    }

    @Test
    public void test_toString() throws Exception {
        tuple = new Tuple4<>(INT_0, INT_1, INT_2, STR_0);
        Assert.assertEquals("{A="+INT_0+", B="+INT_1+", C="+INT_2+", D="+STR_0+"}", tuple.toString());

        // null test
        tuple.setAll(null, null, null, null);
        Assert.assertEquals("{A=null, B=null, C=null, D=null}", tuple.toString());
    }
}
