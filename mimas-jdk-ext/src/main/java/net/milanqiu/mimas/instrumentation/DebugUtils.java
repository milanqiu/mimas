package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.instrumentation.exception.NeverGoesHereException;
import net.milanqiu.mimas.system.MethodIdentifier;
import net.milanqiu.mimas.system.MethodIdentifierList;

import java.util.Arrays;

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

    private static final String GET_STACK_TRACE_CLASS_NAME = Thread.class.getName();
    private static final String GET_STACK_TRACE_METHOD_NAME = "getStackTrace";

    /**
     * Removes some elements from top of the specified stack trace.
     * Predicate of removing is defined by the specified {@code MethodIdentifierList} object, which indicates the
     * method identifiers to remove. Besides, {@code java.lang.Thread.getStackTrace()} should be removed by default.
     * @param stackTrace the stack trace to remove elements from
     * @param methodsToRemove the method identifiers need to be removed
     * @return the result after removal
     */
    public static StackTraceElement[] removeStackTraceTopElements(StackTraceElement[] stackTrace,
                                                                  MethodIdentifierList methodsToRemove) {
        final int stackTraceLen = stackTrace.length;
        int offset = -1;
        String className;
        String methodName;
        do {
            offset++;
            if (offset >= stackTraceLen)
                break;
            className = stackTrace[offset].getClassName();
            methodName = stackTrace[offset].getMethodName();
        } while (methodsToRemove.indexOf(className, methodName) != -1 ||
                 (className.equals(GET_STACK_TRACE_CLASS_NAME) && methodName.equals(GET_STACK_TRACE_METHOD_NAME)));
        return Arrays.copyOfRange(stackTrace, offset, stackTraceLen);
    }

    /**
     * Returns the current method of running.
     * It is the method which invoke this method.
     * @return the current method of running
     */
    public static MethodIdentifier getCurrentMethod() {
        StackTraceElement[] actualStackTrace = removeStackTraceTopElements(Thread.currentThread().getStackTrace(),
                MethodIdentifierList.create(DebugUtils.class.getName(), "getCurrentMethod"));
        return MethodIdentifier.create(actualStackTrace[0]);
    }
}
