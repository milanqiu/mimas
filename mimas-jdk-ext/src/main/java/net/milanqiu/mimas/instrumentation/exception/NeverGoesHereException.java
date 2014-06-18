package net.milanqiu.mimas.instrumentation.exception;

/**
 * {@code NeverGoesHereException} is placed on where the program will never go logically.
 * So if a {@code NeverGoesHereException} is thrown in runtime, it means the program has bug and the running is wrong.
 *
 * <p>Creation Date: 2014-6-18
 * @author Milan Qiu
 */
public class NeverGoesHereException extends RuntimeException {
    /**
     * Constructs a new never-goes-here exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public NeverGoesHereException() {
        super();
    }

    /**
     * Constructs a new never-goes-here exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public NeverGoesHereException(String message) {
        super(message);
    }
}
