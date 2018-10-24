package net.milanqiu.mimas.runtime;

import java.lang.reflect.Modifier;

/**
 * Utilities related to reflection.
 * <p>
 * Creation Date: 2018-10-24
 * @author Milan Qiu
 */
public class ReflectionUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private ReflectionUtils() {}

    /**
     * Returns whether the specified class is non-abstract.
     * @param clazz the class to be tested
     * @return {@code true} if the specified class is non-abstract
     */
    public static boolean isNonAbstract(Class<?> clazz) {
        return !Modifier.isAbstract(clazz.getModifiers());
    }
}
