package net.milanqiu.mimas.guava.base;

import com.google.common.base.Strings;
import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import static net.milanqiu.mimas.string.StrUtils.*;

/**
 * Creation Date: 2014-07-17
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

    @Test
    public void test_padStart() throws Exception {
        Assert.assertEquals("00123", Strings.padStart("123", 5, '0'));
        Assert.assertEquals("123",   Strings.padStart("123", 3, '0'));
        Assert.assertEquals("123",   Strings.padStart("123", 1, '0'));
    }

    @Test
    public void test_padEnd() throws Exception {
        Assert.assertEquals("12300", Strings.padEnd("123", 5, '0'));
        Assert.assertEquals("123",   Strings.padEnd("123", 3, '0'));
        Assert.assertEquals("123",   Strings.padEnd("123", 1, '0'));
    }

    @Test
    public void test_repeat() throws Exception {
        Assert.assertEquals("123123123", Strings.repeat("123", 3));
    }

    @Test
    public void test_commonPrefix() throws Exception {
        Assert.assertEquals("123", Strings.commonPrefix("123456", "123789"));
        Assert.assertEquals("123", Strings.commonPrefix("123456", "123"));
        Assert.assertEquals("",    Strings.commonPrefix("123456", "456123"));
    }

    @Test
    public void test_commonSuffix() throws Exception {
        Assert.assertEquals("123", Strings.commonSuffix("456123", "789123"));
        Assert.assertEquals("123", Strings.commonSuffix("456123", "123"));
        Assert.assertEquals("",    Strings.commonSuffix("456123", "123456"));
    }
}
