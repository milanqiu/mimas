package net.milanqiu.mimas.guava;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Sets;

import java.util.Set;

/**
 * An extension of {@link com.google.common.base.CharMatcher} to provide more utilities.
 *
 * <p>Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class CharMatcherExt {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private CharMatcherExt() {
    }

    /**
     * Converts the specified {@code CharMatcher} object to a set.
     * The set will contain all characters of the {@code CharMatcher} object.
     * @param chm the {@code CharMatcher} object to be converted
     * @return the result set
     */
    public static Set<Character> toSet(CharMatcher chm) {
        Set<Character> result = Sets.newHashSet();
        for (char ch = Character.MIN_VALUE; ch < Character.MAX_VALUE; ch++) {
            if (chm.matches(ch))
                result.add(ch);
        }
        return result;
    }

    /**
     * Converts the specified {@code CharMatcher} object to a string.
     * The string will be a sequence of all characters of the {@code CharMatcher} object, sorted by character value..
     * @param chm the {@code CharMatcher} object to be converted
     * @return the result string
     */
    public static String toString(CharMatcher chm) {
        StringBuilder sb = new StringBuilder();
        for (char ch = Character.MIN_VALUE; ch < Character.MAX_VALUE; ch++) {
            if (chm.matches(ch))
                sb.append(ch);
        }
        return sb.toString();
    }
}
