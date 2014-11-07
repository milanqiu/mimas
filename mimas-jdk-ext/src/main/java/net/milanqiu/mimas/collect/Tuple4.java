package net.milanqiu.mimas.collect;

import net.milanqiu.mimas.instrumentation.exception.DeprecatedOverrideException;

/**
 * This class holds four objects in one instance.
 * Types of elements is generic.
 *
 * <p>Creation Date: 2014-2-8
 * @author Milan Qiu
 */
public class Tuple4<TA, TB, TC, TD> extends Tuple3<TA, TB, TC> {
    /**
     * Element D.
     */
    protected TD d;

    public TD getD() {
        return d;
    }
    public void setD(TD d) {
        this.d = d;
    }

    /**
     * Removed in subclass. Only available when caller is instance of {@code Tuple3}.
     *
     * @param a new value of element A
     * @param b new value of element B
     * @param c new value of element C
     */
    @Deprecated
    @Override
    public void setAll(TA a, TB b, TC c) {
        throw new DeprecatedOverrideException();
    }

    /**
     * Sets all four elements in one call.
     *
     * @param a new value of element A
     * @param b new value of element B
     * @param c new value of element C
     * @param d new value of element D
     */
    public void setAll(TA a, TB b, TC c, TD d) {
        super.setAll(a, b, c);
        this.d = d;
    }

    /**
     * Constructs a four-element tuple with every element is null.
     */
    public Tuple4() {
    }

    /**
     * Constructs a four-element tuple with specified values.
     *
     * @param a value of element A
     * @param b value of element B
     * @param c value of element C
     * @param d value of element D
     */
    public Tuple4(TA a, TB b, TC c, TD d) {
        setAll(a, b, c, d);
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

        Tuple4 tuple4 = (Tuple4) o;

        if (d != null ? !d.equals(tuple4.d) : tuple4.d != null) return false;

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
        result = 31 * result + (d != null ? d.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of this tuple.
     * The string representation consists of a list of elements, enclosed in brackets("()").
     * Adjacent elements are separated by comma and space(", ").
     * The result may be <tt>(A=valueA, B=valueB, C=valueC, D=valueD)</tt>.
     *
     * @return a string representation of this tuple
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append("A=").append(a).append(", ");
        sb.append("B=").append(b).append(", ");
        sb.append("C=").append(c).append(", ");
        sb.append("D=").append(d);
        sb.append(')');
        return sb.toString();
    }
}
