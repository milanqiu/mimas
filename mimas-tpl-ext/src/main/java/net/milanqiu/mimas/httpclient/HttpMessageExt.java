package net.milanqiu.mimas.httpclient;

import org.apache.http.Header;
import org.apache.http.HeaderIterator;
import org.apache.http.HttpMessage;

/**
 * An extension of {@link org.apache.http.HttpMessage} to provide more utilities.
 * <p>
 * Creation Date: 2022-12-21
 * @author Milan Qiu
 */
public class HttpMessageExt {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private HttpMessageExt() {}

    /**
     * Returns a string representation of all headers of the specified {@code HttpMessage} object.
     * @param message the {@code HttpMessage} object to be represented
     * @return a string representation of all headers of the specified {@code HttpMessage} object
     */
    public static String getAllHeadersStr(HttpMessage message) {
        StringBuilder sb = new StringBuilder();
        HeaderIterator hi = message.headerIterator();
        while (hi.hasNext()) {
            Header h = hi.nextHeader();
            sb.append(h.getName()).append(": ").append(h.getValue()).append(System.lineSeparator());
        }
        return sb.toString();
    }
}
