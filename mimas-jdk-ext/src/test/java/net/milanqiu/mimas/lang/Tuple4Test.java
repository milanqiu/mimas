package net.milanqiu.mimas.lang;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-2-8
 * @author Milan Qiu
 */
public class Tuple4Test {
    Tuple4<Integer, Integer, Integer, String> tuple;
    Tuple4<Integer, Integer, Integer, String> anotherTuple;
    Tuple4<String, String, String, String> anotherTupleWithDifferentType;

    @Test
    public void test_setAll() throws Exception {
        tuple = new Tuple4<>();
        tuple.setAll(INT_0, INT_1, INT_2, STR_0);
        Assert.assertEquals((int) tuple.getA(), INT_0);
        Assert.assertEquals((int) tuple.getB(), INT_1);
        Assert.assertEquals((int) tuple.getC(), INT_2);
        Assert.assertEquals(tuple.getD(), STR_0);
    }

    @Test
    public void test_Constructor() throws Exception {
        tuple = new Tuple4<>(INT_0, INT_1, INT_3, STR_0);
        Assert.assertEquals((int) tuple.getA(), INT_0);
        Assert.assertEquals((int) tuple.getB(), INT_1);
        Assert.assertEquals((int) tuple.getC(), INT_3);
        Assert.assertEquals(tuple.getD(), STR_0);
    }

    @Test
    public void test_equals() throws Exception {
        tuple = new Tuple4<>(INT_0, INT_1, INT_2, STR_0);
        anotherTuple = new Tuple4<>(INT_0, INT_1, INT_2, STR_0);
        Assert.assertTrue(tuple.equals(anotherTuple));

        anotherTuple.setAll(INT_0, INT_1, INT_2, STR_1);
        Assert.assertFalse(tuple.equals(anotherTuple));

        anotherTuple.setAll(INT_0, INT_1, INT_3, STR_0);
        Assert.assertFalse(tuple.equals(anotherTuple));

        anotherTupleWithDifferentType = new Tuple4<>(STR_OF_INT_0, STR_OF_INT_1, STR_OF_INT_2, STR_0);
        Assert.assertFalse(tuple.equals(anotherTupleWithDifferentType));
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
    }

    @Test
    public void test_toString() throws Exception {
        tuple = new Tuple4<>(INT_0, INT_1, INT_2, STR_0);
        Assert.assertEquals(tuple.toString(), "(A="+INT_0+", B="+INT_1+", C="+INT_2+", D="+STR_0+")");
    }
}
