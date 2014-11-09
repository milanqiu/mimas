package net.milanqiu.mimas.system;

import net.milanqiu.mimas.io.FileUtils;

import java.io.File;

/**
 * Properties and running environment of maven project.
 *
 * <p>Creation Date: 2014-11-3
 * @author Milan Qiu
 */
public abstract class MavenProjectConvention implements ProjectConvention {

    protected MavenProjectConvention() {}

    File workspaceDir;
    File projectDir;
    File filesDir;
    File unitTestTempDir;
    File unitTestOutDir;

    @Override
    public File getWorkspaceDir() {
        if (workspaceDir == null) {
            workspaceDir = FileUtils.findAncestor(new File(getSourcePath()), getWorkspaceName());
        }
        return workspaceDir;
    }

    @Override
    public File getProjectDir() {
        if (projectDir == null) {
            File workspaceDir = getWorkspaceDir();
            projectDir = new File(workspaceDir.getPath() + File.separator + getProjectName());
        }
        return projectDir;
    }

    @Override
    public File getFilesDir() {
        if (filesDir == null) {
            File workspaceDir = getWorkspaceDir();
            filesDir = new File(workspaceDir.getPath() + File.separator + "files");
        }
        return filesDir;
    }

    @Override
    public File getTestTempDir() {
        if (unitTestTempDir == null) {
            File filesDir = getFilesDir();
            unitTestTempDir = new File(filesDir.getPath() + File.separator + "test_temp");
        }
        return unitTestTempDir;
    }

    @Override
    public File getTestOutDir() {
        if (unitTestOutDir == null) {
            File filesDir = getFilesDir();
            unitTestOutDir = new File(filesDir.getPath() + File.separator + "test_out");
        }
        return unitTestOutDir;
    }
}
