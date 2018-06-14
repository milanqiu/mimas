package net.milanqiu.mimas.io;

import net.milanqiu.mimas.config.MimasJdkExtProjectConfig;
import net.milanqiu.mimas.junit.AssertExt;
import net.milanqiu.mimas.lang.TypeUtils;
import net.milanqiu.mimas.string.EncodingUtils;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.nio.file.NoSuchFileException;
import java.util.Arrays;

/**
 * Creation Date: 2014-11-03
 * @author Milan Qiu
 */
public class FileUtilsTest {

    @Test
    public void test_findAncestor() throws Exception {
        Assert.assertEquals(new File("C:/aaa/bbb"),
                FileUtils.findAncestor(new File("C:/aaa/bbb/bbbccc/bbb ddd/eee"), "bbb"));
        Assert.assertEquals(new File("aaa/bbb"),
                FileUtils.findAncestor(new File("aaa/bbb/bbbccc/bbb ddd/eee"), "bbb"));

        Assert.assertNull(FileUtils.findAncestor(new File(""), "F166BC75-C87F-DB35-75B1-AC2FD5C9A16A"));
    }

    @Test
    public void test_getSubFile() throws Exception {
        // File getSubFile(File dir, String subName)
        Assert.assertEquals(new File("C:/aaa/bbb/ccc"),
                FileUtils.getSubFile(new File("C:/aaa/bbb"), "ccc"));
        Assert.assertEquals(new File("aaa/bbb/ccc"),
                FileUtils.getSubFile(new File("aaa/bbb"), "ccc"));

        // File getSubFile(File dir, String... subNamesLbl)
        Assert.assertEquals(new File("C:/aaa/bbb/ccc/ddd/eee"),
                FileUtils.getSubFile(new File("C:/aaa/bbb"), "ccc", "ddd", "eee"));
        Assert.assertEquals(new File("aaa/bbb/ccc/ddd/eee"),
                FileUtils.getSubFile(new File("aaa/bbb"), "ccc", "ddd", "eee"));

        // File getSubFile(File dir, Iterable<String> subNamesLbl)
        Assert.assertEquals(new File("C:/aaa/bbb/ccc/ddd/eee"),
                FileUtils.getSubFile(new File("C:/aaa/bbb"), Arrays.asList("ccc", "ddd", "eee")));
        Assert.assertEquals(new File("aaa/bbb/ccc/ddd/eee"),
                FileUtils.getSubFile(new File("aaa/bbb"), Arrays.asList("ccc", "ddd", "eee")));
    }

    @Test
    public void test_deleteDirectoryContents() throws Exception {
        File workDir = MimasJdkExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        Assert.assertTrue(FileUtils.getSubFile(workDir, "aaa", "bbb", "ccc").mkdirs());
        Assert.assertTrue(FileUtils.getSubFile(workDir, "aaa", "ddd", "eee").mkdirs());
        Assert.assertTrue(FileUtils.getSubFile(workDir, "aaa", "ddd", "fff").createNewFile());

        AssertExt.assertExceptionThrown(() -> FileUtils.deleteDirectoryContents(FileUtils.getSubFile(workDir, "aaa", "ddd", "fff")), IllegalArgumentException.class);

        FileUtils.deleteDirectoryContents(workDir);
        Assert.assertEquals(0, workDir.listFiles().length);
        Assert.assertTrue(workDir.exists());
        Assert.assertTrue(workDir.delete());

        AssertExt.assertExceptionThrown(() -> FileUtils.deleteDirectoryContents(workDir), NoSuchFileException.class);
    }

    @Test
    public void test_deleteRecursively() throws Exception {
        File workDir = MimasJdkExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        Assert.assertTrue(FileUtils.getSubFile(workDir, "aaa", "bbb", "ccc").mkdirs());
        Assert.assertTrue(FileUtils.getSubFile(workDir, "aaa", "ddd", "eee").mkdirs());
        Assert.assertTrue(FileUtils.getSubFile(workDir, "aaa", "ddd", "fff").createNewFile());

        FileUtils.deleteRecursively(workDir);
        Assert.assertFalse(workDir.exists());

        AssertExt.assertExceptionThrown(() -> FileUtils.deleteRecursively(workDir), NoSuchFileException.class);
    }

