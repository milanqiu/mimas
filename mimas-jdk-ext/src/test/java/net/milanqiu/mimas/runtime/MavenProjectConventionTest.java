package net.milanqiu.mimas.runtime;

import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.instrumentation.exception.CodeContextException;
import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.junit.AssertExt;
import net.milanqiu.mimas.string.StrUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

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

        try {
            wpc.getWorkspaceDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(CodeContextException.class, e);
        }
    }

    @Test
    public void test_getProjectDir() throws Exception {
        Assert.assertEquals(new File(ROOT_DIR + "workspace/project/"), dpc.getProjectDir());

        try {
            wpc.getProjectDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(CodeContextException.class, e);
        }
    }

    @Test
    public void test_getFilesDir() throws Exception {
        Assert.assertEquals(new File(ROOT_DIR + "workspace/files/"), dpc.getFilesDir());

        try {
            wpc.getFilesDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(CodeContextException.class, e);
        }
    }

    @Test
    public void test_getTestTempDir() throws Exception {
        Assert.assertEquals(new File(ROOT_DIR + "workspace/files/test_temp/"), dpc.getTestTempDir());

        try {
            wpc.getTestTempDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(CodeContextException.class, e);
        }
    }

    @Test
    public void test_prepareDirInTestTempDir() throws Exception {
        File expectedDir = new File(ROOT_DIR + "workspace/files/test_temp/" + getClass().getName() + ".test_prepareDirInTestTempDir/");

        Assert.assertFalse(expectedDir.exists());
        Assert.assertEquals(expectedDir, dpc.prepareDirInTestTempDir());
        Assert.assertTrue(expectedDir.exists());

        FileUtils.deleteRecursively(new File(ROOT_DIR));

        try {
            wpc.prepareDirInTestTempDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(CodeContextException.class, e);
        }
    }

    @Test
    public void test_getTestOutDir() throws Exception {
        Assert.assertEquals(new File(ROOT_DIR + "workspace/files/test_out/"), dpc.getTestOutDir());

        try {
            wpc.getTestOutDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(CodeContextException.class, e);
        }
    }

    @Test
    public void test_prepareFileInTestOutDir() throws Exception {
        File expectedFile = new File(ROOT_DIR + "workspace/files/test_out/" + getClass().getName() + ".test_prepareFileInTestOutDir.tmp");

        Assert.assertFalse(expectedFile.exists());
        Assert.assertEquals(expectedFile, dpc.prepareFileInTestOutDir());
        Assert.assertFalse(expectedFile.exists());

        try {
            wpc.prepareFileInTestOutDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(CodeContextException.class, e);
        }
    }

    @Test
    public void test_writeFileInTestOutDir() throws Exception {
        File expectedFile = new File(ROOT_DIR + "workspace/files/test_out/" + getClass().getName() + ".test_writeFileInTestOutDir.tmp");

        Assert.assertTrue(expectedFile.getParentFile().mkdirs());;
        String content = StrUtils.getValidUnicodeString();

        Assert.assertFalse(expectedFile.exists());
        dpc.writeFileInTestOutDir(content);
        Assert.assertTrue(expectedFile.exists());
        Assert.assertEquals(content, FileUtils.readCharsUsingUtf8(expectedFile));

        FileUtils.deleteRecursively(new File(ROOT_DIR));

        try {
            wpc.writeFileInTestOutDir(content);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(CodeContextException.class, e);
        }
    }
}
