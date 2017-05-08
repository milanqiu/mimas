package net.milanqiu.mimas.code.script;

import net.milanqiu.mimas.string.StrUtils;

/**
 * Utilities related to HTML.
 * <p>
 * Creation Date: 2016-07-23
 * @author Milan Qiu
 */
public class HtmlUtils {
    /**
     * Utility class is forbidden to be instantiated.
     */
    private HtmlUtils() {}

    /**
     * HTML escape sequence of space.
     */
    public static final String HTML_SPACE = "&nbsp;";
    /**
     * HTML escape sequence of ampersand.
     */
    public static final String HTML_AMPERSAND = "&amp;";
    /**
     * HTML escape sequence of less than.
     */
    public static final String HTML_LESS_THAN = "&lt;";
    /**
     * HTML escape sequence of greater than.
     */
    public static final String HTML_GREATER_THAN = "&gt;";
    /**
     * HTML escape sequence of double quotation mark.
     */
    public static final String HTML_DOUBLE_QUOTATION = "&quot;";
    /**
     * HTML escape sequence of single quotation mark.
     */
    public static final String HTML_SINGLE_QUOTATION = "&#39;";

    /**
     * HTML tag of line break.
     */
    public static final String HTML_LINE_BREAK = "<br>";

    /**
     * Converts a series of spaces into HTML escape sequence.
     * @param count the count of spaces
     * @return HTML escape sequence of a series of spaces
     */
    public static String htmlSpaces(int count) {
        return StrUtils.repeat(HTML_SPACE, count);
    }

    /**
     * Converts a string into HTML representation by escape sequence.
     * @param str the specified string to be converted
     * @return HTML representation of the string
     */
    public static String htmlEscape(String str) {
        StringBuilder sb = new StringBuilder(str.length());
        for (char ch : str.toCharArray()) {
            switch (ch) {
                case '&':
                    sb.append(HTML_AMPERSAND);
                    break;
                case '<':
                    sb.append(HTML_LESS_THAN);
                    break;
                case '>':
                    sb.append(HTML_GREATER_THAN);
                    break;
                case '"':
                    sb.append(HTML_DOUBLE_QUOTATION);
                    break;
                case '\'':
                    sb.append(HTML_SINGLE_QUOTATION);
                    break;
                case ' ':
                    sb.append(HTML_SPACE);
                    break;
                default:
                    sb.append(ch);
            }
        }
        return sb.toString();
    }

    /**
     * Converts a series of line breaks into HTML tags.
     * @param count the count of line breaks
     * @return HTML tags of a series of line breaks
     */
    public static String htmlLineBreaks(int count) {
        return StrUtils.repeat(HTML_LINE_BREAK, count);
    }

    /**
     * Generates a series of option tags of HTML, which indicate an integer range to be selected.
     * @param lowerBound the lower bound of the integer range
     * @param upperBound the upper bound of the integer range
     * @param defaultSelected the selected integer by default
     * @return HTML representation of option tags
     */
    public static String optionsOfIntRange(int lowerBound, int upperBound, int defaultSelected) {
        StringBuilder sb = new StringBuilder();
        for (int i = lowerBound; i <= upperBound; i++) {
            sb.append("<option value=\"")
              .append(i)
              .append("\"");
            if (i == defaultSelected)
                sb.append(" selected");
            sb.append(">")
              .append(i)
              .append("</option>");
        }
        return sb.toString();
    }

    /**
     * Removes HTML tags from a string.
     * @param html the string to be removed HTML tags from
     * @return the result string with HTML tags removed
     */
    public static String removeTags(String html) {
        return html.replaceAll("<.*?>", "");
    }
}
