package net.milanqiu.mimas.guava;

import com.google.common.collect.ImmutableList;
import net.milanqiu.mimas.collect.CollectionUtils;
import net.milanqiu.mimas.config.MimasTplExtProjectConfig;
import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2021-06-13
 * @author Milan Qiu
 */
public class FilesExtTest {

    @Test
    public void test_writeLines() throws Exception {
        File workDir = MimasTplExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        File workFile = new File(workDir, "temp");

        // void writeLines(Iterable<? extends CharSequence> lines, File toFile, Charset charset)
        {
            FilesExt.writeLines(ImmutableList.of(STR_0, STR_1, "", STR_2, ""), workFile, StandardCharsets.UTF_8);
            Assert.assertEquals(STR_0 + System.lineSeparator() +
                                STR_1 + System.lineSeparator() +
                                System.lineSeparator() +
                                STR_2 + System.lineSeparator() +
                                System.lineSeparator(),
                    FileUtils.readCharsUsingUtf8(workFile));
        }

        // void writeLines(Iterable<? extends CharSequence> lines, String lineSeparator, File toFile, Charset charset)
        {
            FilesExt.writeLines(ImmutableList.of(STR_0, STR_1, "", STR_2, ""), STR_4, workFile, StandardCharsets.UTF_8);
            Assert.assertEquals(STR_0 + STR_4 +
                                STR_1 + STR_4 +
                                STR_4 +
                                STR_2 + STR_4 +
                                STR_4,
                    FileUtils.readCharsUsingUtf8(workFile));
        }

        FileUtils.deleteRecursively(workDir);
    }

    @Test
    public void test_writeLinesUsingUtf8() throws Exception {
        File workDir = MimasTplExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        File workFile = new File(workDir, "temp");

        // void writeLinesUsingUtf8(Iterable<? extends CharSequence> lines, File toFile)
        {
            FilesExt.writeLinesUsingUtf8(ImmutableList.of(STR_0, STR_1, "", STR_2, ""), workFile);
            Assert.assertEquals(STR_0 + System.lineSeparator() +
                                STR_1 + System.lineSeparator() +
                                System.lineSeparator() +
                                STR_2 + System.lineSeparator() +
                                System.lineSeparator(),
                    FileUtils.readCharsUsingUtf8(workFile));
        }

        // void writeLinesUsingUtf8(Iterable<? extends CharSequence> lines, String lineSeparator, File toFile)
        {
            FilesExt.writeLinesUsingUtf8(ImmutableList.of(STR_0, STR_1, "", STR_2, ""), STR_4, workFile);
            Assert.assertEquals(STR_0 + STR_4 +
                                STR_1 + STR_4 +
                                STR_4 +
                                STR_2 + STR_4 +
                                STR_4,
                    FileUtils.readCharsUsingUtf8(workFile));
        }

        FileUtils.deleteRecursively(workDir);
    }

    @Test
    public void test_readLines() throws Exception {
        File workDir = MimasTplExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        File workFile = new File(workDir, "temp");

        FileUtils.writeCharsUsingUtf8(STR_0 + System.lineSeparator() +
                                      STR_1 + System.lineSeparator() +
                                      System.lineSeparator() +
                                      STR_2 + System.lineSeparator() +
                                      System.lineSeparator(),
                workFile);
        List<String> lines = FilesExt.readLines(workFile, StandardCharsets.UTF_8);
        Assert.assertTrue(CollectionUtils.equals(ImmutableList.of(STR_0, STR_1, "", STR_2, ""), lines));

        FileUtils.deleteRecursively(workDir);
    }

    @Test
    public void test_readLinesUsingUtf8() throws Exception {
        File workDir = MimasTplExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        File workFile = new File(workDir, "temp");

        FileUtils.writeCharsUsingUtf8(STR_0 + System.lineSeparator() +
                                      STR_1 + System.lineSeparator() +
                                      System.lineSeparator() +
                                      STR_2 + System.lineSeparator() +
                                      System.lineSeparator(),
                workFile);
        List<String> lines = FilesExt.readLinesUsingUtf8(workFile);
        Assert.assertTrue(CollectionUtils.equals(ImmutableList.of(STR_0, STR_1, "", STR_2, ""), lines));

        FileUtils.deleteRecursively(workDir);
    }
}
