package net.milanqiu.mimas.guava;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableSet;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * <p>Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class CharMatcherExtTest {

    @Test
    public void test_toSet() throws Exception {
        Set<Character> set = CharMatcherExt.toSet(CharMatcher.ANY);
        for (char ch = Character.MIN_VALUE; ch < Character.MAX_VALUE; ch++)
            Assert.assertTrue(set.contains(ch));

        set = CharMatcherExt.toSet(CharMatcher.anyOf("milanqiu"));
        Assert.assertEquals(ImmutableSet.of('m', 'i', 'l', 'a', 'n', 'q', 'u'), set);
    }

    @Test
    public void test_toString() throws Exception {
        Assert.assertEquals("ailmnqu", CharMatcherExt.toString(CharMatcher.anyOf("milanqiu")));
    }
}
