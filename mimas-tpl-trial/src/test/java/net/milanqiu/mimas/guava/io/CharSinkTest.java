package net.milanqiu.mimas.guava.io;

import com.google.common.collect.ImmutableList;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.string.StrUtils;
import net.milanqiu.mimas.system.MimasTplTrialConvention;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.CharArrayReader;
import java.io.File;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class CharSinkTest {

    private File workDir;
    private File workFile;

    @After
    public void tearDown() throws Exception {
        if (workDir != null) {
            FileUtils.deleteRecursively(workDir);
            Assert.assertFalse(workDir.exists());
        }
    }

    @Test
    public void test_openStream() throws Exception {
        workDir = MimasTplTrialConvention.getSingleton().prepareWorkDirInTestTempDir(true);
        workFile = new File(workDir, "temp");

        CharSink csnk = Files.asCharSink(workFile, StandardCharsets.UTF_8);
        try (Writer w = csnk.openStream()) {
            w.write(UNICODE_CHAR_VALUES_STR);
            w.flush();
            assertTestFileOfChar(workFile);
        }
    }

    @Test
    public void test_openBufferedStream() throws Exception {
        workDir = MimasTplTrialConvention.getSingleton().prepareWorkDirInTestTempDir(true);
        workFile = new File(workDir, "temp");

        CharSink csnk = Files.asCharSink(workFile, StandardCharsets.UTF_8);
        try (Writer w = csnk.openBufferedStream()) {
            w.write(UNICODE_CHAR_VALUES_STR);
            w.flush();
            assertTestFileOfChar(workFile);
        }
    }

    @Test
    public void test_write() throws Exception {
        workDir = MimasTplTrialConvention.getSingleton().prepareWorkDirInTestTempDir(true);
        workFile = new File(workDir, "temp");

        CharSink csnk = Files.asCharSink(workFile, StandardCharsets.UTF_8);
        csnk.write(UNICODE_CHAR_VALUES_STR);
        assertTestFileOfChar(workFile);
    }

    @Test
    public void test_writeFrom() throws Exception {
        workDir = MimasTplTrialConvention.getSingleton().prepareWorkDirInTestTempDir(true);
        workFile = new File(workDir, "temp");

        Readable r = new CharArrayReader(UNICODE_CHAR_VALUES);
        CharSink csnk = Files.asCharSink(workFile, StandardCharsets.UTF_8);
        csnk.writeFrom(r);
        assertTestFileOfChar(workFile);
    }

    @Test
    public void test_writeLines() throws Exception {
        workDir = MimasTplTrialConvention.getSingleton().prepareWorkDirInTestTempDir(true);
        workFile = new File(workDir, "temp");

        // void writeLines(Iterable<? extends CharSequence> lines)
        {
            CharSink csnk = Files.asCharSink(workFile, StandardCharsets.UTF_8);
            csnk.writeLines(MULTILINE_LIST);
            assertTestFileOfText(workFile);
        }

        // void writeLines(Iterable<? extends CharSequence> lines, String lineSeparator)
        {
            CharSink csnk = Files.asCharSink(workFile, StandardCharsets.UTF_8);
            csnk.writeLines(
                    ImmutableList.of(MULTILINE_STR.substring(0, MULTILINE_STR.length()-StrUtils.LINE_SEPARATOR.length())),
                    StrUtils.LINE_SEPARATOR);
            assertTestFileOfText(workFile);
        }
    }
}
