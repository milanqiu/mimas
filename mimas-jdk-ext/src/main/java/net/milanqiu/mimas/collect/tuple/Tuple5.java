package net.milanqiu.mimas.collect.tuple;

import net.milanqiu.mimas.instrumentation.exception.DeprecatedOverrideException;

/**
 * This class holds five objects in one instance.
 * <p>
 * Types of elements are generic.
 * <p>
 * Creation Date: 2014-02-08
 * @author Milan Qiu
 */
public class Tuple5<TA, TB, TC, TD, TE> extends Tuple4<TA, TB, TC, TD> {
    /**
     * Element E.
     */
    protected TE e;

    /**
     * Returns element E.
     * @return element E
     */
    public TE getE() {
        return e;
    }
    /**
     * A setter corresponding to the getter {@link #getE()}.
     * @param e the new value of element E
     */
    public void setE(TE e) {
        this.e = e;
    }

    /**
     * Removed in subclass. Only available when invoker is an instance of {@link Tuple4}.
     * @param a the new value of element A
     * @param b the new value of element B
     * @param c the new value of element C
     * @param d the new value of element D
     */
    @Deprecated
    @Override
    public void setAll(TA a, TB b, TC c, TD d) {
        throw new DeprecatedOverrideException();
    }

    /**
     * Changes all five elements in one invocation.
     * @param a the new value of element A
     * @param b the new value of element B
     * @param c the new value of element C
     * @param d the new value of element D
     * @param e the new value of element E
     */
    public void setAll(TA a, TB b, TC c, TD d, TE e) {
        super.setAll(a, b, c, d);
        this.e = e;
    }

    /**
     * Constructs a five-element tuple with each element is null.
     */
    public Tuple5() {}

    /**
     * Constructs a five-element tuple with the specified values.
     * @param a the value of element A
     * @param b the value of element B
     * @param c the value of element C
     * @param d the value of element D
     * @param e the value of element E
     */
    public Tuple5(TA a, TB b, TC c, TD d, TE e) {
        setAll(a, b, c, d, e);
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

        Tuple5 tuple5 = (Tuple5) o;

        if (e != null ? !e.equals(tuple5.e) : tuple5.e != null) return false;

        return true;
    }

    /**
     * Returns the hash code value for this tuple.
     * @return the hash code value for this tuple
     */
    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (e != null ? e.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of this object.
     * The string representation consists of a list of elements, enclosed in braces("{}").
     * Adjacent elements are separated by comma and space(", ").
     * The result may be <i><tt>{A=valueA, B=valueB, C=valueC, D=valueD, E=valueE}</tt></i>.
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{A=").append(a);
        sb.append(", B=").append(b);
        sb.append(", C=").append(c);
        sb.append(", D=").append(d);
        sb.append(", E=").append(e);
        sb.append('}');
        return sb.toString();
    }
}
