package net.milanqiu.mimas.collect;

/**
 * This class holds a string and an integer in one instance.
 * <p>
 * Creation Date: 2014-6-9
 * @author Milan Qiu
 */
public class StrInt extends Tuple2<String, Integer> {

    /**
     * Returns the string element(element A).
     * @return the string element(element A)
     */
    public String getStr() {
        return a;
    }
    /**
     * A setter corresponding to the getter {@link #getStr()}.
     */
    public void setStr(String a) {
        this.a = a;
    }
    /**
     * Returns the integer element(element B).
     * @return the integer element(element B)
     */
    public int getInt() {
        return b;
    }
    /**
     * A setter corresponding to the getter {@link #getInt()}.
     */
    public void setInt(int b) {
        this.b = b;
    }
}
