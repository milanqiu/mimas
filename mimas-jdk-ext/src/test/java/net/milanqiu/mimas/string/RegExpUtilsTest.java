package net.milanqiu.mimas.string;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Creation Date: 2020-03-08
 * @author Milan Qiu
 */
public class RegExpUtilsTest {

    @Test
    public void test_strict() throws Exception {
        Assert.assertTrue(Pattern.compile(RegExpConsts.REG_EXP_INTEGER).matcher("abc123def").find());
        Assert.assertFalse(Pattern.compile(RegExpUtils.strict(RegExpConsts.REG_EXP_INTEGER)).matcher("abc123def").find());
        Assert.assertTrue(Pattern.compile(RegExpUtils.strict(RegExpConsts.REG_EXP_INTEGER)).matcher("123").find());
        Assert.assertFalse(Pattern.compile(RegExpUtils.strict(RegExpConsts.REG_EXP_INTEGER)).matcher("abc\n123\ndef").find());
        Assert.assertTrue(Pattern.compile(RegExpUtils.strict(RegExpConsts.REG_EXP_INTEGER), Pattern.MULTILINE).matcher("abc\n123\ndef").find());
        Assert.assertTrue(Pattern.compile(RegExpUtils.strict("a|b")).matcher("a").find());
        Assert.assertFalse(Pattern.compile(RegExpUtils.strict("a|b")).matcher("ac").find());
    }

    @Test
    public void test_removeGroupNames() throws Exception {
        Assert.assertEquals("aaa(.*)bbb", RegExpUtils.removeGroupNames("aaa(.*)bbb"));
        Assert.assertEquals("aaa(.*)bbb", RegExpUtils.removeGroupNames("aaa(?<GroupName>.*)bbb"));
        Assert.assertEquals("aaa(((.)*)bbb)", RegExpUtils.removeGroupNames("aaa(?<GroupName1>(?<GroupName2>(?<GroupName3>.)*)bbb)"));
    }

    @Test
    public void test_findAll() throws Exception {
        List<String> allOccurences = RegExpUtils.findAll(RegExpConsts.REG_EXP_INTEGER, "aaa123bbb456ccc789ddd");
        Assert.assertEquals(3, allOccurences.size());
        Assert.assertEquals("123", allOccurences.get(0));
        Assert.assertEquals("456", allOccurences.get(1));
        Assert.assertEquals("789", allOccurences.get(2));

        allOccurences = RegExpUtils.findAll(RegExpConsts.REG_EXP_INTEGER, "123bbb456ccc789");
        Assert.assertEquals(3, allOccurences.size());
        Assert.assertEquals("123", allOccurences.get(0));
        Assert.assertEquals("456", allOccurences.get(1));
        Assert.assertEquals("789", allOccurences.get(2));

        allOccurences = RegExpUtils.findAll("(?<Name1>(?<Name2>\\d)+)", "aaa123bbb456ccc789ddd");
        Assert.assertEquals(3, allOccurences.size());
        Assert.assertEquals("123", allOccurences.get(0));
        Assert.assertEquals("456", allOccurences.get(1));
        Assert.assertEquals("789", allOccurences.get(2));

        allOccurences = RegExpUtils.findAll(RegExpConsts.REG_EXP_INTEGER, "aaa");
        Assert.assertEquals(0, allOccurences.size());

        allOccurences = RegExpUtils.findAll(RegExpConsts.REG_EXP_INTEGER, "");
        Assert.assertEquals(0, allOccurences.size());
    }

    @Test
    public void test_matchesZeroOrMore() throws Exception {
        List<String> allRepetitions = new ArrayList<>();

        Assert.assertTrue(RegExpUtils.matchesZeroOrMore(RegExpConsts.REG_EXP_INTEGER, "123456789", allRepetitions));
        Assert.assertEquals(1, allRepetitions.size());
        Assert.assertEquals("123456789", allRepetitions.get(0));

        Assert.assertTrue(RegExpUtils.matchesZeroOrMore("\\{"+RegExpConsts.REG_EXP_INTEGER+"\\}", "{123}{456}{789}", allRepetitions));
        Assert.assertEquals(4, allRepetitions.size());
        Assert.assertEquals("123456789", allRepetitions.get(0));
        Assert.assertEquals("{123}", allRepetitions.get(1));
        Assert.assertEquals("{456}", allRepetitions.get(2));
        Assert.assertEquals("{789}", allRepetitions.get(3));

        allRepetitions.clear();
        Assert.assertTrue(RegExpUtils.matchesZeroOrMore("\\{(?<Name1>(?<Name2>\\d)+)\\}", "{123}{456}{789}", allRepetitions));
        Assert.assertEquals(3, allRepetitions.size());
        Assert.assertEquals("{123}", allRepetitions.get(0));
        Assert.assertEquals("{456}", allRepetitions.get(1));
        Assert.assertEquals("{789}", allRepetitions.get(2));

        allRepetitions.clear();
        Assert.assertFalse(RegExpUtils.matchesZeroOrMore(RegExpConsts.REG_EXP_INTEGER, "123a", allRepetitions));
        Assert.assertEquals(0, allRepetitions.size());

        allRepetitions.clear();
        Assert.assertTrue(RegExpUtils.matchesZeroOrMore(RegExpConsts.REG_EXP_INTEGER, "", allRepetitions));
        Assert.assertEquals(0, allRepetitions.size());
    }