    @Test
    public void test_deleteRecursivelyIfExists() throws Exception {
        File workDir = MimasJdkExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        Assert.assertTrue(FileUtils.getSubFile(workDir, "aaa", "bbb", "ccc").mkdirs());
        Assert.assertTrue(FileUtils.getSubFile(workDir, "aaa", "ddd", "eee").mkdirs());
        Assert.assertTrue(FileUtils.getSubFile(workDir, "aaa", "ddd", "fff").createNewFile());

        FileUtils.deleteRecursivelyIfExists(workDir);
        Assert.assertFalse(workDir.exists());

        FileUtils.deleteRecursivelyIfExists(workDir);
        Assert.assertFalse(workDir.exists());
    }

    @Test
    public void test_readBytes_writeBytes() throws Exception {
        File workDir = MimasJdkExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        File workFile = new File(workDir, "temp");
        byte[] content = TypeUtils.getAllByteValues();

        FileUtils.writeBytes(content, workFile);
        Assert.assertArrayEquals(content, FileUtils.readBytes(workFile));

        AssertExt.assertExceptionThrown(() -> FileUtils.readBytes(new File(workDir, "fake")), FileNotFoundException.class);
        AssertExt.assertExceptionThrown(() -> FileUtils.readBytes(workDir),                   FileNotFoundException.class);
        AssertExt.assertExceptionThrown(() -> FileUtils.writeBytes(content, workDir),         FileNotFoundException.class);

        FileUtils.deleteRecursively(workDir);
        Assert.assertFalse(workDir.exists());
    }

    @Test
    public void test_readChars_writeChars() throws Exception {
        File workDir = MimasJdkExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        File workFile = new File(workDir, "temp");
        String content = EncodingUtils.getValidUnicodeString();

        FileUtils.writeChars(content, workFile, StandardCharsets.UTF_16LE);
        Assert.assertEquals(content, FileUtils.readChars(workFile, StandardCharsets.UTF_16LE));
        Assert.assertNotEquals(content, FileUtils.readChars(workFile, StandardCharsets.UTF_16BE));
        Assert.assertArrayEquals(content.getBytes(StandardCharsets.UTF_16LE), FileUtils.readBytes(workFile));

        AssertExt.assertExceptionThrown(() -> FileUtils.readChars(new File(workDir, "fake"), StandardCharsets.UTF_16LE), FileNotFoundException.class);
        AssertExt.assertExceptionThrown(() -> FileUtils.readChars(workDir, StandardCharsets.UTF_16LE),                   FileNotFoundException.class);
        AssertExt.assertExceptionThrown(() -> FileUtils.writeChars(content, workDir, StandardCharsets.UTF_16LE),         FileNotFoundException.class);

        FileUtils.deleteRecursively(workDir);
        Assert.assertFalse(workDir.exists());
    }

    @Test
    public void test_readCharsUsingUtf8_writeCharsUsingUtf8() throws Exception {
        File workDir = MimasJdkExtProjectConfig.getSingleton().prepareDirInTestTempDir();
        File workFile = new File(workDir, "temp");
        String content = EncodingUtils.getValidUnicodeString();

        FileUtils.writeCharsUsingUtf8(content, workFile);
        Assert.assertEquals(content, FileUtils.readCharsUsingUtf8(workFile));
        Assert.assertNotEquals(content, FileUtils.readChars(workFile, StandardCharsets.UTF_16LE));
        Assert.assertArrayEquals(content.getBytes(StandardCharsets.UTF_8), FileUtils.readBytes(workFile));

        AssertExt.assertExceptionThrown(() -> FileUtils.readCharsUsingUtf8(new File(workDir, "fake")), FileNotFoundException.class);
        AssertExt.assertExceptionThrown(() -> FileUtils.readCharsUsingUtf8(workDir),                   FileNotFoundException.class);
        AssertExt.assertExceptionThrown(() -> FileUtils.writeCharsUsingUtf8(content, workDir),         FileNotFoundException.class);

        FileUtils.deleteRecursively(workDir);
        Assert.assertFalse(workDir.exists());
    }
}
