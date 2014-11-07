package net.milanqiu.mimas.guava.io;

import com.google.common.io.ByteSink;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * <p>Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class ByteSinkTest {

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
        ByteSink bsnk = Files.asByteSink(TEST_FILE);
        OutputStream os = bsnk.openStream();
        try {
            os.write(BYTE_ARR);
            checkTestFileOfByte();
        } finally {
            os.close();
        }
    }

    @Test
    public void test_openBufferedStream() throws Exception {
        ByteSink bsnk = Files.asByteSink(TEST_FILE);
        try (OutputStream os = bsnk.openBufferedStream()) {
            os.write(BYTE_ARR);
            os.flush();
            checkTestFileOfByte();
        }
    }

    @Test
    public void test_write() throws Exception {
        ByteSink bsnk = Files.asByteSink(TEST_FILE);
        bsnk.write(BYTE_ARR);
        checkTestFileOfByte();
    }

    @Test
    public void test_writeFrom() throws Exception {
        InputStream is = new ByteArrayInputStream(BYTE_ARR);
        ByteSink bsnk = Files.asByteSink(TEST_FILE);
        Assert.assertEquals(BYTE_ARR_LEN, bsnk.writeFrom(is));
        checkTestFileOfByte();
    }

    @Test
    public void test_asCharSink() throws Exception {
        ByteSink bsnk = Files.asByteSink(TEST_FILE);
        CharSink cs = bsnk.asCharSink(StandardCharsets.UTF_8);
        cs.write(STR_OF_CHAR_ARR);
        checkTestFileOfChar();
    }
}
