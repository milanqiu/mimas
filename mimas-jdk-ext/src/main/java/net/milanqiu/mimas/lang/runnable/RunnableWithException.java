package net.milanqiu.mimas.lang.runnable;

/**
 * Likes {@link java.lang.Runnable}, but may throw an exception when running.
 * <p>
 * Creation Date: 2020-02-19
 * @author Milan Qiu
 */
@FunctionalInterface
public interface RunnableWithException {

    /**
     * May take any action whatsoever, allowing exception thrown.
     * @throws Exception any type of exception thrown out
     */
    void run() throws Exception;
}
