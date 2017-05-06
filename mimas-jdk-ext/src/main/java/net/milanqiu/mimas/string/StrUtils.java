package net.milanqiu.mimas.string;

import net.milanqiu.mimas.lang.RunnableWithParam;
import net.milanqiu.mimas.lang.TypeUtils;

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
     * Converts the specified character from native format to ASCII format.
     * Specifically, converts to ASCII format of unicode like {@code "&#92;u82AE"}.
     * All letters will be uppercase except the 'u' mark.
     * @param ntv the character in native format
     * @return the result character in ASCII format
     */
    public static String nativeToAscii(char ntv) {
        if (ntv <= '\u000F')
            return "\\u000" + Integer.toHexString(ntv).toUpperCase();
        else if (ntv <= '\u00FF')
            return "\\u00" + Integer.toHexString(ntv).toUpperCase();
        else if (ntv <= '\u0FFF')
            return "\\u0" + Integer.toHexString(ntv).toUpperCase();
        else
            return "\\u" + Integer.toHexString(ntv).toUpperCase();
    }

    /**
     * Converts the specified string from native format to ASCII format.
     * Specifically, converts to ASCII format of unicode like {@code "&#92;u82AE&#92;uDC29"}.
     * All letters will be uppercase except the 'u' mark.
     * The conversion is mandatory on every character in the source string, even if it has already been an ASCII character.
     * @param ntv the string in native format
     * @return the result string in ASCII format
     */
    public static String nativeToAscii(String ntv) {
        char[] ntvCharArr = ntv.toCharArray();
        StringBuilder sb = new StringBuilder(ntvCharArr.length*6);
        for (char ch : ntvCharArr) {
            sb.append(nativeToAscii(ch));
        }
        return sb.toString();
    }

    /**
     * Converts the specified string from ASCII format to native format.
     * This conversion allows the source string is not in "pure" ASCII format. All characters which can't be parsed as
     * ASCII format will be directly copied to result string.
     * For example, {@code "a&#92;u006&#92;u0061&#92;u006"} will be converted to {@code "a&#92;u006a&#92;u006"}.
     * @param ascii the string in ASCII format
     * @return the result string in native format
     */
    public static String asciiToNative(String ascii) {
        int asciiLen = ascii.length();
        StringBuilder sb = new StringBuilder(asciiLen);
        int cursor = 0;
        while (cursor < asciiLen) {
            if (ascii.charAt(cursor) == '\\' && cursor <= asciiLen-6 && ascii.charAt(cursor+1) == 'u' &&
                    isHexChar(ascii.charAt(cursor+2)) && isHexChar(ascii.charAt(cursor+3)) &&
                    isHexChar(ascii.charAt(cursor+4)) && isHexChar(ascii.charAt(cursor+5))) {
                char ch = (char) Integer.parseInt(ascii.substring(cursor+2, cursor+6), 16);
                sb.append(ch);
                cursor += 6;
            } else {
                sb.append(ascii.charAt(cursor));
                cursor++;
            }
        }
        return sb.toString();
    }

    /**
     * Byte Order Mark of Unicode, which represents little-endian.
     */
    public static final char UNICODE_BOM = '\uFEFF';
    /**
     * High/low byte reversed Byte Order Mark of Unicode, which represents big-endian.
     */
    public static final char REVERSED_UNICODE_BOM = '\uFFFE';

    /**
     * The count of surrogate unicode character values.
     */
    public static final int SURROGATE_UNICODE_CHAR_VALUE_COUNT = Character.MAX_SURROGATE - Character.MIN_SURROGATE + 1;

    /**
     * The count of invalid unicode character values.
     * See {@link #getValidUnicodeCharValues()} to study invalid unicode character values.
     */
    public static final int INVALID_UNICODE_CHAR_VALUE_COUNT = SURROGATE_UNICODE_CHAR_VALUE_COUNT + 1;
    /**
     * The count of valid unicode character values.
     * See {@link #getValidUnicodeCharValues()} to study valid unicode character values.
     */
    public static final int VALID_UNICODE_CHAR_VALUE_COUNT = TypeUtils.CHAR_VALUE_COUNT - INVALID_UNICODE_CHAR_VALUE_COUNT;

    /**
     * Returns an array holding all valid unicode character values in ascending order.
     * The surrogate section (from {@code '&#92;uD800'} to {@code '&#92;uDFFF'}) and the reversed BOM ({@code '&#92;uFFFE'})
     * are invalid unicode character values. All other characters are valid unicode character values.
     * The production of array costs some time. So if need to use the array repeatedly, create a variable to cache it.
     * @return an array holding all valid unicode character values in ascending order
     */
    public static char[] getValidUnicodeCharValues() {
        char[] result = new char[VALID_UNICODE_CHAR_VALUE_COUNT];
        int index = 0;
        for (int i = Character.MIN_VALUE; i <= Character.MAX_VALUE; i++) {
            if ((i < Character.MIN_SURROGATE || i > Character.MAX_SURROGATE) && i != REVERSED_UNICODE_BOM)
                result[index++] = (char) i;
        }
        return result;
    }

    /**
     * Traverses all valid unicode character values in ascending order.
     * See {@link #getValidUnicodeCharValues()} to study valid unicode character values.
     * @param action the action to be taken in each visit
     */
    public static void traverseValidUnicodeCharValues(RunnableWithParam.WithChar action) {
        for (int i = Character.MIN_VALUE; i <= Character.MAX_VALUE; i++) {
            if ((i < Character.MIN_SURROGATE || i > Character.MAX_SURROGATE) && i != REVERSED_UNICODE_BOM)
                action.run((char) i);
        }
    }

    /**
     * Returns a string holding all valid unicode characters in ascending order.
     * The surrogate section (from {@code '&#92;uD800'} to {@code '&#92;uDFFF'}) and the reversed BOM ({@code '&#92;uFFFE'})
     * are invalid unicode characters. All other characters are valid unicode characters.
     * The production of string costs some time. So if need to use the string repeatedly, create a variable to cache it.
     * @return a string holding all valid unicode characters in ascending order
     */
    public static String getValidUnicodeString() {
        return new String(getValidUnicodeCharValues());
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
