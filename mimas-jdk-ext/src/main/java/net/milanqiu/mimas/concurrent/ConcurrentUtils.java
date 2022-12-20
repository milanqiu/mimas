package net.milanqiu.mimas.concurrent;

/**
 * Utilities related to concurrent.
 * <p>
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class ConcurrentUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private ConcurrentUtils() {}

    private static final Thread.UncaughtExceptionHandler SYSTEM_DEFAULT_UNCAUGHT_EXCEPTION_HANDLER = Thread.getDefaultUncaughtExceptionHandler();

    /**
     * Blocks all uncaught exceptions of child threads from passing to main thread.
     * It will assign an idle method to default uncaught exception handler of all child threads.
     */
    public static void blockUncaughtExceptions() {
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            @Override
            public void uncaughtException(Thread t, Throwable e) {
                // do nothing
            }
        });
    }

    /**
     * Unblocks all uncaught exceptions of child threads by passing to main thread.
     * It will reset default uncaught exception handler of all child threads to system default.
     * Actually, only {@link java.lang.Error} and {@link java.lang.RuntimeException} can be passed to main thread.
     */
    public static void unblockUncaughtExceptions() {
        Thread.setDefaultUncaughtExceptionHandler(SYSTEM_DEFAULT_UNCAUGHT_EXCEPTION_HANDLER);
    }

    /**
     * Causes the currently executing thread to sleep. Resets the interrupted status when {@link java.lang.InterruptedException} is thrown.
     * @param millis the length of time to sleep in milliseconds
     */
    public static void sleepSafely(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Causes the currently executing thread to sleep. Resets the interrupted status when {@link java.lang.InterruptedException} is thrown.
     * @param millis the length of time to sleep in milliseconds
     * @param nanos {@code 0-999999} additional nanoseconds to sleep
     */
    public static void sleepSafely(long millis, int nanos) {
        try {
            Thread.sleep(millis, nanos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
