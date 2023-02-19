package net.milanqiu.mimas.httpclient;

import com.google.common.io.ByteStreams;
import net.milanqiu.mimas.config.MimasTplExtProjectConfig;
import net.milanqiu.mimas.instrumentation.TestConsts;
import net.milanqiu.mimas.junit.AssertExt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;

/**
 * Creation Date: 2022-12-20
 * @author Milan Qiu
 */
public class HttpClientUtilsTest {

    private UrlEncodedFormEntity formEntity;

    @Before
    public void setUp() throws Exception {
        formEntity = UrlEncodedFormEntityExt.createUsingUtf8(
                TestConsts.PARAM1_NAME_POST, TestConsts.PARAM1_VALUE_POST,
                TestConsts.PARAM2_NAME_POST, TestConsts.PARAM2_VALUE_POST);
    }

    @Test
    public void test_setRetryStrategy_clearRetryStrategy() throws Exception {
        Log log = LogFactory.getLog(this.getClass());

        log.info("HttpClientUtils.setRetryStrategy(3, 500)");
        HttpClientUtils.setRetryStrategy(3, 500);
        log.info("HttpClientUtils.getOkString(TestConsts.URL_FAKE_PAGE)");
        AssertExt.assertExceptionThrown(() -> HttpClientUtils.getOkString(TestConsts.URL_FAKE_PAGE), HttpStatusCodeException.class, "404");
        log.info("HttpClientUtils.clearRetryStrategy()");
        HttpClientUtils.clearRetryStrategy();
        log.info("HttpClientUtils.getOkString(TestConsts.URL_FAKE_PAGE)");
        AssertExt.assertExceptionThrown(() -> HttpClientUtils.getOkString(TestConsts.URL_FAKE_PAGE), HttpStatusCodeException.class, "404");

        log.info("HttpClientUtils.setRetryStrategy(3, 500)");
        HttpClientUtils.setRetryStrategy(3, 500);
        log.info("HttpClientUtils.getOkString(TestConsts.URL_FAKE_HOST)");
        AssertExt.assertExceptionThrown(() -> HttpClientUtils.getOkString(TestConsts.URL_FAKE_HOST), UnknownHostException.class);
        log.info("HttpClientUtils.clearRetryStrategy()");
        HttpClientUtils.clearRetryStrategy();
        log.info("HttpClientUtils.getOkString(TestConsts.URL_FAKE_HOST)");
        AssertExt.assertExceptionThrown(() -> HttpClientUtils.getOkString(TestConsts.URL_FAKE_HOST), UnknownHostException.class);
    }

    @Test
    public void test_registerHttps() throws Exception {
        test_get();
        AssertExt.assertExceptionThrown(() -> HttpClientUtils.getOkByteArray(TestConsts.URL_HTTPS), SSLHandshakeException.class);

        HttpClientUtils.registerHttps();

        test_get();
        MimasTplExtProjectConfig.getSingleton().writeFileInTestOutDirUsingUtf8(HttpClientUtils.getOkStringUsingUtf8AsDefault(TestConsts.URL_HTTPS));
    }

