package net.milanqiu.mimas.string;

import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.string.RegExpConsts.*;

/**
 * Creation Date: 2014-11-17
 * @author Milan Qiu
 */
public class RegExpConstsTest {

    @Test
    public void test_REG_EXP_IDENTIFIER_NAME() throws Exception {
        Assert.assertTrue("a".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertTrue("a0".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertTrue("a_".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertTrue("_0".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertFalse("".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertFalse("%".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertFalse("8a".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertFalse("$a".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertFalse("a b".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertFalse("a\nb".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertFalse("abcd*".matches(REG_EXP_IDENTIFIER_NAME));
    }
}
