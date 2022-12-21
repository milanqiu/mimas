package net.milanqiu.mimas.httpclient;

import net.milanqiu.mimas.concurrent.ConcurrentUtils;
import net.milanqiu.mimas.instrumentation.exception.CodeContextException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * Utilities related to http client.
 * <p>
 * Creation Date: 2022-12-20
 * @author Milan Qiu
 */
public class HttpClientUtils {
    /**
     * Constant class is forbidden to be instantiated.
     */
    private HttpClientUtils() {}

    /**
     * The global static {@code HttpClientBuilder} object.
     */
    private static HttpClientBuilder hcb = HttpClientBuilder.create();

    /**
     * Sets retry strategy in all following http client executions.
     * @param retryTimes maximum retry times
     * @param sleepInterval sleep interval after each retry
     */
    public static void setRetryStrategy(int retryTimes, int sleepInterval) {
        hcb.setRetryHandler(new HttpRequestRetryHandler() {
            @Override
            public boolean retryRequest(IOException e, int i, HttpContext httpContext) {
                if (i >= retryTimes) {
                    return false;
                }
                ConcurrentUtils.sleepSafely(sleepInterval);
                return true;
            }
        });
    }

    /**
     * Clears retry strategy in all following http client executions.
     */
    public static void clearRetryStrategy() {
        hcb.setRetryHandler(null);
    }

