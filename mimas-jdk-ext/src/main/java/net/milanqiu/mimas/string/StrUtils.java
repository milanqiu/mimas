package net.milanqiu.mimas.string;

import net.milanqiu.mimas.collect.tuple.StrStr;

import java.util.regex.Matcher;

/**
 * Utilities related to string.
 * <p>
 * Creation Date: 2014-07-17
 * @author Milan Qiu
 */
public class StrUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private StrUtils() {}

    /**
     * Empty string constant.
     */
    public static final String STR_EMPTY = "";
    /**
     * Space string constant.
     */
    public static final String STR_SPACE = " ";

    /**
     * Returns whether the specified character is an ASCII uppercase letter(regular expression: <i><tt>[A-Z]</tt></i>).
     * @param ch the character to be tested
     * @return {@code true} if the character is an ASCII uppercase letter
     */
    public static boolean isAsciiUpperCase(char ch) {
        return (ch >= 'A') && (ch <= 'Z');
    }

    /**
     * Returns whether the specified character is an ASCII lowercase letter(regular expression: <i><tt>[a-z]</tt></i>).
     * @param ch the character to be tested
     * @return {@code true} if the character is an ASCII lowercase letter
     */
    public static boolean isAsciiLowerCase(char ch) {
        return (ch >= 'a') && (ch <= 'z');
    }

    /**
     * Returns whether the specified character is an ASCII letter(regular expression: <i><tt>[A-Za-z]</tt></i>).
     * @param ch the character to be tested
     * @return {@code true} if the character is an ASCII letter
     */
    public static boolean isAsciiLetter(char ch) {
        return isAsciiUpperCase(ch) || isAsciiLowerCase(ch);
    }

    /**
     * Returns whether the specified character is an ASCII digit(regular expression: <i><tt>[0-9]</tt></i>).
     * @param ch the character to be tested
     * @return {@code true} if the character is an ASCII digit
     */
    public static boolean isAsciiDigit(char ch) {
        return (ch >= '0') && (ch <= '9');
    }

    /**
     * Returns whether the specified character is an ASCII letter or digit(regular expression: <i><tt>[A-Za-z0-9]</tt></i>).
     * @param ch the character to be tested
     * @return {@code true} if the character is an ASCII letter or digit
     */
    public static boolean isAsciiLetterOrDigit(char ch) {
        return isAsciiLetter(ch) || isAsciiDigit(ch);
    }

    /**
     * Returns whether the specified character is an ASCII plus or minus sign(regular expression: <i><tt>[\+\-]</tt></i>).
     * @param ch the character to be tested
     * @return {@code true} if the character is an ASCII plus or minus sign
     */
    public static boolean isAsciiSign(char ch) {
        return (ch == '+') || (ch == '-');
    }

    /**
     * Returns whether the specified character is a valid hex character(regular expression: <i><tt>[A-Fa-f0-9]</tt></i>).
     * @param ch the character to be tested
     * @return {@code true} if the character is a valid hex character
     */
    public static boolean isHexChar(char ch) {
        return isAsciiDigit(ch) || ((ch >= 'A') && (ch <= 'F')) || ((ch >= 'a') && (ch <= 'f'));
    }

    /**
     * A standard string mark, used as default value of separator, connector and the similar usages.
     */
    public static final String STD_MARK = "```";

    /**
     * In source string, assigns all occurrences of the specified parameter with the specified value.
     * Parameter is identified by a name, enclosed in two standard marks.
     * @param s the source string
     * @param paramName the name of parameter to be assigned
     * @param paramValue the value to be assigned with
     * @return the result string with all occurrences of the specified parameter been assigned
     */
    public static String assign(String s, String paramName, String paramValue) {
        return assign(s, paramName, paramValue, STD_MARK);
    }

    /**
     * In source string, assigns all occurrences of the specified parameter with the specified value.
     * Parameter is identified by a name, enclosed in two marks.
     * @param s the source string
     * @param paramName the name of parameter to be assigned
     * @param paramValue the value to be assigned with
     * @param paramMark the mark of parameter, enclosing parameter name
     * @return the result string with all occurrences of the specified parameter been assigned
     */
    public static String assign(String s, String paramName, String paramValue, String paramMark) {
        return s.replace(paramMark + paramName + paramMark, paramValue);
    }

    /**
     * Adds the specified prefix to the specified string if the string is not empty.
     * @param prefix the prefix to be added
     * @param s the string to add to
     * @return the empty string if {@code s} is empty, or the concatenation of {@code prefix} and {@code s}
     */
    public static String addPrefixIfNotEmpty(String prefix, String s) {
        return s.isEmpty() ? "" : (prefix + s);
    }

    /**
     * Adds the specified prefix to the specified string if the string is not null or empty.
     * @param prefix the prefix to be added
     * @param s the string to add to
     * @return the empty string if {@code s} is null or empty, or the concatenation of {@code prefix} and {@code s}
     */
    public static String addPrefixIfNotNullOrEmpty(String prefix, String s) {
        return (s == null || s.isEmpty()) ? "" : (prefix + s);
    }

    /**
     * Adds the specified suffix to the specified string if the string is not empty.
     * @param s the string to add to
     * @param suffix the suffix to be added
     * @return the empty string if {@code s} is empty, or the concatenation of {@code s} and {@code suffix}
     */
    public static String addSuffixIfNotEmpty(String s, String suffix) {
        return s.isEmpty() ? "" : (s + suffix);
    }

    /**
     * Adds the specified suffix to the specified string if the string is not null or empty.
     * @param s the string to add to
     * @param suffix the suffix to be added
     * @return the empty string if {@code s} is null or empty, or the concatenation of {@code s} and {@code suffix}
     */
    public static String addSuffixIfNotNullOrEmpty(String s, String suffix) {
        return (s == null || s.isEmpty()) ? "" : (s + suffix);
    }

    /**
     * Removes the specified prefix from the specified string.
     * Throws a {@link StringNotFoundException} if the prefix doesn't exist.
     * @param prefix the prefix to be removed
     * @param s the string to be removed prefix from
     * @return the result string with prefix removed
     */
    public static String removePrefix(String prefix, String s) {
        if (s.startsWith(prefix)) {
            return s.substring(prefix.length());
        } else {
            throw new StringNotFoundException("prefix %s not found in %s", prefix, s);
        }
    }

    /**
     * Removes the specified prefix from the specified string.
     * Returns the original string if the prefix doesn't exist.
     * @param prefix the prefix to be removed
     * @param s the string to be removed prefix from
     * @return the result string with prefix removed
     */
    public static String removePrefixIfExists(String prefix, String s) {
        if (s.startsWith(prefix)) {
            return s.substring(prefix.length());
        } else {
            return s;
        }
    }

    /**
     * Removes the specified regular expression prefix from the specified string.
     * Throws a {@link StringNotFoundException} if the regular expression prefix doesn't exist.
     * @param regExpPrefix the regular expression prefix to be removed
     * @param s the string to be removed regular expression prefix from
     * @return a {@link StrStr} object with A equaling to actually matched prefix and B equaling to the result string with regular expression prefix removed
     */
    public static StrStr removeRegExpPrefix(String regExpPrefix, String s) {
        Matcher matcher = PatternCache.get(regExpPrefix).matcher(s);
        if (matcher.lookingAt()) {
            return new StrStr(matcher.group(), s.substring(matcher.end()));
        } else {
            throw new StringNotFoundException("regular expression prefix %s not found in %s", regExpPrefix, s);
        }
    }

    /**
     * Removes the specified regular expression prefix from the specified string.
     * Returns a {@link StrStr} object with A equaling to empty string and B equaling to the original string if the prefix doesn't exist.
     * @param regExpPrefix the regular expression prefix to be removed
     * @param s the string to be removed regular expression prefix from
     * @return a {@link StrStr} object with A equaling to actually matched prefix and B equaling to the result string with regular expression prefix removed
     */
    public static StrStr removeRegExpPrefixIfExists(String regExpPrefix, String s) {
        Matcher matcher = PatternCache.get(regExpPrefix).matcher(s);
        if (matcher.lookingAt()) {
            return new StrStr(matcher.group(), s.substring(matcher.end()));
        } else {
            return new StrStr("", s);
        }
    }

    /**
     * Removes the specified suffix from the specified string.
     * Throws a {@link StringNotFoundException} if the suffix doesn't exist.
     * @param suffix the suffix to be removed
     * @param s the string to be removed suffix from
     * @return the result string with suffix removed
     */
    public static String removeSuffix(String suffix, String s) {
        if (s.endsWith(suffix)) {
            return s.substring(0, s.length()-suffix.length());
        } else {
            throw new StringNotFoundException("suffix %s not found in %s", suffix, s);
        }
    }

    /**
     * Removes the specified suffix from the specified string.
     * Returns the original string if the suffix doesn't exist.
     * @param suffix the suffix to be removed
     * @param s the string to be removed suffix from
     * @return the result string with suffix removed
     */
    public static String removeSuffixIfExists(String suffix, String s) {
        if (s.endsWith(suffix)) {
            return s.substring(0, s.length()-suffix.length());
        } else {
            return s;
        }
    }

    /**
     * Returns a string consisting of a specified number of concatenated copies of a specified string.
     * Same as <code>com.google.common.base.Strings.repeat(String, int)</code> of guava.
     * @param s the string to be repeated
     * @param count the number of times to repeat the string
     * @return the result string containing {@code s} repeated {@code count} times, or the empty string if {@code count} is less then one
     */
    public static String repeat(String s, int count) {
        if (count <= 0)
            return StrUtils.STR_EMPTY;
        else if (count == 1)
            return s;

        final int len = s.length();
        final long longSize = (long) len * (long) count;
        final int size = (int) longSize;
        if (size != longSize) {
            throw new ArrayIndexOutOfBoundsException("Required array size too large: " + longSize);
        }

        final char[] array = new char[size];
        s.getChars(0, len, array, 0);
        int n;
        for (n = len; n < size-n; n <<= 1) {
            System.arraycopy(array, 0, array, n, n);
        }
        System.arraycopy(array, 0, array, n, size-n);
        return new String(array);
    }
}
