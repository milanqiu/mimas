package net.milanqiu.mimas.system;

import net.milanqiu.mimas.instrumentation.exception.CodeContextException;

import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Utilities related to system.
 *
 * <p>Creation Date: 2014-11-2
 * @author Milan Qiu
 */
public class SysUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private SysUtils() {
    }

    /**
     * The location of compiled code source of the specified class, represented by {@link java.net.URL}.
     * @param clazz the specified class to get location
     * @return the location of clazz
     */
    public static URL getClassSourceLocation(Class clazz) {
        return clazz.getProtectionDomain().getCodeSource().getLocation();
    }

    /**
     * The path of compiled code source of the specified class.
     * @param clazz the specified class to get path
     * @return the path of clazz
     */
    public static String getClassSourcePath(Class clazz) {
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
