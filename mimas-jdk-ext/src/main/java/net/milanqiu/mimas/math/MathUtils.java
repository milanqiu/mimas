package net.milanqiu.mimas.math;

import java.util.Random;

/**
 * Utilities related to math.
 * <p>
 * Creation Date: 2014-10-21
 * @author Milan Qiu
 */
public class MathUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private MathUtils() {}

    private static Random random = new Random(System.currentTimeMillis());

    /**
     * Returns a singleton pseudorandom number generator. The seed has been set.
     * @return the result pseudorandom number generator
     */
    public static Random getRandom() {
        return random;
    }

    /**
     * Returns a pseudorandom integer in the specified range.
     * @param min the minimum of the pseudorandom integer (inclusive)
     * @param max the maximum of the pseudorandom integer (inclusive)
     * @return the result pseudorandom integer
     */
    public static int randomInRange(int min, int max) {
        if (max == min)
            return max;
        if (max < min) {
            int tmp = max;
            max = min;
            min = tmp;
        }
        return random.nextInt(max-min+1) + min;
    }
}
