package net.milanqiu.mimas.collect;

/**
 * This class holds an integer and a string in one instance.
 * <p>
 * Creation Date: 2014-6-9
 * @author Milan Qiu
 */
public class IntStr extends Tuple2<Integer, String> {

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
