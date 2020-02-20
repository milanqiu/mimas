package net.milanqiu.mimas.runtime;

import net.milanqiu.mimas.config.MimasJdkExtProjectConfig;
import net.milanqiu.mimas.instrumentation.TestConsts;
import net.milanqiu.mimas.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Creation Date: 2014-11-02
 * @author Milan Qiu
 */
public class RuntimeUtilsTest {

    @Test
    public void test_getClassSourceLocation() throws Exception {
        MimasJdkExtProjectConfig.getSingleton().writeFileInTestOutDir(
                RuntimeUtils.getClassSourceLocation(RuntimeUtilsTest.class) +
                System.lineSeparator() +
                RuntimeUtils.getClassSourceLocation(RuntimeUtils.class));
    }

    @Test
    public void test_getClassSourceDir() throws Exception {
        MimasJdkExtProjectConfig.getSingleton().writeFileInTestOutDir(
                RuntimeUtils.getClassSourceDir(RuntimeUtilsTest.class) +
                System.lineSeparator() +
                RuntimeUtils.getClassSourceDir(RuntimeUtils.class));
    }

    @Test
    public void test_announceFinished() throws Exception {
        File workDir = MimasJdkExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        File announcementFile = new File(workDir, RuntimeUtils.ANNOUNCEMENT_FILE_NAME);
        Assert.assertFalse(announcementFile.exists());

        RuntimeUtils.announceFinished(workDir, TestConsts.STR_0);
        Assert.assertTrue(announcementFile.exists());
        Assert.assertEquals(RuntimeUtils.ANNOUNCEMENT_RESULT_FINISHED + System.lineSeparator() + TestConsts.STR_0,
                FileUtils.readCharsUsingUtf8(announcementFile));

        FileUtils.deleteRecursively(workDir);
        Assert.assertFalse(workDir.exists());
    }

    @Test
    public void test_announceException() throws Exception {
        File workDir = MimasJdkExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        File announcementFile = new File(workDir, RuntimeUtils.ANNOUNCEMENT_FILE_NAME);
        Assert.assertFalse(announcementFile.exists());

        Exception e = new RuntimeException();
        RuntimeUtils.announceException(workDir, e);
        Assert.assertEquals(RuntimeUtils.ANNOUNCEMENT_RESULT_EXCEPTION + System.lineSeparator() + e.toString(),
                FileUtils.readCharsUsingUtf8(announcementFile));

        FileUtils.deleteRecursively(workDir);
        Assert.assertFalse(workDir.exists());
    }
}
