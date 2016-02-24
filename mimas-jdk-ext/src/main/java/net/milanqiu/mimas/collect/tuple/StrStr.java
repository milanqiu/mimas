package net.milanqiu.mimas.collect.tuple;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds two strings in one instance.
 * <p>
 * Creation Date: 2014-06-09
 * @author Milan Qiu
 */
public class StrStr extends Tuple2<String, String> {

    /**
     * Constructs a two-string tuple with each element is null.
     */
    public StrStr() {}

    /**
     * Constructs a two-string tuple with the specified values.
     * @param a the value of element A
     * @param b the value of element B
     */
    public StrStr(String a, String b) {
        setAll(a, b);
    }

    /**
     * Creates and returns a list of new instances of {@code StrStr} with the specified values.
     * @param strs the sequence of string pairs, each pair used to create an element of the result list, such as <code>0a, 0b, 1a, 1b, 2a, 2b</code>
     * @return the list of new instances of {@code StrStr}
     * @throws IllegalArgumentException if there are redundant elements in the incoming {@code strs} parameter
     */
    public static List<StrStr> createList(String... strs) {
        if (strs.length % 2 != 0)
            throw new IllegalArgumentException();

        int count = strs.length / 2;
        List<StrStr> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(new StrStr(strs[i*2], strs[i*2+1]));
        }
        return result;
    }
}
