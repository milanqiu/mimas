package net.milanqiu.mimas.guava.base;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableSet;
import net.milanqiu.mimas.config.MimasTplTrialProjectConfig;
import net.milanqiu.mimas.guava.CharMatcherExt;
import net.milanqiu.mimas.lang.TypeUtils;
import net.milanqiu.mimas.string.EncodingUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class CharMatcherTest {

    @Test
    public void test_ANY() throws Exception {
        /*
            Matches any character.
         */
        Set<Character> charSet = CharMatcherExt.toSet(CharMatcher.ANY);
        Assert.assertEquals(TypeUtils.CHAR_VALUE_COUNT, charSet.size());
        TypeUtils.traverseCharValues((ch) -> Assert.assertTrue(charSet.contains(ch)));
    }

    @Test
    public void test_NONE() throws Exception {
        /*
            Matches no characters.
         */
        Set<Character> charSet = CharMatcherExt.toSet(CharMatcher.NONE);
        Assert.assertTrue(charSet.isEmpty());
    }

    @Test
    public void test_WHITESPACE() throws Exception {
        /*
            Determines whether a character is whitespace according to the latest Unicode standard, as illustrated here.
        */
        String charAsciis = EncodingUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.WHITESPACE));
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(charAsciis);
    }

    @Test
    public void test_BREAKING_WHITESPACE() throws Exception {
        /*
            Determines whether a character is a breaking whitespace (that is, a whitespace which can be interpreted as
            a break between words for formatting purposes).
        */
        String charAsciis = EncodingUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.BREAKING_WHITESPACE));
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(charAsciis);
    }

    @Test
    public void test_INVISIBLE() throws Exception {
        /*
            Determines whether a character is invisible; that is, if its Unicode category is any of SPACE_SEPARATOR,
            LINE_SEPARATOR, PARAGRAPH_SEPARATOR, CONTROL, FORMAT, SURROGATE, and PRIVATE_USE according to ICU4J.
         */
        String charAsciis = EncodingUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.INVISIBLE));
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(charAsciis);
    }

    @Test
    public void test_DIGIT() throws Exception {
        /*
            Determines whether a character is a digit according to Unicode.
         */
        String charAsciis = EncodingUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.DIGIT));
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(charAsciis);
    }

    @Test
    public void test_JAVA_LETTER() throws Exception {
        /*
            Determines whether a character is a letter according to Java's definition.
         */
        String charAsciis = EncodingUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.JAVA_LETTER));
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(charAsciis);
    }

    @Test
    public void test_JAVA_DIGIT() throws Exception {
        /*
            Determines whether a character is a digit according to Java's definition.
         */
        String charAsciis = EncodingUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.JAVA_DIGIT));
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(charAsciis);
    }

    @Test
    public void test_JAVA_LETTER_OR_DIGIT() throws Exception {
        /*
            Determines whether a character is a letter or digit according to Java's definition.
         */
        String charAsciis = EncodingUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.JAVA_LETTER_OR_DIGIT));
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(charAsciis);
    }

    @Test
    public void test_JAVA_ISO_CONTROL() throws Exception {
        /*
            Determines whether a character is an ISO control character as specified by Character.isISOControl(char).
         */
        String charAsciis = EncodingUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.JAVA_ISO_CONTROL));
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(charAsciis);
    }

    @Test
    public void test_JAVA_LOWER_CASE() throws Exception {
        /*
            Determines whether a character is lower case according to Java's definition.
         */
        String charAsciis = EncodingUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.JAVA_LOWER_CASE));
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(charAsciis);
    }

    @Test
    public void test_JAVA_UPPER_CASE() throws Exception {
        /*
            Determines whether a character is upper case according to Java's definition.
         */
        String charAsciis = EncodingUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.JAVA_UPPER_CASE));
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(charAsciis);
    }

    @Test
    public void test_ASCII() throws Exception {
        /*
            Determines whether a character is ASCII, meaning that its code point is less than 128.
         */
        String charAsciis = EncodingUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.ASCII));
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(charAsciis);
    }

    @Test
    public void test_SINGLE_WIDTH() throws Exception {
        /*
            Determines whether a character is single-width (not double-width).
         */
        String charAsciis = EncodingUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.SINGLE_WIDTH));
        MimasTplTrialProjectConfig.getSingleton().writeFileInTestOutDir(charAsciis);
    }

    @Test
    public void test_anyOf() throws Exception {
        /*
            anyOf(CharSequence)
            Specify all the characters you wish matched. For example, CharMatcher.anyOf("aeiou") matches lowercase
            English vowels.
         */
        Assert.assertEquals(ImmutableSet.of('m', 'i', 'l', 'a', 'n', 'q', 'u'),
                CharMatcherExt.toSet(CharMatcher.anyOf("milanqiu")));
    }

    @Test
   public void test_is() throws Exception {
        /*
            is(char)
            Specify exactly one character to match.
         */
        Assert.assertEquals(ImmutableSet.of('a'),
                CharMatcherExt.toSet(CharMatcher.is('a')));
    }

    @Test
    public void test_inRange() throws Exception {
        /*
            inRange(char, char)
            Specify a range of characters to match, e.g. CharMatcher.inRange('a', 'z').
         */
        Assert.assertEquals(ImmutableSet.of('a', 'b', 'c', 'd', 'e', 'f'),
                CharMatcherExt.toSet(CharMatcher.inRange('a', 'f')));
    }

    @Test
    public void test_negate() throws Exception {
        /*
            negate()
         */
        Assert.assertEquals(CharMatcher.ANY, CharMatcher.NONE.negate());
        Assert.assertEquals(CharMatcher.NONE, CharMatcher.ANY.negate());
    }

    @Test
    public void test_and() throws Exception {
        /*
            and(CharMatcher)
         */
        Assert.assertEquals(ImmutableSet.of('d', 'e'),
                CharMatcherExt.toSet(CharMatcher.anyOf("abcde").and(CharMatcher.anyOf("defgh"))));
    }

    @Test
    public void test_or() throws Exception {
        /*
            or(CharMatcher)
         */
        Assert.assertEquals(ImmutableSet.of('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'),
                CharMatcherExt.toSet(CharMatcher.anyOf("abcde").or(CharMatcher.anyOf("defgh"))));
    }

    @Test
    public void test_collapseFrom() throws Exception {
        /*
            String collapseFrom(CharSequence, char)
            Replace each group of consecutive matched characters with the specified character. For example,
            WHITESPACE.collapseFrom(string, ' ') collapses whitespaces down to a single space.
         */
        Assert.assertEquals("+f-f*/f",
                CharMatcher.anyOf("abc").collapseFrom("+a-aab*/abca", 'f'));
    }

    @Test
    public void test_matchesAllOf() throws Exception {
        /*
            boolean matchesAllOf(CharSequence)
            Test if this matcher matches all characters in the sequence. For example, ASCII.matchesAllOf(string) tests
            if all characters in the string are ASCII.
         */
        Assert.assertTrue(CharMatcher.anyOf("abc").matchesAllOf(""));
        Assert.assertTrue(CharMatcher.anyOf("abc").matchesAllOf("aaabbbccc"));
        Assert.assertFalse(CharMatcher.anyOf("abc").matchesAllOf("aaabbbccc-"));
    }

    @Test
    public void test_removeFrom() throws Exception {
        /*
            String removeFrom(CharSequence)
            Removes matching characters from the sequence.
         */
        Assert.assertEquals("+-*/",
                CharMatcher.anyOf("abc").removeFrom("+a-aab*/abca"));
    }

    @Test
    public void test_retainFrom() throws Exception {
        /*
            String retainFrom(CharSequence)
            Removes all non-matching characters from the sequence.
         */
        Assert.assertEquals("aaababca",
                CharMatcher.anyOf("abc").retainFrom("+a-aab*/abca"));
    }

    @Test
    public void test_trimFrom() throws Exception {
        /*
            String trimFrom(CharSequence)
            Removes leading and trailing matching characters.
         */
        Assert.assertEquals("+a-aab*/",
                CharMatcher.anyOf("abc").trimFrom("a+a-aab*/abca"));
    }

    @Test
    public void test_replaceFrom() throws Exception {
        /*
            String replaceFrom(CharSequence, CharSequence)
            Replace matching characters with a given sequence.
         */
        Assert.assertEquals("+fg-fgfgfg*/fgfgfgfg",
                CharMatcher.anyOf("abc").replaceFrom("+a-aab*/abca", "fg"));
    }

    @Test
    public void test_isNot() throws Exception {
        Set<Character> charSet = CharMatcherExt.toSet(CharMatcher.isNot('a'));
        Assert.assertEquals(TypeUtils.CHAR_VALUE_COUNT-1, charSet.size());
        TypeUtils.traverseCharValues((ch) -> {
            if (ch == 'a')
                Assert.assertFalse(charSet.contains(ch));
            else
                Assert.assertTrue(charSet.contains(ch));
        });
    }

    @Test
    public void test_noneOf() throws Exception {
        Set<Character> charSet = CharMatcherExt.toSet(CharMatcher.noneOf("milanqiu"));
        Assert.assertEquals(TypeUtils.CHAR_VALUE_COUNT-7, charSet.size());
        TypeUtils.traverseCharValues((ch) -> {
            if (ImmutableSet.of('m', 'i', 'l', 'a', 'n', 'q', 'u').contains(ch))
                Assert.assertFalse(charSet.contains(ch));
            else
                Assert.assertTrue(charSet.contains(ch));
        });
    }

    @Test
    public void test_matches() throws Exception {
        CharMatcher chm = CharMatcher.anyOf("milanqiu");
        TypeUtils.traverseCharValues((ch) -> {
            if (ImmutableSet.of('m', 'i', 'l', 'a', 'n', 'q', 'u').contains(ch))
                Assert.assertTrue(chm.matches(ch));
            else
                Assert.assertFalse(chm.matches(ch));
        });
    }

    @Test
    public void test_matchesAnyOf() throws Exception {
        Assert.assertTrue(CharMatcher.anyOf("abc").matchesAnyOf("aaabbbccc"));
        Assert.assertTrue(CharMatcher.anyOf("abc").matchesAnyOf("+-a*/"));
        Assert.assertFalse(CharMatcher.anyOf("abc").matchesAnyOf("+-*/"));
        Assert.assertFalse(CharMatcher.anyOf("abc").matchesAnyOf(""));
    }

    @Test
    public void test_matchesNoneOf() throws Exception {
        Assert.assertFalse(CharMatcher.anyOf("abc").matchesNoneOf("aaabbbccc"));
        Assert.assertFalse(CharMatcher.anyOf("abc").matchesNoneOf("+-a*/"));
        Assert.assertTrue(CharMatcher.anyOf("abc").matchesNoneOf("+-*/"));
        Assert.assertTrue(CharMatcher.anyOf("abc").matchesNoneOf(""));
    }

    @Test
    public void test_indexIn() throws Exception {
        // int indexIn(CharSequence sequence)
        Assert.assertEquals(1, CharMatcher.anyOf("abc").indexIn("+a-aab*/abca"));
        Assert.assertEquals(-1, CharMatcher.anyOf("abc").indexIn("+-*/"));
        Assert.assertEquals(-1, CharMatcher.anyOf("abc").indexIn(""));

        // int indexIn(CharSequence sequence, int start)
        Assert.assertEquals(8, CharMatcher.anyOf("abc").indexIn("+a-aab*/abca", 6));
        Assert.assertEquals(-1, CharMatcher.anyOf("abc").indexIn("+-*/", 2));
        Assert.assertEquals(-1, CharMatcher.anyOf("abc").indexIn("", 0));
    }

    @Test
    public void test_lastIndexIn() throws Exception {
        Assert.assertEquals(11, CharMatcher.anyOf("abc").lastIndexIn("+a-aab*/abca"));
        Assert.assertEquals(-1, CharMatcher.anyOf("abc").lastIndexIn("+-*/"));
        Assert.assertEquals(-1, CharMatcher.anyOf("abc").lastIndexIn(""));
    }

    @Test
    public void test_countIn() throws Exception {
        Assert.assertEquals(8, CharMatcher.anyOf("abc").countIn("+a-aab*/abca"));
        Assert.assertEquals(0, CharMatcher.anyOf("abc").countIn("+-*/"));
        Assert.assertEquals(0, CharMatcher.anyOf("abc").countIn(""));
    }

    @Test
    public void test_trimLeadingFrom() throws Exception {
        Assert.assertEquals("+a-aab*/abca",
                CharMatcher.anyOf("abc").trimLeadingFrom("a+a-aab*/abca"));
    }

    @Test
    public void test_trimTrailingFrom() throws Exception {
        Assert.assertEquals("a+a-aab*/",
                CharMatcher.anyOf("abc").trimTrailingFrom("a+a-aab*/abca"));
    }

    @Test
    public void test_trimAndCollapseFrom() throws Exception {
        Assert.assertEquals("+f-f*/",
                CharMatcher.anyOf("abc").trimAndCollapseFrom("a+a-aab*/abca", 'f'));
    }

    @Test
    public void test_toString() throws Exception {
        Assert.assertEquals("CharMatcher.ANY", CharMatcher.ANY.toString());
        Assert.assertEquals("CharMatcher.anyOf(\"\\u0061\\u0062\\u0063\")", CharMatcher.anyOf("cba").toString());
        Assert.assertEquals("CharMatcher.is('\\u0061')", CharMatcher.is('a').and(CharMatcher.isNot('b')).toString());
        Assert.assertEquals("CharMatcher.isNot('\\u0062')", CharMatcher.is('a').or(CharMatcher.isNot('b')).toString());
    }
}
