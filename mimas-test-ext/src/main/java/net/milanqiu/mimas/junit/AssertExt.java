package net.milanqiu.mimas.junit;

import org.junit.Assert;
import org.junit.Test;

/**
 * More forms of assert functions.
 * An extension of {@link org.junit.Assert}.
 *
 * <p>Creation Date: 2014-7-8
 * @author Milan Qiu
 */
public class AssertExt {
    /**
     * Utility class is forbidden to be instantiated.
     */
    protected AssertExt() {
    }

    /**
     * Asserts an actual object is belong to an expected class.
     * @param expectedClazz the expected class
     * @param actualObj the actual object
     */
    public static void assertClassification(Class<?> expectedClazz, Object actualObj) {
        Assert.assertEquals(expectedClazz, actualObj.getClass());
    }
}
