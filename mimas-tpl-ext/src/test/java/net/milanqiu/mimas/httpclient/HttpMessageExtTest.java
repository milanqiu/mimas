package net.milanqiu.mimas.httpclient;

import net.milanqiu.mimas.config.MimasTplExtProjectConfig;
import net.milanqiu.mimas.instrumentation.TestConsts;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.junit.Test;

import java.io.IOException;

/**
 * Creation Date: 2022-12-21
 * @author Milan Qiu
 */
public class HttpMessageExtTest {

    @Test
    public void test_getAllHeadersStr() throws Exception {
        String allHeadersStr = HttpClientUtils.get(TestConsts.URL_GET + TestConsts.PARAMS_GET, new ResponseHandler<String>() {
            @Override
            public String handleResponse(HttpResponse httpResponse) throws ClientProtocolException, IOException {
                return HttpMessageExt.getAllHeadersStr(httpResponse);
            }
        });
        MimasTplExtProjectConfig.getSingleton().writeFileInTestOutDir(allHeadersStr);
    }
}
