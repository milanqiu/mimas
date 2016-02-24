package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.lang.StackTrace;
import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2014-11-10
 * @author Milan Qiu
 */
public class DebugUtilsTest {

    private void getCurrentMethodInEncapsulation() {
        StackTraceElement method = DebugUtils.getCurrentMethod();
        Assert.assertEquals(this.getClass().getName(), method.getClassName());
        Assert.assertEquals("getCurrentMethodInEncapsulation", method.getMethodName());
    }

    @Test
    public void test_getCurrentMethod() throws Exception {
        StackTraceElement method = DebugUtils.getCurrentMethod();
        Assert.assertEquals(this.getClass().getName(), method.getClassName());
        Assert.assertEquals("test_getCurrentMethod", method.getMethodName());

        getCurrentMethodInEncapsulation();
    }

    private void getInvokerMethodInEncapsulation() {
        StackTraceElement method = DebugUtils.getInvokerMethod();
        Assert.assertEquals(this.getClass().getName(), method.getClassName());
        Assert.assertEquals("test_getInvokerMethod", method.getMethodName());
    }

    @Test
    public void test_getInvokerMethod() throws Exception {
        StackTraceElement method = DebugUtils.getInvokerMethod();
        Assert.assertEquals("sun.reflect.NativeMethodAccessorImpl", method.getClassName());
        Assert.assertEquals("invoke0", method.getMethodName());

        getInvokerMethodInEncapsulation();
    }

    private void getSourceMethodInEncapsulation() {
        StackTraceElement method = DebugUtils.getSourceMethod(
                StackTrace.createFromMethodIdentifier(this.getClass().getName(), "getSourceMethodInEncapsulation"));
        Assert.assertEquals(this.getClass().getName(), method.getClassName());
        Assert.assertEquals("test_getSourceMethod", method.getMethodName());
    }

    @Test
    public void test_getSourceMethod() throws Exception {
        StackTraceElement method = DebugUtils.getSourceMethod(StackTrace.create());
        Assert.assertEquals(this.getClass().getName(), method.getClassName());
        Assert.assertEquals("test_getSourceMethod", method.getMethodName());

        getSourceMethodInEncapsulation();
    }
}
