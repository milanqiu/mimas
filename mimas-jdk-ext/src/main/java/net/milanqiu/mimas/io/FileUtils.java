package net.milanqiu.mimas.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.NoSuchFileException;

/**
 * Utilities related to file.
 *
 * <p>Creation Date: 2014-11-3
 * @author Milan Qiu
 */
public class FileUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private FileUtils() {
    }

    /**
     * Finds an ancestor of the specified file with name equal to the specified name.
     * If not found, returns {@code null}.
     * @param file the file whose ancestors is going to be traversed
     * @param ancestorName the name to be found
     * @return the ancestor of {@code file} with name equal to {@code ancestorName}, or {@code null} if not found
     */
    public static File findAncestor(File file, String ancestorName) {
        while (file != null && !file.getName().equals(ancestorName)) {
            file = file.getParentFile();
        }
        return file;
    }

    /**
     * Returns a sub file of the specified directory with name assigned to the specified name.
     * @param dir the parent directory
     * @param subName the name of sub file
     * @return the sub file
     */
    public static File getSubFile(File dir, String subName) {
        return new File(dir, subName);
    }

    /**
     * Returns a sub file of the specified directory in a deep level-by-level sub directory.
     * @param dir the parent directory
     * @param subNamesLbl the names of sub directories level by level
     * @return the sub file
     */
    public static File getSubFile(File dir, String... subNamesLbl) {
        StringBuilder sb = new StringBuilder(dir.getPath());
        for (String subName : subNamesLbl)
            sb.append(File.separator).append(subName);
        return new File(sb.toString());
    }

    /**
     * Returns a sub file of the specified directory in a deep level-by-level sub directory.
     * @param dir the parent directory
     * @param subNamesLbl the names of sub directories level by level
     * @return the sub file
     */
    public static File getSubFile(File dir, Iterable<String> subNamesLbl) {
        StringBuilder sb = new StringBuilder(dir.getPath());
        for (String subName : subNamesLbl)
            sb.append(File.separator).append(subName);
        return new File(sb.toString());
    }

    /**
     * Deletes all the files within a directory. Does not delete the directory itself.
     * Note it is ever provided in guava but deprecated from guava release 11.0 for the reason of poor symlink detection
     * and race conditions.
     * @param dir the directory to delete the contents of
     * @throws IOException if an I/O error occurs
     * @throws NoSuchFileException if the directory does not exist
     * @throws IllegalArgumentException if the argument is not a directory
     */
    public static void deleteDirectoryContents(File dir) throws IOException  {
        if (!dir.exists())
            throw new NoSuchFileException(dir.getPath());
        if (!dir.isDirectory())
            throw new IllegalArgumentException(dir.getPath());

        for (File child : dir.listFiles()) {
            deleteRecursively(child);
        }
    }

    /**
     * Deletes a file or directory and all contents recursively.
     * Note it is ever provided in guava but deprecated from guava release 11.0 for the reason of poor symlink detection
     * and race conditions.
     * @param fileOrDir the file to delete
     * @throws IOException if an I/O error occurs
     * @throws NoSuchFileException if the file or directory does not exist
     */
    public static void deleteRecursively(File fileOrDir) throws IOException {
        if (!fileOrDir.exists())
            throw new NoSuchFileException(fileOrDir.getPath());
        if (fileOrDir.isDirectory())
            deleteDirectoryContents(fileOrDir);
        fileOrDir.delete();
    }
}
