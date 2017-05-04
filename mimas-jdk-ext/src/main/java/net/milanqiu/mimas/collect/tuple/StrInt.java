package net.milanqiu.mimas.collect.tuple;

/**
 * This class holds a string and an integer in one instance.
 * <p>
 * Creation Date: 2014-06-09
 * @author Milan Qiu
 */
public class StrInt extends Tuple2<String, Integer> implements Cloneable {

    /**
     * Constructs a string-integer tuple with each element is null.
     */
    public StrInt() {}

    /**
     * Constructs a string-integer tuple with the specified values.
     * @param a the value of element A
     * @param b the value of element B
     */
    public StrInt(String a, int b) {
        setAll(a, b);
    }

    /**
     * Returns the string element(element A).
     * @return the string element(element A)
     */
    public String getStr() {
        return a;
    }
    /**
     * A setter corresponding to the getter {@link #getStr()}.
     * @param a the new value of the string element(element A)
     */
    public void setStr(String a) {
        this.a = a;
    }
    /**
     * Returns the integer element(element B).
     * If the integer element is null, returns {@code 0}.
     * @return the integer element(element B)
     */
    public int getInt() {
        return b==null ? 0 : b;
    }
    /**
     * A setter corresponding to the getter {@link #getInt()}.
     * @param b the new value of the integer element(element B)
     */
    public void setInt(int b) {
        this.b = b;
    }

    @Override
    public StrInt clone() throws CloneNotSupportedException {
        try {
            return (StrInt) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}
