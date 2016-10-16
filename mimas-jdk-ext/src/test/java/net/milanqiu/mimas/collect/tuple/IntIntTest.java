package net.milanqiu.mimas.collect.tuple;

import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2016-02-12
 * @author Milan Qiu
 */
public class IntIntTest {

    @Test
    public void test_createList() throws Exception {
        List<IntInt> list = IntInt.createList(INT_0, INT_1, INT_2, INT_3);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(INT_0, (int) list.get(0).getA());
        Assert.assertEquals(INT_1, (int) list.get(0).getB());
        Assert.assertEquals(INT_2, (int) list.get(1).getA());
        Assert.assertEquals(INT_3, (int) list.get(1).getB());

        AssertExt.assertExceptionThrown(() -> IntInt.createList(INT_0, INT_1, INT_2, INT_3, INT_4), IllegalArgumentException.class);
    }
}
