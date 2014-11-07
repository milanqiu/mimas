package net.milanqiu.mimas.collect;

/**
 * This class holds an integer and a string in one instance.
 *
 * <p>Creation Date: 2014-6-9
 * @author Milan Qiu
 */
public class IntStr extends Tuple2<Integer, String> {
    public int getInt() {
        return a;
    }
    public void setInt(int a) {
        this.a = a;
    }
    public String getStr() {
        return b;
    }
    public void setStr(String b) {
        this.b = b;
    }
}
