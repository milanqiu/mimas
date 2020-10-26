package net.milanqiu.mimas.string;

/**
 * Declares a variety of regular expression constants.
 * <p>
 * Creation Date: 2014-09-26
 * @author Milan Qiu
 */
public class RegExpConsts {
    /**
     * Constant class is forbidden to be instantiated.
     */
    private RegExpConsts() {}

    /**
     * Regular expression of identifier name.
     */
    public static final String REG_EXP_IDENTIFIER_NAME = "[A-Za-z_][A-Za-z_0-9]*";
    /**
     * Regular expression of identifier name in strict mode. Only matches the whole string or the whole line.
     */
    public static final String REG_EXP_STRICT_IDENTIFIER_NAME = RegExpUtils.strict(REG_EXP_IDENTIFIER_NAME);

    /**
     * Regular expression of positive integer.
     */
    public static final String REG_EXP_POSITIVE_INTEGER = "\\+?[1-9][0-9]*";
    /**
     * Regular expression of positive integer in strict mode. Only matches the whole string or the whole line.
     */
    public static final String REG_EXP_STRICT_POSITIVE_INTEGER = RegExpUtils.strict(REG_EXP_POSITIVE_INTEGER);

    /**
     * Regular expression of non-negative integer.
     */
    public static final String REG_EXP_NONNEGATIVE_INTEGER = "\\+?\\d+";
    /**
     * Regular expression of non-negative integer in strict mode. Only matches the whole string or the whole line.
     */
    public static final String REG_EXP_STRICT_NONNEGATIVE_INTEGER = RegExpUtils.strict(REG_EXP_NONNEGATIVE_INTEGER);

    /**
     * Regular expression of integer.
     */
    public static final String REG_EXP_INTEGER = "[\\+-]?\\d+";
    /**
     * Regular expression of integer in strict mode. Only matches the whole string or the whole line.
     */
    public static final String REG_EXP_STRICT_INTEGER = RegExpUtils.strict(REG_EXP_INTEGER);

    /**
     * Regular expression of identifier name or integer.
     */
    public static final String REG_EXP_IDENTIFIER_NAME_OR_INTEGER = REG_EXP_IDENTIFIER_NAME + "|" + REG_EXP_INTEGER;
    /**
     * Regular expression of identifier name or integer in strict mode. Only matches the whole string or the whole line.
     */
    public static final String REG_EXP_STRICT_IDENTIFIER_NAME_OR_INTEGER = RegExpUtils.strict(REG_EXP_IDENTIFIER_NAME_OR_INTEGER);
}
