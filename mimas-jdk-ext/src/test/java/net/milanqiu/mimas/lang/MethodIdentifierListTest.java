package net.milanqiu.mimas.lang;

import net.milanqiu.mimas.instrumentation.DebugUtils;
import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-11-10
 * @author Milan Qiu
 */
public class MethodIdentifierListTest {

    @Test
    public void test_create() throws Exception {
        MethodIdentifierList mil = MethodIdentifierList.create(STR_0, STR_1, STR_2, STR_3);
        Assert.assertEquals(2, mil.size());
        Assert.assertEquals(STR_0, mil.get(0).getClassName());
        Assert.assertEquals(STR_1, mil.get(0).getMethodName());
        Assert.assertEquals(STR_2, mil.get(1).getClassName());
        Assert.assertEquals(STR_3, mil.get(1).getMethodName());

        try {
            MethodIdentifierList.create(STR_0, STR_1, STR_2, STR_3, STR_4);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void test_indexOf() throws Exception {
        MethodIdentifierList mil = MethodIdentifierList.create(STR_0, STR_1, STR_2, STR_3);
        Assert.assertEquals(0, mil.indexOf(STR_0, STR_1));
        Assert.assertEquals(1, mil.indexOf(STR_2, STR_3));
        Assert.assertEquals(-1, mil.indexOf(STR_1, STR_2));
        Assert.assertEquals(-1, mil.indexOf(null, null));
    }
}
