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
     * Regular expression of strict identifier name. Extra character is not allowed.
     */
    public static final String REG_EXP_STRICT_IDENTIFIER_NAME = "^" + REG_EXP_IDENTIFIER_NAME + "$";
}
