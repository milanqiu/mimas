package net.milanqiu.mimas.guava;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableSet;
import net.milanqiu.mimas.lang.TypeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

/**
 * Creation Date: 2014-10-29
 * @author Milan Qiu
 */
public class CharMatcherExtTest {

    @Test
    public void test_toSet() throws Exception {
        Assert.assertEquals(ImmutableSet.of('m', 'i', 'l', 'a', 'n', 'q', 'u'),
                CharMatcherExt.toSet(CharMatcher.anyOf("milanqiu")));

        final Set<Character> setOfAll = CharMatcherExt.toSet(CharMatcher.ANY);
        final Set<Character> setOfNone = CharMatcherExt.toSet(CharMatcher.NONE);
        TypeUtils.traverseCharValues((ch) -> {
            Assert.assertTrue(setOfAll.contains(ch));
            Assert.assertFalse(setOfNone.contains(ch));
        });
    }

    @Test
    public void test_toString() throws Exception {
        Assert.assertEquals("ailmnqu", CharMatcherExt.toString(CharMatcher.anyOf("milanqiu")));
        Assert.assertEquals("",        CharMatcherExt.toString(CharMatcher.NONE));
    }
}
