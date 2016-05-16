package net.milanqiu.mimas.lang;

import net.milanqiu.mimas.collect.tuple.StrStr;

import java.util.Arrays;
import java.util.List;

/**
 * A stack trace manager, holding an array of {@link java.lang.StackTraceElement} objects and providing corresponding utilities.
 * <p>
 * Some utilities are on method level, which means to regard a {@link java.lang.StackTraceElement} object as a method identifier
 * and only use its method properties, what are class name and method name.
 * <p>
 * Creation Date: 2016-02-12
 * @author Milan Qiu
 */
public class StackTrace {

    private StackTrace() {}

    private StackTraceElement[] elements;

    /**
     * Returns the count of elements in this stack trace.
     * @return the count of elements in this stack trace
     */
    public int getElementCount() {
        return elements.length;
    }

    /**
     * Returns the element at the specified position in this stack trace.
     * @param index index of the element to return
     * @return the element at the specified position in this stack trace
     * @throws IndexOutOfBoundsException if the index is out of range (<code>index &lt; 0 || index &gt;= getElementCount()</code>)
     */
    public StackTraceElement getElement(int index) {
        if (index < 0 || index >= elements.length)
            throw new IndexOutOfBoundsException();
        return elements[index];
    }

    /**
     * Creates and returns a new instance of {@code StackTrace} with no elements.
     * @return the new instance of {@code StackTrace}
     */
    public static StackTrace create() {
        StackTrace result = new StackTrace();
        result.elements = new StackTraceElement[0];
        return result;
    }

    /**
     * Creates and returns a new instance of {@code StackTrace} from the stack dump of this thread.
     * @return the new instance of {@code StackTrace}
     */
    public static StackTrace createFromCurrentThread() {
        StackTraceElement[] currentThreadStackTrace = Thread.currentThread().getStackTrace();
        assert currentThreadStackTrace[0].getClassName().equals(Thread.class.getName());
        assert currentThreadStackTrace[0].getMethodName().equals("getStackTrace");
        assert currentThreadStackTrace[1].getClassName().equals(StackTrace.class.getName());
        assert currentThreadStackTrace[1].getMethodName().equals("createFromCurrentThread");

        StackTrace result = new StackTrace();
        result.elements = Arrays.copyOfRange(currentThreadStackTrace, 2, currentThreadStackTrace.length);
        return result;
    }

    /**
     * Creates and returns a new instance of {@code StackTrace} with only one element made of the specified method properties.
     * On the non-method properties, {@code null} will be assigned to file name and {@code -1} will be assigned to line number.
     * @param className the fully qualified class name of the only element
     * @param methodName the method name of the only element
     * @return the new instance of {@code StackTrace}
     */
    public static StackTrace createFromMethodIdentifier(String className, String methodName) {
        StackTrace result = new StackTrace();
        result.elements = new StackTraceElement[1];
        result.elements[0] = createMethodElement(className, methodName);
        return result;
    }

    /**
     * Creates and returns a new instance of {@code StackTrace} with elements made of the specified method properties.
     * On the non-method properties, {@code null} will be assigned to file name and {@code -1} will be assigned to line number.
     * @param methodIdentifiers the list of method properties, each element makes a stack trace element,
     *                          get fully qualified class name from {@code getA()} and method name from {@code getB()}
     * @return the new instance of {@code StackTrace}
     */
    public static StackTrace createFromMethodIdentifiers(List<StrStr> methodIdentifiers) {
        StackTrace result = new StackTrace();
        result.elements = new StackTraceElement[methodIdentifiers.size()];
        for (int i = 0; i < methodIdentifiers.size(); i++) {
            result.elements[i] = createMethodElement(methodIdentifiers.get(i).getA(), methodIdentifiers.get(i).getB());
        }
        return result;
    }

