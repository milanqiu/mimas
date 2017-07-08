package net.milanqiu.mimas.collect.tuple;

import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-06-09
 * @author Milan Qiu
 */
public class StrIntTest {

    private StrInt si;

    @Test
    public void test_ParameterizedGetter() throws Exception {
        si = new StrInt();
        si.setA(STR_0);
        si.setB(INT_0);
        Assert.assertEquals(STR_0, si.getStr());
        Assert.assertEquals(INT_0, si.getInt());

        // null test
        si.setA(null);
        si.setB(null);
        Assert.assertNull(si.getStr());
        Assert.assertEquals(0, si.getInt());
    }

    @Test
    public void test_ParameterizedSetter() throws Exception {
        si = new StrInt();
        si.setStr(STR_1);
        si.setInt(INT_1);
        Assert.assertEquals(STR_1, si.getA());
        Assert.assertEquals(INT_1, (int) si.getB());

        // null test
        si.setStr(null);
        si.setInt(0);
        Assert.assertNull(si.getA());
        Assert.assertEquals(0, (int) si.getB());
    }
}
