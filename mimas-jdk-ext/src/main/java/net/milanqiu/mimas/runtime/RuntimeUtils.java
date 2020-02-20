package net.milanqiu.mimas.runtime;

import net.milanqiu.mimas.instrumentation.exception.CodeContextException;
import net.milanqiu.mimas.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * Utilities related to runtime.
 * <p>
 * Creation Date: 2014-11-02
 * @author Milan Qiu
 */
public class RuntimeUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private RuntimeUtils() {}

    /**
     * Returns the location of compiled code runtime of the specified class, represented by {@link java.net.URL}.
     * @param clazz the class to get location
     * @return the location of compiled code runtime of the specified class
     */
    public static URL getClassSourceLocation(Class clazz) {
        return clazz.getProtectionDomain().getCodeSource().getLocation();
    }

    /**
     * Returns the directory which the compiled code runtime of the specified class locates, represented by {@link java.lang.String}.
     * @param clazz the class to get directory
     * @return the directory which the compiled code runtime of the specified class locates
     */
    public static String getClassSourceDir(Class clazz) {
        String classSourcePath = getClassSourceLocation(clazz).getPath();
        try {
            classSourcePath = URLDecoder.decode(classSourcePath, StandardCharsets.UTF_8.name());
        } catch (UnsupportedEncodingException e) {
            throw new CodeContextException(e);
        }
        return classSourcePath.substring(0, classSourcePath.lastIndexOf('/')+1);
    }

    /**
     * The file name of announcement.
     */
    public static final String ANNOUNCEMENT_FILE_NAME = "ok";
    /**
     * The result text of finished announcement.
     */
    public static final String ANNOUNCEMENT_RESULT_FINISHED = "finished";
    /**
     * The result text of exception announcement.
     */
    public static final String ANNOUNCEMENT_RESULT_EXCEPTION = "exception";

    /**
     * Announces some work is finished via file.
     * The announcement file is readable by external programs.
     * It's directory is specified, file name is {@code ANNOUNCEMENT_FILE_NAME},
     * content is led by {@code ANNOUNCEMENT_RESULT_FINISHED} and followed by specified message.
     * @param dir the directory to put the announcement file
     * @param message the message attached to the announcement
     * @throws IOException if an I/O error occurs
     */
    public static void announceFinished(File dir, String message) throws IOException {
        FileUtils.writeCharsUsingUtf8(ANNOUNCEMENT_RESULT_FINISHED + System.lineSeparator() + message,
                new File(dir, ANNOUNCEMENT_FILE_NAME));
    }

    /**
     * Announces some work is finished via file.
     * The announcement file is readable by external programs.
     * It's directory is specified, file name is {@code ANNOUNCEMENT_FILE_NAME},
     * content is led by {@code ANNOUNCEMENT_RESULT_FINISHED} and followed by specified message.
     * @param dirName the name of directory to put the announcement file
     * @param message the message attached to the announcement
     * @throws IOException if an I/O error occurs
     */
    public static void announceFinished(String dirName, String message) throws IOException {
        FileUtils.writeCharsUsingUtf8(ANNOUNCEMENT_RESULT_FINISHED + System.lineSeparator() + message,
                new File(dirName, ANNOUNCEMENT_FILE_NAME));
    }

    /**
     * Announces an exception is thrown via file.
     * The announcement file is readable by external programs.
     * It's directory is specified, file name is {@code ANNOUNCEMENT_FILE_NAME},
     * content is led by {@code ANNOUNCEMENT_RESULT_EXCEPTION} and followed by description of specified exception.
     * @param dir the directory to put the announcement file
     * @param e the exception attached to the announcement
     * @throws IOException if an I/O error occurs
     */
    public static void announceException(File dir, Exception e) throws IOException {
        FileUtils.writeCharsUsingUtf8(ANNOUNCEMENT_RESULT_EXCEPTION + System.lineSeparator() + e.toString(),
                new File(dir, ANNOUNCEMENT_FILE_NAME));
    }

    /**
     * Announces an exception is thrown via file.
     * The announcement file is readable by external programs.
     * It's directory is specified, file name is {@code ANNOUNCEMENT_FILE_NAME},
     * content is led by {@code ANNOUNCEMENT_RESULT_EXCEPTION} and followed by description of specified exception.
     * @param dirName the name of directory to put the announcement file
     * @param e the exception attached to the announcement
     * @throws IOException if an I/O error occurs
     */
    public static void announceException(String dirName, Exception e) throws IOException {
        FileUtils.writeCharsUsingUtf8(ANNOUNCEMENT_RESULT_EXCEPTION + System.lineSeparator() + e.toString(),
                new File(dirName, ANNOUNCEMENT_FILE_NAME));
    }
}
