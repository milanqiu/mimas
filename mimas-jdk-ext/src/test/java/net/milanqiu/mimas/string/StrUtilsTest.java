package net.milanqiu.mimas.string;

import net.milanqiu.mimas.collect.tuple.StrStr;
import net.milanqiu.mimas.junit.AssertExt;
import net.milanqiu.mimas.lang.TypeUtils;
import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-11-04
 * @author Milan Qiu
 */
public class StrUtilsTest {

    @Test
    public void test_isAsciiUpperCase() throws Exception {
        TypeUtils.traverseCharValues((param) -> {
            Assert.assertEquals(String.valueOf(param).matches("[A-Z]"), StrUtils.isAsciiUpperCase(param));
        });
    }

    @Test
    public void test_isAsciiLowerCase() throws Exception {
        TypeUtils.traverseCharValues((param) -> {
            Assert.assertEquals(String.valueOf(param).matches("[a-z]"), StrUtils.isAsciiLowerCase(param));
        });
    }

    @Test
    public void test_isAsciiLetter() throws Exception {
        TypeUtils.traverseCharValues((param) -> {
            Assert.assertEquals(String.valueOf(param).matches("[A-Za-z]"), StrUtils.isAsciiLetter(param));
        });
    }

    @Test
    public void test_isAsciiDigit() throws Exception {
        TypeUtils.traverseCharValues((param) -> {
            Assert.assertEquals(String.valueOf(param).matches("[0-9]"), StrUtils.isAsciiDigit(param));
        });
    }

    @Test
    public void test_isAsciiLetterOrDigit() throws Exception {
        TypeUtils.traverseCharValues((param) -> {
            Assert.assertEquals(String.valueOf(param).matches("[A-Za-z0-9]"), StrUtils.isAsciiLetterOrDigit(param));
        });
    }

    @Test
    public void test_isAsciiSign() throws Exception {
        TypeUtils.traverseCharValues((param) -> {
            Assert.assertEquals(String.valueOf(param).matches("[\\+\\-]"), StrUtils.isAsciiSign(param));
        });
    }

    @Test
    public void test_isHexChar() throws Exception {
        TypeUtils.traverseCharValues((param) -> {
            Assert.assertEquals(String.valueOf(param).matches("[A-Fa-f0-9]"), StrUtils.isHexChar(param));
        });
    }

    @Test
    public void test_assign() throws Exception {
        // String assign(String s, String paramName, String paramValue)
        Assert.assertEquals("aaabbbaaa", StrUtils.assign("aaa```aaa```aaa", "aaa", "bbb"));
        Assert.assertEquals("bbb", StrUtils.assign("```aaa```", "aaa", "bbb"));
        Assert.assertEquals("aaa```aaa``aaa", StrUtils.assign("aaa```aaa``aaa", "aaa", "bbb"));

        // String assign(String s, String paramName, String paramValue, String paramMark)
        Assert.assertEquals("bbb```aaa", StrUtils.assign("aaa```aaa```aaa", "```", "bbb", "aaa"));
    }

    @Test
    public void test_addPrefixIfNotEmpty() throws Exception {
        Assert.assertEquals("$str", StrUtils.addPrefixIfNotEmpty("$", "str"));
        Assert.assertEquals("",     StrUtils.addPrefixIfNotEmpty("$", ""));
        AssertExt.assertExceptionThrown(() -> StrUtils.addPrefixIfNotEmpty("$", null), NullPointerException.class);
    }

    @Test
    public void test_addPrefixIfNotNullOrEmpty() throws Exception {
        Assert.assertEquals("$str", StrUtils.addPrefixIfNotNullOrEmpty("$", "str"));
        Assert.assertEquals("",     StrUtils.addPrefixIfNotNullOrEmpty("$", ""));
        Assert.assertEquals("",     StrUtils.addPrefixIfNotNullOrEmpty("$", null));
    }

