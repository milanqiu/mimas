package net.milanqiu.mimas.lang;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-2-8
 * @author Milan Qiu
 */
public class Tuple2Test {
    Tuple2<Integer, String> tuple;
    Tuple2<Integer, String> anotherTuple;
    Tuple2<String, String> anotherTupleWithDifferentType;

    @Test
    public void test_setAll() throws Exception {
        tuple = new Tuple2<>();
        tuple.setAll(INT_0, STR_0);
        Assert.assertEquals((int) tuple.getA(), INT_0);
        Assert.assertEquals(tuple.getB(), STR_0);
    }

    @Test
    public void test_Constructor() throws Exception {
        tuple = new Tuple2<>(INT_1, STR_1);
        Assert.assertEquals((int) tuple.getA(), INT_1);
        Assert.assertEquals(tuple.getB(), STR_1);
    }

    @Test
    public void test_equals() throws Exception {
        tuple = new Tuple2<>(INT_0, STR_0);
        anotherTuple = new Tuple2<>(INT_0, STR_0);
        Assert.assertTrue(tuple.equals(anotherTuple));

        anotherTuple.setAll(INT_0, STR_1);
        Assert.assertFalse(tuple.equals(anotherTuple));

        anotherTuple.setAll(INT_1, STR_0);
        Assert.assertFalse(tuple.equals(anotherTuple));

        anotherTupleWithDifferentType = new Tuple2<>(STR_OF_INT_0, STR_0);
        Assert.assertFalse(tuple.equals(anotherTupleWithDifferentType));
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
        Assert.assertEquals(tuple.toString(), "(A="+INT_0+", B="+STR_0+")");
    }
}
