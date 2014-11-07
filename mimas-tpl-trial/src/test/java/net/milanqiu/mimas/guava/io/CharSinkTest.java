package net.milanqiu.mimas.guava.io;

import com.google.common.io.CharSink;
import com.google.common.io.Files;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.CharArrayReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * <p>Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class CharSinkTest {

    @Before
    public void setUp() throws Exception {
        GuavaIoTestUtils.assertTestFileNotExists();
    }

    @After
    public void tearDown() throws Exception {
        GuavaIoTestUtils.deleteTestFile();
    }

    @Test
    public void test_openStream() throws Exception {
        CharSink csnk = Files.asCharSink(TEST_FILE, StandardCharsets.UTF_8);
        Writer w = csnk.openStream();
        w.write(STR_OF_CHAR_ARR);
        w.flush();
        w.close();
        checkTestFileOfChar();
    }

    @Test
    public void test_openBufferedStream() throws Exception {
        CharSink csnk = Files.asCharSink(TEST_FILE, StandardCharsets.UTF_8);
        Writer w = csnk.openBufferedStream();
        try {
            w.write(STR_OF_CHAR_ARR);
            w.flush();
            checkTestFileOfChar();
        } finally {
            w.close();
        }
    }

    @Test
    public void test_write() throws Exception {
        CharSink csnk = Files.asCharSink(TEST_FILE, StandardCharsets.UTF_8);
        csnk.write(STR_OF_CHAR_ARR);
        checkTestFileOfChar();
    }

    @Test
    public void test_writeFrom() throws Exception {
        Readable r = new CharArrayReader(CHAR_ARR);
        CharSink csnk = Files.asCharSink(TEST_FILE, StandardCharsets.UTF_8);
        csnk.writeFrom(r);
        checkTestFileOfChar();
    }

    @Test
    public void test_writeLines() throws Exception {
        CharSink csnk = Files.asCharSink(TEST_FILE, StandardCharsets.UTF_8);
        csnk.writeLines(LIST_MULTILINE);
        checkTestFileOfText();
    }
}
