package net.milanqiu.mimas.collect.tuple;

/**
 * This class holds an integer and a string in one instance.
 * <p>
 * Creation Date: 2014-06-09
 * @author Milan Qiu
 */
public class IntStr extends Tuple2<Integer, String> {

    /**
     * Constructs an integer-string tuple with each element is null.
     */
    public IntStr() {}

    /**
     * Constructs an integer-string tuple with the specified values.
     * @param a the value of element A
     * @param b the value of element B
     */
    public IntStr(int a, String b) {
        setAll(a, b);
    }

    /**
     * Returns the integer element(element A).
     * @return the integer element(element A)
     */
    public int getInt() {
        return a;
    }
    /**
     * A setter corresponding to the getter {@link #getInt()}.
     */
    public void setInt(int a) {
        this.a = a;
    }
    /**
     * Returns the string element(element B).
     * @return the string element(element B)
     */
    public String getStr() {
        return b;
    }
    /**
     * A setter corresponding to the getter {@link #getStr()}.
     */
    public void setStr(String b) {
        this.b = b;
    }
}
