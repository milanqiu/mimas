package net.milanqiu.mimas.guava.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutionException;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-10-21
 * @author Milan Qiu
 */
public class LoadingCacheTest {

    @Test
    public void test_get() throws Exception {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder().build(
                new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer integer) throws Exception {
                        return integer.toString();
                    }
                });
        try {
            Assert.assertEquals(STR_OF_INT_0, cache.get(INT_0));
            Assert.assertEquals(STR_OF_INT_0, cache.get(INT_0));
            Assert.assertEquals(STR_OF_INT_1, cache.get(INT_1));
        } catch (ExecutionException e) {
            DebugUtils.neverGoesHere();
        }
    }

    @Test
    public void test_getUnchecked() throws Exception {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder().build(
                new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer integer) throws Exception {
                        return integer.toString();
                    }
                });
        Assert.assertEquals(STR_OF_INT_0, cache.getUnchecked(INT_0));
        Assert.assertEquals(STR_OF_INT_0, cache.getUnchecked(INT_0));
        Assert.assertEquals(STR_OF_INT_1, cache.getUnchecked(INT_1));
    }

    @Test
    public void test_getAll() throws Exception {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder().build(
                new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer integer) throws Exception {
                        return integer.toString();
                    }
                });
        Assert.assertEquals(ImmutableMap.of(INT_0, STR_OF_INT_0, INT_1, STR_OF_INT_1, INT_2, STR_OF_INT_2),
                cache.getAll(ImmutableList.of(INT_0, INT_1, INT_2)));
    }

    @Test
    public void test_refresh() throws Exception {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder().build(
                new CacheLoader<Integer, String>() {
                    int counter = 0;
                    @Override
                    public String load(Integer integer) throws Exception {
                        return String.valueOf(++counter);
                    }
                });
        Assert.assertEquals("1", cache.get(INT_0));
        Assert.assertEquals("2", cache.get(INT_1));

        Assert.assertEquals("1", cache.get(INT_0));
        cache.refresh(INT_0);
        Assert.assertEquals("3", cache.get(INT_0));
    }
}
