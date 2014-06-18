package net.milanqiu.mimas.instrumentation.exception;

/**
 * When a subclass intends to remove a method implemented in superclass, it should override this method,
 * add a Deprecated annotation and throw a {@code DeprecatedOverrideException} to prevent caller.
 *
 * <p>Creation Date: 2014-2-8
 * @author Milan Qiu
 */
public class DeprecatedOverrideException extends UnsupportedOperationException {
    /**
     * Constructs a new deprecated-override exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public DeprecatedOverrideException() {
        super();
    }

    /**
     * Constructs a new deprecated-override exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public DeprecatedOverrideException(String message) {
        super(message);
    }
}
