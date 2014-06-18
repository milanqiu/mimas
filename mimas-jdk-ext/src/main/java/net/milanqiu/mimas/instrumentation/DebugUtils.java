package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.instrumentation.exception.NeverGoesHereException;

/**
 * Utilities related to debug.
 *
 * <p>Creation Date: 2014-6-18
 * @author Milan Qiu
 */
public class DebugUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private DebugUtils() {
    }

    /**
     * Throws a {@code NeverGoesHereException}.
     * It is placed on where the program will never go logically.
     * If it is called in runtime, it means the program has bug and the running is wrong.
     */
    public static void neverGoesHere() {
        throw new NeverGoesHereException();
    }
}
