package net.milanqiu.mimas.system;

import java.util.Objects;

/**
 * Represents a method identifier.
 * <p>
 * It uses fully qualified class name and method name to identify a method, ignoring overloads.
 * <p>
 * Creation Date: 2014-11-10
 * @author Milan Qiu
 */
public class MethodIdentifier {

    private MethodIdentifier(String className, String methodName) {
        this.className = className;
        this.methodName = methodName;
    }

    /**
     * Creates and returns a new instance of {@code MethodIdentifier} with the specified class name and method name.
     * @param className the class name of the new instance of {@code MethodIdentifier}
     * @param methodName the method name of the new instance of {@code MethodIdentifier}
     * @return the new instance of {@code MethodIdentifier}
     */
    public static MethodIdentifier create(String className, String methodName) {
        return new MethodIdentifier(className, methodName);
    }

    /**
     * Creates and returns a new instance of {@code MethodIdentifier} with class name and method name fetched from the
     * specified {@code StackTraceElement} object.
     * @param stackTraceElement the specified {@code StackTraceElement} object to fetch class name and method name
     * @return the new instance of {@code MethodIdentifier}
     */
    public static MethodIdentifier create(StackTraceElement stackTraceElement) {
        return new MethodIdentifier(stackTraceElement.getClassName(), stackTraceElement.getMethodName());
    }

    /**
     * The fully qualified name of the class which this method locates in.
     */
    protected String className;
    /**
     * The name of this method.
     */
    protected String methodName;

    /**
     * Returns the fully qualified name of the class which this method locates in.
     * @return the fully qualified name of the class which this method locates in
     */
    public String getClassName() {
        return className;
    }
    /**
     * Returns the name of this method.
     * @return the name of this method
     */
    public String getMethodName() {
        return methodName;
    }

    /**
     * Compares with the specified class name and method name for equality.
     * @param className the class name to be tested for equality
     * @param methodName the method name to be tested for equality
     * @return {@code true} if class name and method name of this object are equal to the incoming parameters
     */
    public boolean equals(String className, String methodName) {
        return Objects.equals(className, this.className) && Objects.equals(methodName, this.methodName);
    }

    /**
     * Compares with the specified object for equality.
     * @param o the object to be tested for equality
     * @return {@code true} if class name and method name of this object are equal to those of the specified object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MethodIdentifier that = (MethodIdentifier) o;

        if (className != null ? !className.equals(that.className) : that.className != null) return false;
        if (methodName != null ? !methodName.equals(that.methodName) : that.methodName != null) return false;

        return true;
    }

    /**
     * Returns a hash code value for this object.
     * @return a hash code value for this object
     */
    @Override
    public int hashCode() {
        int result = className != null ? className.hashCode() : 0;
        result = 31 * result + (methodName != null ? methodName.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of this object.
     * The string representation consists of linked class name, a dot('.') and method name.
     * The result may be <tt>net.milanqiu.mimas.system.MethodIdentifier.toString</tt>.
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append(className).append('.').append(methodName);
        return sb.toString();
    }
}