    private static TrustManager[] getTrustManagers() {
        TrustManager tm = new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };
        return new TrustManager[] {tm};
    }

    /**
     * Registers to support HTTPS server.
     */
    public static void registerHttps() {
        try {
            SSLContext sc = SSLContext.getInstance("TLS");
            sc.init(null, getTrustManagers(), new SecureRandom());
            SSLConnectionSocketFactory scsf = new SSLConnectionSocketFactory(sc, NoopHostnameVerifier.INSTANCE);

            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", scsf)
                    .build();
            hcb.setConnectionManager(new PoolingHttpClientConnectionManager(registry));
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            throw new CodeContextException(e);
        }
    }

    /**
     * Executes a http get.
     * @param uri the URI to get
     * @param responseHandler the handler to handle http response
     * @param <T> the result type of http response handler
     * @return the result of http response handler
     * @throws IOException if an I/O error occurs
     */
    public static <T> T get(String uri, ResponseHandler<? extends T> responseHandler) throws IOException {
        CloseableHttpClient hc = hcb.build();
        HttpGet hg = new HttpGet(uri);
        return hc.execute(hg, responseHandler);
    }

    /**
     * Executes a http get and returns the content string when http status code is 2XX.
     * @param uri the URI to get
     * @return the content string when http status code is 2XX
     * @throws IOException if an I/O error occurs, or http status code is not 2XX
     */
    public static String getOkString(String uri) throws IOException {
        return get(uri, new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                if (httpResponse.getStatusLine().getStatusCode() >= 200 && httpResponse.getStatusLine().getStatusCode() < 300) {
                    return EntityUtils.toString(httpResponse.getEntity());
                } else {
                    throw new HttpStatusCodeException(httpResponse.getStatusLine().getStatusCode());
                }
            }
        });
    }

    /**
     * Executes a http get and returns the content string when http status code is 2XX.
     * If the content type doesn't include character set, it will use the specified default character set to encode the content string.
     * @param uri the URI to get
     * @param defaultCharset the default character set to encode the content string
     * @return the content string when http status code is 2XX
     * @throws IOException if an I/O error occurs, or http status code is not 2XX
     */
    public static String getOkString(String uri, Charset defaultCharset) throws IOException {
        return get(uri, new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                if (httpResponse.getStatusLine().getStatusCode() >= 200 && httpResponse.getStatusLine().getStatusCode() < 300) {
                    return EntityUtils.toString(httpResponse.getEntity(), defaultCharset);
                } else {
                    throw new HttpStatusCodeException(httpResponse.getStatusLine().getStatusCode());
                }
            }
        });
    }

    /**
     * Executes a http get and returns the content string when http status code is 2XX.
     * If the content type doesn't include character set, it will use UTF-8 as default character set to encode the content string.
     * @param uri the URI to get
     * @return the content string when http status code is 2XX
     * @throws IOException if an I/O error occurs, or http status code is not 2XX
     */
    public static String getOkStringUsingUtf8AsDefault(String uri) throws IOException {
        return getOkString(uri, StandardCharsets.UTF_8);
    }

    /**
     * Executes a http get and returns the content byte array when http status code is 2XX.
     * @param uri the URI to get
     * @return the content byte array when http status code is 2XX
     * @throws IOException if an I/O error occurs, or http status code is not 2XX
     */
    public static byte[] getOkByteArray(String uri) throws IOException {
        return get(uri, new ResponseHandler<byte[]>() {
            @Override
            public byte[] handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                if (httpResponse.getStatusLine().getStatusCode() >= 200 && httpResponse.getStatusLine().getStatusCode() < 300) {
                    return EntityUtils.toByteArray(httpResponse.getEntity());
                } else {
                    throw new HttpStatusCodeException(httpResponse.getStatusLine().getStatusCode());
                }
            }
        });
    }

    /**
     * Executes a http post.
     * @param uri the URI to post
     * @param formEntity the form entity for the http post
     * @param responseHandler the handler to handle http response
     * @param <T> the result type of http response handler
     * @return the result of http response handler
     * @throws IOException if an I/O error occurs
     */
    public static <T> T post(String uri, UrlEncodedFormEntity formEntity, ResponseHandler<? extends T> responseHandler) throws IOException {
        CloseableHttpClient hc = hcb.build();
        HttpPost hp = new HttpPost(uri);
        hp.setEntity(formEntity);
        return hc.execute(hp, responseHandler);
    }

    /**
     * Executes a http post and returns the content string when http status code is 2XX.
     * @param uri the URI to post
     * @param formEntity the form entity for the http post
     * @return the content string when http status code is 2XX
     * @throws IOException if an I/O error occurs, or http status code is not 2XX
     */
    public static String postOkString(String uri, UrlEncodedFormEntity formEntity) throws IOException {
        return post(uri, formEntity, new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                if (httpResponse.getStatusLine().getStatusCode() >= 200 && httpResponse.getStatusLine().getStatusCode() < 300) {
                    return EntityUtils.toString(httpResponse.getEntity());
                } else {
                    throw new HttpStatusCodeException(httpResponse.getStatusLine().getStatusCode());
                }
            }
        });
    }

    /**
     * Executes a http post and returns the content string when http status code is 2XX.
     * If the content type doesn't include character set, it will use the specified default character set to encode the content string.
     * @param uri the URI to post
     * @param formEntity the form entity for the http post
     * @param defaultCharset the default character set to encode the content string
     * @return the content string when http status code is 2XX
     * @throws IOException if an I/O error occurs, or http status code is not 2XX
     */
    public static String postOkString(String uri, UrlEncodedFormEntity formEntity, Charset defaultCharset) throws IOException {
        return post(uri, formEntity, new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                if (httpResponse.getStatusLine().getStatusCode() >= 200 && httpResponse.getStatusLine().getStatusCode() < 300) {
                    return EntityUtils.toString(httpResponse.getEntity(), defaultCharset);
                } else {
                    throw new HttpStatusCodeException(httpResponse.getStatusLine().getStatusCode());
                }
            }
        });
    }

    /**
     * Executes a http post and returns the content string when http status code is 2XX.
     * If the content type doesn't include character set, it will use UTF-8 as default character set to encode the content string.
     * @param uri the URI to post
     * @param formEntity the form entity for the http post
     * @return the content string when http status code is 2XX
     * @throws IOException if an I/O error occurs, or http status code is not 2XX
     */
    public static String postOkStringUsingUtf8AsDefault(String uri, UrlEncodedFormEntity formEntity) throws IOException {
        return postOkString(uri, formEntity, StandardCharsets.UTF_8);
    }

    /**
     * Executes a http post and returns the content byte array when http status code is 2XX.
     * @param uri the URI to post
     * @param formEntity the form entity for the http post
     * @return the content byte array when http status code is 2XX
     * @throws IOException if an I/O error occurs, or http status code is not 2XX
     */
    public static byte[] postOkByteArray(String uri, UrlEncodedFormEntity formEntity) throws IOException {
        return post(uri, formEntity, new ResponseHandler<byte[]>() {
            @Override
            public byte[] handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                if (httpResponse.getStatusLine().getStatusCode() >= 200 && httpResponse.getStatusLine().getStatusCode() < 300) {
                    return EntityUtils.toByteArray(httpResponse.getEntity());
                } else {
                    throw new HttpStatusCodeException(httpResponse.getStatusLine().getStatusCode());
                }
            }
        });
    }
}
