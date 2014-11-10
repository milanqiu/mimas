package net.milanqiu.mimas.system;

import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-11-10
 * @author Milan Qiu
 */
public class MethodIdentifierTest {

    @Test
    public void test_create() throws Exception {
        // MethodIdentifier create(String className, String methodName)
        MethodIdentifier mi = MethodIdentifier.create(STR_0, STR_1);
        Assert.assertEquals(STR_0, mi.getClassName());
        Assert.assertEquals(STR_1, mi.getMethodName());

        // null test
        mi = MethodIdentifier.create(null, null);
        Assert.assertNull(mi.getClassName());
        Assert.assertNull(mi.getMethodName());

        // MethodIdentifier create(StackTraceElement stackTraceElement)
        mi = MethodIdentifier.create(Thread.currentThread().getStackTrace()[0]);
        Assert.assertEquals(Thread.class.getName(), mi.getClassName());
        Assert.assertEquals("getStackTrace", mi.getMethodName());
    }

    @Test
    public void test_equals() throws Exception {
        MethodIdentifier mi = MethodIdentifier.create(STR_0, STR_1);
        MethodIdentifier mi2 = MethodIdentifier.create(STR_0, STR_1);
        MethodIdentifier mi3 = MethodIdentifier.create(STR_0, STR_2);
        MethodIdentifier mi4 = MethodIdentifier.create(STR_2, STR_1);
        MethodIdentifier mi5 = MethodIdentifier.create(null, null);

        // boolean equals(String className, String methodName)
        Assert.assertTrue(mi.equals(STR_0, STR_1));
        Assert.assertFalse(mi.equals(STR_0, STR_2));
        Assert.assertFalse(mi.equals(STR_2, STR_1));
        Assert.assertFalse(mi.equals(null, null));

        // boolean equals(Object o)
        Assert.assertTrue(mi.equals(mi2));
        Assert.assertFalse(mi.equals(mi3));
        Assert.assertFalse(mi.equals(mi4));
        Assert.assertFalse(mi.equals(mi5));
        Assert.assertTrue(mi2.equals(mi));
        Assert.assertFalse(mi3.equals(mi));
        Assert.assertFalse(mi4.equals(mi));
        Assert.assertFalse(mi5.equals(mi));
    }

    @Test
    public void test_toString() throws Exception {
        MethodIdentifier mi = MethodIdentifier.create("PackageName.SimpleClassName", "MethodName");
        Assert.assertEquals("PackageName.SimpleClassName.MethodName", mi.toString());

        // null test
        mi = MethodIdentifier.create(null, null);
        Assert.assertEquals("null.null", mi.toString());
    }
}
