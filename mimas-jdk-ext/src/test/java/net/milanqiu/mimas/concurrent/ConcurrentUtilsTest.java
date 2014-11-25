package net.milanqiu.mimas.concurrent;

import net.milanqiu.mimas.instrumentation.exception.DeliberateException;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

/**
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class ConcurrentUtilsTest {

    @Test
    public void test_blockUncaughtExceptions_resetUncaughtExceptionHandler() throws Exception {
        ConcurrentUtils.blockUncaughtExceptions();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // the exception will NOT be passed to main thread and printed
                throw new DeliberateException();
            }
        }).start();
        TimeUnit.MILLISECONDS.sleep(10);

        ConcurrentUtils.resetUncaughtExceptionHandler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // if remove the comment mark, the exception will be passed to main thread and printed
                //throw new DeliberateException();
            }
        }).start();
        TimeUnit.MILLISECONDS.sleep(10);
    }
}
