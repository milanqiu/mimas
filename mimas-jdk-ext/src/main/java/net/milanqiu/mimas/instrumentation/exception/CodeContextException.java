package net.milanqiu.mimas.instrumentation.exception;

import java.io.UnsupportedEncodingException;

/**
 * {@code CodeContextException} is thrown when program code is not supported by context.
 * <p>
 * Usually, it may occur for one of the following two reasons:
 * <ul>
 *   <li>the code is wrong</li>
 *   <li>the context is absent or wrong</li>
 * </ul>
 * <p>
 * It is often caused by {@link java.lang.ClassNotFoundException} or {@link java.io.UnsupportedEncodingException}.
 * Actually, it provides a way to convert these exceptions to unchecked exceptions which they should be.
 * <p>
 * Creation Date: 2014-11-03
 * @author Milan Qiu
 */
public class CodeContextException extends RuntimeException {
    /**
     * Constructs a new {@code CodeContextException} with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public CodeContextException() {
        super();
    }

    /**
     * Constructs a new {@code CodeContextException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     */
    public CodeContextException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CodeContextException} with the specified detail message and cause.
     * Note that the detail message associated with {@code cause} is NOT automatically incorporated in this
     * exception's detail message.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public CodeContextException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code CodeContextException} with the specified cause and a detail message of
     * <i><tt>(cause==null ? null : cause.toString())</tt></i> (which typically contains the class and detail message
     * of {@code cause}).
     * This constructor is useful for exceptions that are little more than wrappers for other throwables.
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public CodeContextException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code CodeContextException} with the specified {@code ClassNotFoundException} as its
     * cause and a detail message of <i><tt>(cause==null ? null : cause.toString())</tt></i> (which typically contains
     * the class and detail message of {@code cause}).
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public CodeContextException(ClassNotFoundException cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code CodeContextException} with the specified {@code UnsupportedEncodingException} as its
     * cause and a detail message of <i><tt>(cause==null ? null : cause.toString())</tt></i> (which typically contains
     * the class and detail message of {@code cause}).
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public CodeContextException(UnsupportedEncodingException cause) {
        super(cause);
    }
}
