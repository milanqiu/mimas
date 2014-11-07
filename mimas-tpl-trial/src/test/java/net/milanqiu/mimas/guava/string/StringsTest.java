package net.milanqiu.mimas.guava.string;

import com.google.common.base.Strings;
import static net.milanqiu.mimas.string.StrUtils.*;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-17
 * @author Milan Qiu
 */
public class StringsTest {

    @Test
    public void test_emptyToNull() throws Exception {
        Assert.assertEquals(STR_0, Strings.emptyToNull(STR_0));
        Assert.assertEquals(STR_SPACE, Strings.emptyToNull(STR_SPACE));
        Assert.assertNull(Strings.emptyToNull(STR_EMPTY));
        Assert.assertNull(Strings.emptyToNull(null));
    }

    @Test
    public void test_nullToEmpty() throws Exception {
        Assert.assertEquals(STR_0, Strings.nullToEmpty(STR_0));
        Assert.assertEquals(STR_SPACE, Strings.nullToEmpty(STR_SPACE));
        Assert.assertEquals(STR_EMPTY, Strings.nullToEmpty(STR_EMPTY));
        Assert.assertEquals(STR_EMPTY, Strings.nullToEmpty(null));
    }

    @Test
    public void test_isNullOrEmpty() throws Exception {
        Assert.assertFalse(Strings.isNullOrEmpty(STR_0));
        Assert.assertFalse(Strings.isNullOrEmpty(STR_SPACE));
        Assert.assertTrue(Strings.isNullOrEmpty(STR_EMPTY));
        Assert.assertTrue(Strings.isNullOrEmpty(null));
    }
}
