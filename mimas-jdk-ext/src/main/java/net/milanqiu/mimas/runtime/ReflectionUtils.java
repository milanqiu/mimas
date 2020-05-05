package net.milanqiu.mimas.runtime;

import java.lang.reflect.Constructor;
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
     * Returns whether the specified class is abstract.
     * @param clazz the class to be tested
     * @return {@code true} if the specified class is abstract
     */
    public static boolean isAbstract(Class<?> clazz) {
        return Modifier.isAbstract(clazz.getModifiers());
    }

    /**
     * Returns whether the specified class has a default constructor without any parameter.
     * @param clazz the class to be tested
     * @return {@code true} if the specified class has a default constructor without any parameter
     */
    public static boolean hasDefaultConstructor(Class<?> clazz) {
        try {
            clazz.getDeclaredConstructor();
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    /**
     * Returns whether the specified class has a public default constructor without any parameter.
     * @param clazz the class to be tested
     * @return {@code true} if the specified class has a public default constructor without any parameter
     */
    public static boolean hasPublicDefaultConstructor(Class<?> clazz) {
        try {
            clazz.getConstructor();
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    /**
     * Finds the constructor of the specified class with the specified parameter count.
     * If not found, returns {@code null}.
     * If there is more than one constructor with the specified parameter count, returns the first.
     * @param clazz the class to find constructor
     * @param paramCount the parameter count of constructor to be found by
     * @return the constructor of the specified class with the specified parameter count, or {@code null} if not found
     */
    public static Constructor<?> findConstructorByParamCount(Class<?> clazz, int paramCount) {
        for (Constructor<?> constructor : clazz.getConstructors()) {
            if (constructor.getParameterCount() == paramCount)
                return constructor;
        }
        return null;
    }
}
