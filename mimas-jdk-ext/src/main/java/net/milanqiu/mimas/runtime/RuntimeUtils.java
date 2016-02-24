package net.milanqiu.mimas.runtime;

import net.milanqiu.mimas.instrumentation.exception.CodeContextException;

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
}
