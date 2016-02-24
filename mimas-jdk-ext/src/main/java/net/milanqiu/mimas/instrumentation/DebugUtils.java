package net.milanqiu.mimas.instrumentation;

import net.milanqiu.mimas.instrumentation.exception.NeverGoesHereException;
import net.milanqiu.mimas.lang.StackTrace;

/**
 * Utilities related to debug.
 * <p>
 * Creation Date: 2014-06-18
 * @author Milan Qiu
 */
public class DebugUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private DebugUtils() {}

    /**
     * Throws a {@link net.milanqiu.mimas.instrumentation.exception.NeverGoesHereException}.
     * It is placed on where the program will never go logically.
     * If it is invoked in runtime, it means the program has bug and the running is wrong.
     */
    public static void neverGoesHere() {
        throw new NeverGoesHereException();
    }

    /**
     * Throws a {@link net.milanqiu.mimas.instrumentation.exception.NeverGoesHereException} with the specified detail message.
     * It is placed on where the program will never go logically.
     * If it is invoked in runtime, it means the program has bug and the running is wrong.
     * @param message the detail message
     */
    public static void neverGoesHere(String message) {
        throw new NeverGoesHereException(message);
    }

    /**
     * Returns the current method of running.
     * The current method is the method which invoke this.
     * @return the current method of running
     */
    public static StackTraceElement getCurrentMethod() {
        StackTrace methodsToRemove = StackTrace.createFromMethodIdentifier(DebugUtils.class.getName(), "getCurrentMethod");
        StackTrace actualStackTrace = StackTrace.createFromCurrentThread().removeTopElements(methodsToRemove);
        return actualStackTrace.getElement(0);
    }

    /**
     * Returns the invoker method of running.
     * The invoker method is the method which invoke the current method.
     * @return the invoker method of running
     */
    public static StackTraceElement getInvokerMethod() {
        StackTrace methodsToRemove = StackTrace.createFromMethodIdentifier(DebugUtils.class.getName(), "getInvokerMethod");
        StackTrace actualStackTrace = StackTrace.createFromCurrentThread().removeTopElements(methodsToRemove);
        return actualStackTrace.getElement(1);
    }

    /**
     * Returns the source method of running, ignoring the specified procedural methods.
     * The source method is the method which invoke this via all procedural methods.
     * @param proceduralMethodsToIgnore the procedural methods to be ignored
     * @return the source method of running
     */
    public static StackTraceElement getSourceMethod(StackTrace proceduralMethodsToIgnore) {
        StackTrace methodsToRemove = proceduralMethodsToIgnore.append(StackTrace.createMethodElement(DebugUtils.class.getName(), "getSourceMethod"));
        StackTrace actualStackTrace = StackTrace.createFromCurrentThread().removeTopElements(methodsToRemove);
        return actualStackTrace.getElement(0);
    }
}
