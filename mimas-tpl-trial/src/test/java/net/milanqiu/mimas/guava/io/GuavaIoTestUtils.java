package net.milanqiu.mimas.guava.io;

import com.google.common.collect.ImmutableList;
import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.lang.LangUtils;
import net.milanqiu.mimas.string.StrUtils;
import org.junit.Assert;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class GuavaIoTestUtils {

    public static final byte[] ALL_BYTE_VALUES = LangUtils.getAllByteValues();
    public static final int ALL_BYTE_VALUES_LEN = LangUtils.BYTE_VALUE_COUNT;

    public static final char[] ALL_CHAR_VALUES = LangUtils.getAllCharValues();
    public static final int ALL_CHAR_VALUES_LEN = LangUtils.CHAR_VALUE_COUNT;
    public static final String ALL_CHAR_VALUES_STR = String.valueOf(ALL_CHAR_VALUES);

    public static final char[] UNICODE_CHAR_VALUES = StrUtils.getValidUnicodeCharValues();
    public static final int UNICODE_CHAR_VALUES_LEN = UNICODE_CHAR_VALUES.length;
    public static final String UNICODE_CHAR_VALUES_STR = String.valueOf(UNICODE_CHAR_VALUES);

    public static final String MULTILINE_STR =
                    STR_0 + StrUtils.LINE_SEPARATOR +
                    STR_1 + StrUtils.LINE_SEPARATOR +
                            StrUtils.LINE_SEPARATOR +
                    STR_2 + StrUtils.LINE_SEPARATOR +
                            StrUtils.LINE_SEPARATOR;
    public static final int MULTILINE_STR_LEN = MULTILINE_STR.length();
    public static final List<String> MULTILINE_LIST = ImmutableList.of(STR_0, STR_1, "", STR_2, "");

    public static void createTestFileOfByte(File file) throws IOException {
        FileUtils.writeBytes(ALL_BYTE_VALUES, file);
    }

    public static void assertTestFileOfByte(File file) throws IOException {
        byte[] fileContent = FileUtils.readBytes(file);
        Assert.assertEquals(ALL_BYTE_VALUES_LEN, fileContent.length);
        Assert.assertArrayEquals(ALL_BYTE_VALUES, fileContent);
    }

    public static void createTestFileOfChar(File file) throws IOException {
        FileUtils.writeCharsUsingUtf8(UNICODE_CHAR_VALUES_STR, file);
    }

    public static void assertTestFileOfChar(File file) throws IOException {
        String fileContent = FileUtils.readCharsUsingUtf8(file);
        Assert.assertEquals(UNICODE_CHAR_VALUES_STR, fileContent);
    }

    public static void createTestFileOfText(File file) throws IOException {
        FileUtils.writeCharsUsingUtf8(MULTILINE_STR, file);
    }

    public static void assertTestFileOfText(File file) throws IOException {
        String fileContent = FileUtils.readCharsUsingUtf8(file);
        Assert.assertEquals(MULTILINE_STR, fileContent);
    }
}
