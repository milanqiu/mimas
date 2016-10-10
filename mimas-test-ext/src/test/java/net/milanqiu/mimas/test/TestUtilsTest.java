package net.milanqiu.mimas.test;

import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2016-10-10
 * @author Milan Qiu
 */
public class TestUtilsTest {

    @Test
    public void test_comparisonMsg() throws Exception {
        // String comparisonMsg(String expected, String actual)
        Assert.assertEquals(System.lineSeparator() +
                        "Expected :123" + System.lineSeparator() +
                        "Actual   :abc" + System.lineSeparator(),
                TestUtils.comparisonMsg("123", "abc"));

        // String comparisonMsg(Class<?> expectedClass, Class<?> actualClass)
        Assert.assertEquals(System.lineSeparator() +
                        "Expected class :" + Integer.class.getName() + System.lineSeparator() +
                        "Actual class   :" + String.class.getName() + System.lineSeparator(),
                TestUtils.comparisonMsg(Integer.class, String.class));
    }
}
