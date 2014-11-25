package net.milanqiu.mimas.system;

import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.instrumentation.exception.CodeContextException;
import net.milanqiu.mimas.io.FileUtils;
import net.milanqiu.mimas.lang.MethodIdentifierList;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Conventional properties and running environment of maven project.
 * <p>
 * Creation Date: 2014-11-3
 * @author Milan Qiu
 */
public abstract class MavenProjectConvention implements ProjectConvention {

    protected File workspaceDir;
    protected File projectDir;
    protected File filesDir;
    protected File testTempDir;
    protected File testOutDir;

    @Override
    public File getWorkspaceDir() {
        if (workspaceDir == null) {
            workspaceDir = FileUtils.findAncestor(new File(getSourceDir()), getWorkspaceName());
            if (workspaceDir == null)
                throw new CodeContextException("Workspace name(" + getWorkspaceName() +
                                               ") not found in ancestors of source directory(" + getSourceDir() + ")");
        }
        return workspaceDir;
    }

    @Override
    public File getProjectDir() {
        if (projectDir == null) {
            File workspaceDir = getWorkspaceDir();
            projectDir = new File(workspaceDir, getProjectName());
        }
        return projectDir;
    }

    @Override
    public File getFilesDir() {
        if (filesDir == null) {
            File workspaceDir = getWorkspaceDir();
            filesDir = new File(workspaceDir, "files");
        }
        return filesDir;
    }

    @Override
    public File getTestTempDir() {
        if (testTempDir == null) {
            File filesDir = getFilesDir();
            testTempDir = new File(filesDir, "test_temp");
        }
        return testTempDir;
    }

    @Override
    public final File prepareWorkDirInTestTempDir(boolean needToCreate) throws IOException {
        File result = new File(getTestTempDir(), DebugUtils.getActualCurrentMethod(MethodIdentifierList.create(
                MavenProjectConvention.class.getName(), "prepareWorkDirInTestTempDir")).toString());
        FileUtils.deleteRecursivelyIfExists(result);
        if (needToCreate)
            Files.createDirectories(result.toPath());
        return result;
    }

    @Override
    public File getTestOutDir() {
        if (testOutDir == null) {
            File filesDir = getFilesDir();
            testOutDir = new File(filesDir, "test_out");
        }
        return testOutDir;
    }

    @Override
    public File prepareWorkFileInTestOutDir() {
        File result = new File(getTestOutDir(), DebugUtils.getActualCurrentMethod(MethodIdentifierList.create(
                MavenProjectConvention.class.getName(), "prepareWorkFileInTestOutDir")).toString() + ".tmp");
        return result;
    }

    @Override
    public void writeWorkFileInTestOutDir(CharSequence chars) throws IOException {
        File workFile = new File(getTestOutDir(), DebugUtils.getActualCurrentMethod(MethodIdentifierList.create(
                MavenProjectConvention.class.getName(), "writeWorkFileInTestOutDir")).toString() + ".tmp");
        FileUtils.writeCharsUsingUtf8(chars, workFile);
    }
}
