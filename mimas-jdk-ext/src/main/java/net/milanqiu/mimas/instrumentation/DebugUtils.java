package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.instrumentation.exception.NeverGoesHereException;
import net.milanqiu.mimas.lang.MethodIdentifier;
import net.milanqiu.mimas.lang.MethodIdentifierList;

import java.util.Arrays;

/**
 * Utilities related to debug.
 * <p>
 * Creation Date: 2014-6-18
 * @author Milan Qiu
 */
public class DebugUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private DebugUtils() {}

    /**
     * Throws a {@code NeverGoesHereException}.
     * It is placed on where the program will never go logically.
     * If it is invoked in runtime, it means the program has bug and the running is wrong.
     */
    public static void neverGoesHere() {
        throw new NeverGoesHereException();
    }

    /**
     * Throws a {@code NeverGoesHereException} with the specified detail message.
     * It is placed on where the program will never go logically.
     * If it is invoked in runtime, it means the program has bug and the running is wrong.
     * @param message the detail message
     */
    public static void neverGoesHere(String message) {
        throw new NeverGoesHereException(message);
    }

    private static final String GET_STACK_TRACE_CLASS_NAME = Thread.class.getName();
    private static final String GET_STACK_TRACE_METHOD_NAME = "getStackTrace";

    /**
     * Removes some elements from top of the specified stack trace.
     * Predicate of removing is defined by the specified {@code MethodIdentifierList} object, which indicates the
     * method identifiers to remove. Anyway, {@code java.lang.Thread.getStackTrace} should be removed by default.
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
     * Returns the current stack trace element of running.
     * It is the stack trace element which invoke this method.
     * @return the current stack trace element of running
     */
    public static StackTraceElement getCurrentStackTraceElement() {
        StackTraceElement[] actualStackTrace = removeStackTraceTopElements(Thread.currentThread().getStackTrace(),
                MethodIdentifierList.create(DebugUtils.class.getName(), "getCurrentStackTraceElement"));
        return actualStackTrace[0];
    }

    /**
     * Returns the current method of running.
     * The current method is the method which invoke this method.
     * @return the current method of running
     */
    public static MethodIdentifier getCurrentMethod() {
        StackTraceElement[] actualStackTrace = removeStackTraceTopElements(Thread.currentThread().getStackTrace(),
                MethodIdentifierList.create(DebugUtils.class.getName(), "getCurrentMethod"));
        return MethodIdentifier.create(actualStackTrace[0]);
    }

    /**
     * Returns the actual current method of running, ignoring the specified procedural methods.
     * The actual current method is the method which invoke this method via all procedural methods.
     * @param ignoredProceduralMethods the procedural methods to be ignored
     * @return the actual current method of running
     */
    public static MethodIdentifier getActualCurrentMethod(MethodIdentifierList ignoredProceduralMethods) {
        ignoredProceduralMethods.add(MethodIdentifier.create(DebugUtils.class.getName(), "getActualCurrentMethod"));
        StackTraceElement[] actualStackTrace = removeStackTraceTopElements(Thread.currentThread().getStackTrace(),
                ignoredProceduralMethods);
        return MethodIdentifier.create(actualStackTrace[0]);
    }
}
