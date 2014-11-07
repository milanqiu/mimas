package net.milanqiu.mimas.guava.string;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableSet;

import java.util.Set;

import net.milanqiu.mimas.guava.CharMatcherExt;
import net.milanqiu.mimas.string.StrUtils;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class CharMatcherTest {

    @Test
    public void test_ANY() throws Exception {
        /*
            Matches any character.
         */
        Set<Character> set = CharMatcherExt.toSet(CharMatcher.ANY);
        for (char ch = Character.MIN_VALUE; ch < Character.MAX_VALUE; ch++) {
            Assert.assertTrue(set.contains(ch));
        }
    }

    @Test
    public void test_NONE() throws Exception {
        /*
            Matches no characters.
         */
        Set<Character> set = CharMatcherExt.toSet(CharMatcher.NONE);
        for (char ch = Character.MIN_VALUE; ch < Character.MAX_VALUE; ch++) {
            Assert.assertFalse(set.contains(ch));
        }
    }

    @Test
    public void test_WHITESPACE() throws Exception {
        /*
            Determines whether a character is whitespace according to the latest Unicode standard, as illustrated here.
        */
        System.out.println(StrUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.WHITESPACE)));
    }

    @Test
    public void test_BREAKING_WHITESPACE() throws Exception {
        /*
            Determines whether a character is a breaking whitespace (that is, a whitespace which can be interpreted as
            a break between words for formatting purposes).
        */
        System.out.println(StrUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.BREAKING_WHITESPACE)));
    }

    @Test
    public void test_INVISIBLE() throws Exception {
        /*
            Determines whether a character is invisible; that is, if its Unicode category is any of SPACE_SEPARATOR,
            LINE_SEPARATOR, PARAGRAPH_SEPARATOR, CONTROL, FORMAT, SURROGATE, and PRIVATE_USE according to ICU4J.
         */
        System.out.println(StrUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.INVISIBLE)));
    }

    @Test
    public void test_DIGIT() throws Exception {
        /*
            Determines whether a character is a digit according to Unicode.
         */
        System.out.println(StrUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.DIGIT)));
    }

    @Test
    public void test_JAVA_LETTER() throws Exception {
        /*
            Determines whether a character is a letter according to Java's definition.
         */
        System.out.println(StrUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.JAVA_LETTER)));
    }

    @Test
    public void test_JAVA_DIGIT() throws Exception {
        /*
            Determines whether a character is a digit according to Java's definition.
         */
        System.out.println(StrUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.JAVA_DIGIT)));
    }

    @Test
    public void test_JAVA_LETTER_OR_DIGIT() throws Exception {
        /*
            Determines whether a character is a letter or digit according to Java's definition.
         */
        System.out.println(StrUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.JAVA_LETTER_OR_DIGIT)));
    }

    @Test
    public void test_JAVA_ISO_CONTROL() throws Exception {
        /*
            Determines whether a character is an ISO control character as specified by Character.isISOControl(char).
         */
        System.out.println(StrUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.JAVA_ISO_CONTROL)));
    }

    @Test
    public void test_JAVA_LOWER_CASE() throws Exception {
        /*
            Determines whether a character is lower case according to Java's definition.
         */
        System.out.println(StrUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.JAVA_LOWER_CASE)));
    }

    @Test
    public void test_JAVA_UPPER_CASE() throws Exception {
        /*
            Determines whether a character is upper case according to Java's definition.
         */
        System.out.println(StrUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.JAVA_UPPER_CASE)));
    }

    @Test
    public void test_ASCII() throws Exception {
        /*
            Determines whether a character is ASCII, meaning that its code point is less than 128.
         */
        System.out.println(StrUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.ASCII)));
    }

    @Test
    public void test_SINGLE_WIDTH() throws Exception {
        /*
            Determines whether a character is single-width (not double-width).
         */
        System.out.println(StrUtils.nativeToAscii(CharMatcherExt.toString(CharMatcher.SINGLE_WIDTH)));
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
                CharMatcher.anyOf("abc").trimFrom("+a-aab*/abca"));
    }

    @Test
    public void test_() throws Exception {
        /*
            String replaceFrom(CharSequence, CharSequence)
            Replace matching characters with a given sequence.
         */
        Assert.assertEquals("+f-fff*/ffff",
                CharMatcher.anyOf("abc").replaceFrom("+a-aab*/abca", "f"));
    }
}
