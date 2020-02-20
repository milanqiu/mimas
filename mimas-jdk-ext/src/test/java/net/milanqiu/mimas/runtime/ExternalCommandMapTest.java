package net.milanqiu.mimas.runtime;

import net.milanqiu.mimas.config.MimasJdkExtProjectConfig;
import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

/**
 * Creation Date: 2020-02-20
 * @author Milan Qiu
 */
public class ExternalCommandMapTest {

    @Test
    public void test_put_executeAndAnnounce() throws Exception {
        File workDir = MimasJdkExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        File announcementFile = new File(workDir, RuntimeUtils.ANNOUNCEMENT_FILE_NAME);

        ExternalCommandMap commands = new ExternalCommandMap();
        commands.put("c1", () -> "111");
        commands.put("c2", () -> "222");
        commands.put("ce", () -> Integer.toString(1/0));

        commands.executeAndAnnounce("c1", workDir);
        Assert.assertEquals(RuntimeUtils.ANNOUNCEMENT_RESULT_FINISHED + System.lineSeparator() + "111",
                FileUtils.readCharsUsingUtf8(announcementFile));

        commands.executeAndAnnounce("c2", workDir);
        Assert.assertEquals(RuntimeUtils.ANNOUNCEMENT_RESULT_FINISHED + System.lineSeparator() + "222",
                FileUtils.readCharsUsingUtf8(announcementFile));

        commands.executeAndAnnounce("ce", workDir);
        Assert.assertEquals(RuntimeUtils.ANNOUNCEMENT_RESULT_EXCEPTION + System.lineSeparator() + "java.lang.ArithmeticException: / by zero",
                FileUtils.readCharsUsingUtf8(announcementFile));

        AssertExt.assertExceptionThrown(() -> {
            commands.executeAndAnnounce("c0", workDir);
        }, IllegalArgumentException.class, "command name not found: c0");

        FileUtils.deleteRecursively(workDir);
        Assert.assertFalse(workDir.exists());
    }
}
