package net.milanqiu.mimas.runtime;

import net.milanqiu.mimas.config.MimasJdkExtProjectConfig;
import org.junit.Test;

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
}
