package net.milanqiu.mimas.guava;

import com.google.common.base.Throwables;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * <p>Creation Date: 2014-7-18
 * @author Milan Qiu
 */
public class ThrowablesTest {

    private class ExceptionA extends Exception {
    }

    private class ExceptionB extends Exception {
        public ExceptionB(Throwable cause) {
            super(cause);
        }
    }

    private class ExceptionC extends Exception {
        public ExceptionC(Throwable cause) {
            super(cause);
        }
    }

    @Test
    public void test_getRootCause_getCausalChain_getStackTraceAsString() throws Exception {
        ExceptionA ea = new ExceptionA();
        ExceptionB eb = new ExceptionB(ea);
        ExceptionC ec = new ExceptionC(eb);
        try {
            throw ec;
        } catch (Exception e) {
            Assert.assertEquals(ec, e);
            Assert.assertEquals(ea, Throwables.getRootCause(e));
            Assert.assertTrue(Throwables.getCausalChain(e).equals(Arrays.asList(ec, eb, ea)));
            Assert.assertTrue(Throwables.getStackTraceAsString(e).startsWith(
                    "net.milanqiu.mimas.guava.ThrowablesTest$ExceptionC: " +
                    "net.milanqiu.mimas.guava.ThrowablesTest$ExceptionB: " +
                    "net.milanqiu.mimas.guava.ThrowablesTest$ExceptionA"));
            //System.out.println(Throwables.getStackTraceAsString(e));
        }
    }
}
