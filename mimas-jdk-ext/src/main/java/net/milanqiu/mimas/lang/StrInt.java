package net.milanqiu.mimas.lang;

/**
 * This class holds a string and an integer in one instance.
 *
 * <p>Creation Date: 2014-6-9
 * @author Milan Qiu
 */
public class StrInt extends Tuple2<String, Integer> {
    public String getStr() {
        return a;
    }
    public void setStr(String a) {
        this.a = a;
    }
    public int getInt() {
        return b;
    }
    public void setInt(int b) {
        this.b = b;
    }
}
