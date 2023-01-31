package net.milanqiu.mimas.runtime;

import net.milanqiu.mimas.instrumentation.exception.CodeContextException;
import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.junit.AssertExt;
import net.milanqiu.mimas.string.EncodingUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.charset.StandardCharsets;

/**
 * Creation Date: 2014-11-03
 * @author Milan Qiu
 */
public class MavenProjectConventionTest {

    private static final String ROOT_DIR = "C:/BFF45C34-C279-E61C-119D-C53434174908/";

    private static class DummyProjectConvention extends MavenProjectConvention {

        private static DummyProjectConvention singleton;

        public static DummyProjectConvention getSingleton() {
            if (singleton == null) {
                singleton = new DummyProjectConvention();
            }
            return singleton;
        }

        private DummyProjectConvention() {}

        @Override
        public String getSourceDir() {
            return ROOT_DIR + "workspace/project/target/classes/";
        }

        @Override
        public String getWorkspaceName() {
            return "workspace";
        }

        @Override
        public String getProjectName() {
            return "project";
        }
    }

    private static class WrongProjectConvention extends MavenProjectConvention {

        private static WrongProjectConvention singleton;

        public static WrongProjectConvention getSingleton() {
            if (singleton == null) {
                singleton = new WrongProjectConvention();
            }
            return singleton;
        }

        private WrongProjectConvention() {}

        @Override
        public String getSourceDir() {
            return ROOT_DIR + "workspaceWRONG/project/target/classes/";
        }

        @Override
        public String getWorkspaceName() {
            return "workspace";
        }

        @Override
        public String getProjectName() {
            return "project";
        }
    }

    DummyProjectConvention dpc;
    WrongProjectConvention wpc;

    @Before
    public void setUp() throws Exception {
        dpc = DummyProjectConvention.getSingleton();
        wpc = WrongProjectConvention.getSingleton();
    }

    @Test
    public void test_getWorkspaceDir() throws Exception {
        Assert.assertEquals(new File(ROOT_DIR + "workspace/"), dpc.getWorkspaceDir());

        AssertExt.assertExceptionThrown(wpc::getWorkspaceDir, CodeContextException.class);
    }

    @Test
    public void test_getProjectDir() throws Exception {
        Assert.assertEquals(new File(ROOT_DIR + "workspace/project/"), dpc.getProjectDir());

        AssertExt.assertExceptionThrown(wpc::getProjectDir, CodeContextException.class);
    }

    @Test
    public void test_getFilesDir() throws Exception {
        Assert.assertEquals(new File(ROOT_DIR + "workspace/files/"), dpc.getFilesDir());
        Assert.assertEquals(new File(ROOT_DIR + "workspace/files/aaa/"), dpc.getFilesDir("aaa"));

        AssertExt.assertExceptionThrown(wpc::getFilesDir, CodeContextException.class);
    }

    @Test
    public void test_getTestTempDir() throws Exception {
        Assert.assertEquals(new File(ROOT_DIR + "workspace/files/test_temp/"), dpc.getTestTempDir());

        AssertExt.assertExceptionThrown(wpc::getTestTempDir, CodeContextException.class);
    }

    @Test
    public void test_prepareDirInTestTempDir() throws Exception {
        File expectedDir = new File(ROOT_DIR + "workspace/files/test_temp/" + getClass().getName() + ".test_prepareDirInTestTempDir/");

        Assert.assertFalse(expectedDir.exists());
        Assert.assertEquals(expectedDir, dpc.prepareDirInTestTempDir());
        Assert.assertTrue(expectedDir.exists());

        FileUtils.deleteRecursively(new File(ROOT_DIR));

        AssertExt.assertExceptionThrown(wpc::prepareDirInTestTempDir, CodeContextException.class);
    }

    @Test
    public void test_getTestOutDir() throws Exception {
        Assert.assertEquals(new File(ROOT_DIR + "workspace/files/test_out/"), dpc.getTestOutDir());

        AssertExt.assertExceptionThrown(wpc::getTestOutDir, CodeContextException.class);
    }

    @Test
    public void test_prepareFileInTestOutDir() throws Exception {
        File expectedFile = new File(ROOT_DIR + "workspace/files/test_out/" + getClass().getName() + ".test_prepareFileInTestOutDir.tmp");

        Assert.assertFalse(expectedFile.exists());
        Assert.assertEquals(expectedFile, dpc.prepareFileInTestOutDir());
        Assert.assertFalse(expectedFile.exists());

        AssertExt.assertExceptionThrown(wpc::prepareFileInTestOutDir, CodeContextException.class);
    }

    @Test
    public void test_writeFileInTestOutDir() throws Exception {
        File expectedFile = new File(ROOT_DIR + "workspace/files/test_out/" + getClass().getName() + ".test_writeFileInTestOutDir.tmp");

        Assert.assertTrue(expectedFile.getParentFile().mkdirs());;
        String content = EncodingUtils.getValidUnicodeString();

        Assert.assertFalse(expectedFile.exists());
        dpc.writeFileInTestOutDir(content, StandardCharsets.UTF_16LE);
        Assert.assertTrue(expectedFile.exists());
        Assert.assertEquals(content, FileUtils.readChars(expectedFile, StandardCharsets.UTF_16LE));

        FileUtils.deleteRecursively(new File(ROOT_DIR));

        AssertExt.assertExceptionThrown(() -> wpc.writeFileInTestOutDir(content, StandardCharsets.UTF_16LE), CodeContextException.class);
    }

    @Test
    public void test_writeFileInTestOutDirUsingUtf8() throws Exception {
        File expectedFile = new File(ROOT_DIR + "workspace/files/test_out/" + getClass().getName() + ".test_writeFileInTestOutDirUsingUtf8.tmp");

        Assert.assertTrue(expectedFile.getParentFile().mkdirs());;
        String content = EncodingUtils.getValidUnicodeString();

        Assert.assertFalse(expectedFile.exists());
        dpc.writeFileInTestOutDirUsingUtf8(content);
        Assert.assertTrue(expectedFile.exists());
        Assert.assertEquals(content, FileUtils.readCharsUsingUtf8(expectedFile));

        FileUtils.deleteRecursively(new File(ROOT_DIR));

        AssertExt.assertExceptionThrown(() -> wpc.writeFileInTestOutDirUsingUtf8(content), CodeContextException.class);
    }
}
