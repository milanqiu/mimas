package net.milanqiu.mimas.string;

import net.milanqiu.mimas.lang.LangUtils;
import net.milanqiu.mimas.lang.RunnableWithParam;
import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2014-11-4
 * @author Milan Qiu
 */
public class StrUtilsTest {

    @Test
    public void test_getValidUnicodeCharValues() throws Exception {
        char[] charArr = StrUtils.getValidUnicodeCharValues();
        Assert.assertEquals(StrUtils.VALID_UNICODE_CHAR_VALUE_COUNT, charArr.length);
        int cursor = Character.MIN_VALUE;
        for (int i = 0; i < StrUtils.VALID_UNICODE_CHAR_VALUE_COUNT; i++) {
            if (cursor == Character.MIN_SURROGATE)
                cursor = Character.MAX_SURROGATE + 1;
            if (cursor == StrUtils.REVERSED_UNICODE_BOM)
                cursor++;
            Assert.assertEquals(cursor++, charArr[i]);
        }
    }

    private int cursor;
    private int count;

    @Test
    public void test_traverseValidUnicodeCharValues() throws Exception {
        cursor = Character.MIN_VALUE;
        count = 0;
        StrUtils.traverseValidUnicodeCharValues(new RunnableWithParam.WithChar() {
            @Override
            public void run(char param) {
                if (cursor == Character.MIN_SURROGATE)
                    cursor = Character.MAX_SURROGATE + 1;
                if (cursor == StrUtils.REVERSED_UNICODE_BOM)
                    cursor++;
                Assert.assertEquals(cursor++, param);
                count++;
            }
        });
        Assert.assertEquals(Character.MAX_VALUE+1, cursor);
        Assert.assertEquals(StrUtils.VALID_UNICODE_CHAR_VALUE_COUNT, count);
    }

    @Test
    public void test_isAsciiUpperCase() throws Exception {
        LangUtils.traverseCharValues(new RunnableWithParam.WithChar() {
            @Override
            public void run(char param) {
                Assert.assertEquals(String.valueOf(param).matches("[A-Z]"), StrUtils.isAsciiUpperCase(param));
            }
        });
    }

    @Test
    public void test_isAsciiLowerCase() throws Exception {
        LangUtils.traverseCharValues(new RunnableWithParam.WithChar() {
            @Override
            public void run(char param) {
                Assert.assertEquals(String.valueOf(param).matches("[a-z]"), StrUtils.isAsciiLowerCase(param));
            }
        });
    }

    @Test
    public void test_isAsciiLetter() throws Exception {
        LangUtils.traverseCharValues(new RunnableWithParam.WithChar() {
            @Override
            public void run(char param) {
                Assert.assertEquals(String.valueOf(param).matches("[A-Za-z]"), StrUtils.isAsciiLetter(param));
            }
        });
    }

    @Test
    public void test_isAsciiDigit() throws Exception {
        LangUtils.traverseCharValues(new RunnableWithParam.WithChar() {
            @Override
            public void run(char param) {
                Assert.assertEquals(String.valueOf(param).matches("[0-9]"), StrUtils.isAsciiDigit(param));
            }
        });
    }

    @Test
    public void test_isAsciiLetterOrDigit() throws Exception {
        LangUtils.traverseCharValues(new RunnableWithParam.WithChar() {
            @Override
            public void run(char param) {
                Assert.assertEquals(String.valueOf(param).matches("[A-Za-z0-9]"), StrUtils.isAsciiLetterOrDigit(param));
            }
        });
    }

    @Test
    public void test_isAsciiSign() throws Exception {
        LangUtils.traverseCharValues(new RunnableWithParam.WithChar() {
            @Override
            public void run(char param) {
                Assert.assertEquals(String.valueOf(param).matches("[\\+\\-]"), StrUtils.isAsciiSign(param));
            }
        });
    }

    @Test
    public void test_isHexChar() throws Exception {
        LangUtils.traverseCharValues(new RunnableWithParam.WithChar() {
            @Override
            public void run(char param) {
                Assert.assertEquals(String.valueOf(param).matches("[A-Fa-f0-9]"), StrUtils.isHexChar(param));
            }
        });
    }

    @Test
    public void test_nativeToAscii() throws Exception {
        // String nativeToAscii(char ntv)
        Assert.assertEquals("\\u0061", StrUtils.nativeToAscii('a'));
        Assert.assertEquals("\\u0000", StrUtils.nativeToAscii('\u0000'));
        Assert.assertEquals("\\u000F", StrUtils.nativeToAscii('\u000f'));
        Assert.assertEquals("\\u0010", StrUtils.nativeToAscii('\u0010'));
        Assert.assertEquals("\\u00FF", StrUtils.nativeToAscii('\u00ff'));
        Assert.assertEquals("\\u0100", StrUtils.nativeToAscii('\u0100'));
        Assert.assertEquals("\\u0FFF", StrUtils.nativeToAscii('\u0fff'));
        Assert.assertEquals("\\u1000", StrUtils.nativeToAscii('\u1000'));
        Assert.assertEquals("\\uFFFF", StrUtils.nativeToAscii('\uffff'));

        // String nativeToAscii(String ntv)
        Assert.assertEquals("\\u0061\\u0000\\u000F\\u0010\\u00FF\\u0100\\u0FFF\\u1000\\uFFFF",
                StrUtils.nativeToAscii("a\u0000\u000f\u0010\u00ff\u0100\u0fff\u1000\uffff"));
    }

    @Test
    public void test_asciiToNative() throws Exception {
        Assert.assertEquals("a\u0000\u000f\u0010\u00ff\u0100\u0fff\u1000\uffff",
                StrUtils.asciiToNative("\\u0061\\u0000\\u000F\\u0010\\u00FF\\u0100\\u0FFF\\u1000\\uFFFF"));
        Assert.assertEquals("a\\u006a\\u006",
                StrUtils.asciiToNative("a\\u006\\u0061\\u006"));
    }
}
