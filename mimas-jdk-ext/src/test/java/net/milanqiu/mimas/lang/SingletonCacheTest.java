package net.milanqiu.mimas.lang;

import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.util.NoSuchElementException;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2016-10-08
 * @author Milan Qiu
 */
public class SingletonCacheTest {

    private SingletonCache<Integer> cache;
    private SingletonCache<Integer> anotherCache;
    private SingletonCache<String> anotherCacheWithDifferentType;

    @Test
    public void test_Constructor() throws Exception {
        cache = new SingletonCache<>();
        Assert.assertFalse(cache.isEnabled());

        cache = new SingletonCache<>(INT_0);
        Assert.assertTrue(cache.isEnabled());
        Assert.assertEquals(INT_0, (int) cache.getData());

        // null test
        cache = new SingletonCache<>(null);
        Assert.assertTrue(cache.isEnabled());
        Assert.assertNull(cache.getData());
    }

    @Test
    public void test_getData_setData() throws Exception {
        cache = new SingletonCache<>();
        Assert.assertFalse(cache.isEnabled());
        AssertExt.assertExceptionThrown(cache::getData, NoSuchElementException.class);

        // D getData(Supplier<D> supplier)
        Assert.assertEquals(INT_0, (int) cache.getData(() -> INT_0));
        Assert.assertTrue(cache.isEnabled());
        Assert.assertEquals(INT_0, (int) cache.getData());

        // D getData()
        // void setData(D data)
        cache.setData(INT_1);
        Assert.assertTrue(cache.isEnabled());
        Assert.assertEquals(INT_1, (int) cache.getData());
        cache.setData(INT_2);
        Assert.assertTrue(cache.isEnabled());
        Assert.assertEquals(INT_2, (int) cache.getData());

        // null test
        cache.setData(null);
        Assert.assertTrue(cache.isEnabled());
        Assert.assertNull(cache.getData());
    }

    @Test
    public void test_disable() throws Exception {
        cache = new SingletonCache<>(INT_0);
        Assert.assertTrue(cache.isEnabled());
        Assert.assertEquals(INT_0, (int) cache.getData());

        cache.disable();
        Assert.assertFalse(cache.isEnabled());
        AssertExt.assertExceptionThrown(cache::getData, NoSuchElementException.class);
    }

    @Test
    public void test_equals() throws Exception {
        cache = new SingletonCache<>(INT_0);
        anotherCache = new SingletonCache<>(INT_0);
        Assert.assertTrue(cache.equals(anotherCache));
        Assert.assertTrue(anotherCache.equals(cache));

        anotherCache.setData(INT_1);
        Assert.assertFalse(cache.equals(anotherCache));
        Assert.assertFalse(anotherCache.equals(cache));

        anotherCacheWithDifferentType = new SingletonCache<>(STR_OF_INT_0);
        Assert.assertFalse(cache.equals(anotherCacheWithDifferentType));
        Assert.assertFalse(anotherCacheWithDifferentType.equals(cache));

        // disabled test
        cache.setData(INT_0);
        anotherCache.setData(INT_0);
        anotherCache.disable();
        Assert.assertFalse(cache.equals(anotherCache));
        Assert.assertFalse(anotherCache.equals(cache));

        cache.setData(INT_1);
        cache.disable();
        Assert.assertTrue(cache.equals(anotherCache));
        Assert.assertTrue(anotherCache.equals(cache));

        // null test
        cache.setData(null);
        anotherCache.setData(null);
        Assert.assertTrue(cache.equals(anotherCache));
        Assert.assertTrue(anotherCache.equals(cache));

        anotherCache.setData(INT_0);
        Assert.assertFalse(cache.equals(anotherCache));
        Assert.assertFalse(anotherCache.equals(cache));
    }

    @Test
    public void test_hashCode() throws Exception {
        cache = new SingletonCache<>(INT_0);
        anotherCache = new SingletonCache<>(INT_0);
        Assert.assertEquals(cache.hashCode(), anotherCache.hashCode());

        anotherCache.setData(INT_1);
        Assert.assertNotEquals(cache.hashCode(), anotherCache.hashCode());

        anotherCacheWithDifferentType = new SingletonCache<>(STR_OF_INT_0);
        Assert.assertNotEquals(cache.hashCode(), anotherCacheWithDifferentType.hashCode());

        // disabled test
        cache.setData(INT_0);
        anotherCache.setData(INT_0);
        anotherCache.disable();
        Assert.assertNotEquals(cache.hashCode(), anotherCache.hashCode());

        cache.setData(INT_1);
        cache.disable();
        Assert.assertEquals(cache.hashCode(), anotherCache.hashCode());

        // null test
        anotherCache.setData(null);
        Assert.assertNotEquals(cache.hashCode(), anotherCache.hashCode());

        cache.setData(null);
        Assert.assertEquals(cache.hashCode(), anotherCache.hashCode());
    }

    @Test
    public void test_toString() throws Exception {
        cache = new SingletonCache<>(INT_0);
        Assert.assertEquals("SingletonCache enabled : " + STR_OF_INT_0, cache.toString());

        cache.disable();
        Assert.assertEquals("SingletonCache disabled", cache.toString());
    }
}
