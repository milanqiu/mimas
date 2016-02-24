package net.milanqiu.mimas.collect.tuple;

import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-06-09
 * @author Milan Qiu
 */
public class IntStrTest {

    private IntStr is;

    @Test
    public void test_ParameterizedGetter() throws Exception {
        is = new IntStr();
        is.setA(INT_0);
        is.setB(STR_0);
        Assert.assertEquals(INT_0, is.getInt());
        Assert.assertEquals(STR_0, is.getStr());
    }

    @Test
    public void test_ParameterizedSetter() throws Exception {
        is = new IntStr();
        is.setInt(INT_1);
        is.setStr(STR_1);
        Assert.assertEquals(INT_1, (int) is.getA());
        Assert.assertEquals(STR_1, is.getB());
    }
}