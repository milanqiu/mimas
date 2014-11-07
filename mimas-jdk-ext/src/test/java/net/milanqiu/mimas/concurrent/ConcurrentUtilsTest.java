package net.milanqiu.mimas.concurrent;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * <p>Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class ConcurrentUtilsTest {

    @Test
    public void test_blockUncaughtExceptions_unblockUncaughtExceptions() throws Exception {
        ConcurrentUtils.blockUncaughtExceptions();
        new Thread(new Runnable() {
            @Override
            public void run() {
                throw new RuntimeException("Deliberate Exception"); // the exception will NOT be printed
            }
        }).start();
        TimeUnit.MILLISECONDS.sleep(10);
        ConcurrentUtils.resetUncaughtExceptionHandler();
    }
}
