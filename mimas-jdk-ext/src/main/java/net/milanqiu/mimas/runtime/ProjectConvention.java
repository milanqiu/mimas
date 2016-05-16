package net.milanqiu.mimas.runtime;

import java.io.File;
import java.io.IOException;

/**
 * Conventional properties and running environment of project, according to rule "Convention over Configuration".
 * <p>
 * Creation Date: 2014-11-02
 * @author Milan Qiu
 */
public interface ProjectConvention {

    /**
     * Returns the directory which the compiled code runtime locates.
     * @return the directory which the compiled code runtime locates
     */
    String getSourceDir();

    /**
     * Returns the name of workspace(named as project in IntelliJ IDEA).
     * @return the name of workspace
     */
    String getWorkspaceName();

    /**
     * Returns the name of project(named as module in IntelliJ IDEA).
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
     * Returns the temporary directory of test.
     * The directory is used to put temporary files when running tests, especially unit tests.
     * @return the temporary directory of test
     */
    File getTestTempDir();

    /**
     * Prepares a special work directory in the temporary directory of test for next tests.
     * @return the prepared special work directory
     * @throws IOException if an I/O error occurs
     */
    File prepareDirInTestTempDir() throws IOException;

    /**
     * Returns the output directory of test.
     * The directory is used to put output files when running tests, especially unit tests.
     * @return the output directory of test
     */
    File getTestOutDir();

    /**
     * Prepares a special work file in the output directory of test for next tests.
     * @return the prepared special work file
     */
    File prepareFileInTestOutDir();

    /**
     * Writes a special work file in the output directory of test with the contents of the specified character sequence,
     * using the UTF-8 character set.
     * @param chars the character sequence to write
     * @throws IOException if an I/O error occurs
     */
    void writeFileInTestOutDir(CharSequence chars) throws IOException;
}
