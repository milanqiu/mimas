package net.milanqiu.mimas.instrumentation.exception;

/**
 * {@code CompiletimeException} is the superclass of those exceptions that prevent compiler from passing.
 * <p>
 * That means, {@code CompiletimeException} shouldn't appear in runtime.
 * <p>
 * If a method throws {@code CompiletimeException}, it is shouting "NEVER CALL ME!".
 * <p>
 * Creation Date: 2014-2-8
 * @author Milan Qiu
 */
public class CompiletimeException extends Error {
    /**
     * Constructs a new {@code CompiletimeException} with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public CompiletimeException() {
        super();
    }

    /**
     * Constructs a new {@code CompiletimeException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     */
    public CompiletimeException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CompiletimeException} with the specified detail message and cause.
     * Note that the detail message associated with {@code cause} is NOT automatically incorporated in this
     * exception's detail message.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public CompiletimeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code CompiletimeException} with the specified cause and a detail message of
     * <i><tt>(cause==null ? null : cause.toString())</tt></i> (which typically contains the class and detail message
     * of {@code cause}).
     * This constructor is useful for exceptions that are little more than wrappers for other throwables.
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public CompiletimeException(Throwable cause) {
        super(cause);
    }
}
