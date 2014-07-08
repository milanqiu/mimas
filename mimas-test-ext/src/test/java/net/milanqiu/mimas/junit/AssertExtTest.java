package net.milanqiu.mimas.junit;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-8
 * @author Milan Qiu
 */
public class AssertExtTest {

    @Test
    public void test_assertClassification() throws Exception {
        AssertExt.assertClassification(Integer.class, 123);
        AssertExt.assertClassification(String.class, "abc");
        AssertExt.assertClassification(ArithmeticException.class, new ArithmeticException());
    }
}
