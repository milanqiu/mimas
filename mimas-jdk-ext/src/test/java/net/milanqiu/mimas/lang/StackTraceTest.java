package net.milanqiu.mimas.lang;

import net.milanqiu.mimas.collect.tuple.StrStr;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2016-02-12
 * @author Milan Qiu
 */
public class StackTraceTest {

    @Test
    public void test_create() throws Exception {
        StackTrace stackTrace = StackTrace.create();
        Assert.assertEquals(0, stackTrace.getElementCount());
    }

    @Test
    public void test_createFromCurrentThread() throws Exception {
        StackTrace stackTrace = StackTrace.createFromCurrentThread();
        Assert.assertEquals(StackTraceTest.class.getName(),         stackTrace.getElement(0).getClassName());
        Assert.assertEquals("test_createFromCurrentThread",         stackTrace.getElement(0).getMethodName());
        Assert.assertEquals("sun.reflect.NativeMethodAccessorImpl", stackTrace.getElement(1).getClassName());
        Assert.assertEquals("invoke0",                              stackTrace.getElement(1).getMethodName());
    }

    @Test
    public void test_createFromMethodIdentifier() throws Exception {
        StackTrace stackTrace = StackTrace.createFromMethodIdentifier(STR_0, STR_1);
        Assert.assertEquals(1, stackTrace.getElementCount());
        Assert.assertEquals(STR_0, stackTrace.getElement(0).getClassName());
        Assert.assertEquals(STR_1, stackTrace.getElement(0).getMethodName());
    }

    @Test
    public void test_createFromMethodIdentifiers() throws Exception {
        StackTrace stackTrace = StackTrace.createFromMethodIdentifiers(StrStr.createList(STR_0, STR_1, STR_2, STR_3));
        Assert.assertEquals(2, stackTrace.getElementCount());
        Assert.assertEquals(STR_0, stackTrace.getElement(0).getClassName());
        Assert.assertEquals(STR_1, stackTrace.getElement(0).getMethodName());
        Assert.assertEquals(STR_2, stackTrace.getElement(1).getClassName());
        Assert.assertEquals(STR_3, stackTrace.getElement(1).getMethodName());
    }

    @Test
    public void test_createElement() throws Exception {
        StackTraceElement element = StackTrace.createElement(STR_0, STR_1, STR_2, INT_0);
        Assert.assertEquals(STR_0, element.getClassName());
        Assert.assertEquals(STR_1, element.getMethodName());
        Assert.assertEquals(STR_2, element.getFileName());
        Assert.assertEquals(INT_0, element.getLineNumber());

        // null test
        AssertExt.assertExceptionThrown(() -> StackTrace.createElement(null, null, null, 0), NullPointerException.class);
    }

    @Test
    public void test_createMethodElement() throws Exception {
        StackTraceElement element = StackTrace.createMethodElement(STR_0, STR_1);
        Assert.assertEquals(STR_0, element.getClassName());
        Assert.assertEquals(STR_1, element.getMethodName());
        Assert.assertEquals(null, element.getFileName());
        Assert.assertEquals(-1, element.getLineNumber());

        // null test
        AssertExt.assertExceptionThrown(() -> StackTrace.createMethodElement(null, null), NullPointerException.class);
    }

    @Test
    public void test_methodToString() throws Exception {
        StackTraceElement element = StackTrace.createMethodElement("PackageName.SimpleClassName", "MethodName");
        Assert.assertEquals("PackageName.SimpleClassName.MethodName", StackTrace.methodToString(element));
    }

