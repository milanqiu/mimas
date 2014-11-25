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
     * It will assign an idle method to default uncaught exception handlers of all child threads.
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
     * Resets default uncaught exception handlers of all child threads to system default.
     * It would unblock all uncaught exceptions of child threads from passing to main thread.
     * Actually, only {@link java.lang.Error} and {@link java.lang.RuntimeException} can be passed to main thread.
     */
    public static void resetUncaughtExceptionHandler() {
        Thread.setDefaultUncaughtExceptionHandler(SYSTEM_DEFAULT_UNCAUGHT_EXCEPTION_HANDLER);
    }
}
