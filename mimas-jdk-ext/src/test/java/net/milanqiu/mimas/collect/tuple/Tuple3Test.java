package net.milanqiu.mimas.collect.tuple;

import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-02-08
 * @author Milan Qiu
 */
public class Tuple3Test {

    private Tuple3<Integer, Integer, String> tuple;
    private Tuple3<Integer, Integer, String> anotherTuple;
    private Tuple3<String, String, String> anotherTupleWithDifferentType;

    @Test
    public void test_setAll() throws Exception {
        tuple = new Tuple3<>();
        tuple.setAll(INT_0, INT_1, STR_0);
        Assert.assertEquals(INT_0, (int) tuple.getA());
        Assert.assertEquals(INT_1, (int) tuple.getB());
        Assert.assertEquals(STR_0, tuple.getC());
    }

    @Test
    public void test_Constructor() throws Exception {
        // Tuple3()
        tuple = new Tuple3<>();
        Assert.assertNull(tuple.getA());
        Assert.assertNull(tuple.getB());
        Assert.assertNull(tuple.getC());

        // Tuple3(TA a, TB b, TC c)
        tuple = new Tuple3<>(INT_0, INT_2, STR_0);
        Assert.assertEquals(INT_0, (int) tuple.getA());
        Assert.assertEquals(INT_2, (int) tuple.getB());
        Assert.assertEquals(STR_0, tuple.getC());
    }

    @Test
    public void test_equals() throws Exception {
        tuple = new Tuple3<>(INT_0, INT_1, STR_0);
        anotherTuple = new Tuple3<>(INT_0, INT_1, STR_0);
        Assert.assertTrue(tuple.equals(anotherTuple));
        Assert.assertTrue(anotherTuple.equals(tuple));

        anotherTuple.setAll(INT_0, INT_1, STR_1);
        Assert.assertFalse(tuple.equals(anotherTuple));
        Assert.assertFalse(anotherTuple.equals(tuple));

        anotherTuple.setAll(INT_0, INT_2, STR_0);
        Assert.assertFalse(tuple.equals(anotherTuple));
        Assert.assertFalse(anotherTuple.equals(tuple));

        anotherTupleWithDifferentType = new Tuple3<>(STR_OF_INT_0, STR_OF_INT_1, STR_0);
        Assert.assertFalse(tuple.equals(anotherTupleWithDifferentType));
        Assert.assertFalse(anotherTupleWithDifferentType.equals(tuple));
    }

    @Test
    public void test_hashCode() throws Exception {
        tuple = new Tuple3<>(INT_0, INT_1, STR_0);
        anotherTuple = new Tuple3<>(INT_0, INT_1, STR_0);
        Assert.assertEquals(tuple.hashCode(), anotherTuple.hashCode());

        anotherTuple.setAll(INT_0, INT_1, STR_1);
        Assert.assertNotEquals(tuple.hashCode(), anotherTuple.hashCode());

        anotherTuple.setAll(INT_0, INT_2, STR_0);
        Assert.assertNotEquals(tuple.hashCode(), anotherTuple.hashCode());

        anotherTupleWithDifferentType = new Tuple3<>(STR_OF_INT_0, STR_OF_INT_1, STR_0);
        Assert.assertNotEquals(tuple.hashCode(), anotherTupleWithDifferentType.hashCode());
    }

    @Test
    public void test_toString() throws Exception {
        tuple = new Tuple3<>(INT_0, INT_1, STR_0);
        Assert.assertEquals("{A="+INT_0+", B="+INT_1+", C="+STR_0+"}", tuple.toString());
    }
}
