package net.milanqiu.mimas.io;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;

/**
 * Utilities related to file.
 * <p>
 * Creation Date: 2014-11-3
 * @author Milan Qiu
 */
public class FileUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private FileUtils() {}

    /**
     * Finds an ancestor of the specified file with name equal to the specified name.
     * If not found, returns {@code null}.
     * @param file the file whose ancestors is going to be sought in
     * @param ancestorName the name to look for
     * @return the ancestor of {@code file} with name equal to {@code ancestorName}, or {@code null} if not found
     */
    public static File findAncestor(File file, String ancestorName) {
        while (file != null && !file.getName().equals(ancestorName)) {
            file = file.getParentFile();
        }
        return file;
    }

    /**
     * Returns a sub file of the specified directory with name assigned the specified name to.
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
    public static void deleteDirectoryContents(File dir) throws IOException {
        if (!dir.exists())
            throw new NoSuchFileException(dir.getPath());
        if (!dir.isDirectory())
            throw new IllegalArgumentException(dir.getPath());

        File[] children = dir.listFiles();
        if (children == null)
            return;
        for (File child : children) {
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
        Files.delete(fileOrDir.toPath());
    }

    /**
     * Deletes a file or directory and all contents recursively if it exists.
     * @param fileOrDir the file to delete
     * @throws IOException if an I/O error occurs
     */
    public static void deleteRecursivelyIfExists(File fileOrDir) throws IOException {
        if (!fileOrDir.exists())
            return;
        if (fileOrDir.isDirectory())
            deleteDirectoryContents(fileOrDir);
        Files.delete(fileOrDir.toPath());
    }

    /**
     * Reads the content of a file as bytes.
     * @param fromFile the file to read
     * @return the file content as bytes
     * @throws IOException if an I/O error occurs
     * @throws FileNotFoundException if the specified file does not exist, is a directory rather than a regular file,
     *                               or for some other reason cannot be opened for reading
     */
    public static byte[] readBytes(File fromFile) throws IOException {
        long fileSize = fromFile.length();
        if (fileSize > Integer.MAX_VALUE)
            throw new IOException("File too big");

        byte[] result = new byte[(int) fileSize];
        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(fromFile))) {
            int fileSizeRead = bis.read(result);
            if (fileSizeRead != fileSize) {
                throw new IOException("File size not match. Length: " + fileSize + ", Read: " + fileSizeRead);
            }
            return result;
        }
    }

    /**
     * Reads the content of a file as characters, using the given character set.
     * @param fromFile the file to read
     * @param charset the character set used to decode the file content
     * @return the file content as characters
     * @throws IOException if an I/O error occurs
     * @throws FileNotFoundException if the specified file does not exist, is a directory rather than a regular file,
     *                               or for some other reason cannot be opened for reading
     */
    public static String readChars(File fromFile, Charset charset) throws IOException {
        long fileSize = fromFile.length();
        if (fileSize > Integer.MAX_VALUE)
            throw new IOException("File too big");

        char[] buf = new char[(int) fileSize];
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fromFile), charset))) {
            int fileSizeRead = br.read(buf);
            return new String(buf, 0, fileSizeRead);
        }
    }

    /**
     * Reads the content of a file as characters, using the UTF-8 character set.
     * @param fromFile the file to read
     * @return the file content as characters
     * @throws IOException if an I/O error occurs
     * @throws FileNotFoundException if the specified file does not exist, is a directory rather than a regular file,
     *                               or for some other reason cannot be opened for reading
     */
    public static String readCharsUsingUtf8(File fromFile) throws IOException {
        return readChars(fromFile, StandardCharsets.UTF_8);
    }

    /**
     * Overwrites a file with the contents of a byte array.
     * @param bytes the byte array to write
     * @param toFile the destination file
     * @throws IOException if an I/O error occurs
     * @throws FileNotFoundException if the specified file exists but is a directory rather than a regular file, does
     *                               not exist but cannot be created, or cannot be opened for any other reason
     */
    public static void writeBytes(byte[] bytes, File toFile) throws IOException {
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(toFile))) {
            bos.write(bytes);
        }
    }

    /**
     * Overwrites a file with the contents of a character sequence, using the given character set.
     * @param chars the character sequence to write
     * @param toFile the destination file
     * @param charset the character set used to encode the character sequence
     * @throws IOException if an I/O error occurs
     * @throws FileNotFoundException if the specified file exists but is a directory rather than a regular file, does
     *                               not exist but cannot be created, or cannot be opened for any other reason
     */
    public static void writeChars(CharSequence chars, File toFile, Charset charset) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(toFile), charset))) {
            bw.append(chars);
        }
    }

    /**
     * Overwrites a file with the contents of a character sequence, using the UTF-8 character set.
     * @param chars the character sequence to write
     * @param toFile the destination file
     * @throws IOException if an I/O error occurs
     * @throws FileNotFoundException if the specified file exists but is a directory rather than a regular file, does
     *                               not exist but cannot be created, or cannot be opened for any other reason
     */
    public static void writeCharsUsingUtf8(CharSequence chars, File toFile) throws IOException {
        writeChars(chars, toFile, StandardCharsets.UTF_8);
    }
}
