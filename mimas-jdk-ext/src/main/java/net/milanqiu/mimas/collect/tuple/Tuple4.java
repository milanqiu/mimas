package net.milanqiu.mimas.collect.tuple;

import net.milanqiu.mimas.instrumentation.exception.DeprecatedOverrideException;

/**
 * This class holds four objects in one instance.
 * <p>
 * Types of elements are generic.
 * <p>
 * Creation Date: 2014-02-08
 * @author Milan Qiu
 */
public class Tuple4<TA, TB, TC, TD> extends Tuple3<TA, TB, TC> implements Cloneable {
    /**
     * Element D.
     */
    protected TD d;

    /**
     * Returns element D.
     * @return element D
     */
    public TD getD() {
        return d;
    }
    /**
     * A setter corresponding to the getter {@link #getD()}.
     * @param d the new value of element D
     */
    public void setD(TD d) {
        this.d = d;
    }

    /**
     * Removed in subclass. Only available when invoker is an instance of {@link Tuple3}.
     * @param a the new value of element A
     * @param b the new value of element B
     * @param c the new value of element C
     */
    @Deprecated
    @Override
    public void setAll(TA a, TB b, TC c) {
        throw new DeprecatedOverrideException();
    }

    /**
     * Changes all four elements in one invocation.
     * @param a the new value of element A
     * @param b the new value of element B
     * @param c the new value of element C
     * @param d the new value of element D
     */
    public void setAll(TA a, TB b, TC c, TD d) {
        super.setAll(a, b, c);
        this.d = d;
    }

    /**
     * Constructs a four-element tuple with each element is null.
     */
    public Tuple4() {}

    /**
     * Constructs a four-element tuple with the specified values.
     * @param a the value of element A
     * @param b the value of element B
     * @param c the value of element C
     * @param d the value of element D
     */
    public Tuple4(TA a, TB b, TC c, TD d) {
        setAll(a, b, c, d);
    }

    /**
     * Compares the specified object with this tuple for equality.
     * Return {@code true} if each element of the given object is equal to corresponding element of this tuple.
     * @param o the object to be tested for equality with this tuple
     * @return equality result
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
     * @return the hash code value for this tuple
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (d != null ? d.hashCode() : 0);
        return result;
    }

    @Override
    public Tuple4<TA, TB, TC, TD> clone() throws CloneNotSupportedException {
        try {
            return (Tuple4<TA, TB, TC, TD>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Returns a string representation of this object.
     * The string representation consists of a list of elements, enclosed in braces("{}").
     * Adjacent elements are separated by comma and space(", ").
     * The result may be <i><tt>{A=valueA, B=valueB, C=valueC, D=valueD}</tt></i>.
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "{A=" + a + ", B=" + b + ", C=" + c + ", D=" + d + '}';
    }
}
