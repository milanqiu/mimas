package net.milanqiu.mimas.string;

import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2020-03-07
 * @author Milan Qiu
 */
public class PatternCacheTest {

    @Test
    public void test_getPattern() throws Exception {
        Assert.assertEquals("a",  PatternCache.getSingleton().getPattern("a").pattern());
        Assert.assertEquals(".*", PatternCache.getSingleton().getPattern(".*").pattern());
        Assert.assertEquals("a",  PatternCache.getSingleton().getPattern("a").pattern());
    }

    @Test
    public void test_get() throws Exception {
        Assert.assertEquals("a",  PatternCache.get("a").pattern());
        Assert.assertEquals(".*", PatternCache.get(".*").pattern());
        Assert.assertEquals("a",  PatternCache.get("a").pattern());
    }
}
