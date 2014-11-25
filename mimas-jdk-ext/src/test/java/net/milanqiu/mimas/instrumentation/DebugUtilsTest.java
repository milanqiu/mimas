package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.lang.MethodIdentifier;
import net.milanqiu.mimas.lang.MethodIdentifierList;
import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2014-11-10
 * @author Milan Qiu
 */
public class DebugUtilsTest {

    private StackTraceElement[] getStackTraceReally() {
        return Thread.currentThread().getStackTrace();
    }

    private StackTraceElement[] getStackTrace() {
        return getStackTraceReally();
    }

    @Test
    public void test_removeStackTraceTopElements() throws Exception {
        StackTraceElement[] currStackTrace = DebugUtils.removeStackTraceTopElements(Thread.currentThread().getStackTrace(),
                MethodIdentifierList.create());
        StackTraceElement[] processedStackTrace = DebugUtils.removeStackTraceTopElements(getStackTrace(),
                MethodIdentifierList.create(this.getClass().getName(), "getStackTrace",
                                            this.getClass().getName(), "getStackTraceReally"));

        Assert.assertEquals(currStackTrace.length, processedStackTrace.length);
        for (int i = 0; i < currStackTrace.length; i++) {
            Assert.assertEquals(currStackTrace[i].getFileName(), processedStackTrace[i].getFileName());
            Assert.assertEquals(currStackTrace[i].getClassName(), processedStackTrace[i].getClassName());
            Assert.assertEquals(currStackTrace[i].getMethodName(), processedStackTrace[i].getMethodName());
            if (i != 0)
                Assert.assertEquals(currStackTrace[i].getLineNumber(), processedStackTrace[i].getLineNumber());
        }
    }

    private void getCurrentStackTraceElementAnother() {
        StackTraceElement ste = DebugUtils.getCurrentStackTraceElement();
        Assert.assertEquals(this.getClass().getName(), ste.getClassName());
        Assert.assertEquals("getCurrentStackTraceElementAnother", ste.getMethodName());
    }

    @Test
    public void test_getCurrentStackTraceElement() throws Exception {
        StackTraceElement ste = DebugUtils.getCurrentStackTraceElement();
        Assert.assertEquals(this.getClass().getName(), ste.getClassName());
        Assert.assertEquals("test_getCurrentStackTraceElement", ste.getMethodName());

        getCurrentStackTraceElementAnother();
    }

    private void getCurrentMethodAnother() {
        MethodIdentifier mi = DebugUtils.getCurrentMethod();
        Assert.assertEquals(this.getClass().getName(), mi.getClassName());
        Assert.assertEquals("getCurrentMethodAnother", mi.getMethodName());
    }

    @Test
    public void test_getCurrentMethod() throws Exception {
        MethodIdentifier mi = DebugUtils.getCurrentMethod();
        Assert.assertEquals(this.getClass().getName(), mi.getClassName());
        Assert.assertEquals("test_getCurrentMethod", mi.getMethodName());

        getCurrentMethodAnother();
    }

    private void getActualCurrentMethodAnother() {
        MethodIdentifier mi = DebugUtils.getActualCurrentMethod(
                MethodIdentifierList.create(this.getClass().getName(), "getActualCurrentMethodAnother"));
        Assert.assertEquals(this.getClass().getName(), mi.getClassName());
        Assert.assertEquals("test_getActualCurrentMethod", mi.getMethodName());
    }

    @Test
    public void test_getActualCurrentMethod() throws Exception {
        MethodIdentifier mi = DebugUtils.getActualCurrentMethod(MethodIdentifierList.create());
        Assert.assertEquals(this.getClass().getName(), mi.getClassName());
        Assert.assertEquals("test_getActualCurrentMethod", mi.getMethodName());

        getActualCurrentMethodAnother();
    }
}
