package net.milanqiu.mimas.system;

import java.io.File;

/**
 * Properties and running environment of project according to rule "Convention over Configuration".
 *
 * <p>Creation Date: 2014-11-2
 * @author Milan Qiu
 */
public interface ProjectConvention {

    /**
     * Returns the path of compiled code source.
     * @return the path of compiled code source
     */
    String getSourcePath();

    /**
     * Returns the name of workspace.
     * @return the name of workspace
     */
    String getWorkspaceName();

    /**
     * Returns the name of project.
     * @return the name of project
     */
    String getProjectName();

    /**
     * Returns the root directory of workspace.
     * @return the root directory of workspace
     */
    File getWorkspaceDir();

    /**
     * Returns the root directory of project.
     * @return the root directory of project
     */
    File getProjectDir();

    /**
     * Returns the directory of files.
     * The directory is used to store independent files from code and resources.
     * @return the directory of files
     */
    File getFilesDir();

    /**
     * Returns the temporary directory of unit test.
     * The directory is used to store temporary files when running unit tests.
     * @return the temporary directory of unit test
     */
    File getUnitTestTempDir();

    /**
     * Returns the output directory of unit test.
     * The directory is used to store output files when running unit tests.
     * @return the output directory of unit test
     */
    File getUnitTestOutDir();
}
