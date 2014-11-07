package net.milanqiu.mimas.string;

/**
 * Utilities related to string.
 *
 * <p>Creation Date: 2014-7-17
 * @author Milan Qiu
 */
public class StrUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private StrUtils() {
    }

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
     * Converts a character from native format to ascii format.
     * Specifically, converts to ascii format of unicode like '\u82ae'. All letters will be lowercase.
     * @param ntv the character in native format
     * @return the result character in ascii format
     */
    public static String nativeToAscii(char ntv) {
        if (ntv <= '\u000f')
            return "\\u000" + Integer.toHexString(ntv);
        else if (ntv <= '\u00ff')
            return "\\u00" + Integer.toHexString(ntv);
        else if (ntv <= '\u0fff')
            return "\\u0" + Integer.toHexString(ntv);
        else
            return "\\u" + Integer.toHexString(ntv);
    }

    /**
     * Converts a string from native format to ascii format.
     * Specifically, converts to ascii format of unicode like "\u82ae\udc29". All letters will be lowercase.
     * Every character in the string will be converted, even if it is an ascii character itself.
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
     * Converts a string from ascii format to native format.
     * @param ascii the string in ascii format
     * @return the result string in native format
     */
    public static String asciiToNative(String ascii) {
        int asciiLen = ascii.length();
        StringBuilder sb = new StringBuilder(asciiLen);
        int cursor = 0;
        while (cursor < asciiLen) {
            if (ascii.charAt(cursor) == '\\' && cursor <= asciiLen-6 && ascii.charAt(cursor+1) == 'u') {
                char ch = (char) Integer.parseInt(ascii.substring(cursor+2, cursor+6), 16);
                sb.append(ch);
                cursor+=6;
            } else {
                sb.append(ascii.charAt(cursor));
                cursor++;
            }
        }
        return sb.toString();
    }
}
