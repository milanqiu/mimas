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
        StringBuilder sb = new StringBuilder();
        sb.append(RuntimeUtils.getClassSourceLocation(RuntimeUtilsTest.class)).append(System.lineSeparator());
        sb.append(RuntimeUtils.getClassSourceLocation(RuntimeUtils.class));
        MimasJdkExtProjectConfig.getSingleton().writeFileInTestOutDir(sb);
    }

    @Test
    public void test_getClassSourceDir() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(RuntimeUtils.getClassSourceDir(RuntimeUtilsTest.class)).append(System.lineSeparator());
        sb.append(RuntimeUtils.getClassSourceDir(RuntimeUtils.class));
        MimasJdkExtProjectConfig.getSingleton().writeFileInTestOutDir(sb);
    }
}
