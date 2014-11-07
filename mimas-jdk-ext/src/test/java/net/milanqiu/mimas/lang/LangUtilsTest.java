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

    private boolean bool;
    private char ch;
    private byte b;
    private short s;
    private int n;
    private long l;
    private float f;
    private double d;
    private String str;
    private Object obj;

    @Test
    public void test_isDefault() throws Exception {
        Assert.assertTrue(LangUtils.isDefault(false));
        Assert.assertTrue(LangUtils.isDefault('\u0000'));
        Assert.assertTrue(LangUtils.isDefault((byte) 0));
        Assert.assertTrue(LangUtils.isDefault((short) 0));
        Assert.assertTrue(LangUtils.isDefault(0));
        Assert.assertTrue(LangUtils.isDefault(0L));
        Assert.assertTrue(LangUtils.isDefault(0.0F));
        Assert.assertTrue(LangUtils.isDefault(0.0D));
        Assert.assertTrue(LangUtils.isDefault(null));

        Assert.assertTrue(LangUtils.isDefault(bool));
        Assert.assertTrue(LangUtils.isDefault(ch));
        Assert.assertTrue(LangUtils.isDefault(b));
        Assert.assertTrue(LangUtils.isDefault(s));
        Assert.assertTrue(LangUtils.isDefault(n));
        Assert.assertTrue(LangUtils.isDefault(l));
        Assert.assertTrue(LangUtils.isDefault(f));
        Assert.assertTrue(LangUtils.isDefault(d));
        Assert.assertTrue(LangUtils.isDefault(str));
        Assert.assertTrue(LangUtils.isDefault(obj));
    }

    @Test
    public void test_hasDefaultToString() throws Exception {
        Assert.assertTrue(LangUtils.hasDefaultToString(new Object()));
        Assert.assertTrue(LangUtils.hasDefaultToString(this));
    }

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
