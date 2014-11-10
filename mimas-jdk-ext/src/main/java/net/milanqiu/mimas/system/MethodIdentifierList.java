package net.milanqiu.mimas.system;

import java.util.ArrayList;

/**
 * Represents an array list of {@link net.milanqiu.mimas.system.MethodIdentifier}.
 * <p>
 * Creation Date: 2014-11-10
 * @author Milan Qiu
 */
public class MethodIdentifierList extends ArrayList<MethodIdentifier> {

    /**
     * Creates and returns a new instance of {@code MethodIdentifierList} with the specified class names and method names.
     * @param classNameAndMethodNameGroupSequence the sequence of class_name-method_name groups,
     *                                            each group used to create an element of {@code MethodIdentifierList}
     * @return the new instance of {@code MethodIdentifierList}
     * @throws IllegalArgumentException if there are redundant elements in the incoming {@code classNameAndMethodNameGroupSequence} parameter
     */
    public static MethodIdentifierList create(String... classNameAndMethodNameGroupSequence) {
        if (classNameAndMethodNameGroupSequence.length % 2 != 0)
            throw new IllegalArgumentException();

        MethodIdentifierList result = new MethodIdentifierList();
        int count = classNameAndMethodNameGroupSequence.length / 2;
        result.ensureCapacity(count);
        for (int i = 0; i < count; i++) {
            result.add(MethodIdentifier.create(classNameAndMethodNameGroupSequence[i*2], classNameAndMethodNameGroupSequence[i*2+1]));
        }
        return result;
    }

    /**
     * Returns the index of the first occurrence of the specified class name and method name in this list, or -1 if this
     * list does not contain such element.
     * @param className the class name to search for
     * @param methodName the method name to search for
     * @return the index of the first occurrence of the specified class name and method name in this list,
     *         or -1 if this list does not contain such element
     */
    public int indexOf(String className, String methodName) {
        for (int i = 0; i < size(); i++)
            if (get(i).equals(className, methodName))
                return i;
        return -1;
    }
}
