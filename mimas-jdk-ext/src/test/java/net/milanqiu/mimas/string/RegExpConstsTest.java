package net.milanqiu.mimas.string;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

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
        Assert.assertFalse("88".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertFalse("$a".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertFalse("a b".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertFalse("a\nb".matches(REG_EXP_IDENTIFIER_NAME));
        Assert.assertFalse("abcd*".matches(REG_EXP_IDENTIFIER_NAME));

        Assert.assertTrue(Pattern.compile(REG_EXP_IDENTIFIER_NAME).matcher("abcd*").find());
        Assert.assertFalse(Pattern.compile(REG_EXP_STRICT_IDENTIFIER_NAME).matcher("abcd*").find());
    }

    @Test
    public void test_REG_EXP_POSITIVE_INTEGER() throws Exception {
        Assert.assertFalse("0".matches(REG_EXP_POSITIVE_INTEGER));
        Assert.assertTrue("123".matches(REG_EXP_POSITIVE_INTEGER));
        Assert.assertTrue("+123".matches(REG_EXP_POSITIVE_INTEGER));
        Assert.assertFalse("-123".matches(REG_EXP_POSITIVE_INTEGER));
        Assert.assertFalse("".matches(REG_EXP_POSITIVE_INTEGER));
        Assert.assertFalse("a0".matches(REG_EXP_POSITIVE_INTEGER));
        Assert.assertFalse("123.456".matches(REG_EXP_POSITIVE_INTEGER));
        Assert.assertFalse("+123.456".matches(REG_EXP_POSITIVE_INTEGER));
        Assert.assertFalse("-123.456".matches(REG_EXP_POSITIVE_INTEGER));

        Assert.assertTrue(Pattern.compile(REG_EXP_POSITIVE_INTEGER).matcher("123.456").find());
        Assert.assertFalse(Pattern.compile(REG_EXP_STRICT_POSITIVE_INTEGER).matcher("123.456").find());
    }

    @Test
    public void test_REG_EXP_NONNEGATIVE_INTEGER() throws Exception {
        Assert.assertTrue("0".matches(REG_EXP_NONNEGATIVE_INTEGER));
        Assert.assertTrue("123".matches(REG_EXP_NONNEGATIVE_INTEGER));
        Assert.assertTrue("+123".matches(REG_EXP_NONNEGATIVE_INTEGER));
        Assert.assertFalse("-123".matches(REG_EXP_NONNEGATIVE_INTEGER));
        Assert.assertFalse("".matches(REG_EXP_NONNEGATIVE_INTEGER));
        Assert.assertFalse("a0".matches(REG_EXP_NONNEGATIVE_INTEGER));
        Assert.assertFalse("123.456".matches(REG_EXP_NONNEGATIVE_INTEGER));
        Assert.assertFalse("+123.456".matches(REG_EXP_NONNEGATIVE_INTEGER));
        Assert.assertFalse("-123.456".matches(REG_EXP_NONNEGATIVE_INTEGER));

        Assert.assertTrue(Pattern.compile(REG_EXP_NONNEGATIVE_INTEGER).matcher("123.456").find());
        Assert.assertFalse(Pattern.compile(REG_EXP_STRICT_NONNEGATIVE_INTEGER).matcher("123.456").find());
    }

    @Test
    public void test_REG_EXP_INTEGER() throws Exception {
        Assert.assertTrue("0".matches(REG_EXP_INTEGER));
        Assert.assertTrue("123".matches(REG_EXP_INTEGER));
        Assert.assertTrue("+123".matches(REG_EXP_INTEGER));
        Assert.assertTrue("-123".matches(REG_EXP_INTEGER));
        Assert.assertFalse("".matches(REG_EXP_INTEGER));
        Assert.assertFalse("a0".matches(REG_EXP_INTEGER));
        Assert.assertFalse("123.456".matches(REG_EXP_INTEGER));
        Assert.assertFalse("+123.456".matches(REG_EXP_INTEGER));
        Assert.assertFalse("-123.456".matches(REG_EXP_INTEGER));

        Assert.assertTrue(Pattern.compile(REG_EXP_INTEGER).matcher("123.456").find());
        Assert.assertFalse(Pattern.compile(REG_EXP_STRICT_INTEGER).matcher("123.456").find());
    }

    @Test
    public void test_REG_EXP_IDENTIFIER_NAME_OR_INTEGER() throws Exception {
        Assert.assertTrue("a".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertTrue("a0".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertTrue("a_".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertTrue("_0".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertFalse("".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertFalse("%".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertFalse("8a".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertTrue("88".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertFalse("$a".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertFalse("a b".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertFalse("a\nb".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertFalse("abcd*".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));

        Assert.assertTrue(Pattern.compile(REG_EXP_IDENTIFIER_NAME_OR_INTEGER).matcher("abcd*").find());
        Assert.assertFalse(Pattern.compile(REG_EXP_STRICT_IDENTIFIER_NAME_OR_INTEGER).matcher("abcd*").find());

        Assert.assertTrue("0".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertTrue("123".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertTrue("+123".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertTrue("-123".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertFalse("".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertTrue("a0".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertFalse("123.456".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertFalse("+123.456".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));
        Assert.assertFalse("-123.456".matches(REG_EXP_IDENTIFIER_NAME_OR_INTEGER));

        Assert.assertTrue(Pattern.compile(REG_EXP_IDENTIFIER_NAME_OR_INTEGER).matcher("123.456").find());
        Assert.assertFalse(Pattern.compile(REG_EXP_STRICT_IDENTIFIER_NAME_OR_INTEGER).matcher("123.456").find());
    }
}
