package net.milanqiu.mimas.string;

import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2018-06-14
 * @author Milan Qiu
 */
public class EncodingUtilsTest {

    @Test
    public void test_nativeToAscii() throws Exception {
        // String nativeToAscii(char ntv)
        Assert.assertEquals("\\u0061", EncodingUtils.nativeToAscii('a'));
        Assert.assertEquals("\\u0000", EncodingUtils.nativeToAscii('\u0000'));
        Assert.assertEquals("\\u000F", EncodingUtils.nativeToAscii('\u000f'));
        Assert.assertEquals("\\u0010", EncodingUtils.nativeToAscii('\u0010'));
        Assert.assertEquals("\\u00FF", EncodingUtils.nativeToAscii('\u00ff'));
        Assert.assertEquals("\\u0100", EncodingUtils.nativeToAscii('\u0100'));
        Assert.assertEquals("\\u0FFF", EncodingUtils.nativeToAscii('\u0fff'));
        Assert.assertEquals("\\u1000", EncodingUtils.nativeToAscii('\u1000'));
        Assert.assertEquals("\\uFFFF", EncodingUtils.nativeToAscii('\uffff'));

        // String nativeToAscii(String ntv)
        Assert.assertEquals("\\u0061\\u0000\\u000F\\u0010\\u00FF\\u0100\\u0FFF\\u1000\\uFFFF",
                EncodingUtils.nativeToAscii("a\u0000\u000f\u0010\u00ff\u0100\u0fff\u1000\uffff"));
    }

    @Test
    public void test_asciiToNative() throws Exception {
        Assert.assertEquals("a\u0000\u000f\u0010\u00ff\u0100\u0fff\u1000\uffff",
                EncodingUtils.asciiToNative("\\u0061\\u0000\\u000F\\u0010\\u00FF\\u0100\\u0FFF\\u1000\\uFFFF"));
        Assert.assertEquals("a\\u006a\\u006",
                EncodingUtils.asciiToNative("a\\u006\\u0061\\u006"));
    }

    private int cursor;
    private int count;

    @Test
    public void test_getValidUnicodeCharValues() throws Exception {
        char[] charArr = EncodingUtils.getValidUnicodeCharValues();
        Assert.assertEquals(EncodingUtils.VALID_UNICODE_CHAR_VALUE_COUNT, charArr.length);
        int cursor = Character.MIN_VALUE;
        for (int i = 0; i < EncodingUtils.VALID_UNICODE_CHAR_VALUE_COUNT; i++) {
            if (cursor == Character.MIN_SURROGATE)
                cursor = Character.MAX_SURROGATE + 1;
            if (cursor == EncodingUtils.REVERSED_UNICODE_BOM)
                cursor++;
            Assert.assertEquals(cursor++, charArr[i]);
        }
        Assert.assertEquals(Character.MAX_VALUE+1, cursor);
    }

    @Test
    public void test_traverseValidUnicodeCharValues() throws Exception {
        cursor = Character.MIN_VALUE;
        count = 0;
        EncodingUtils.traverseValidUnicodeCharValues((param) -> {
            if (cursor == Character.MIN_SURROGATE)
                cursor = Character.MAX_SURROGATE + 1;
            if (cursor == EncodingUtils.REVERSED_UNICODE_BOM)
                cursor++;
            Assert.assertEquals(cursor++, param);
            count++;
        });
        Assert.assertEquals(Character.MAX_VALUE+1, cursor);
        Assert.assertEquals(EncodingUtils.VALID_UNICODE_CHAR_VALUE_COUNT, count);
    }

    @Test
    public void test_getValidUnicodeString() throws Exception {
        String str = EncodingUtils.getValidUnicodeString();
        Assert.assertEquals(EncodingUtils.VALID_UNICODE_CHAR_VALUE_COUNT, str.length());
        int cursor = Character.MIN_VALUE;
        for (int i = 0; i < EncodingUtils.VALID_UNICODE_CHAR_VALUE_COUNT; i++) {
            if (cursor == Character.MIN_SURROGATE)
                cursor = Character.MAX_SURROGATE + 1;
            if (cursor == EncodingUtils.REVERSED_UNICODE_BOM)
                cursor++;
            Assert.assertEquals(cursor++, str.charAt(i));
        }
        Assert.assertEquals(Character.MAX_VALUE+1, cursor);
    }
}
