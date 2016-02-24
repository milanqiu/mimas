package net.milanqiu.mimas.instrumentation.exception;

/**
 * {@code NeverGoesHereException} is placed on where the program will never go logically.
 * <p>
 * So if a {@code NeverGoesHereException} is thrown in runtime, it means the program has bug and the running is wrong.
 * <p>
 * Creation Date: 2014-06-18
 * @author Milan Qiu
 */
public class NeverGoesHereException extends RuntimeException {
    /**
     * Constructs a new {@code NeverGoesHereException} with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public NeverGoesHereException() {
        super();
    }

    /**
     * Constructs a new {@code NeverGoesHereException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     */
    public NeverGoesHereException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code NeverGoesHereException} with the specified detail message and cause.
     * Note that the detail message associated with {@code cause} is NOT automatically incorporated in this
     * exception's detail message.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public NeverGoesHereException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code NeverGoesHereException} with the specified cause and a detail message of
     * <i><tt>(cause==null ? null : cause.toString())</tt></i> (which typically contains the class and detail message
     * of {@code cause}).
     * This constructor is useful for exceptions that are little more than wrappers for other throwables.
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public NeverGoesHereException(Throwable cause) {
        super(cause);
    }
}
