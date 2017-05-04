package net.milanqiu.mimas.collect.tuple;

/**
 * This class holds two objects in one instance.
 * <p>
 * Types of elements are generic.
 * <p>
 * Creation Date: 2014-02-08
 * @author Milan Qiu
 */
public class Tuple2<TA, TB> implements Cloneable {
    /**
     * Element A.
     */
    protected TA a;
    /**
     * Element B.
     */
    protected TB b;

    /**
     * Returns element A.
     * @return element A
     */
    public TA getA() {
        return a;
    }
    /**
     * A setter corresponding to the getter {@link #getA()}.
     * @param a the new value of element A
     */
    public void setA(TA a) {
        this.a = a;
    }
    /**
     * Returns element B.
     * @return element B
     */
    public TB getB() {
        return b;
    }
    /**
     * A setter corresponding to the getter {@link #getB()}.
     * @param b the new value of element B
     */
    public void setB(TB b) {
        this.b = b;
    }

    /**
     * Changes all two elements in one invocation.
     * @param a the new value of element A
     * @param b the new value of element B
     */
    public void setAll(TA a, TB b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Constructs a two-element tuple with each element is null.
     */
    public Tuple2() {}

    /**
     * Constructs a two-element tuple with the specified values.
     * @param a the value of element A
     * @param b the value of element B
     */
    public Tuple2(TA a, TB b) {
        setAll(a, b);
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

        Tuple2 tuple2 = (Tuple2) o;

        if (a != null ? !a.equals(tuple2.a) : tuple2.a != null) return false;
        if (b != null ? !b.equals(tuple2.b) : tuple2.b != null) return false;

        return true;
    }

    /**
     * Returns the hash code value for this tuple.
     * @return the hash code value for this tuple
     */
    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }

    @Override
    public Tuple2<TA, TB> clone() throws CloneNotSupportedException {
        try {
            return (Tuple2<TA, TB>) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Returns a string representation of this object.
     * The string representation consists of a list of elements, enclosed in braces("{}").
     * Adjacent elements are separated by comma and space(", ").
     * The result may be <i><tt>{A=valueA, B=valueB}</tt></i>.
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "{A=" + a + ", B=" + b + '}';
    }
}
