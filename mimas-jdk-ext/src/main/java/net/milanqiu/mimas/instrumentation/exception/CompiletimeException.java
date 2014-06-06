package net.milanqiu.mimas.instrumentation.exception;

/**
 * {@code CompiletimeException} is the superclass of those exceptions that prevent compiler from passing.
 * That means, {@code CompiletimeException} shouldn't appear in runtime.
 * If a function throws {@code CompiletimeException}, it tells "NEVER CALL ME!".
 *
 * <p>Creation Date: 2014-2-8
 * @author Milan Qiu
 */
public class CompiletimeException extends Exception {
    /**
     * Constructs a new compile-time exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public CompiletimeException() {
        super();
    }

    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public CompiletimeException(String message) {
        super(message);
    }
}