    @Test
    public void test_matchesOneOrMore() throws Exception {
        List<String> allRepetitions = new ArrayList<>();

        Assert.assertTrue(RegExpUtils.matchesOneOrMore(RegExpConsts.REG_EXP_INTEGER, "123456789", allRepetitions));
        Assert.assertEquals(1, allRepetitions.size());
        Assert.assertEquals("123456789", allRepetitions.get(0));

        Assert.assertTrue(RegExpUtils.matchesOneOrMore("\\{" + RegExpConsts.REG_EXP_INTEGER + "\\}", "{123}{456}{789}", allRepetitions));
        Assert.assertEquals(4, allRepetitions.size());
        Assert.assertEquals("123456789", allRepetitions.get(0));
        Assert.assertEquals("{123}", allRepetitions.get(1));
        Assert.assertEquals("{456}", allRepetitions.get(2));
        Assert.assertEquals("{789}", allRepetitions.get(3));

        allRepetitions.clear();
        Assert.assertTrue(RegExpUtils.matchesOneOrMore("\\{(?<Name1>(?<Name2>\\d)+)\\}", "{123}{456}{789}", allRepetitions));
        Assert.assertEquals(3, allRepetitions.size());
        Assert.assertEquals("{123}", allRepetitions.get(0));
        Assert.assertEquals("{456}", allRepetitions.get(1));
        Assert.assertEquals("{789}", allRepetitions.get(2));

        allRepetitions.clear();
        Assert.assertFalse(RegExpUtils.matchesOneOrMore(RegExpConsts.REG_EXP_INTEGER, "123a", allRepetitions));
        Assert.assertEquals(0, allRepetitions.size());

        allRepetitions.clear();
        Assert.assertFalse(RegExpUtils.matchesOneOrMore(RegExpConsts.REG_EXP_INTEGER, "", allRepetitions));
        Assert.assertEquals(0, allRepetitions.size());
    }

    @Test
    public void test_matchesOneOrMoreWithSeparator() throws Exception {
        List<String> allRepetitions = new ArrayList<>();

        Assert.assertTrue(RegExpUtils.matchesOneOrMoreWithSeparator(RegExpConsts.REG_EXP_INTEGER, "123456789", ",", allRepetitions));
        Assert.assertEquals(1, allRepetitions.size());
        Assert.assertEquals("123456789", allRepetitions.get(0));

        Assert.assertTrue(RegExpUtils.matchesOneOrMoreWithSeparator("\\{" + RegExpConsts.REG_EXP_INTEGER + "\\}", "{123},{456},{789}", ",", allRepetitions));
        Assert.assertEquals(4, allRepetitions.size());
        Assert.assertEquals("123456789", allRepetitions.get(0));
        Assert.assertEquals("{123}", allRepetitions.get(1));
        Assert.assertEquals("{456}", allRepetitions.get(2));
        Assert.assertEquals("{789}", allRepetitions.get(3));

        allRepetitions.clear();
        Assert.assertTrue(RegExpUtils.matchesOneOrMoreWithSeparator("\\{(?<Name1>(?<Name2>\\d)+)\\}", "{123},{456},{789}", ",", allRepetitions));
        Assert.assertEquals(3, allRepetitions.size());
        Assert.assertEquals("{123}", allRepetitions.get(0));
        Assert.assertEquals("{456}", allRepetitions.get(1));
        Assert.assertEquals("{789}", allRepetitions.get(2));

        allRepetitions.clear();
        Assert.assertFalse(RegExpUtils.matchesOneOrMoreWithSeparator(RegExpConsts.REG_EXP_INTEGER, "123a", ",", allRepetitions));
        Assert.assertEquals(0, allRepetitions.size());

        allRepetitions.clear();
        Assert.assertFalse(RegExpUtils.matchesOneOrMoreWithSeparator(RegExpConsts.REG_EXP_INTEGER, "", ",", allRepetitions));
        Assert.assertEquals(0, allRepetitions.size());

        allRepetitions.clear();
        Assert.assertTrue(RegExpUtils.matchesOneOrMoreWithSeparator(",?\\d*", ",123,456,,,789,", ",", allRepetitions));
        Assert.assertEquals(5, allRepetitions.size());
        Assert.assertEquals(",123", allRepetitions.get(0));
        Assert.assertEquals("456",  allRepetitions.get(1));
        Assert.assertEquals(",",    allRepetitions.get(2));
        Assert.assertEquals("789",  allRepetitions.get(3));
        Assert.assertEquals("",     allRepetitions.get(4));

        allRepetitions.clear();
        Assert.assertTrue(RegExpUtils.matchesOneOrMoreWithSeparator(",?\\d*", ",123,456,,,789,", ",*", allRepetitions));
        Assert.assertEquals(4, allRepetitions.size());
        Assert.assertEquals(",123", allRepetitions.get(0));
        Assert.assertEquals("456",  allRepetitions.get(1));
        Assert.assertEquals("789",  allRepetitions.get(2));
        Assert.assertEquals("",     allRepetitions.get(3));

        allRepetitions.clear();
        Assert.assertTrue(RegExpUtils.matchesOneOrMoreWithSeparator(",?\\d*", ",123,456,,,789,", ",*?", allRepetitions));
        Assert.assertEquals(6, allRepetitions.size());
        Assert.assertEquals(",123", allRepetitions.get(0));
        Assert.assertEquals(",456", allRepetitions.get(1));
        Assert.assertEquals(",",    allRepetitions.get(2));
        Assert.assertEquals(",",    allRepetitions.get(3));
        Assert.assertEquals(",789", allRepetitions.get(4));
        Assert.assertEquals(",",    allRepetitions.get(5));
    }
}