    /**
     * Creates and returns a new instance of {@link java.lang.StackTraceElement} with the specified properties.
     * {@link java.lang.StackTraceElement} is a stack trace element representing the specified execution point.
     * @param className the fully qualified name of the class containing the execution point represented by the stack trace element
     * @param methodName the name of the method containing the execution point represented by the stack trace element
     * @param fileName the name of the file containing the execution point represented by the stack trace element, or {@code null} if this information is unavailable
     * @param lineNumber the line number of the source line containing the execution point represented by this stack trace element,
     *                   or a negative number if this information is unavailable. A value of -2 indicates that the method containing the execution point is a native method
     * @return the new instance of {@link java.lang.StackTraceElement}
     */
    public static StackTraceElement createElement(String className, String methodName, String fileName, int lineNumber) {
        return new StackTraceElement(className, methodName, fileName, lineNumber);
    }

    /**
     * On method level, creates and returns a new instance of {@link java.lang.StackTraceElement} with the specified method properties.
     * On the non-method properties, {@code null} will be assigned to file name and {@code -1} will be assigned to line number.
     * {@link java.lang.StackTraceElement} is a stack trace element representing the specified execution point.
     * @param className the fully qualified name of the class containing the execution point represented by the stack trace element
     * @param methodName the name of the method containing the execution point represented by the stack trace element
     * @return the new instance of {@link java.lang.StackTraceElement}
     */
    public static StackTraceElement createMethodElement(String className, String methodName) {
        return new StackTraceElement(className, methodName, null, -1);
    }

    /**
     * On method level, returns a string representation of the specified {@link java.lang.StackTraceElement} object.
     * The string representation consists of linked class name, a dot('.') and method name.
     * The result may be <i><tt>"net.milanqiu.mimas.lang.StackTraceElementExt.methodToString"</tt></i>.
     * @param element the {@link java.lang.StackTraceElement} object to be represented as string
     * @return a string representation of the specified {@link java.lang.StackTraceElement} object
     */
    public static String methodToString(StackTraceElement element) {
        return element.getClassName() + "." + element.getMethodName();
    }

    /**
     * On method level, compares two {@link java.lang.StackTraceElement} objects for method equality.
     * It only compare method properties, including class name and method name.
     * @param elementA the first {@link java.lang.StackTraceElement} object to be compared for method equality
     * @param elementB the second {@link java.lang.StackTraceElement} object to be compared for method equality
     * @return {@code true} if the method properties of arguments are equal to each other
     */
    public static boolean methodEquals(StackTraceElement elementA, StackTraceElement elementB) {
        if (elementA == elementB)
            return true;
        else if (elementA == null || elementB == null)
            return false;
        else
            return elementA.getClassName().equals(elementB.getClassName()) && elementA.getMethodName().equals(elementB.getMethodName());
    }

    /**
     * On method level, returns whether this stack trace contains the specified {@link java.lang.StackTraceElement} object.
     * It only compare method properties, including class name and method name.
     * @param method the {@link java.lang.StackTraceElement} object to be tested
     * @return {@code true} if this stack trace contains the specified {@link java.lang.StackTraceElement} object
     */
    public boolean containsMethod(StackTraceElement method) {
        for (StackTraceElement element : elements)
            if (methodEquals(element, method))
                return true;
        return false;
    }

    /**
     * Creates and returns a new instance of {@code StackTrace} copying from this stack trace,
     * appending the specified {@link java.lang.StackTraceElement} object to the end of it.
     * @param element the {@link java.lang.StackTraceElement} object to be appended to
     * @return the stack trace appended the specified {@link java.lang.StackTraceElement} object
     */
    public StackTrace append(StackTraceElement element) {
        StackTrace result = new StackTrace();
        result.elements = new StackTraceElement[elements.length+1];
        System.arraycopy(elements, 0, result.elements, 0, elements.length);
        result.elements[result.elements.length-1] = element;
        return result;
    }

    /**
     * Creates and returns a new instance of {@code StackTrace} copying from this stack trace,
     * removing some elements from the top of it.
     * Predicate of removing is defined by the specified stack trace, which indicates the methods to remove.
     * @param methodsToRemove the methods need to be removed
     * @return the stack trace removed top elements
     */
    public StackTrace removeTopElements(StackTrace methodsToRemove) {
        int offset = -1;
        do {
            offset++;
            if (offset >= elements.length)
                break;
        } while (methodsToRemove.containsMethod(elements[offset]));

        StackTrace result = new StackTrace();
        result.elements = Arrays.copyOfRange(elements, offset, elements.length);
        return result;
    }
}
