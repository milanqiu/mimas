package net.milanqiu.mimas.lang;

/**
 * A tristate boolean with three alternatives: true, false and the third state,
 * which has special meaning defined by caller convention, such as uncertain or use-default.
 * <p>
 * Creation Date: 2016-12-18
 * @author Milan Qiu
 */
public enum TristateBoolean {
    TRUE,
    FALSE,
    THIRD_STATE;

    /**
     * Returns whether the tristate boolean is true.
     * @return {@code true} if the tristate boolean is true
     */
    public boolean isTrue() {
        return this == TRUE;
    }

    /**
     * Returns whether the tristate boolean is false.
     * @return {@code true} if the tristate boolean is false
     */
    public boolean isFalse() {
        return this == FALSE;
    }

    /**
     * Returns whether the tristate boolean is the third state.
     * @return {@code true} if the tristate boolean is the third state
     */
    public boolean isThirdState() {
        return this == THIRD_STATE;
    }

    /**
     * Returns a tristate boolean converted from the specified boolean.
     * @param b the boolean to be converted
     * @return the tristate boolean converted from the specified boolean
     */
    public static TristateBoolean valueOf(boolean b) {
        return (b ? TRUE : FALSE);
    }
}
