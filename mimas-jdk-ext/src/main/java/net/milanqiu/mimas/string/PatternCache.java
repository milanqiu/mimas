package net.milanqiu.mimas.string;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * A cache holding some {@link java.util.regex.Pattern} objects.
 * A singleton class.
 * <p>
 * Creation Date: 2020-03-07
 * @author Milan Qiu
 */
public class PatternCache {

    private static PatternCache singleton;

    public static PatternCache getSingleton() {
        if (singleton == null) {
            singleton = new PatternCache();
        }
        return singleton;
    }

    private PatternCache() {}

    private Map<String, Pattern> patterns = new HashMap<>();

    /**
     * Returns a {@link java.util.regex.Pattern} object of the specified regular expression.
     * Doesn't need to put before get. Actually this cache class has no put method.
     * @param regex the regular expression to got by
     * @return a {@link java.util.regex.Pattern} object of the specified regular expression
     */
    public Pattern getPattern(String regex) {
        Pattern result = patterns.get(regex);
        if (result == null) {
            result = Pattern.compile(regex);
            patterns.put(regex, result);
        }
        return result;
    }

    /**
     * Returns a {@link java.util.regex.Pattern} object of the specified regular expression, which is fetched from a static cache.
     * Doesn't need to put before get. Actually this cache class has no put method.
     * @param regex the regular expression to got by
     * @return a {@link java.util.regex.Pattern} object of the specified regular expression
     */
    public static Pattern get(String regex) {
        return PatternCache.getSingleton().getPattern(regex);
    }
}
