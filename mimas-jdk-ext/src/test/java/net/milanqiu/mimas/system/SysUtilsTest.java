package net.milanqiu.mimas.system;

import org.junit.Test;

/**
 * Creation Date: 2014-11-2
 * @author Milan Qiu
 */
public class SysUtilsTest {

    @Test
    public void test_getClassSourceLocation() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(SysUtils.getClassSourceLocation(SysUtilsTest.class)).append(System.lineSeparator());
        sb.append(SysUtils.getClassSourceLocation(SysUtils.class));
        MimasJdkExtConvention.getSingleton().writeWorkFileInTestOutDir(sb);
    }

    @Test
    public void test_getClassSourceDir() throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append(SysUtils.getClassSourceDir(SysUtilsTest.class)).append(System.lineSeparator());
        sb.append(SysUtils.getClassSourceDir(SysUtils.class));
        MimasJdkExtConvention.getSingleton().writeWorkFileInTestOutDir(sb);
    }
}
