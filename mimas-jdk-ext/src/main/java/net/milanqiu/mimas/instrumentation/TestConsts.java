package net.milanqiu.mimas.instrumentation;

import java.util.concurrent.Callable;

/**
 * Declares a variety of constants used for unit tests and other tests.
 *
 * <p>Creation Date: 2014-2-8
 * @author Milan Qiu
 */
public class TestConsts {
    /**
     * Constant class is forbidden to be instantiated.
     */
    private TestConsts() {
    }

    /**
     * Byte constant #0.
     */
    public static final byte BYTE_0 = 82;
    /**
     * Byte constant #1.
     */
    public static final byte BYTE_1 = 8;
    /**
     * Byte constant #2.
     */
    public static final byte BYTE_2 = 29;
    /**
     * Byte constant #3.
     */
    public static final byte BYTE_3 = 7;
    /**
     * Byte constant #4.
     */
    public static final byte BYTE_4 = 50;

    /**
     * Character constant #0.
     */
    public static final char CHAR_0 = 1982;
    /**
     * Character constant #1.
     */
    public static final char CHAR_1 = 8;
    /**
     * Character constant #2.
     */
    public static final char CHAR_2 = 29;
    /**
     * Character constant #3.
     */
    public static final char CHAR_3 = 7;
    /**
     * Character constant #4.
     */
    public static final char CHAR_4 = 50;

    /**
     * Integer constant #0.
     */
    public static final int INT_0 = 1982;
    /**
     * Integer constant #1.
     */
    public static final int INT_1 = 8;
    /**
     * Integer constant #2.
     */
    public static final int INT_2 = 29;
    /**
     * Integer constant #3.
     */
    public static final int INT_3 = 7;
    /**
     * Integer constant #4.
     */
    public static final int INT_4 = 50;

    /**
     * String constant converted from integer constant #0.
     */
    public static final String STR_OF_INT_0 = "1982";
    /**
     * String constant converted from integer constant #1.
     */
    public static final String STR_OF_INT_1 = "8";
    /**
     * String constant converted from integer constant #2.
     */
    public static final String STR_OF_INT_2 = "29";
    /**
     * String constant converted from integer constant #3.
     */
    public static final String STR_OF_INT_3 = "7";
    /**
     * String constant converted from integer constant #4.
     */
    public static final String STR_OF_INT_4 = "50";

    /**
     * Float constant #0.
     */
    public static final float FLOAT_0 = 123.45F;
    /**
     * Float constant #1.
     */
    public static final float FLOAT_1 = 678.9F;
    /**
     * Float constant #2.
     */
    public static final float FLOAT_2 = 12345.6789F;
    /**
     * Float constant #3.
     */
    public static final float FLOAT_3 = 12345.678F;
    /**
     * Float constant #4.
     */
    public static final float FLOAT_4 = 12345.67F;

    /**
     * Double constant #0.
     */
    public static final double DOUBLE_0 = 123.45;
    /**
     * Double constant #1.
     */
    public static final double DOUBLE_1 = 678.9;
    /**
     * Double constant #2.
     */
    public static final double DOUBLE_2 = 12345.6789;
    /**
     * Double constant #3.
     */
    public static final double DOUBLE_3 = 12345.678;
    /**
     * Double constant #4.
     */
    public static final double DOUBLE_4 = 12345.67;

    /**
     * String constant #0.
     */
    public static final String STR_0 = "Milan";
    /**
     * String constant #1.
     */
    public static final String STR_1 = "Qiu";
    /**
     * String constant #2.
     */
    public static final String STR_2 = "aaa";
    /**
     * String constant #3.
     */
    public static final String STR_3 = "bbb";
    /**
     * String constant #4.
     */
    public static final String STR_4 = "ccc";

    /**
     * Object constant #0.
     */
    public static final Object OBJ_0 = new Object();
    /**
     * Object constant #1.
     */
    public static final Object OBJ_1 = new Object();
    /**
     * Object constant #2.
     */
    public static final Object OBJ_2 = new Object();
    /**
     * Object constant #3.
     */
    public static final Object OBJ_3 = new Object();
    /**
     * Object constant #4.
     */
    public static final Object OBJ_4 = new Object();

    /**
     * String constant converted from object constant #0.
     */
    public static final String STR_OF_OBJ_0 = OBJ_0.toString();
    /**
     * String constant converted from object constant #1.
     */
    public static final String STR_OF_OBJ_1 = OBJ_1.toString();
    /**
     * String constant converted from object constant #2.
     */
    public static final String STR_OF_OBJ_2 = OBJ_2.toString();
    /**
     * String constant converted from object constant #3.
     */
    public static final String STR_OF_OBJ_3 = OBJ_3.toString();
    /**
     * String constant converted from object constant #4.
     */
    public static final String STR_OF_OBJ_4 = OBJ_4.toString();

    /**
     * Callable constant returning a null object.
     */
    public static final Callable<Object> CALLABLE_RETURNING_NULL_OBJ = new Callable<Object>() {
        @Override
        public Object call() throws Exception {
            return null;
        }
    };
    /**
     * Callable constant returning a null string.
     */
    public static final Callable<String> CALLABLE_RETURNING_NULL_STR = new Callable<String>() {
        @Override
        public String call() throws Exception {
            return null;
        }
    };
}
