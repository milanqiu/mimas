package net.milanqiu.mimas.lang;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-2-8
 * @author Milan Qiu
 */
public class Tuple3Test {
    Tuple3<Integer, Integer, String> tuple;
    Tuple3<Integer, Integer, String> anotherTuple;
    Tuple3<String, String, String> anotherTupleWithDifferentType;

    @Test
    public void test_setAll() throws Exception {
        tuple = new Tuple3<>();
        tuple.setAll(INT_0, INT_1, STR_0);
        Assert.assertEquals((int) tuple.getA(), INT_0);
        Assert.assertEquals((int) tuple.getB(), INT_1);
        Assert.assertEquals(tuple.getC(), STR_0);
    }

    @Test
    public void test_Constructor() throws Exception {
        tuple = new Tuple3<>(INT_0, INT_2, STR_0);
        Assert.assertEquals((int) tuple.getA(), INT_0);
        Assert.assertEquals((int) tuple.getB(), INT_2);
        Assert.assertEquals(tuple.getC(), STR_0);
    }

    @Test
    public void test_equals() throws Exception {
        tuple = new Tuple3<>(INT_0, INT_1, STR_0);
        anotherTuple = new Tuple3<>(INT_0, INT_1, STR_0);
        Assert.assertTrue(tuple.equals(anotherTuple));

        anotherTuple.setAll(INT_0, INT_1, STR_1);
        Assert.assertFalse(tuple.equals(anotherTuple));

        anotherTuple.setAll(INT_0, INT_2, STR_0);
        Assert.assertFalse(tuple.equals(anotherTuple));

        anotherTupleWithDifferentType = new Tuple3<>(STR_OF_INT_0, STR_OF_INT_1, STR_0);
        Assert.assertFalse(tuple.equals(anotherTupleWithDifferentType));
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
        Assert.assertEquals(tuple.toString(), "(A="+INT_0+", B="+INT_1+", C="+STR_0+")");
    }
}
