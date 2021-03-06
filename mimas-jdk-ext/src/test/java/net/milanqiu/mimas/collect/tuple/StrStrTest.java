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
public class StrStrTest {

    @Test
    public void test_createList() throws Exception {
        List<StrStr> list = StrStr.createList(STR_0, STR_1, STR_2, STR_3);
        Assert.assertEquals(2, list.size());
        Assert.assertEquals(STR_0, list.get(0).getA());
        Assert.assertEquals(STR_1, list.get(0).getB());
        Assert.assertEquals(STR_2, list.get(1).getA());
        Assert.assertEquals(STR_3, list.get(1).getB());

        AssertExt.assertExceptionThrown(() -> StrStr.createList(STR_0, STR_1, STR_2, STR_3, STR_4), IllegalArgumentException.class);

        // null test
        list = StrStr.createList(null, null, null, null);
        Assert.assertEquals(2, list.size());
        Assert.assertNull(list.get(0).getA());
        Assert.assertNull(list.get(0).getB());
        Assert.assertNull(list.get(1).getA());
        Assert.assertNull(list.get(1).getB());
    }
}
