package net.milanqiu.mimas.system;

import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.instrumentation.exception.CodeContextException;
import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.string.StrUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * Creation Date: 2014-11-3
 * @author Milan Qiu
 */
public class MavenProjectConventionTest {

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
            return "C:/BFF45C34-C279-E61C-119D-C53434174908/workspace/project/target/classes/";
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
            return "C:/BFF45C34-C279-E61C-119D-C53434174908/workspaceWRONG/project/target/classes/";
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
        Assert.assertEquals(new File("C:/BFF45C34-C279-E61C-119D-C53434174908/workspace/"), dpc.getWorkspaceDir());

        try {
            wpc.getWorkspaceDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CodeContextException);
        }
    }

    @Test
    public void test_getProjectDir() throws Exception {
        Assert.assertEquals(new File("C:/BFF45C34-C279-E61C-119D-C53434174908/workspace/project/"), dpc.getProjectDir());

        try {
            wpc.getProjectDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CodeContextException);
        }
    }

    @Test
    public void test_getFilesDir() throws Exception {
        Assert.assertEquals(new File("C:/BFF45C34-C279-E61C-119D-C53434174908/workspace/files/"), dpc.getFilesDir());

        try {
            wpc.getFilesDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CodeContextException);
        }
    }

    @Test
    public void test_getTestTempDir() throws Exception {
        Assert.assertEquals(new File("C:/BFF45C34-C279-E61C-119D-C53434174908/workspace/files/test_temp/"), dpc.getTestTempDir());

        try {
            wpc.getTestTempDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CodeContextException);
        }
    }

    @Test
    public void test_prepareWorkDirInTestTempDir() throws Exception {
        File workDirExpected = new File("C:/BFF45C34-C279-E61C-119D-C53434174908/workspace/files/test_temp/" +
                "net.milanqiu.mimas.system.MavenProjectConventionTest.test_prepareWorkDirInTestTempDir/");

        Assert.assertEquals(workDirExpected, dpc.prepareWorkDirInTestTempDir(false));
        Assert.assertFalse(workDirExpected.exists());
        Assert.assertEquals(workDirExpected, dpc.prepareWorkDirInTestTempDir(true));
        Assert.assertTrue(workDirExpected.exists());
        Assert.assertEquals(workDirExpected, dpc.prepareWorkDirInTestTempDir(false));
        Assert.assertFalse(workDirExpected.exists());

        FileUtils.deleteRecursively(new File("C:/BFF45C34-C279-E61C-119D-C53434174908"));

        try {
            wpc.prepareWorkDirInTestTempDir(false);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CodeContextException);
        }
    }

    @Test
    public void test_getTestOutDir() throws Exception {
        Assert.assertEquals(new File("C:/BFF45C34-C279-E61C-119D-C53434174908/workspace/files/test_out/"), dpc.getTestOutDir());

        try {
            wpc.getTestOutDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CodeContextException);
        }
    }

    @Test
    public void test_prepareWorkFileInTestOutDir() throws Exception {
        File workFileExpected = new File("C:/BFF45C34-C279-E61C-119D-C53434174908/workspace/files/test_out/" +
                "net.milanqiu.mimas.system.MavenProjectConventionTest.test_prepareWorkFileInTestOutDir.tmp");

        Assert.assertEquals(workFileExpected, dpc.prepareWorkFileInTestOutDir());
        Assert.assertFalse(workFileExpected.exists());

        try {
            wpc.prepareWorkFileInTestOutDir();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CodeContextException);
        }
    }

    @Test
    public void test_writeWorkFileInTestOutDir() throws Exception {
        File workFileExpected = new File("C:/BFF45C34-C279-E61C-119D-C53434174908/workspace/files/test_out/" +
                "net.milanqiu.mimas.system.MavenProjectConventionTest.test_writeWorkFileInTestOutDir.tmp");
        Assert.assertTrue(workFileExpected.getParentFile().mkdirs());;
        String content = new String(StrUtils.getValidUnicodeCharValues());

        Assert.assertFalse(workFileExpected.exists());
        dpc.writeWorkFileInTestOutDir(content);
        Assert.assertTrue(workFileExpected.exists());
        Assert.assertEquals(content, FileUtils.readCharsUsingUtf8(workFileExpected));

        FileUtils.deleteRecursively(new File("C:/BFF45C34-C279-E61C-119D-C53434174908"));

        try {
            wpc.writeWorkFileInTestOutDir(content);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof CodeContextException);
        }
    }
}
