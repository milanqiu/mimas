package net.milanqiu.mimas.lang;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-6-9
 * @author Milan Qiu
 */
public class IntStrTest {
    IntStr is;

    @Test
    public void test_ParameterizedGetter() throws Exception {
        is = new IntStr();
        is.setA(INT_0);
        is.setB(STR_0);
        Assert.assertEquals(is.getInt(), INT_0);
        Assert.assertEquals(is.getStr(), STR_0);
    }

    @Test
    public void test_ParameterizedSetter() throws Exception {
        is = new IntStr();
        is.setInt(INT_1);
        is.setStr(STR_1);
        Assert.assertEquals((int) is.getA(), INT_1);
        Assert.assertEquals(is.getB(), STR_1);
    }
}
