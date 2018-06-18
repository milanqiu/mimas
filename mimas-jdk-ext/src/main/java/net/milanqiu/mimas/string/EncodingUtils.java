package net.milanqiu.mimas.string;

import net.milanqiu.mimas.lang.RunnableWithParam;
import net.milanqiu.mimas.lang.TypeUtils;

import java.io.UnsupportedEncodingException;

/**
 * Utilities related to encoding.
 * <p>
 * Creation Date: 2018-06-14
 * @author Milan Qiu
 */
public class EncodingUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private EncodingUtils() {}

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
                    StrUtils.isHexChar(ascii.charAt(cursor + 2)) && StrUtils.isHexChar(ascii.charAt(cursor + 3)) &&
                    StrUtils.isHexChar(ascii.charAt(cursor + 4)) && StrUtils.isHexChar(ascii.charAt(cursor + 5))) {
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
     * Returns the byte count of a UTF-8 character base on the leading byte.
     * @param leadingByte the leading byte of the UTF-8 character
     * @return the byte count of the UTF-8 character
     * @throws UnsupportedEncodingException if the leading byte is illegal
     */
    public static int getByteCountOfUtf8(byte leadingByte) throws UnsupportedEncodingException {
        if ((leadingByte & 0b10000000) == 0)
            return 1;
        else if ((leadingByte & 0b11100000) == 0b11000000)
            return 2;
        else if ((leadingByte & 0b11110000) == 0b11100000)
            return 3;
        else if ((leadingByte & 0b11111000) == 0b11110000)
            return 4;
        else if ((leadingByte & 0b11111100) == 0b11111000)
            return 5;
        else if ((leadingByte & 0b11111110) == 0b11111100)
            return 6;
        else
            throw new UnsupportedEncodingException();
    }
}
