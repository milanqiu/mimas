package net.milanqiu.mimas.guava.string;

import com.google.common.base.CharMatcher;
import com.google.common.base.Splitter;
import com.google.common.collect.*;

import java.util.regex.Pattern;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-10-28
 * @author Milan Qiu
 */
public class SplitterTest {

    @Test
    public void test_on_split() throws Exception {
        /*
            Splitter.on(char)
            Split on occurrences of a specific, individual character.
         */
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of("", "a", "", "b", ""),
                Splitter.on(',').split(",a,,b,")));

        /*
            Splitter.on(CharMatcher)
            Split on occurrences of any character in some category.
         */
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of("", "a", "", "b", ""),
                Splitter.on(CharMatcher.anyOf(",;/")).split(",a;,b/")));

        /*
            Splitter.on(String)
            Split on a literal String.
         */
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of("", "a", "", "b", ""),
                Splitter.on(",,").split(",,a,,,,b,,")));

        /*
            Splitter.on(Pattern)
            Splitter.onPattern(String)
            Split on a regular expression.
         */
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of("", "a", "", "b", ""),
                Splitter.on(Pattern.compile("[0-9]")).split("0a37b4")));
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of("", "a", "", "b", ""),
                Splitter.onPattern("[0-9]").split("0a37b4")));
    }

    @Test
    public void test_fixedLength() throws Exception {
        /*
            Splitter.fixedLength(int)
            Splits strings into substrings of the specified fixed length. The last piece can be smaller than length,
            but will never be empty.
         */
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of("aaa", "aaa", "a"),
                Splitter.fixedLength(3).split("aaaaaaa")));
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of("aaa", "aaa"),
                Splitter.fixedLength(3).split("aaaaaa")));
    }

    @Test
    public void test_omitEmptyStrings() throws Exception {
        /*
            omitEmptyStrings()
            Automatically omits empty strings from the result.
         */
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of("a", "b"),
                Splitter.on(',').omitEmptyStrings().split(",a,,b,")));
    }

    @Test
    public void test_trimResults() throws Exception {
        /*
            trimResults()
            Trims whitespace from the results; equivalent to trimResults(CharMatcher.WHITESPACE).
         */
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of("", "a", "", "b", ""),
                Splitter.on(',').trimResults().split("  ,  a  ,  ,  b,")));

        /*
            trimResults(CharMatcher)
            Trims characters matching the specified CharMatcher from results.
         */
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of("", "a", "", "b", ""),
                Splitter.on(',').trimResults(CharMatcher.is('/')).split("/,//a//,,/b,")));
    }

    @Test
    public void test_limit() throws Exception {
        /*
            limit(int)
            Stops splitting after the specified number of strings have been returned.
         */
        Assert.assertTrue(Iterables.elementsEqual(ImmutableList.of("", "a", ",b,"),
                Splitter.on(',').limit(3).split(",a,,b,")));
    }

    @Test
    public void test_MapSplitter() throws Exception {
        Splitter.MapSplitter mapSplitter = Splitter.on(',').withKeyValueSeparator("=");
        Assert.assertEquals(ImmutableMap.of(STR_0, STR_OF_INT_0, STR_1, STR_OF_INT_1, STR_2, STR_OF_INT_2),
                mapSplitter.split(STR_0 + "=" + INT_0 + "," + STR_1 + "=" + INT_1 + "," + STR_2 + "=" + INT_2));
    }
}
