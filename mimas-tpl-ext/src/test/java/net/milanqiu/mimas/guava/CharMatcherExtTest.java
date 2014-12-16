package net.milanqiu.mimas.guava;

import com.google.common.base.CharMatcher;
import com.google.common.collect.ImmutableSet;
import net.milanqiu.mimas.lang.LangUtils;
import net.milanqiu.mimas.lang.RunnableWithParam;
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
        LangUtils.traverseCharValues(new RunnableWithParam.WithChar() {
            @Override
            public void run(char param) {
                Assert.assertTrue(setOfAll.contains(param));
                Assert.assertFalse(setOfNone.contains(param));
            }
        });
    }

    @Test
    public void test_toString() throws Exception {
        Assert.assertEquals("ailmnqu", CharMatcherExt.toString(CharMatcher.anyOf("milanqiu")));
        Assert.assertEquals("", CharMatcherExt.toString(CharMatcher.NONE));
    }
}
