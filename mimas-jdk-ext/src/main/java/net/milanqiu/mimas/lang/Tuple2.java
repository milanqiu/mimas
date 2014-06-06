package net.milanqiu.mimas.lang;

/**
 * This class holds two objects in one instance.
 * Types of elements is generic.
 *
 * <p>Creation Date: 2014-2-8
 * @author Milan Qiu
 */
public class Tuple2<TA, TB> {
    /**
     * Element A.
     */
    protected TA a;
    /**
     * Element B.
     */
    protected TB b;

    public TA getA() {
        return a;
    }
    public void setA(TA a) {
        this.a = a;
    }
    public TB getB() {
        return b;
    }
    public void setB(TB b) {
        this.b = b;
    }

    /**
     * Sets all two elements in one call.
     *
     * @param a new value of element A
     * @param b new value of element B
     */
    public void setAll(TA a, TB b) {
        this.a = a;
        this.b = b;
    }

    /**
     * Constructs a two-element tuple with every element is null.
     */
    public Tuple2() {
    }

    /**
     * Constructs a two-element tuple with specified values.
     *
     * @param a value of element A
     * @param b value of element B
     */
    public Tuple2(TA a, TB b) {
        setAll(a, b);
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

        Tuple2 tuple2 = (Tuple2) o;

        if (a != null ? !a.equals(tuple2.a) : tuple2.a != null) return false;
        if (b != null ? !b.equals(tuple2.b) : tuple2.b != null) return false;

        return true;
    }

    /**
     * Returns the hash code value for this tuple.
     *
     * @return the hash code value for this tuple
     */
    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }

    /**
     * Returns a string representation of this tuple.
     * The string representation consists of a list of elements, enclosed in brackets("()").
     * Adjacent elements are separated by comma and space(", ").
     * The result may be <tt>(A=valueA, B=valueB)</tt>.
     *
     * @return a string representation of this tuple
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('(');
        sb.append("A=").append(a).append(", ");
        sb.append("B=").append(b);
        sb.append(')');
        return sb.toString();
    }
}
