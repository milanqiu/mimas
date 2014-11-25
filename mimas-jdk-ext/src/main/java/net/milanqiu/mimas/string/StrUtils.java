package net.milanqiu.mimas.string;

import net.milanqiu.mimas.lang.LangUtils;
import net.milanqiu.mimas.lang.RunnableWithParam;

/**
 * Utilities related to string.
 * <p>
 * Creation Date: 2014-7-17
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
     * Line separator constant. Cross-platform.
     */
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    /**
     * Byte Order Mark of Unicode.
     */
    public static final char UNICODE_BOM = '\uFEFF';
    /**
     * High/low byte reversed Byte Order Mark of Unicode.
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
    public static final int VALID_UNICODE_CHAR_VALUE_COUNT = LangUtils.CHAR_VALUE_COUNT - INVALID_UNICODE_CHAR_VALUE_COUNT;

    /**
     * Returns an array holding all valid unicode character values in ascending order.
     * The surrogate section (from '\uD800' to '\uDFFF') and the reversed BOM ('\uFFFE') are invalid unicode character
     * values. All other characters are valid unicode character values.
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
     * Converts the specified character from native format to ascii format.
     * Specifically, converts to ascii format of unicode like <code>'\\u82AE'</code>.
     * All letters will be uppercase except the 'u' mark.
     * @param ntv the character in native format
     * @return the result character in ascii format
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
     * Converts the specified string from native format to ascii format.
     * Specifically, converts to ascii format of unicode like <code>"\\u82AE\\uDC29"</code>.
     * All letters will be uppercase except the 'u' mark.
     * The conversion is mandatory on every character in the source string, even if it is an ascii character.
     * @param ntv the string in native format
     * @return the result string in ascii format
     */
    public static String nativeToAscii(String ntv) {
        StringBuilder sb = new StringBuilder(ntv.length()*6);
        for (int i = 0; i < ntv.length(); i++) {
            sb.append(nativeToAscii(ntv.charAt(i)));
        }
        return sb.toString();
    }

    /**
     * Converts the specified string from ascii format to native format.
     * The conversion allows the source string is not in "pure" ascii format. All characters which can't be parsed as
     * ascii format will be directly copied to result string. For example,
     * <code>"a\\u006\\u0061\\u006"</code> will be converted to <code>"a\\u006a\\u006"</code>.
     * @param ascii the string in ascii format
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
}