    @Test
    public void test_get() throws Exception {
        Assert.assertEquals(TestConsts.STR_0, HttpClientUtils.get(TestConsts.URL_GET + TestConsts.PARAMS_GET, new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                Assert.assertEquals("HTTP/1.1", httpResponse.getProtocolVersion().toString());
                Assert.assertEquals("HTTP/1.1", httpResponse.getStatusLine().getProtocolVersion().toString());
                Assert.assertEquals(200,        httpResponse.getStatusLine().getStatusCode());
                Assert.assertEquals("OK",       httpResponse.getStatusLine().getReasonPhrase());

                Assert.assertEquals(4, httpResponse.getAllHeaders().length);
                Assert.assertEquals("Server",                  httpResponse.getAllHeaders()[0].getName());
                Assert.assertEquals("Content-Type",            httpResponse.getAllHeaders()[1].getName());
                Assert.assertEquals("text/html;charset=UTF-8", httpResponse.getAllHeaders()[1].getValue());
                Assert.assertEquals("Content-Length",          httpResponse.getAllHeaders()[2].getName());
                Assert.assertEquals(Integer.toString(TestConsts.CONTENT_GET_BYTE_ARRAY_LENGTH), httpResponse.getAllHeaders()[2].getValue());
                Assert.assertEquals("Date",                    httpResponse.getAllHeaders()[3].getName());

                Assert.assertFalse(httpResponse.getEntity().isRepeatable());
                Assert.assertFalse(httpResponse.getEntity().isChunked());
                Assert.assertTrue(httpResponse.getEntity().isStreaming());
                Assert.assertEquals(TestConsts.CONTENT_GET_BYTE_ARRAY_LENGTH, httpResponse.getEntity().getContentLength());
                Assert.assertArrayEquals(TestConsts.CONTENT_GET_BYTE_ARRAY, ByteStreams.toByteArray(httpResponse.getEntity().getContent()));

                Assert.assertEquals("text/html",            ContentType.get(httpResponse.getEntity()).getMimeType());
                Assert.assertEquals(StandardCharsets.UTF_8, ContentType.get(httpResponse.getEntity()).getCharset());

                return TestConsts.STR_0;
            }
        }));

        Assert.assertEquals(TestConsts.STR_0, HttpClientUtils.get(TestConsts.URL_FAKE_PAGE, new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                Assert.assertEquals("HTTP/1.1",  httpResponse.getProtocolVersion().toString());
                Assert.assertEquals("HTTP/1.1",  httpResponse.getStatusLine().getProtocolVersion().toString());
                Assert.assertEquals(404,         httpResponse.getStatusLine().getStatusCode());
                Assert.assertEquals("Not Found", httpResponse.getStatusLine().getReasonPhrase());

                return TestConsts.STR_0;
            }
        }));

        AssertExt.assertExceptionThrown(() -> HttpClientUtils.get(TestConsts.URL_FAKE_HOST, httpResponse -> null), UnknownHostException.class);
    }

    @Test
    public void test_getOkString() throws Exception {
        // String getOkString(String uri)
        Assert.assertEquals(TestConsts.CONTENT_GET, HttpClientUtils.getOkString(TestConsts.URL_GET + TestConsts.PARAMS_GET));

        AssertExt.assertExceptionThrown(() -> HttpClientUtils.getOkString(TestConsts.URL_FAKE_PAGE), HttpStatusCodeException.class, "404");
        AssertExt.assertExceptionThrown(() -> HttpClientUtils.getOkString(TestConsts.URL_FAKE_HOST), UnknownHostException.class);

        // String getOkString(String uri, Charset defaultCharset)
        Assert.assertEquals(TestConsts.CONTENT_GET, HttpClientUtils.getOkString(TestConsts.URL_GET + TestConsts.PARAMS_GET, StandardCharsets.ISO_8859_1));
    }

    @Test
    public void test_getOkStringUsingUtf8AsDefault() throws Exception {
        Assert.assertEquals(TestConsts.CONTENT_GET, HttpClientUtils.getOkStringUsingUtf8AsDefault(TestConsts.URL_GET + TestConsts.PARAMS_GET));

        AssertExt.assertExceptionThrown(() -> HttpClientUtils.getOkStringUsingUtf8AsDefault(TestConsts.URL_FAKE_PAGE), HttpStatusCodeException.class, "404");
        AssertExt.assertExceptionThrown(() -> HttpClientUtils.getOkStringUsingUtf8AsDefault(TestConsts.URL_FAKE_HOST), UnknownHostException.class);
    }

    @Test
    public void test_getOkByteArray() throws Exception {
        Assert.assertArrayEquals(TestConsts.CONTENT_GET_BYTE_ARRAY, HttpClientUtils.getOkByteArray(TestConsts.URL_GET + TestConsts.PARAMS_GET));

        AssertExt.assertExceptionThrown(() -> HttpClientUtils.getOkByteArray(TestConsts.URL_FAKE_PAGE), HttpStatusCodeException.class, "404");
        AssertExt.assertExceptionThrown(() -> HttpClientUtils.getOkByteArray(TestConsts.URL_FAKE_HOST), UnknownHostException.class);
    }

    @Test
    public void test_post() throws Exception {
        Assert.assertEquals(TestConsts.STR_0, HttpClientUtils.post(TestConsts.URL_POST, formEntity, new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                Assert.assertEquals("HTTP/1.1", httpResponse.getProtocolVersion().toString());
                Assert.assertEquals("HTTP/1.1", httpResponse.getStatusLine().getProtocolVersion().toString());
                Assert.assertEquals(200,        httpResponse.getStatusLine().getStatusCode());
                Assert.assertEquals("OK",       httpResponse.getStatusLine().getReasonPhrase());

                Assert.assertEquals(4, httpResponse.getAllHeaders().length);
                Assert.assertEquals("Server",                  httpResponse.getAllHeaders()[0].getName());
                Assert.assertEquals("Content-Type",            httpResponse.getAllHeaders()[1].getName());
                Assert.assertEquals("text/html;charset=UTF-8", httpResponse.getAllHeaders()[1].getValue());
                Assert.assertEquals("Content-Length",          httpResponse.getAllHeaders()[2].getName());
                Assert.assertEquals(Integer.toString(TestConsts.CONTENT_POST_BYTE_ARRAY_LENGTH), httpResponse.getAllHeaders()[2].getValue());
                Assert.assertEquals("Date",                    httpResponse.getAllHeaders()[3].getName());

                Assert.assertFalse(httpResponse.getEntity().isRepeatable());
                Assert.assertFalse(httpResponse.getEntity().isChunked());
                Assert.assertTrue(httpResponse.getEntity().isStreaming());
                Assert.assertEquals(TestConsts.CONTENT_POST_BYTE_ARRAY_LENGTH, httpResponse.getEntity().getContentLength());
                Assert.assertArrayEquals(TestConsts.CONTENT_POST_BYTE_ARRAY, ByteStreams.toByteArray(httpResponse.getEntity().getContent()));

                Assert.assertEquals("text/html",            ContentType.get(httpResponse.getEntity()).getMimeType());
                Assert.assertEquals(StandardCharsets.UTF_8, ContentType.get(httpResponse.getEntity()).getCharset());

                return TestConsts.STR_0;
            }
        }));

        Assert.assertEquals(TestConsts.STR_0, HttpClientUtils.post(TestConsts.URL_FAKE_PAGE, formEntity, new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                Assert.assertEquals("HTTP/1.1",  httpResponse.getProtocolVersion().toString());
                Assert.assertEquals("HTTP/1.1",  httpResponse.getStatusLine().getProtocolVersion().toString());
                Assert.assertEquals(404,         httpResponse.getStatusLine().getStatusCode());
                Assert.assertEquals("Not Found", httpResponse.getStatusLine().getReasonPhrase());

                return TestConsts.STR_0;
            }
        }));

        AssertExt.assertExceptionThrown(() -> HttpClientUtils.post(TestConsts.URL_FAKE_HOST, formEntity, httpResponse -> null), UnknownHostException.class);
    }

    @Test
    public void test_postOkString() throws Exception {
        // String postOkString(String uri, UrlEncodedFormEntity formEntity)
        Assert.assertEquals(TestConsts.CONTENT_POST, HttpClientUtils.postOkString(TestConsts.URL_POST, formEntity));

        AssertExt.assertExceptionThrown(() -> HttpClientUtils.postOkString(TestConsts.URL_FAKE_PAGE, formEntity), HttpStatusCodeException.class, "404");
        AssertExt.assertExceptionThrown(() -> HttpClientUtils.postOkString(TestConsts.URL_FAKE_HOST, formEntity), UnknownHostException.class);

        // String postOkString(String uri, UrlEncodedFormEntity formEntity, Charset defaultCharset)
        Assert.assertEquals(TestConsts.CONTENT_POST, HttpClientUtils.postOkString(TestConsts.URL_POST, formEntity, StandardCharsets.ISO_8859_1));
    }

    @Test
    public void test_postOkStringUsingUtf8AsDefault() throws Exception {
        Assert.assertEquals(TestConsts.CONTENT_POST, HttpClientUtils.postOkStringUsingUtf8AsDefault(TestConsts.URL_POST, formEntity));

        AssertExt.assertExceptionThrown(() -> HttpClientUtils.postOkStringUsingUtf8AsDefault(TestConsts.URL_FAKE_PAGE, formEntity), HttpStatusCodeException.class, "404");
        AssertExt.assertExceptionThrown(() -> HttpClientUtils.postOkStringUsingUtf8AsDefault(TestConsts.URL_FAKE_HOST, formEntity), UnknownHostException.class);
    }

    @Test
    public void test_postOkByteArray() throws Exception {
        Assert.assertArrayEquals(TestConsts.CONTENT_POST_BYTE_ARRAY, HttpClientUtils.postOkByteArray(TestConsts.URL_POST, formEntity));

        AssertExt.assertExceptionThrown(() -> HttpClientUtils.postOkByteArray(TestConsts.URL_FAKE_PAGE, formEntity), HttpStatusCodeException.class, "404");
        AssertExt.assertExceptionThrown(() -> HttpClientUtils.postOkByteArray(TestConsts.URL_FAKE_HOST, formEntity), UnknownHostException.class);
    }
}
