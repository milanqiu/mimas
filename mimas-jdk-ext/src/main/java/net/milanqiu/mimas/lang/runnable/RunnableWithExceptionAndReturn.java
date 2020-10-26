package net.milanqiu.mimas.lang.runnable;

/**
 * Likes {@link java.lang.Runnable}, but may throw an exception and return a value when running.
 * <p>
 * Creation Date: 2020-02-19
 * @author Milan Qiu
 */
@FunctionalInterface
public interface RunnableWithExceptionAndReturn<T> {

    /**
     * May take any action whatsoever, allowing exception thrown and returning a value.
     * @return the value to be returned
     * @throws Exception any type of exception thrown out
     */
    T run() throws Exception;
}
