package net.milanqiu.mimas.guava.io;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.TreeTraverser;
import com.google.common.io.*;
import com.google.common.io.Files;
import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.system.MimasTplTrialConvention;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * <p>Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class FilesTest {

    @Before
    public void setUp() throws Exception {
        GuavaIoTestUtils.assertTestFileNotExists();
    }

    @After
    public void tearDown() throws Exception {
        GuavaIoTestUtils.deleteTestFile();
    }

    @Test
    public void test_asByteSource() throws Exception {
        createTestFileOfByte();
        ByteSource bs = Files.asByteSource(TEST_FILE);
        Assert.assertArrayEquals(BYTE_ARR, bs.read());
    }

    @Test
    public void test_asByteSink() throws Exception {
        ByteSink bsnk = Files.asByteSink(TEST_FILE);
        bsnk.write(BYTE_ARR);
        checkTestFileOfByte();
    }

    @Test
    public void test_asCharSource() throws Exception {
        createTestFileOfChar();
        CharSource cs = Files.asCharSource(TEST_FILE, StandardCharsets.UTF_8);
        Assert.assertEquals(STR_OF_CHAR_ARR, cs.read());
    }

    @Test
    public void test_asCharSink() throws Exception {
        CharSink csnk = Files.asCharSink(TEST_FILE, StandardCharsets.UTF_8);
        csnk.write(STR_OF_CHAR_ARR);
        checkTestFileOfChar();
    }

    @Test
    public void test_createParentDirs() throws Exception {
        /*
            createParentDirs(File)
            Creates necessary but nonexistent parent directories of the file.
         */
        File unitTestTempDir = MimasTplTrialConvention.getSingleton().getTestTempDir();
        File file = FileUtils.getSubFile(unitTestTempDir, "temp2", "temp3", "temp.tmp");
        File dir3 = FileUtils.getSubFile(unitTestTempDir, "temp2", "temp3");
        File dir2 = FileUtils.getSubFile(unitTestTempDir, "temp2");

        Assert.assertFalse(file.exists());
        Assert.assertFalse(dir3.exists());
        Assert.assertFalse(dir2.exists());

        Files.createParentDirs(file);
        Assert.assertFalse(file.exists());
        Assert.assertTrue(dir3.exists());
        Assert.assertTrue(dir2.exists());

        Files.createParentDirs(file);
        Assert.assertFalse(file.exists());
        Assert.assertTrue(dir3.exists());
        Assert.assertTrue(dir2.exists());

        java.nio.file.Files.delete(dir3.toPath());
        java.nio.file.Files.delete(dir2.toPath());
        Assert.assertFalse(file.exists());
        Assert.assertFalse(dir3.exists());
        Assert.assertFalse(dir2.exists());
    }

    @Test
    public void test_getFileExtension() throws Exception {
        /*
            getFileExtension(String)
            Gets the file extension of the file described by the path.
         */
        Assert.assertEquals("tmp", Files.getFileExtension("C:\\aaa\\bbb\\ccc.tmp"));
        Assert.assertEquals("tmp", Files.getFileExtension("/C:/aaa/bbb/ccc.tmp"));
        Assert.assertEquals("tmp", Files.getFileExtension("/aaa/bbb/ccc.tmp"));

        Assert.assertEquals("", Files.getFileExtension("C:\\aaa\\bbb\\ccc"));
        Assert.assertEquals("", Files.getFileExtension("/C:/aaa/bbb/ccc"));
        Assert.assertEquals("", Files.getFileExtension("/aaa/bbb/ccc"));
    }

    @Test
    public void test_getNameWithoutExtension() throws Exception {
        /*
            getNameWithoutExtension(String)
            Gets the name of the file with its extension removed.
         */
        Assert.assertEquals("ccc", Files.getNameWithoutExtension("C:\\aaa\\bbb\\ccc.tmp"));
        Assert.assertEquals("ccc", Files.getNameWithoutExtension("/C:/aaa/bbb/ccc.tmp"));
        Assert.assertEquals("ccc", Files.getNameWithoutExtension("/aaa/bbb/ccc.tmp"));

        Assert.assertEquals("ccc", Files.getNameWithoutExtension("C:\\aaa\\bbb\\ccc"));
        Assert.assertEquals("ccc", Files.getNameWithoutExtension("/C:/aaa/bbb/ccc"));
        Assert.assertEquals("ccc", Files.getNameWithoutExtension("/aaa/bbb/ccc"));
    }

    @Test
    public void test_simplifyPath() throws Exception {
        /*
            simplifyPath(String)
            Cleans up the path. Not always consistent with your filesystem; test carefully!

            Returns the lexically cleaned form of the path name, usually (but not always) equivalent to the original.
            The following heuristics are used:
             - empty string becomes .
             - . stays as .
             - fold out ./
             - fold out ../ when possible
             - collapse multiple slashes
             - delete trailing slashes (unless the path is just "/")
            These heuristics do not always match the behavior of the filesystem. In particular, consider the path
            a/../b, which simplifyPath will change to b. If a is a symlink to x, a/../b may refer to a sibling of x,
            rather than the sibling of a referred to by b.
         */
        Assert.assertEquals(".", Files.simplifyPath(""));
        Assert.assertEquals(".", Files.simplifyPath("."));
        Assert.assertEquals("aaa/bbb", Files.simplifyPath("./aaa/bbb"));
        Assert.assertEquals("bbb", Files.simplifyPath("aaa/../bbb"));
        Assert.assertEquals("../bbb", Files.simplifyPath("aaa/../../bbb"));
        Assert.assertEquals("aaa/bbb", Files.simplifyPath("aaa///bbb"));
        Assert.assertEquals("aaa/bbb", Files.simplifyPath("aaa/bbb/"));
    }

    @Test
    public void test_fileTreeTraverser() throws Exception {
        /*
            fileTreeTraverser()
            Returns a TreeTraverser that can traverse file trees.
         */
        File unitTestTempDir = MimasTplTrialConvention.getSingleton().getTestTempDir();
        Assert.assertTrue(FileUtils.getSubFile(unitTestTempDir, "aaa", "bbb", "ccc").mkdirs());
        Assert.assertTrue(FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd", "eee").mkdirs());
        Assert.assertTrue(FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd", "fff").mkdirs());
        // then the file tree will be
        //              aaa
        //            /     \
        //          bbb     ddd
        //        /       /     \
        //      ccc     eee     fff

        TreeTraverser<File> tt = Files.fileTreeTraverser();

        // children()
        Assert.assertEquals(ImmutableList.of(
                FileUtils.getSubFile(unitTestTempDir, ".gitkeep"),
                FileUtils.getSubFile(unitTestTempDir, "aaa")
        ), tt.children(unitTestTempDir));
        Assert.assertEquals(ImmutableList.of(
                FileUtils.getSubFile(unitTestTempDir, "aaa", "bbb"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd")
        ), tt.children(FileUtils.getSubFile(unitTestTempDir, "aaa")));

        // preOrderTraversal()
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of(
                unitTestTempDir,
                FileUtils.getSubFile(unitTestTempDir, ".gitkeep"),
                FileUtils.getSubFile(unitTestTempDir, "aaa"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "bbb"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "bbb", "ccc"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd", "eee"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd", "fff")
        ), tt.preOrderTraversal(unitTestTempDir)));

        // postOrderTraversal()
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of(
                FileUtils.getSubFile(unitTestTempDir, ".gitkeep"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "bbb", "ccc"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "bbb"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd", "eee"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd", "fff"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd"),
                FileUtils.getSubFile(unitTestTempDir, "aaa"),
                unitTestTempDir
        ), tt.postOrderTraversal(unitTestTempDir)));

        // breadthFirstTraversal()
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of(
                unitTestTempDir,
                FileUtils.getSubFile(unitTestTempDir, ".gitkeep"),
                FileUtils.getSubFile(unitTestTempDir, "aaa"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "bbb"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "bbb", "ccc"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd", "eee"),
                FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd", "fff")
        ), tt.breadthFirstTraversal(unitTestTempDir)));

        java.nio.file.Files.delete(FileUtils.getSubFile(unitTestTempDir, "aaa", "bbb", "ccc").toPath());
        java.nio.file.Files.delete(FileUtils.getSubFile(unitTestTempDir, "aaa", "bbb").toPath());
        java.nio.file.Files.delete(FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd", "eee").toPath());
        java.nio.file.Files.delete(FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd", "fff").toPath());
        java.nio.file.Files.delete(FileUtils.getSubFile(unitTestTempDir, "aaa", "ddd").toPath());
        java.nio.file.Files.delete(FileUtils.getSubFile(unitTestTempDir, "aaa").toPath());
    }
}
