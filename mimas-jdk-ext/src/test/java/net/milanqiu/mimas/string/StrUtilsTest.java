package net.milanqiu.mimas.string;

import net.milanqiu.mimas.system.SysUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.regex.Pattern;

/**
 * <p>Creation Date: 2014-11-4
 * @author Milan Qiu
 */
public class StrUtilsTest {

    @Test
    public void test_LINE_SEPARATOR() throws Exception {
        System.out.println(StrUtils.nativeToAscii(StrUtils.LINE_SEPARATOR));
    }

    @Test
    public void test_nativeToAscii() throws Exception {
        Assert.assertEquals("\\u0000\\u000f\\u0010\\u00ff\\u0100\\u0fff\\u1000\\uffff",
                StrUtils.nativeToAscii("\u0000\u000F\u0010\u00FF\u0100\u0FFF\u1000\uFFFF"));
    }

    @Test
    public void test_asciiToNative() throws Exception {
        Assert.assertEquals("\u0000\u000F\u0010\u00FF\u0100\u0FFF\u1000\uFFFF",
                StrUtils.asciiToNative("\\u0000\\u000f\\u0010\\u00ff\\u0100\\u0fff\\u1000\\uffff"));
    }
}
