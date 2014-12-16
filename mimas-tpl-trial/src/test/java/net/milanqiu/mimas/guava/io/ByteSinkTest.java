package net.milanqiu.mimas.guava.io;

import com.google.common.io.ByteSink;
import com.google.common.io.CharSink;
import com.google.common.io.Files;
import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.system.MimasTplTrialConvention;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class ByteSinkTest {

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

        ByteSink bsnk = Files.asByteSink(workFile);
        try (OutputStream os = bsnk.openStream()) {
            os.write(ALL_BYTE_VALUES);
            assertTestFileOfByte(workFile);
        }
    }

    @Test
    public void test_openBufferedStream() throws Exception {
        workDir = MimasTplTrialConvention.getSingleton().prepareWorkDirInTestTempDir(true);
        workFile = new File(workDir, "temp");

        ByteSink bsnk = Files.asByteSink(workFile);
        try (OutputStream os = bsnk.openBufferedStream()) {
            os.write(ALL_BYTE_VALUES);
            os.flush(); // this sentence can NOT be removed
            assertTestFileOfByte(workFile);
        }
    }

    @Test
    public void test_write() throws Exception {
        workDir = MimasTplTrialConvention.getSingleton().prepareWorkDirInTestTempDir(true);
        workFile = new File(workDir, "temp");

        ByteSink bsnk = Files.asByteSink(workFile);
        bsnk.write(ALL_BYTE_VALUES);
        assertTestFileOfByte(workFile);
    }

    @Test
    public void test_writeFrom() throws Exception {
        workDir = MimasTplTrialConvention.getSingleton().prepareWorkDirInTestTempDir(true);
        workFile = new File(workDir, "temp");

        InputStream is = new ByteArrayInputStream(ALL_BYTE_VALUES);
        ByteSink bsnk = Files.asByteSink(workFile);
        Assert.assertEquals(ALL_BYTE_VALUES_LEN, bsnk.writeFrom(is));
        assertTestFileOfByte(workFile);
    }

    @Test
    public void test_asCharSink() throws Exception {
        workDir = MimasTplTrialConvention.getSingleton().prepareWorkDirInTestTempDir(true);
        workFile = new File(workDir, "temp");

        ByteSink bsnk = Files.asByteSink(workFile);
        CharSink cs = bsnk.asCharSink(StandardCharsets.UTF_8);
        cs.write(UNICODE_CHAR_VALUES_STR);
        assertTestFileOfChar(workFile);
    }
}
