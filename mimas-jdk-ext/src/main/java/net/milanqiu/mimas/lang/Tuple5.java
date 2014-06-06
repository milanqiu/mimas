package net.milanqiu.mimas.lang;

import net.milanqiu.mimas.instrumentation.exception.DeprecatedOverrideException;

/**
 * This class holds five objects in one instance.
 * Types of elements is generic.
 *
 * <p>Creation Date: 2014-2-8
 * @author Milan Qiu
 */
public class Tuple5<TA, TB, TC, TD, TE> extends Tuple4<TA, TB, TC, TD> {
    /**
     * Element E.
     */
    protected TE e;

    public TE getE() {
        return e;
    }
    public void setE(TE e) {
        this.e = e;
    }

    /**
     * Removed in subclass. Only available when caller is instance of {@code Tuple4}.
     *
     * @param a new value of element A
     * @param b new value of element B
     * @param c new value of element C
     * @param d new value of element D
     */
    @Deprecated
    @Override
    public void setAll(TA a, TB b, TC c, TD d) {
        throw new DeprecatedOverrideException();
    }

    /**
     * Sets all five elements in one call.
     *
     * @param a new value of element A
     * @param b new value of element B
     * @param c new value of element C
     * @param d new value of element D
     * @param e new value of element E
     */
    public void setAll(TA a, TB b, TC c, TD d, TE e) {
        super.setAll(a, b, c, d);
        this.e = e;
    }

    /**
     * Constructs a five-element tuple with every element is null.
     */
    public Tuple5() {
    }

    /**
     * Constructs a five-element tuple with specified values.
     *
     * @param a value of element A
     * @param b value of element B
     * @param c value of element C
     * @param d value of element D
     * @param e value of element E
     */
    public Tuple5(TA a, TB b, TC c, TD d, TE e) {
        setAll(a, b, c, d, e);
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

        Tuple5 tuple5 = (Tuple5) o;

        if (e != null ? !e.equals(tuple5.e) : tuple5.e != null) return false;

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
        result = 31 * result + (e != null ? e.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of this tuple.
     * The string representation consists of a list of elements, enclosed in brackets("()").
     * Adjacent elements are separated by comma and space(", ").
     * The result may be <tt>(A=valueA, B=valueB, C=valueC, D=valueD, E=valueE)</tt>.
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
        sb.append("D=").append(d).append(", ");
        sb.append("E=").append(e);
        sb.append(')');
        return sb.toString();
    }
}
