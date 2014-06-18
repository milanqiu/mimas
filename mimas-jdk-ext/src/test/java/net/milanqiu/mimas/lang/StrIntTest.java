package net.milanqiu.mimas.lang;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-6-9
 * @author Milan Qiu
 */
public class StrIntTest {
    StrInt si;

    @Test
    public void test_ParameterizedGetter() throws Exception {
        si = new StrInt();
        si.setA(STR_0);
        si.setB(INT_0);
        Assert.assertEquals(si.getStr(), STR_0);
        Assert.assertEquals(si.getInt(), INT_0);
    }

    @Test
    public void test_ParameterizedSetter() throws Exception {
        si = new StrInt();
        si.setStr(STR_1);
        si.setInt(INT_1);
        Assert.assertEquals(si.getA(), STR_1);
        Assert.assertEquals((int) si.getB(), INT_1);
    }
}
