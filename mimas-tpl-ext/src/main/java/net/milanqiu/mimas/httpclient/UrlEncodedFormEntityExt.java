package net.milanqiu.mimas.httpclient;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * An extension of {@link org.apache.http.client.entity.UrlEncodedFormEntity} to provide more utilities.
 * <p>
 * Creation Date: 2022-12-20
 * @author Milan Qiu
 */
public class UrlEncodedFormEntityExt {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private UrlEncodedFormEntityExt() {}

    /**
     * Creates and returns a new instance of {@code UrlEncodedFormEntity} with the specified parameters.
     * @param charset the character set to encode entity
     * @param parameterNameValues the sequence of name and value of parameters to encode entity
     * @return the new instance of {@code UrlEncodedFormEntity}
     */
    public static UrlEncodedFormEntity create(Charset charset, String... parameterNameValues) {
        if ((parameterNameValues.length & 0x1) == 0x1) {
            throw new IllegalArgumentException("the length of parameterNameValues should be even");
        }
        List<NameValuePair> parameters = new ArrayList<>();
        for (int i = 0; i < parameterNameValues.length>>1; i++) {
            parameters.add(new BasicNameValuePair(parameterNameValues[i<<1], parameterNameValues[(i<<1)+1]));
        }
        return new UrlEncodedFormEntity(parameters, charset);
    }

    /**
     * Creates and returns a new instance of {@code UrlEncodedFormEntity} with the specified parameters.
     * @param charset the character set to encode entity
     * @param parameterNameValues the map of name and value of parameters to encode entity
     * @return the new instance of {@code UrlEncodedFormEntity}
     */
    public static UrlEncodedFormEntity create(Charset charset, Map<String, String> parameterNameValues) {
        List<NameValuePair> parameters = new ArrayList<>();
        parameterNameValues.entrySet().forEach(entry -> {
            parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        });
        return new UrlEncodedFormEntity(parameters, charset);
    }

    /**
     * Creates and returns a new instance of {@code UrlEncodedFormEntity} with the specified parameters, using the UTF-8 character set.
     * @param parameterNameValues the sequence of name and value of parameters to encode entity
     * @return the new instance of {@code UrlEncodedFormEntity}
     */
    public static UrlEncodedFormEntity createUsingUtf8(String... parameterNameValues) {
        return create(StandardCharsets.UTF_8, parameterNameValues);
    }

    /**
     * Creates and returns a new instance of {@code UrlEncodedFormEntity} with the specified parameters, using the UTF-8 character set.
     * @param parameterNameValues the map of name and value of parameters to encode entity
     * @return the new instance of {@code UrlEncodedFormEntity}
     */
    public static UrlEncodedFormEntity createUsingUtf8(Map<String, String> parameterNameValues) {
        return create(StandardCharsets.UTF_8, parameterNameValues);
    }
}