    @Test
    public void test_methodEquals() throws Exception {
        StackTraceElement element = StackTrace.createMethodElement(STR_0, STR_1);
        StackTraceElement element2 = StackTrace.createMethodElement(STR_0, STR_1);
        StackTraceElement element3 = StackTrace.createMethodElement(STR_0, STR_2);
        StackTraceElement element4 = StackTrace.createMethodElement(STR_2, STR_1);
        StackTraceElement element5 = StackTrace.createElement(STR_0, STR_2, STR_3, INT_0);
        StackTraceElement element6 = StackTrace.createElement(STR_0, STR_1, STR_3, INT_0);

        Assert.assertTrue(StackTrace.methodEquals(element, element2));
        Assert.assertFalse(StackTrace.methodEquals(element, element3));
        Assert.assertFalse(StackTrace.methodEquals(element, element4));
        Assert.assertFalse(StackTrace.methodEquals(element, element5));
        Assert.assertTrue(StackTrace.methodEquals(element, element6));
        Assert.assertTrue(StackTrace.methodEquals(element2, element));
        Assert.assertFalse(StackTrace.methodEquals(element3, element));
        Assert.assertFalse(StackTrace.methodEquals(element4, element));
        Assert.assertFalse(StackTrace.methodEquals(element5, element));
        Assert.assertTrue(StackTrace.methodEquals(element6, element));
    }

    @Test
    public void test_containsMethod() throws Exception {
        StackTrace stackTrace = StackTrace.createFromMethodIdentifiers(StrStr.createList(STR_0, STR_1, STR_2, STR_3));
        Assert.assertTrue(stackTrace.containsMethod(StackTrace.createMethodElement(STR_0, STR_1)));
        Assert.assertTrue(stackTrace.containsMethod(StackTrace.createMethodElement(STR_2, STR_3)));
        Assert.assertFalse(stackTrace.containsMethod(StackTrace.createMethodElement(STR_1, STR_2)));
        Assert.assertTrue(stackTrace.containsMethod(StackTrace.createElement(STR_0, STR_1, STR_2, INT_0)));
    }

    @Test
    public void test_append() throws Exception {
        StackTrace stackTrace = StackTrace.createFromMethodIdentifier(STR_0, STR_1);
        StackTrace stackTraceAfterAdd = stackTrace.append(StackTrace.createMethodElement(STR_2, STR_3));

        Assert.assertEquals(2, stackTraceAfterAdd.getElementCount());
        Assert.assertEquals(STR_0, stackTraceAfterAdd.getElement(0).getClassName());
        Assert.assertEquals(STR_1, stackTraceAfterAdd.getElement(0).getMethodName());
        Assert.assertEquals(STR_2, stackTraceAfterAdd.getElement(1).getClassName());
        Assert.assertEquals(STR_3, stackTraceAfterAdd.getElement(1).getMethodName());

        Assert.assertEquals(1, stackTrace.getElementCount());
        Assert.assertEquals(STR_0, stackTrace.getElement(0).getClassName());
        Assert.assertEquals(STR_1, stackTrace.getElement(0).getMethodName());
    }

    @Test
    public void test_removeTopElements() throws Exception {
        StackTrace stackTrace = StackTrace.createFromMethodIdentifiers(StrStr.createList(STR_0, STR_1, STR_2, STR_3));
        StackTrace stackTraceAfterRemove = stackTrace.removeTopElements(StackTrace.createFromMethodIdentifier(STR_2, STR_3));

        Assert.assertEquals(2, stackTraceAfterRemove.getElementCount());
        Assert.assertEquals(STR_0, stackTraceAfterRemove.getElement(0).getClassName());
        Assert.assertEquals(STR_1, stackTraceAfterRemove.getElement(0).getMethodName());
        Assert.assertEquals(STR_2, stackTraceAfterRemove.getElement(1).getClassName());
        Assert.assertEquals(STR_3, stackTraceAfterRemove.getElement(1).getMethodName());

        stackTraceAfterRemove = stackTrace.removeTopElements(StackTrace.createFromMethodIdentifier(STR_0, STR_1));

        Assert.assertEquals(1, stackTraceAfterRemove.getElementCount());
        Assert.assertEquals(STR_2, stackTraceAfterRemove.getElement(0).getClassName());
        Assert.assertEquals(STR_3, stackTraceAfterRemove.getElement(0).getMethodName());

        Assert.assertEquals(2, stackTrace.getElementCount());
        Assert.assertEquals(STR_0, stackTrace.getElement(0).getClassName());
        Assert.assertEquals(STR_1, stackTrace.getElement(0).getMethodName());
        Assert.assertEquals(STR_2, stackTrace.getElement(1).getClassName());
        Assert.assertEquals(STR_3, stackTrace.getElement(1).getMethodName());
    }
}
