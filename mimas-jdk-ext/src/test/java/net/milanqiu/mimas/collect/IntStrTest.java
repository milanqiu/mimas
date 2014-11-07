package net.milanqiu.mimas.collect;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

import net.milanqiu.mimas.collect.IntStr;
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
