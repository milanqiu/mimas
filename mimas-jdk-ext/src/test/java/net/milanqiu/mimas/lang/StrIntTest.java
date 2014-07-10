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
        Assert.assertEquals(STR_0, si.getStr());
        Assert.assertEquals(INT_0, si.getInt());
    }

    @Test
    public void test_ParameterizedSetter() throws Exception {
        si = new StrInt();
        si.setStr(STR_1);
        si.setInt(INT_1);
        Assert.assertEquals(STR_1, si.getA());
        Assert.assertEquals(INT_1, (int) si.getB());
    }
}
