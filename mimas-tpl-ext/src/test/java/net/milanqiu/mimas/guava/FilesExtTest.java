package net.milanqiu.mimas.guava;

import com.google.common.collect.ImmutableList;
import net.milanqiu.mimas.config.MimasTplExtProjectConfig;
import net.milanqiu.mimas.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

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
}
