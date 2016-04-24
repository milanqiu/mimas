package net.milanqiu.mimas.guava.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.junit.AssertExt;
import net.milanqiu.mimas.lang.TypeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-12-15
 * @author Milan Qiu
 */
public class CacheTest {

    @Test
    public void test_put_getIfPresent() throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.put(INT_0, STR_0);
        cache.put(INT_1, STR_1);
        Assert.assertEquals(STR_0, cache.getIfPresent(INT_0));
        Assert.assertEquals(STR_1, cache.getIfPresent(INT_1));
        Assert.assertEquals(null, cache.getIfPresent(INT_2));
    }

    @Test
    public void test_get() throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.put(INT_0, STR_0);

        try {
            Assert.assertEquals(STR_0, cache.get(INT_0, CALLABLE_RETURNING_NULL_STR));
            Assert.assertEquals(STR_4, cache.get(INT_1, new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return STR_4;
                }
            }));
            Assert.assertEquals(STR_4, cache.get(INT_1, CALLABLE_RETURNING_NULL_STR));
        } catch (ExecutionException e) {
            DebugUtils.neverGoesHere();
        }

        try {
            cache.get(INT_2, CALLABLE_RETURNING_NULL_STR);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(CacheLoader.InvalidCacheLoadException.class, e);
        }
    }

    @Test
    public void test_getAllPresent() throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.put(INT_0, STR_0);
        cache.put(INT_1, STR_1);
        Assert.assertEquals(ImmutableMap.of(INT_0, STR_0, INT_1, STR_1),
                cache.getAllPresent(ImmutableList.of(INT_0, INT_1, INT_2, INT_3)));
    }

    @Test
    public void test_putAll() throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.putAll(ImmutableMap.of(INT_0, STR_0, INT_1, STR_1));
        Assert.assertEquals(STR_0, cache.getIfPresent(INT_0));
        Assert.assertEquals(STR_1, cache.getIfPresent(INT_1));
    }

    @Test
    public void test_invalidate() throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.put(INT_0, STR_0);
        cache.put(INT_1, STR_1);

        cache.invalidate(INT_1);
        Assert.assertEquals(STR_0, cache.getIfPresent(INT_0));
        Assert.assertEquals(null, cache.getIfPresent(INT_1));
    }

    @Test
    public void test_invalidateAll() throws Exception {
        // void invalidateAll(java.lang.Iterable<?> objects)
        {
            Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
            cache.put(INT_0, STR_0);
            cache.put(INT_1, STR_1);
            cache.put(INT_2, STR_2);

            cache.invalidateAll(ImmutableList.of(INT_0, INT_1));
            Assert.assertEquals(null, cache.getIfPresent(INT_0));
            Assert.assertEquals(null, cache.getIfPresent(INT_1));
            Assert.assertEquals(STR_2, cache.getIfPresent(INT_2));
        }

        // void invalidateAll()
        {
            Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
            cache.put(INT_0, STR_0);
            cache.put(INT_1, STR_1);
            cache.put(INT_2, STR_2);

            cache.invalidateAll();
            Assert.assertEquals(null, cache.getIfPresent(INT_0));
            Assert.assertEquals(null, cache.getIfPresent(INT_1));
            Assert.assertEquals(null, cache.getIfPresent(INT_2));
        }
    }

    @Test
    public void test_size() throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        Assert.assertEquals(0, cache.size());

        cache.put(INT_0, STR_0);
        cache.put(INT_1, STR_1);
        Assert.assertEquals(2, cache.size());
    }

    @Test
    public void test_stats() throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().recordStats().maximumSize(1).build();
        cache.put(INT_0, STR_0);
        cache.put(INT_1, STR_1);

        Assert.assertEquals(null, cache.getIfPresent(INT_0));
        Assert.assertEquals(STR_1, cache.getIfPresent(INT_1));
        Assert.assertEquals(STR_2, cache.get(INT_2, new Callable<String>() {
            @Override
            public String call() throws Exception {
                return STR_2;
            }
        }));

        CacheStats stats = cache.stats();
        Assert.assertEquals(3,     stats.requestCount());
        Assert.assertEquals(1,     stats.hitCount());
        Assert.assertEquals(1.0/3, stats.hitRate(), TypeUtils.PARTICLE_DOUBLE);
        Assert.assertEquals(2,     stats.missCount());
        Assert.assertEquals(2.0/3, stats.missRate(), TypeUtils.PARTICLE_DOUBLE);
        Assert.assertEquals(1,     stats.loadCount());
        Assert.assertEquals(1,     stats.loadSuccessCount());
        Assert.assertEquals(0,     stats.loadExceptionCount());
        Assert.assertEquals(0.0,   stats.loadExceptionRate(), TypeUtils.PARTICLE_DOUBLE);
        stats.totalLoadTime();
        stats.averageLoadPenalty();
        Assert.assertEquals(2,     stats.evictionCount());
    }

    @Test
    public void test_asMap() throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.put(INT_0, STR_0);
        cache.put(INT_1, STR_1);
        Assert.assertEquals(ImmutableMap.of(INT_0, STR_0, INT_1, STR_1), cache.asMap());
    }
}
