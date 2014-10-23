package net.milanqiu.mimas.lang;

import java.util.Arrays;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-25
 * @author Milan Qiu
 */
public class LangUtilsTest {

    @Test
    public void test_OBJECT_COMPARATOR() throws Exception {
        Assert.assertEquals(0, LangUtils.OBJECT_COMPARATOR.compare(OBJ_0, OBJ_0));
        Assert.assertTrue(Arrays.asList(-1, 1).contains(LangUtils.OBJECT_COMPARATOR.compare(OBJ_0, OBJ_1)));

        Assert.assertEquals(0, LangUtils.OBJECT_COMPARATOR.compare(10, 10));
        Assert.assertEquals(1, LangUtils.OBJECT_COMPARATOR.compare(10, 9));
        Assert.assertEquals(-1, LangUtils.OBJECT_COMPARATOR.compare(10, 11));

        // null test
        Assert.assertEquals(1, LangUtils.OBJECT_COMPARATOR.compare(OBJ_0, null));
        Assert.assertEquals(-1, LangUtils.OBJECT_COMPARATOR.compare(null, OBJ_0));
        Assert.assertEquals(0, LangUtils.OBJECT_COMPARATOR.compare(null, null));
    }
}
