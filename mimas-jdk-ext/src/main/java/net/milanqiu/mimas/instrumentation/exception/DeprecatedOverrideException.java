package net.milanqiu.mimas.instrumentation.exception;

/**
 * When a subclass intends to remove a method implemented in superclass, it should override this method,
 * add a {@code Deprecated} annotation and throw a {@code DeprecatedOverrideException} to prevent invocations.
 * <p>
 * Creation Date: 2014-2-8
 * @author Milan Qiu
 */
public class DeprecatedOverrideException extends UnsupportedOperationException {
    /**
     * Constructs a new {@code DeprecatedOverrideException} with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public DeprecatedOverrideException() {
        super();
    }

    /**
     * Constructs a new {@code DeprecatedOverrideException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     */
    public DeprecatedOverrideException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code DeprecatedOverrideException} with the specified detail message and cause.
     * Note that the detail message associated with {@code cause} is NOT automatically incorporated in this
     * exception's detail message.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public DeprecatedOverrideException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code DeprecatedOverrideException} with the specified cause and a detail message of
     * <i><tt>(cause==null ? null : cause.toString())</tt></i> (which typically contains the class and detail message
     * of {@code cause}).
     * This constructor is useful for exceptions that are little more than wrappers for other throwables.
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public DeprecatedOverrideException(Throwable cause) {
        super(cause);
    }
}
