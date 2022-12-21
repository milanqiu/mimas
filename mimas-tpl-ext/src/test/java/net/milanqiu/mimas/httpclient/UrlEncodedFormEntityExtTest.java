package net.milanqiu.mimas.httpclient;

import com.google.common.io.ByteStreams;
import net.milanqiu.mimas.instrumentation.TestConsts;
import net.milanqiu.mimas.junit.AssertExt;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.junit.Assert;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Creation Date: 2022-12-21
 * @author Milan Qiu
 */
public class UrlEncodedFormEntityExtTest {

    @Test
    public void test_create() throws Exception {
        // UrlEncodedFormEntity create(Charset charset, String... parameterNameValues)
        {
            UrlEncodedFormEntity formEntity = UrlEncodedFormEntityExt.create(StandardCharsets.UTF_8,
                    TestConsts.PARAM1_NAME_POST, TestConsts.PARAM1_VALUE_POST,
                    TestConsts.PARAM2_NAME_POST, TestConsts.PARAM2_VALUE_POST);
            Assert.assertEquals(TestConsts.PARAMS_POST.getBytes(StandardCharsets.UTF_8).length, formEntity.getContentLength());
            Assert.assertArrayEquals(TestConsts.PARAMS_POST.getBytes(StandardCharsets.UTF_8), ByteStreams.toByteArray(formEntity.getContent()));

            AssertExt.assertExceptionThrown(() -> UrlEncodedFormEntityExt.create(StandardCharsets.UTF_8,
                    TestConsts.PARAM1_NAME_POST, TestConsts.PARAM1_VALUE_POST,
                    TestConsts.PARAM2_NAME_POST),
                    IllegalArgumentException.class, "the length of parameterNameValues should be even");
        }

        // UrlEncodedFormEntity create(Charset charset, Map<String, String> parameterNameValues)
        {
            Map<String, String> parameterNameValues = new LinkedHashMap<>();
            parameterNameValues.put(TestConsts.PARAM1_NAME_POST, TestConsts.PARAM1_VALUE_POST);
            parameterNameValues.put(TestConsts.PARAM2_NAME_POST, TestConsts.PARAM2_VALUE_POST);
            UrlEncodedFormEntity formEntity = UrlEncodedFormEntityExt.create(StandardCharsets.UTF_8, parameterNameValues);
            Assert.assertEquals(TestConsts.PARAMS_POST.getBytes(StandardCharsets.UTF_8).length, formEntity.getContentLength());
            Assert.assertArrayEquals(TestConsts.PARAMS_POST.getBytes(StandardCharsets.UTF_8), ByteStreams.toByteArray(formEntity.getContent()));
        }
    }

    @Test
    public void test_createUsingUtf8() throws Exception {
        // UrlEncodedFormEntity createUsingUtf8(String... parameterNameValues)
        {
            UrlEncodedFormEntity formEntity = UrlEncodedFormEntityExt.createUsingUtf8(
                    TestConsts.PARAM1_NAME_POST, TestConsts.PARAM1_VALUE_POST,
                    TestConsts.PARAM2_NAME_POST, TestConsts.PARAM2_VALUE_POST);
            Assert.assertEquals(TestConsts.PARAMS_POST.getBytes(StandardCharsets.UTF_8).length, formEntity.getContentLength());
            Assert.assertArrayEquals(TestConsts.PARAMS_POST.getBytes(StandardCharsets.UTF_8), ByteStreams.toByteArray(formEntity.getContent()));

            AssertExt.assertExceptionThrown(() -> UrlEncodedFormEntityExt.createUsingUtf8(
                    TestConsts.PARAM1_NAME_POST, TestConsts.PARAM1_VALUE_POST,
                    TestConsts.PARAM2_NAME_POST),
                    IllegalArgumentException.class, "the length of parameterNameValues should be even");
        }

        // UrlEncodedFormEntity createUsingUtf8(Map<String, String> parameterNameValues)
        {
            Map<String, String> parameterNameValues = new LinkedHashMap<>();
            parameterNameValues.put(TestConsts.PARAM1_NAME_POST, TestConsts.PARAM1_VALUE_POST);
            parameterNameValues.put(TestConsts.PARAM2_NAME_POST, TestConsts.PARAM2_VALUE_POST);
            UrlEncodedFormEntity formEntity = UrlEncodedFormEntityExt.createUsingUtf8(parameterNameValues);
            Assert.assertEquals(TestConsts.PARAMS_POST.getBytes(StandardCharsets.UTF_8).length, formEntity.getContentLength());
            Assert.assertArrayEquals(TestConsts.PARAMS_POST.getBytes(StandardCharsets.UTF_8), ByteStreams.toByteArray(formEntity.getContent()));
        }
    }
}
