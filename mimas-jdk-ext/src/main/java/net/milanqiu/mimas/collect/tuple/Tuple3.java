package net.milanqiu.mimas.collect.tuple;

import net.milanqiu.mimas.instrumentation.exception.DeprecatedOverrideException;

/**
 * This class holds three objects in one instance.
 * <p>
 * Types of elements are generic.
 * <p>
 * Creation Date: 2014-02-08
 * @author Milan Qiu
 */
public class Tuple3<TA, TB, TC> extends Tuple2<TA, TB> {
    /**
     * Element C.
     */
    protected TC c;

    /**
     * Returns element C.
     * @return element C
     */
    public TC getC() {
        return c;
    }
    /**
     * A setter corresponding to the getter {@link #getC()}.
     */
    public void setC(TC c) {
        this.c = c;
    }

    /**
     * Removed in subclass. Only available when invoker is an instance of {@link Tuple2}.
     * @param a the new value of element A
     * @param b the new value of element B
     */
    @Deprecated
    @Override
    public void setAll(TA a, TB b) {
        throw new DeprecatedOverrideException();
    }

    /**
     * Changes all three elements in one invocation.
     * @param a the new value of element A
     * @param b the new value of element B
     * @param c the new value of element C
     */
    public void setAll(TA a, TB b, TC c) {
        super.setAll(a, b);
        this.c = c;
    }

    /**
     * Constructs a three-element tuple with each element is null.
     */
    public Tuple3() {}

    /**
     * Constructs a three-element tuple with the specified values.
     * @param a the value of element A
     * @param b the value of element B
     * @param c the value of element C
     */
    public Tuple3(TA a, TB b, TC c) {
        setAll(a, b, c);
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

        Tuple3 tuple3 = (Tuple3) o;

        if (c != null ? !c.equals(tuple3.c) : tuple3.c != null) return false;

        return true;
    }

    /**
     * Returns the hash code value for this tuple.
     * @return the hash code value for this tuple
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (c != null ? c.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of this object.
     * The string representation consists of a list of elements, enclosed in braces("{}").
     * Adjacent elements are separated by comma and space(", ").
     * The result may be <i><tt>{A=valueA, B=valueB, C=valueC}</tt></i>.
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{A=").append(a);
        sb.append(", B=").append(b);
        sb.append(", C=").append(c);
        sb.append('}');
        return sb.toString();
    }
}
