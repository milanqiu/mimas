package net.milanqiu.mimas.guava.base;

import com.google.common.base.Throwables;
import net.milanqiu.mimas.config.MimasTplTrialProjectConfig;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 * Creation Date: 2014-07-18
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
                    "net.milanqiu.mimas.guava.base.ThrowablesTest$ExceptionC: " +
                    "net.milanqiu.mimas.guava.base.ThrowablesTest$ExceptionB: " +
                    "net.milanqiu.mimas.guava.base.ThrowablesTest$ExceptionA"));
            MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDirUsingUtf8(Throwables.getStackTraceAsString(e));
        }
    }
}
