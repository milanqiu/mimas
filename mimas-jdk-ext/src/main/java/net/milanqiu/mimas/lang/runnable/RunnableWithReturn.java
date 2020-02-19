package net.milanqiu.mimas.lang.runnable;

/**
 * Likes {@link java.lang.Runnable}, but returns a value when running.
 * <p>
 * Creation Date: 2020-02-19
 * @author Milan Qiu
 */
@FunctionalInterface
public interface RunnableWithReturn<T> {

    /**
     * May take any action whatsoever, returning a value.
     * @return the value to be returned
     */
     T run();
}
