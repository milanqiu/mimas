package net.milanqiu.mimas.instrumentation.exception;

import java.io.UnsupportedEncodingException;

/**
 * {@code CodeContextException} is thrown when the code is not supported by context.
 *
 * <p>Usually, it may occur for one of the following two reasons:
 * <ul>
 *   <li>the code is wrong</li>
 *   <li>the context is absent</li>
 * </ul>
 *
 * <p>It is often caused by {@link java.lang.ClassNotFoundException} or {@link java.io.UnsupportedEncodingException}.
 * Actually, it provides a way to convert these exceptions to unchecked exceptions which they should be.
 *
 * <p>Creation Date: 2014-11-3
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
     *
     * @param message the detail message. The detail message is saved for later retrieval by the {@link #getMessage()} method.
     */
    public CodeContextException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code CodeContextException}n with the specified cause and a detail message of
     * <tt>(cause==null ? null : cause.toString())</tt> (which typically contains the class and detail message
     * of <tt>cause</tt>).
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public CodeContextException(ClassNotFoundException cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code CodeContextException}n with the specified cause and a detail message of
     * <tt>(cause==null ? null : cause.toString())</tt> (which typically contains the class and detail message
     * of <tt>cause</tt>).
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method)
     */
    public CodeContextException(UnsupportedEncodingException cause) {
        super(cause);
    }
}
