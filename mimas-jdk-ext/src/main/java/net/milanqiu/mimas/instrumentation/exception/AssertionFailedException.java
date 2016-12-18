package net.milanqiu.mimas.instrumentation.exception;

/**
 * {@code AssertionFailedException} is thrown when an assertion fails.
 * <p>
 * Creation Date: 2016-12-18
 * @author Milan Qiu
 */
public class AssertionFailedException extends RuntimeException {
    /**
     * Constructs a new {@code AssertionFailedException} with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public AssertionFailedException() {
        super();
    }

    /**
     * Constructs a new {@code AssertionFailedException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     */
    public AssertionFailedException(String message) {
        super(message);
    }
}