    @Test
    public void test_addSuffixIfNotEmpty() throws Exception {
        Assert.assertEquals("str$", StrUtils.addSuffixIfNotEmpty("str", "$"));
        Assert.assertEquals("",     StrUtils.addSuffixIfNotEmpty("", "$"));
        AssertExt.assertExceptionThrown(() -> StrUtils.addSuffixIfNotEmpty(null, "$"), NullPointerException.class);
    }

    @Test
    public void test_addSuffixIfNotNullOrEmpty() throws Exception {
        Assert.assertEquals("str$", StrUtils.addSuffixIfNotNullOrEmpty("str", "$"));
        Assert.assertEquals("",     StrUtils.addSuffixIfNotNullOrEmpty("", "$"));
        Assert.assertEquals("",     StrUtils.addSuffixIfNotNullOrEmpty(null, "$"));
    }

    @Test
    public void test_removePrefix() throws Exception {
        Assert.assertEquals("str", StrUtils.removePrefix("$", "$str"));
        Assert.assertEquals("$str", StrUtils.removePrefix("", "$str"));
        AssertExt.assertExceptionThrown(() -> StrUtils.removePrefix("$", "str"),
                StringNotFoundException.class, "prefix $ not found in str");
    }

    @Test
    public void test_removePrefixIfExists() throws Exception {
        Assert.assertEquals("str", StrUtils.removePrefixIfExists("$", "$str"));
        Assert.assertEquals("$str", StrUtils.removePrefixIfExists("", "$str"));
        Assert.assertEquals("str", StrUtils.removePrefixIfExists("$", "str"));
    }

    @Test
    public void test_removeRegExpPrefix() throws Exception {
        StrStr ss = StrUtils.removeRegExpPrefix(RegExpConsts.REG_EXP_INTEGER, "123str");
        Assert.assertEquals("123", ss.getA());
        Assert.assertEquals("str", ss.getB());

        ss = StrUtils.removeRegExpPrefix("", "123str");
        Assert.assertEquals("", ss.getA());
        Assert.assertEquals("123str", ss.getB());

        AssertExt.assertExceptionThrown(() -> StrUtils.removeRegExpPrefix(RegExpConsts.REG_EXP_INTEGER, "str"),
                StringNotFoundException.class, "regular expression prefix \\d+ not found in str");
    }

    @Test
    public void test_removeRegExpPrefixIfExists() throws Exception {
        StrStr ss = StrUtils.removeRegExpPrefixIfExists(RegExpConsts.REG_EXP_INTEGER, "123str");
        Assert.assertEquals("123", ss.getA());
        Assert.assertEquals("str", ss.getB());

        ss = StrUtils.removeRegExpPrefixIfExists("", "123str");
        Assert.assertEquals("", ss.getA());
        Assert.assertEquals("123str", ss.getB());

        ss = StrUtils.removeRegExpPrefixIfExists(RegExpConsts.REG_EXP_INTEGER, "str");
        Assert.assertEquals("", ss.getA());
        Assert.assertEquals("str", ss.getB());
    }

    @Test
    public void test_removeSuffix() throws Exception {
        Assert.assertEquals("str", StrUtils.removeSuffix("$", "str$"));
        Assert.assertEquals("str$", StrUtils.removeSuffix("", "str$"));
        AssertExt.assertExceptionThrown(() -> StrUtils.removeSuffix("$", "str"),
                StringNotFoundException.class, "suffix $ not found in str");
    }

    @Test
    public void test_removeSuffixIfExists() throws Exception {
        Assert.assertEquals("str", StrUtils.removeSuffixIfExists("$", "str$"));
        Assert.assertEquals("str$", StrUtils.removeSuffixIfExists("", "str$"));
        Assert.assertEquals("str", StrUtils.removeSuffixIfExists("$", "str"));
    }

    @Test
    public void test_repeat() throws Exception {
        Assert.assertEquals(STR_0+STR_0+STR_0, StrUtils.repeat(STR_0, 3));
        Assert.assertEquals(STR_0, StrUtils.repeat(STR_0, 1));
        Assert.assertEquals("", StrUtils.repeat(STR_0, 0));
        Assert.assertEquals("", StrUtils.repeat(STR_0, -3));
    }
}
