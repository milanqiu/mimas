package net.milanqiu.mimas.string;

/**
 * {@code StringNotFoundException} is thrown when expected content is not found in string.
 * <p>
 * Creation Date: 2020-03-08
 * @author Milan Qiu
 */
public class StringNotFoundException extends RuntimeException {
    /**
     * Constructs a new {@code StringNotFoundException} with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public StringNotFoundException() {
        super();
    }

    /**
     * Constructs a new {@code StringNotFoundException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     */
    public StringNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code StringNotFoundException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     * @param messageFormat the detail message format which is saved for later retrieval by the {@link #getMessage()} method
     * @param messageArgs the detail message arguments which is saved for later retrieval by the {@link #getMessage()} method
     */
    public StringNotFoundException(String messageFormat, Object... messageArgs) {
        super(String.format(messageFormat, messageArgs));
    }

    /**
     * Constructs a new {@code StringNotFoundException} with the specified detail message and cause.
     * Note that the detail message associated with {@code cause} is NOT automatically incorporated in this
     * exception's detail message.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public StringNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code StringNotFoundException} with the specified cause and a detail message of
     * <i><tt>(cause==null ? null : cause.toString())</tt></i> (which typically contains the class and detail message
     * of {@code cause}).
     * This constructor is useful for exceptions that are little more than wrappers for other throwables.
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public StringNotFoundException(Throwable cause) {
        super(cause);
    }
}
