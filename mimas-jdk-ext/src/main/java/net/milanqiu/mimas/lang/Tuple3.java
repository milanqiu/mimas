package net.milanqiu.mimas.lang;

import net.milanqiu.mimas.instrumentation.exception.DeprecatedOverrideException;

/**
 * This class holds three objects in one instance.
 * Types of elements is generic.
 *
 * <p>Creation Date: 2014-2-8
 * @author Milan Qiu
 */
public class Tuple3<TA, TB, TC> extends Tuple2<TA, TB> {
    /**
     * Element C.
     */
    protected TC c;

    public TC getC() {
        return c;
    }
    public void setC(TC c) {
        this.c = c;
    }

    /**
     * Removed in subclass. Only available when caller is instance of {@code Tuple2}.
     *
     * @param a new value of element A
     * @param b new value of element B
     */
    @Deprecated
    @Override
    public void setAll(TA a, TB b) {
        throw new DeprecatedOverrideException();
    }

    /**
     * Sets all three elements in one call.
     *
     * @param a new value of element A
     * @param b new value of element B
     * @param c new value of element C
     */
    public void setAll(TA a, TB b, TC c) {
        super.setAll(a, b);
        this.c = c;
    }

    /**
     * Constructs a three-element tuple with every element is null.
     */
    public Tuple3() {
    }

    /**
     * Constructs a three-element tuple with specified values.
     *
     * @param a value of element A
     * @param b value of element B
     * @param c value of element C
     */
    public Tuple3(TA a, TB b, TC c) {
        setAll(a, b, c);
    }

    /**
     * Compares the specified object with this tuple for equality.
     * Return true if each element of the given object is equal to corresponding element of this tuple.
     *
     * @param o object to be compared for equality with this tuple
     * @return if the specified object is equal to this tuple
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Tuple3 tuple3 = (Tuple3) o;

        if (c != null ? !c.equals(tuple3.c) : tuple3.c != null) return false;

        return true;
    }

    /**
     * Returns the hash code value for this tuple.
     *
     * @return the hash code value for this tuple
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (c != null ? c.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of this tuple.
     * The string representation consists of a list of elements, enclosed in brackets("()").
     * Adjacent elements are separated by comma and space(", ").
     * The result may be <tt>(A=valueA, B=valueB, C=valueC)</tt>.
     *
     * @return a string representation of this tuple
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append("A=").append(a).append(", ");
        sb.append("B=").append(b).append(", ");
        sb.append("C=").append(c);
        sb.append(')');
        return sb.toString();
    }
}
