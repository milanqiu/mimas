package net.milanqiu.mimas.collect.tuple;

import java.util.ArrayList;
import java.util.List;

/**
 * This class holds two integers in one instance.
 * <p>
 * Creation Date: 2014-06-09
 * @author Milan Qiu
 */
public class IntInt extends Tuple2<Integer, Integer> implements Cloneable {

    /**
     * Constructs a two-integer tuple with each element is null.
     */
    public IntInt() {}

    /**
     * Constructs a two-integer tuple with the specified values.
     * @param a the value of element A
     * @param b the value of element B
     */
    public IntInt(int a, int b) {
        setAll(a, b);
    }

    @Override
    public IntInt clone() throws CloneNotSupportedException {
        try {
            return (IntInt) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }

    /**
     * Creates and returns a list of new instances of {@code IntInt} with the specified values.
     * @param ints the sequence of integer pairs, each pair used to create an element of the result list, such as <code>0a, 0b, 1a, 1b, 2a, 2b</code>
     * @return the list of new instances of {@code IntInt}
     * @throws IllegalArgumentException if there are redundant elements in the incoming {@code ints} parameter
     */
    public static List<IntInt> createList(int... ints) {
        if ((ints.length & 1) != 0)
            throw new IllegalArgumentException();

        int count = ints.length >> 1;
        List<IntInt> result = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            result.add(new IntInt(ints[i*2], ints[i*2+1]));
        }
        return result;
    }
}
