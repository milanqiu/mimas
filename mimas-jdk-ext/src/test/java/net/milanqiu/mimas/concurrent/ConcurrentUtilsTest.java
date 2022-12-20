package net.milanqiu.mimas.concurrent;

import net.milanqiu.mimas.instrumentation.exception.DeliberateException;
import org.junit.Assert;
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
        TimeUnit.MILLISECONDS.sleep(10);
        new Thread(() -> {
            // the exception will NOT be passed to main thread and printed
            throw new DeliberateException();
        }).start();
        TimeUnit.MILLISECONDS.sleep(10);

        ConcurrentUtils.unblockUncaughtExceptions();
        TimeUnit.MILLISECONDS.sleep(10);
        new Thread(() -> {
            // if remove the comment mark, the exception will be passed to main thread and printed
            //throw new DeliberateException();
        }).start();
        TimeUnit.MILLISECONDS.sleep(10);
    }

    @Test
    public void test_sleepSafely() throws Exception {
        // void sleepSafely(long millis)
        Thread thread = new Thread(() -> {
            ConcurrentUtils.sleepSafely(500);
            Assert.assertTrue(Thread.currentThread().isInterrupted());
        });
        thread.start();
        thread.interrupt();
        Thread.sleep(1000);
    }
}
