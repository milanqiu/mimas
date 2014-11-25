package net.milanqiu.mimas.system;

import net.milanqiu.mimas.instrumentation.exception.CodeContextException;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Utilities related to system.
 * <p>
 * Creation Date: 2014-11-2
 * @author Milan Qiu
 */
public class SysUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private SysUtils() {}

    /**
     * Returns the location of compiled code source of the specified class, represented by {@link java.net.URL}.
     * @param clazz the class to get location
     * @return the location of compiled code source of the specified class
     */
    public static URL getClassSourceLocation(Class clazz) {
        return clazz.getProtectionDomain().getCodeSource().getLocation();
    }

    /**
     * Returns the directory which the compiled code source of the specified class locates.
     * @param clazz the class to get directory
     * @return the directory which the compiled code source of the specified class locates
     */
    public static String getClassSourceDir(Class clazz) {
        String result = getClassSourceLocation(clazz).getPath();
        try {
            result = URLDecoder.decode(result, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new CodeContextException(e);
        }
        result = result.substring(0, result.lastIndexOf('/')+1);
        return result;
    }
}
