package net.milanqiu.mimas.io;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * <p>Creation Date: 2014-11-3
 * @author Milan Qiu
 */
public class FileUtilsTest {

    @Test
    public void test_getAncestor() throws Exception {
        Assert.assertEquals(new File("/D:/Career/Qiu/mimas"),
                FileUtils.findAncestor(new File("/D:/Career/Qiu/mimas/mimas-jdk-ext/target/classes"), "mimas"));
        Assert.assertEquals(new File("D:\\Career\\Qiu\\mimas\\mimas-jdk-ext\\"),
                FileUtils.findAncestor(new File("D:\\Career\\Qiu\\mimas\\mimas-jdk-ext\\target\\classes\\"), "mimas-jdk-ext"));
        Assert.assertEquals(new File("/mimas/mimas-jdk-ext/target"),
                FileUtils.findAncestor(new File("/mimas/mimas-jdk-ext/target/classes"), "target"));
        Assert.assertNull(FileUtils.findAncestor(new File(""), "F166BC75-C87F-DB35-75B1-AC2FD5C9A16A"));
    }

    @Test
    public void test_getSubFile() throws Exception {
        Assert.assertEquals(new File("aaa/bbb/ccc"),
                FileUtils.getSubFile(new File("aaa/bbb"), "ccc"));
        Assert.assertEquals(new File("aaa/bbb/ccc/ddd/eee"),
                FileUtils.getSubFile(new File("aaa/bbb"), "ccc", "ddd", "eee"));
        Assert.assertEquals(new File("aaa/bbb/ccc/ddd/eee"),
                FileUtils.getSubFile(new File("aaa/bbb"), Arrays.asList("ccc", "ddd", "eee")));
    }
}
