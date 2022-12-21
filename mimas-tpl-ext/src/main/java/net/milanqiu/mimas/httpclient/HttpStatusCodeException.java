package net.milanqiu.mimas.httpclient;

import java.io.IOException;

/**
 * {@code HttpStatusCodeException} is thrown when http status code is not 2XX.
 * <p>
 * Creation Date: 2022-12-20
 * @author Milan Qiu
 */
public class HttpStatusCodeException extends IOException {

    /**
     * Constructs a new {@code HttpStatusCodeException} with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     */
    public HttpStatusCodeException() {
        super();
    }

    /**
     * Constructs a new {@code HttpStatusCodeException} with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     */
    public HttpStatusCodeException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code HttpStatusCodeException} with the specified detail message and cause.
     * Note that the detail message associated with {@code cause} is NOT automatically incorporated in this
     * exception's detail message.
     * @param message the detail message which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public HttpStatusCodeException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new {@code HttpStatusCodeException} with the specified cause and a detail message of
     * <i><tt>(cause==null ? null : cause.toString())</tt></i> (which typically contains the class and detail message
     * of {@code cause}).
     * This constructor is useful for exceptions that are little more than wrappers for other throwables.
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public HttpStatusCodeException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructs a new {@code HttpStatusCodeException} with the specified detail message filled by http status code.
     * The cause is not initialized, and may subsequently be initialized by a call to {@link #initCause}.
     * @param httpStatusCode fills the detail message which is saved for later retrieval by the {@link #getMessage()} method
     */
    public HttpStatusCodeException(int httpStatusCode) {
        super(Integer.toString(httpStatusCode));
    }

    /**
     * Constructs a new {@code HttpStatusCodeException} with the specified detail message filled by http status code and cause.
     * Note that the detail message associated with {@code cause} is NOT automatically incorporated in this
     * exception's detail message.
     * @param httpStatusCode fills the detail message which is saved for later retrieval by the {@link #getMessage()} method
     * @param cause the cause which is saved for later retrieval by the {@link #getCause()} method
     */
    public HttpStatusCodeException(int httpStatusCode, Throwable cause) {
        super(Integer.toString(httpStatusCode), cause);
    }
}
