package net.milanqiu.mimas.instrumentation;

import java.util.concurrent.Callable;

/**
 * Declares a variety of constants used for unit tests and other tests.
 * <p>
 * Many of them are marginal.
 * <p>
 * Creation Date: 2014-02-08
 * @author Milan Qiu
 */
public class TestConsts {
    /**
     * Constant class is forbidden to be instantiated.
     */
    private TestConsts() {}

    /**
     * Character constant #0.
     */
    public static final char CHAR_0 = Character.MAX_VALUE;
    /**
     * Character constant #1.
     */
    public static final char CHAR_1 = Character.MIN_VALUE;
    /**
     * Character constant #2.
     */
    public static final char CHAR_2 = 0;
    /**
     * Character constant #3.
     */
    public static final char CHAR_3 = 1;
    /**
     * Character constant #4.
     */
    public static final char CHAR_4 = 255;

    /**
     * Byte constant #0.
     */
    public static final byte BYTE_0 = Byte.MAX_VALUE;
    /**
     * Byte constant #1.
     */
    public static final byte BYTE_1 = Byte.MIN_VALUE;
    /**
     * Byte constant #2.
     */
    public static final byte BYTE_2 = 0;
    /**
     * Byte constant #3.
     */
    public static final byte BYTE_3 = 1;
    /**
     * Byte constant #4.
     */
    public static final byte BYTE_4 = -1;

    /**
     * Short constant #0.
     */
    public static final short SHORT_0 = Short.MAX_VALUE;
    /**
     * Short constant #1.
     */
    public static final short SHORT_1 = Short.MIN_VALUE;
    /**
     * Short constant #2.
     */
    public static final short SHORT_2 = 0;
    /**
     * Short constant #3.
     */
    public static final short SHORT_3 = 1;
    /**
     * Short constant #4.
     */
    public static final short SHORT_4 = -1;

    /**
     * Integer constant #0.
     */
    public static final int INT_0 = Integer.MAX_VALUE;
    /**
     * Integer constant #1.
     */
    public static final int INT_1 = Integer.MIN_VALUE;
    /**
     * Integer constant #2.
     */
    public static final int INT_2 = 0;
    /**
     * Integer constant #3.
     */
    public static final int INT_3 = 1;
    /**
     * Integer constant #4.
     */
    public static final int INT_4 = -1;

    /**
     * String constant converted from integer constant #0.
     */
    public static final String STR_OF_INT_0 = Integer.toString(Integer.MAX_VALUE);
    /**
     * String constant converted from integer constant #1.
     */
    public static final String STR_OF_INT_1 = Integer.toString(Integer.MIN_VALUE);
    /**
     * String constant converted from integer constant #2.
     */
    public static final String STR_OF_INT_2 = "0";
    /**
     * String constant converted from integer constant #3.
     */
    public static final String STR_OF_INT_3 = "1";
    /**
     * String constant converted from integer constant #4.
     */
    public static final String STR_OF_INT_4 = "-1";

    /**
     * Long constant #0.
     */
    public static final long LONG_0 = Long.MAX_VALUE;
    /**
     * Long constant #1.
     */
    public static final long LONG_1 = Long.MIN_VALUE;
    /**
     * Long constant #2.
     */
    public static final long LONG_2 = 0L;
    /**
     * Long constant #3.
     */
    public static final long LONG_3 = 1L;
    /**
     * Long constant #4.
     */
    public static final long LONG_4 = -1L;

    /**
     * Float constant #0.
     */
    public static final float FLOAT_0 = Float.MAX_VALUE;
    /**
     * Float constant #1.
     */
    public static final float FLOAT_1 = Float.MIN_VALUE;
    /**
     * Float constant #2.
     */
    public static final float FLOAT_2 = 0.0f;
    /**
     * Float constant #3.
     */
    public static final float FLOAT_3 = 123.45f;
    /**
     * Float constant #4.
     */
    public static final float FLOAT_4 = -67.89f;

    /**
     * Double constant #0.
     */
    public static final double DOUBLE_0 = Double.MAX_VALUE;
    /**
     * Double constant #1.
     */
    public static final double DOUBLE_1 = Double.MIN_VALUE;
    /**
     * Double constant #2.
     */
    public static final double DOUBLE_2 = 0.0;
    /**
     * Double constant #3.
     */
    public static final double DOUBLE_3 = 123.45;
    /**
     * Double constant #4.
     */
    public static final double DOUBLE_4 = -67.89;

    /**
     * String constant #0.
     */
    public static final String STR_0 = "str0";
    /**
     * String constant #1.
     */
    public static final String STR_1 = "str1";
    /**
     * String constant #2.
     */
    public static final String STR_2 = "str2";
    /**
     * String constant #3.
     */
    public static final String STR_3 = "str23";
    /**
     * String constant #4.
     */
    public static final String STR_4 = "str234";

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
