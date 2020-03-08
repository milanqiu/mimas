package net.milanqiu.mimas.string;

import net.milanqiu.mimas.instrumentation.DebugUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilities related to regular expression.
 * <p>
 * Creation Date: 2020-03-07
 * @author Milan Qiu
 */
public class RegExpUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private RegExpUtils() {}

    /**
     * Removes all group names from a regular expression.
     * @param regex the regular expression to be removed group names from
     * @return the result regular expression with group names removed
     */
    public static String removeGroupNames(String regex) {
        return regex.replaceAll("\\?<.*?>", "");
    }

    /**
     * Finds all occurences of a regular expression in a string, and fills them into a list.
     * @param regex the regular expression to be found
     * @param s the string to be found in
     * @return all occurences of the regular expression in the string
     */
    public static List<String> findAll(String regex, String s) {
        List<String> result = new ArrayList<>();
        Matcher matcher = PatternCache.get(regex).matcher(s);
        int start = 0;
        while (matcher.find(start)) {
            result.add(matcher.group());
            start = matcher.end();
        }
        return result;
    }

    /**
     * Attempts to match the specified string against 0 or more repetitions of the specified regular expression.
     * Different from using "*" directly, this method will collect all repetitions into a list.
     * @param regex the regular expression to be matched against
     * @param s the string to be matched
     * @param allRepetitions all repetitions of the regular expression in the string
     * @return whether or not the string matches against 0 or more repetitions of the regular expression
     */
    public static boolean matchesZeroOrMore(String regex, String s, List<String> allRepetitions) {
        if (PatternCache.get("("+regex+")*").matcher(s).matches()) {
            Pattern pattern = PatternCache.get(regex);
            while (!s.isEmpty()) {
                Matcher matcher = pattern.matcher(s);
                DebugUtils.assertTrue(matcher.lookingAt());
                allRepetitions.add(matcher.group());
                s = s.substring(matcher.end());
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempts to match the specified string against 1 or more repetitions of the specified regular expression.
     * Different from using "+" directly, this method will collect all repetitions into a list.
     * @param regex the regular expression to be matched against
     * @param s the string to be matched
     * @param allRepetitions all repetitions of the regular expression in the string
     * @return whether or not the string matches against 1 or more repetitions of the regular expression
     */
    public static boolean matchesOneOrMore(String regex, String s, List<String> allRepetitions) {
        if (PatternCache.get("("+regex+")+").matcher(s).matches()) {
            Pattern pattern = PatternCache.get(regex);
            do {
                Matcher matcher = pattern.matcher(s);
                DebugUtils.assertTrue(matcher.lookingAt());
                allRepetitions.add(matcher.group());
                s = s.substring(matcher.end());
            } while (!s.isEmpty());
            return true;
        } else {
            return false;
        }
    }

    /**
     * Attempts to match the specified string against 1 or more repetitions of the specified regular expression,
     * each repetition separated by the specified separator.
     * Different from using "+" or "*" directly, this method will collect all repetitions into a list.
     * @param regex the regular expression to be matched against
     * @param s the string to be matched
     * @param separator the separator to separate each repetition, which can also be a regular expression
     * @param allRepetitions all repetitions of the regular expression in the string
     * @return whether or not the string matches against 1 or more repetitions of the regular expression with the separator
     */
    public static boolean matchesOneOrMoreWithSeparator(String regex, String s, String separator, List<String> allRepetitions) {
        if (PatternCache.get(removeGroupNames(regex)+"("+separator+regex+")*").matcher(s).matches()) {
            Pattern pattern = PatternCache.get(regex);
            Pattern patternSeparator = PatternCache.get(separator);
            do {
                Matcher matcher = pattern.matcher(s);
                DebugUtils.assertTrue(matcher.lookingAt());
                allRepetitions.add(matcher.group());
                s = s.substring(matcher.end());

                if (s.isEmpty()) {
                    return true;
                } else {
                    Matcher matcherSeparator = patternSeparator.matcher(s);
                    DebugUtils.assertTrue(matcherSeparator.lookingAt());
                    s = s.substring(matcherSeparator.end());
                }
            } while (true);
        } else {
            return false;
        }
    }
}
