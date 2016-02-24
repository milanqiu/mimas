package net.milanqiu.mimas.collect.tuple;

import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-02-08
 * @author Milan Qiu
 */
public class Tuple2Test {

    private Tuple2<Integer, String> tuple;
    private Tuple2<Integer, String> anotherTuple;
    private Tuple2<String, String> anotherTupleWithDifferentType;

    @Test
    public void test_setAll() throws Exception {
        tuple = new Tuple2<>();
        tuple.setAll(INT_0, STR_0);
        Assert.assertEquals(INT_0, (int) tuple.getA());
        Assert.assertEquals(STR_0, tuple.getB());
    }

    @Test
    public void test_Constructor() throws Exception {
        // Tuple2()
        tuple = new Tuple2<>();
        Assert.assertNull(tuple.getA());
        Assert.assertNull(tuple.getB());

        // Tuple2(TA a, TB b)
        tuple = new Tuple2<>(INT_1, STR_1);
        Assert.assertEquals(INT_1, (int) tuple.getA());
        Assert.assertEquals(STR_1, tuple.getB());
    }

    @Test
    public void test_equals() throws Exception {
        tuple = new Tuple2<>(INT_0, STR_0);
        anotherTuple = new Tuple2<>(INT_0, STR_0);
        Assert.assertTrue(tuple.equals(anotherTuple));
        Assert.assertTrue(anotherTuple.equals(tuple));

        anotherTuple.setAll(INT_0, STR_1);
        Assert.assertFalse(tuple.equals(anotherTuple));
        Assert.assertFalse(anotherTuple.equals(tuple));

        anotherTuple.setAll(INT_1, STR_0);
        Assert.assertFalse(tuple.equals(anotherTuple));
        Assert.assertFalse(anotherTuple.equals(tuple));

        anotherTupleWithDifferentType = new Tuple2<>(STR_OF_INT_0, STR_0);
        Assert.assertFalse(tuple.equals(anotherTupleWithDifferentType));
        Assert.assertFalse(anotherTupleWithDifferentType.equals(tuple));
    }

    @Test
    public void test_hashCode() throws Exception {
        tuple = new Tuple2<>(INT_0, STR_0);
        anotherTuple = new Tuple2<>(INT_0, STR_0);
        Assert.assertEquals(tuple.hashCode(), anotherTuple.hashCode());

        anotherTuple.setAll(INT_0, STR_1);
        Assert.assertNotEquals(tuple.hashCode(), anotherTuple.hashCode());

        anotherTuple.setAll(INT_1, STR_0);
        Assert.assertNotEquals(tuple.hashCode(), anotherTuple.hashCode());

        anotherTupleWithDifferentType = new Tuple2<>(STR_OF_INT_0, STR_0);
        Assert.assertNotEquals(tuple.hashCode(), anotherTupleWithDifferentType.hashCode());
    }

    @Test
    public void test_toString() throws Exception {
        tuple = new Tuple2<>(INT_0, STR_0);
        Assert.assertEquals("{A="+INT_0+", B="+STR_0+"}", tuple.toString());
    }
}
