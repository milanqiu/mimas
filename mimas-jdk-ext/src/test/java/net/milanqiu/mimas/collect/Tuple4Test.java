package net.milanqiu.mimas.collect;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

import net.milanqiu.mimas.collect.Tuple4;
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
        Assert.assertEquals(INT_0, (int) tuple.getA());
        Assert.assertEquals(INT_1, (int) tuple.getB());
        Assert.assertEquals(INT_2, (int) tuple.getC());
        Assert.assertEquals(STR_0, tuple.getD());
    }

    @Test
    public void test_Constructor() throws Exception {
        tuple = new Tuple4<>(INT_0, INT_1, INT_3, STR_0);
        Assert.assertEquals(INT_0, (int) tuple.getA());
        Assert.assertEquals(INT_1, (int) tuple.getB());
        Assert.assertEquals(INT_3, (int) tuple.getC());
        Assert.assertEquals(STR_0, tuple.getD());
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
        Assert.assertEquals("(A="+INT_0+", B="+INT_1+", C="+INT_2+", D="+STR_0+")", tuple.toString());
    }
}
