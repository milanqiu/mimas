package net.milanqiu.mimas.junit;

import org.junit.Assume;
import org.junit.Test;

import java.io.IOException;

/**
 * Creation Date: 2014-07-08
 * @author Milan Qiu
 */
public class AssertExtTest {

    private static final boolean WILL_EXECUTE_FAILED_ASSERTION = false;

    @Test
    public void test_assertClassification() throws Exception {
        AssertExt.assertClassification(Integer.class, 123);
        AssertExt.assertClassification(String.class, "abc");
        AssertExt.assertClassification(IndexOutOfBoundsException.class, new IndexOutOfBoundsException());
        AssertExt.assertClassification(IndexOutOfBoundsException.class, new ArrayIndexOutOfBoundsException());
    }

    @Test
    public void test_assertClassification_fail() throws Exception {
        Assume.assumeTrue(WILL_EXECUTE_FAILED_ASSERTION);
        AssertExt.assertClassification(Integer.class, "abc");
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
        AssertExt.assertDefault(0.0f);
        AssertExt.assertDefault(0.0d);
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
    public void test_assertEmpty_fail() throws Exception {
        Assume.assumeTrue(WILL_EXECUTE_FAILED_ASSERTION);
        AssertExt.assertEmpty("abc");
    }

    @Test
    public void test_assertNullOrEmpty() throws Exception {
        AssertExt.assertNullOrEmpty(null);
        AssertExt.assertNullOrEmpty("");
    }

    @Test
    public void test_assertNullOrEmpty_fail() throws Exception {
        Assume.assumeTrue(WILL_EXECUTE_FAILED_ASSERTION);
        AssertExt.assertNullOrEmpty("abc");
    }

    private static class A {}

    private static class B extends A {
        @Override
        public String toString() {
            return super.toString();
        }
    }

    private static class C extends B {}

    @Test
    public void test_assertHasCustomToString() throws Exception {
        AssertExt.assertHasCustomToString(new Object());
        AssertExt.assertHasCustomToString(new B());
    }

    @Test
    public void test_assertHasCustomToString_fail() throws Exception {
        Assume.assumeTrue(WILL_EXECUTE_FAILED_ASSERTION);
        AssertExt.assertHasCustomToString(new A());
    }

    @Test
    public void test_assertHasCustomToString_fail2() throws Exception {
        Assume.assumeTrue(WILL_EXECUTE_FAILED_ASSERTION);
        AssertExt.assertHasCustomToString(new C());
    }

    @Test
    public void test_assertExceptionThrown() throws Exception {
        Exception cause = new ArithmeticException();

        // void assertExceptionThrown(RunnableWithExceptionForAssert runnable, Class<? extends Throwable> exceptionClazz)
        AssertExt.assertExceptionThrown(() -> { Integer n = null; n.toString(); }, NullPointerException.class);
        AssertExt.assertExceptionThrown(() -> { int n = 1/0; }, ArithmeticException.class);

        // void assertExceptionThrown(RunnableWithExceptionForAssert runnable, Class<? extends Throwable> exceptionClazz,
        //                            String exceptionMessage)
        AssertExt.assertExceptionThrown(() -> { int n = 1/0; }, ArithmeticException.class, "/ by zero");

        // void assertExceptionThrown(RunnableWithExceptionForAssert runnable, Class<? extends Throwable> exceptionClazz,
        //                            String exceptionMessage, Throwable exceptionCause)
        AssertExt.assertExceptionThrown(() -> {throw new IOException("msg", cause); }, Exception.class, "msg", cause);

        // void assertExceptionThrown(RunnableWithExceptionForAssert runnable, Class<? extends Throwable> exceptionClazz,
        //                            Throwable exceptionCause)
        AssertExt.assertExceptionThrown(() -> {throw new IOException("msg", cause); }, Exception.class, cause);
    }

    @Test
    public void test_assertExceptionThrown_fail() throws Exception {
        Assume.assumeTrue(WILL_EXECUTE_FAILED_ASSERTION);
        AssertExt.assertExceptionThrown(() -> { int n = 1/0; }, NullPointerException.class);
    }

    @Test
    public void test_assertExceptionThrown_fail2() throws Exception {
        Assume.assumeTrue(WILL_EXECUTE_FAILED_ASSERTION);
        AssertExt.assertExceptionThrown(() -> {}, Exception.class);
    }

    @Test
    public void test_assertExceptionThrown_fail3() throws Exception {
        Assume.assumeTrue(WILL_EXECUTE_FAILED_ASSERTION);
        AssertExt.assertExceptionThrown(() -> { int n = 1/0; }, ArithmeticException.class, "null pointer");
    }

    @Test
    public void test_assertExceptionThrown_fail4() throws Exception {
        Assume.assumeTrue(WILL_EXECUTE_FAILED_ASSERTION);
        Exception cause = new ArithmeticException();
        AssertExt.assertExceptionThrown(() -> {throw new IOException("msg", cause); }, Exception.class, new NullPointerException());
    }
}