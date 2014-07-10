package net.milanqiu.mimas.junit;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-8
 * @author Milan Qiu
 */
public class AssertExtTest {

    @Test
    public void test_assertClassification() throws Exception {
        AssertExt.assertClassification(Integer.class, 123);
        AssertExt.assertClassification(String.class, "abc");
        AssertExt.assertClassification(ArithmeticException.class, new ArithmeticException());
    }

    private boolean bool;
    private char c;
    private byte b;
    private short s;
    private int n;
    private long l;
    private float f;
    private double d;
    private String str;
    private Object obj;

    @Test
    public void test_assertDefault() throws Exception {
        AssertExt.assertDefault(false);
        AssertExt.assertDefault('\u0000');
        AssertExt.assertDefault((byte) 0);
        AssertExt.assertDefault((short) 0);
        AssertExt.assertDefault(0);
        AssertExt.assertDefault(0L);
        AssertExt.assertDefault(0.0F);
        AssertExt.assertDefault(0.0D);
        AssertExt.assertDefault(null);

        AssertExt.assertDefault(bool);
        AssertExt.assertDefault(c);
        AssertExt.assertDefault(b);
        AssertExt.assertDefault(s);
        AssertExt.assertDefault(n);
        AssertExt.assertDefault(l);
        AssertExt.assertDefault(f);
        AssertExt.assertDefault(d);
        AssertExt.assertDefault(str);
        AssertExt.assertDefault(obj);
    }

    @Test
    public void test_assertEmpty() throws Exception {
        AssertExt.assertEmpty("");
    }

    @Test
    public void test_assertHasDefaultToString() throws Exception {
        AssertExt.assertHasDefaultToString(new Object());
        AssertExt.assertHasDefaultToString(this);
    }
}
