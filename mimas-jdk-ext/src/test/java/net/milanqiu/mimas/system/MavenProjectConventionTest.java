package net.milanqiu.mimas.system;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

/**
 * <p>Creation Date: 2014-11-3
 * @author Milan Qiu
 */
public class MavenProjectConventionTest {

    static class DummyProjectConvention extends MavenProjectConvention {

        private static DummyProjectConvention singleton;

        public static DummyProjectConvention getSingleton() {
            if (singleton == null) {
                singleton = new DummyProjectConvention();
            }
            return singleton;
        }

        private DummyProjectConvention() {}

        @Override
        public String getSourcePath() {
            return "/c:/my work/workspace/project/target/classes/";
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

    @Before
    public void setUp() throws Exception {
        dpc = DummyProjectConvention.getSingleton();
    }

    @Test
    public void test_getWorkspaceDir() throws Exception {
        Assert.assertEquals(new File("/c:/my work/workspace/"), dpc.getWorkspaceDir());
    }

    @Test
    public void test_getProjectDir() throws Exception {
        Assert.assertEquals(new File("/c:/my work/workspace/project/"), dpc.getProjectDir());
    }

    @Test
    public void test_getFilesDir() throws Exception {
        Assert.assertEquals(new File("/c:/my work/workspace/files/"), dpc.getFilesDir());
    }

    @Test
    public void test_getUnitTestTempDir() throws Exception {
        Assert.assertEquals(new File("/c:/my work/workspace/files/test_temp"), dpc.getTestTempDir());
    }

    @Test
    public void test_getUnitTestOutDir() throws Exception {
        Assert.assertEquals(new File("/c:/my work/workspace/files/test_out"), dpc.getTestOutDir());
    }
}
