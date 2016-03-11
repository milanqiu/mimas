package net.milanqiu.mimas.guava;

import com.google.common.base.CharMatcher;
import com.google.common.collect.Sets;
import net.milanqiu.mimas.lang.TypeUtils;

import java.util.Set;

/**
 * An extension of {@link com.google.common.base.CharMatcher} to provide more utilities.
 * <p>
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class CharMatcherExt {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private CharMatcherExt() {}

    /**
     * Converts the specified {@link com.google.common.base.CharMatcher} object to a set.
     * The set will contain all matching characters of the {@link com.google.common.base.CharMatcher} object.
     * @param chm the {@link com.google.common.base.CharMatcher} object to be converted
     * @return the result set
     */
    public static Set<Character> toSet(CharMatcher chm) {
        final CharMatcher pchm = chm.precomputed();
        Set<Character> result = Sets.newHashSet();
        TypeUtils.traverseCharValues((ch) -> {
            if (pchm.matches(ch))
                result.add(ch);
        });
        return result;
    }

    /**
     * Returns a string representation of the specified {@link com.google.common.base.CharMatcher} object.
     * The string representation consists of a list of all matching characters of the {@link com.google.common.base.CharMatcher} object.
     * @param chm the {@link com.google.common.base.CharMatcher} object whose string representation to return
     * @return a string representation of {@code chm}
     */
    public static String toString(CharMatcher chm) {
        final CharMatcher pchm = chm.precomputed();
        StringBuilder sb = new StringBuilder();
        TypeUtils.traverseCharValues((ch) -> {
            if (pchm.matches(ch))
                sb.append(ch);
        });
        return sb.toString();
    }
}
