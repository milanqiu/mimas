package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.system.MethodIdentifier;
import net.milanqiu.mimas.system.MethodIdentifierList;
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

    private void aMethod() {
        MethodIdentifier mi = DebugUtils.getCurrentMethod();
        Assert.assertEquals(this.getClass().getName(), mi.getClassName());
        Assert.assertEquals("aMethod", mi.getMethodName());
    }

    @Test
    public void test_getCurrentMethod() throws Exception {
        MethodIdentifier mi = DebugUtils.getCurrentMethod();
        Assert.assertEquals(this.getClass().getName(), mi.getClassName());
        Assert.assertEquals("test_getCurrentMethod", mi.getMethodName());

        aMethod();
    }
}
