package net.milanqiu.mimas.guava.io;

import com.google.common.collect.ImmutableList;
import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.string.StrUtils;
import net.milanqiu.mimas.system.MimasTplTrialConvention;
import org.junit.Assert;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * <p>Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class GuavaIoTestUtils {

    public static final int BUF_LEN = 255;

    public static final byte[] BYTE_ARR = new byte[] {BYTE_0, BYTE_1, BYTE_2, BYTE_3, BYTE_4};
    public static final int BYTE_ARR_LEN = BYTE_ARR.length;

    public static final char[] CHAR_ARR = new char[] {CHAR_0, CHAR_1, CHAR_2, CHAR_3, CHAR_4};
    public static final int CHAR_ARR_LEN = CHAR_ARR.length;
    public static final String STR_OF_CHAR_ARR = String.valueOf(CHAR_ARR);

    public static final String STR_MULTILINE =
                    STR_0 + StrUtils.LINE_SEPARATOR +
                    STR_1 + StrUtils.LINE_SEPARATOR +
                            StrUtils.LINE_SEPARATOR +
                    STR_2 + StrUtils.LINE_SEPARATOR +
                            StrUtils.LINE_SEPARATOR;
    public static final int STR_MULTILINE_LEN = STR_MULTILINE.length();
    public static final List<String> LIST_MULTILINE = ImmutableList.of(STR_0, STR_1, "", STR_2, "");

    public static final File TEST_FILE =
            new File(MimasTplTrialConvention.getSingleton().getUnitTestTempDir(), "guava_io.tmp");

    public static void createTestFileOfByte() throws IOException {
        FileOutputStream fos = new FileOutputStream(TEST_FILE);
        fos.write(BYTE_ARR);
        fos.close();
    }

    public static void checkTestFileOfByte() throws IOException {
        byte[] buf = new byte[BUF_LEN];
        FileInputStream fis = new FileInputStream(TEST_FILE);
        Assert.assertEquals(BYTE_ARR_LEN, fis.read(buf));
        Assert.assertArrayEquals(BYTE_ARR, Arrays.copyOfRange(buf, 0, BYTE_ARR_LEN));
        fis.close();
    }

    public static void createTestFileOfChar() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TEST_FILE), StandardCharsets.UTF_8));
        bw.write(CHAR_ARR);
        bw.close();
    }

    public static void checkTestFileOfChar() throws IOException {
        char[] buf = new char[BUF_LEN];
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(TEST_FILE), StandardCharsets.UTF_8));
        Assert.assertEquals(CHAR_ARR_LEN, br.read(buf));
        Assert.assertArrayEquals(CHAR_ARR, Arrays.copyOfRange(buf, 0, CHAR_ARR_LEN));
        br.close();
    }

    public static void createTestFileOfText() throws IOException {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(TEST_FILE), StandardCharsets.UTF_8));
        bw.write(STR_MULTILINE);
        bw.close();
    }

    public static void checkTestFileOfText() throws IOException {
        char[] buf = new char[BUF_LEN];
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(TEST_FILE), StandardCharsets.UTF_8));
        Assert.assertEquals(STR_MULTILINE_LEN, br.read(buf));
        Assert.assertEquals(STR_MULTILINE, new String(buf, 0, STR_MULTILINE_LEN));
        br.close();
    }

    public static void assertTestFileNotExists() {
        Assert.assertFalse(TEST_FILE.exists());
    }

    public static void deleteTestFile() throws IOException {
        if (TEST_FILE.exists())
            Files.delete(TEST_FILE.toPath());
        assertTestFileNotExists();
    }
}
