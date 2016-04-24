package net.milanqiu.mimas.guava.io;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.TreeTraverser;
import com.google.common.hash.Hashing;
import com.google.common.io.*;
import net.milanqiu.mimas.collect.CollectionUtils;
import net.milanqiu.mimas.config.MimasTplTrialProjectConfig;
import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.lang.TypeUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

import static net.milanqiu.mimas.guava.io.GuavaIoTestUtils.*;

/**
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class FilesTest {

    private File workDir;
    private File workFile;

    @After
    public void tearDown() throws Exception {
        if (workDir != null) {
            FileUtils.deleteRecursively(workDir);
            Assert.assertFalse(workDir.exists());
        }
    }

    @Test
    public void test_asByteSource() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        createTestFileOfByte(workFile);
        ByteSource bs = Files.asByteSource(workFile);
        Assert.assertArrayEquals(ALL_BYTE_VALUES, bs.read());
    }

    @Test
    public void test_asByteSink() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        ByteSink bsnk = Files.asByteSink(workFile);
        bsnk.write(ALL_BYTE_VALUES);
        assertTestFileOfByte(workFile);
    }

    @Test
    public void test_asCharSource() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        createTestFileOfChar(workFile);
        CharSource cs = Files.asCharSource(workFile, StandardCharsets.UTF_8);
        Assert.assertEquals(UNICODE_CHAR_VALUES_STR, cs.read());
    }

    @Test
    public void test_asCharSink() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        CharSink csnk = Files.asCharSink(workFile, StandardCharsets.UTF_8);
        csnk.write(UNICODE_CHAR_VALUES_STR);
        assertTestFileOfChar(workFile);
    }

    @Test
    public void test_createParentDirs() throws Exception {
        /*
            void createParentDirs(File)
            Creates necessary but nonexistent parent directories of the file.
        */
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        File lv1Dir = FileUtils.getSubFile(workDir, "lv1");
        File lv2Dir = FileUtils.getSubFile(workDir, "lv1", "lv2");
        File file = FileUtils.getSubFile(workDir, "lv1", "lv2", "temp");

        Assert.assertFalse(lv1Dir.exists());
        Assert.assertFalse(lv2Dir.exists());
        Assert.assertFalse(file.exists());

        Files.createParentDirs(file);
        Assert.assertTrue(lv1Dir.exists());
        Assert.assertTrue(lv2Dir.exists());
        Assert.assertFalse(file.exists());

        Files.createParentDirs(file);
        Assert.assertTrue(lv1Dir.exists());
        Assert.assertTrue(lv2Dir.exists());
        Assert.assertFalse(file.exists());
    }

    @Test
    public void test_getFileExtension() throws Exception {
        /*
            String getFileExtension(String)
            Gets the file extension of the file described by the path.
        */
        Assert.assertEquals("tmp", Files.getFileExtension("C:/aaa/bbb/ccc.tmp"));
        Assert.assertEquals("tmp", Files.getFileExtension("aaa/bbb/ccc.tmp"));

        Assert.assertEquals("", Files.getFileExtension("C:/aaa/bbb/ccc"));
        Assert.assertEquals("", Files.getFileExtension("aaa/bbb/ccc"));
    }

    @Test
    public void test_getNameWithoutExtension() throws Exception {
        /*
            String getNameWithoutExtension(String)
            Gets the name of the file with its extension removed.
        */
        Assert.assertEquals("ccc", Files.getNameWithoutExtension("C:/aaa/bbb/ccc.tmp"));
        Assert.assertEquals("ccc", Files.getNameWithoutExtension("aaa/bbb/ccc.tmp"));

        Assert.assertEquals("ccc", Files.getNameWithoutExtension("C:/aaa/bbb/ccc"));
        Assert.assertEquals("ccc", Files.getNameWithoutExtension("aaa/bbb/ccc"));
    }

    @Test
    public void test_simplifyPath() throws Exception {
        /*
            String simplifyPath(String)
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
    public void test_fileTreeTraverser_TreeTraverser_children_preOrderTraversal_postOrderTraversal_breadthFirstTraversal() throws Exception {
        /*
            fileTreeTraverser()
            Returns a TreeTraverser that can traverse file trees.
        */
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();

        File aaa = FileUtils.getSubFile(workDir, "aaa");
        File bbb = FileUtils.getSubFile(workDir, "aaa", "bbb");
        File ccc = FileUtils.getSubFile(workDir, "aaa", "bbb", "ccc");
        File ddd = FileUtils.getSubFile(workDir, "aaa", "ddd");
        File eee = FileUtils.getSubFile(workDir, "aaa", "ddd", "eee");
        File fff = FileUtils.getSubFile(workDir, "aaa", "ddd", "fff");
        Assert.assertTrue(ccc.mkdirs());
        Assert.assertTrue(eee.mkdirs());
        Assert.assertTrue(fff.createNewFile());
        // then the file tree will be
        //              aaa
        //            /     \
        //          bbb     ddd
        //        /       /     \
        //      ccc     eee     fff

        TreeTraverser<File> tt = Files.fileTreeTraverser();

        // children()
        Assert.assertEquals(ImmutableList.of(aaa),      tt.children(workDir));
        Assert.assertEquals(ImmutableList.of(bbb, ddd), tt.children(aaa));
        Assert.assertEquals(ImmutableList.of(ccc),      tt.children(bbb));
        Assert.assertEquals(ImmutableList.of(),         tt.children(ccc));
        Assert.assertEquals(ImmutableList.of(eee, fff), tt.children(ddd));
        Assert.assertEquals(ImmutableList.of(),         tt.children(eee));
        Assert.assertEquals(ImmutableList.of(),         tt.children(fff));

        // preOrderTraversal()
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of(workDir, aaa, bbb, ccc, ddd, eee, fff),
                tt.preOrderTraversal(workDir)));

        // postOrderTraversal()
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of(ccc, bbb, eee, fff, ddd, aaa, workDir),
                tt.postOrderTraversal(workDir)));

        // breadthFirstTraversal()
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of(workDir, aaa, bbb, ddd, ccc, eee, fff),
                tt.breadthFirstTraversal(workDir)));
    }

    @Test
    public void test_newReader() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        createTestFileOfChar(workFile);
        try (BufferedReader br = Files.newReader(workFile, StandardCharsets.UTF_8)) {
            Assert.assertEquals(UNICODE_CHAR_VALUES_STR, CharStreams.toString(br));
        }
    }

    @Test
    public void test_newWriter() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        try (BufferedWriter bw = Files.newWriter(workFile, StandardCharsets.UTF_8)) {
            bw.write(UNICODE_CHAR_VALUES_STR);
            bw.flush();
            assertTestFileOfChar(workFile);
        }
    }

    @Test
    public void test_toByteArray() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        createTestFileOfByte(workFile);
        Assert.assertArrayEquals(ALL_BYTE_VALUES, Files.toByteArray(workFile));
    }

    @Test
    public void test_toString() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        createTestFileOfChar(workFile);
        Assert.assertEquals(UNICODE_CHAR_VALUES_STR, Files.toString(workFile, StandardCharsets.UTF_8));
    }

    @Test
    public void test_write() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        // void write(byte[] from, File to)
        {
            Files.write(ALL_BYTE_VALUES, workFile);
            assertTestFileOfByte(workFile);
        }

        // void write(CharSequence from, File to, Charset charset)
        {
            Files.write(UNICODE_CHAR_VALUES_STR, workFile, StandardCharsets.UTF_8);
            assertTestFileOfChar(workFile);
        }
    }

    @Test
    public void test_append() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        Files.write(UNICODE_CHAR_VALUES_STR, workFile, StandardCharsets.UTF_8);
        Assert.assertEquals(UNICODE_CHAR_VALUES_STR, Files.toString(workFile, StandardCharsets.UTF_8));

        Files.append(UNICODE_CHAR_VALUES_STR, workFile, StandardCharsets.UTF_8);
        Assert.assertEquals(UNICODE_CHAR_VALUES_STR + UNICODE_CHAR_VALUES_STR, Files.toString(workFile, StandardCharsets.UTF_8));
    }

    @Test
    public void test_copy() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");
        File toFile = new File(workDir, "to");

        // void copy(File from, OutputStream to)
        {
            createTestFileOfByte(workFile);
            OutputStream os = new ByteArrayOutputStream();
            Files.copy(workFile, os);
            Assert.assertArrayEquals(ALL_BYTE_VALUES, ((ByteArrayOutputStream) os).toByteArray());
        }

        // void copy(File from, File to)
        {
            createTestFileOfByte(workFile);
            Assert.assertTrue(workFile.exists());
            Assert.assertFalse(toFile.exists());

            Files.copy(workFile, toFile);
            Assert.assertTrue(workFile.exists());
            Assert.assertTrue(toFile.exists());
            Assert.assertArrayEquals(ALL_BYTE_VALUES, Files.toByteArray(toFile));
        }

        // void copy(File from, Charset charset, Appendable to)
        {
            createTestFileOfChar(workFile);
            Appendable a = new CharArrayWriter();
            Files.copy(workFile, StandardCharsets.UTF_8, a);
            Assert.assertEquals(UNICODE_CHAR_VALUES_STR, a.toString());
        }
    }

    @Test
    public void test_equal() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");
        File workFile2 = new File(workDir, "temp2");

        createTestFileOfByte(workFile);
        Files.copy(workFile, workFile2);
        Assert.assertTrue(Files.equal(workFile, workFile2));

        byte[] CHANGED_ALL_BYTE_VALUES = TypeUtils.getAllByteValues();
        CHANGED_ALL_BYTE_VALUES[CHANGED_ALL_BYTE_VALUES.length-1] = 0;
        Files.write(CHANGED_ALL_BYTE_VALUES, workFile2);
        Assert.assertFalse(Files.equal(workFile, workFile2));
    }

    @Test
    public void test_createTempDir() throws Exception {
        File file = Files.createTempDir();
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(file.toString());
    }

    @Test
    public void test_touch() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        Assert.assertFalse(workFile.exists());
        Files.touch(workFile);
        Assert.assertTrue(workFile.exists());
        Assert.assertTrue(Files.asByteSource(workFile).isEmpty());

        long lastModified1 = workFile.lastModified();
        TimeUnit.MILLISECONDS.sleep(100);
        Files.touch(workFile);
        long lastModified2 = workFile.lastModified();
        Assert.assertTrue(lastModified2 - lastModified1 >= 100);
    }

    @Test
    public void test_move() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");
        File toFile = new File(workDir, "to");

        createTestFileOfByte(workFile);
        Assert.assertTrue(workFile.exists());
        Assert.assertFalse(toFile.exists());

        Files.move(workFile, toFile);
        Assert.assertFalse(workFile.exists());
        Assert.assertTrue(toFile.exists());
        assertTestFileOfByte(toFile);
    }

    @Test
    public void test_readFirstLine() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        createTestFileOfText(workFile);
        Assert.assertEquals(MULTILINE_LIST.get(0), Files.readFirstLine(workFile, StandardCharsets.UTF_8));
    }

    @Test
    public void test_readLines() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        // List<String> readLines(File file, Charset charset)
        {
            createTestFileOfText(workFile);
            Assert.assertEquals(MULTILINE_LIST, Files.readLines(workFile, StandardCharsets.UTF_8));
        }

        // T readLines(File file, Charset charset, LineProcessor<T> callback)
        {
            createTestFileOfText(workFile);
            Assert.assertEquals(CollectionUtils.getSumLength(MULTILINE_LIST),
                    (int) Files.readLines(workFile, StandardCharsets.UTF_8, new LineProcessor<Integer>() {
                        int sumLength = 0;

                        @Override
                        public boolean processLine(String s) throws IOException {
                            sumLength += s.length();
                            return true;
                        }

                        @Override
                        public Integer getResult() {
                            return sumLength;
                        }
                    }));
        }
    }

    @Test
    public void test_readBytes() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        createTestFileOfByte(workFile);
        Assert.assertEquals("-128", Files.readBytes(workFile, new ByteProcessor<String>() {
            int sum = 0;

            @Override
            public boolean processBytes(byte[] bytes, int i, int i2) throws IOException {
                for (int index = i; index < i2; index++)
                    sum += bytes[index];
                return true;
            }

            @Override
            public String getResult() {
                return String.valueOf(sum);
            }
        }));
    }

    @Test
    public void test_hash() throws Exception {
        workDir = MimasTplTrialProjectConfig.getSingleton().prepareDirInTestTempDir();
        workFile = new File(workDir, "temp");

        createTestFileOfByte(workFile);
        Assert.assertEquals("03f9522e6aa992641525359b6c67cb55", Files.hash(workFile, Hashing.md5()).toString());
    }

    @Test
    public void test_isDirectory() throws Exception {
        Predicate<File> p = Files.isDirectory();
        Assert.assertTrue(p.apply(MimasTplTrialProjectConfig.getSingleton().getWorkspaceDir()));
    }

    @Test
    public void test_isFile() throws Exception {
        Predicate<File> p = Files.isFile();
        Assert.assertFalse(p.apply(MimasTplTrialProjectConfig.getSingleton().getWorkspaceDir()));
    }
}
