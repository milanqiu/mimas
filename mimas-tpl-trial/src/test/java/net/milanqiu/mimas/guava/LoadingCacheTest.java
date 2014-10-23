package net.milanqiu.mimas.guava;

import com.google.common.cache.*;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.lang.CollectionUtils;
import net.milanqiu.mimas.lang.MapEntry;
import net.milanqiu.mimas.math.MathUtils;

import java.util.concurrent.*;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-10-21
 * @author Milan Qiu
 */
public class LoadingCacheTest {

    @Test
    public void test_buildFromCacheLoader() throws Exception {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder().build(
                new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer integer) throws Exception {
                        return integer.toString();
                    }
                });
        Assert.assertEquals(STR_OF_INT_0, cache.get(INT_0));
        Assert.assertEquals(STR_OF_INT_0, cache.get(INT_0));
        Assert.assertEquals(STR_OF_INT_1, cache.get(INT_1));
    }

    @Test
    public void test_buildFromCallable() throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        Assert.assertEquals(STR_0, cache.get(INT_0, new Callable<String>() {
            @Override
            public String call() throws Exception {
                return STR_0;
            }
        }));
        Assert.assertEquals(STR_0, cache.get(INT_0, CALLABLE_RETURNING_NULL_STR));
        Assert.assertEquals(STR_1, cache.get(INT_1, new Callable<String>() {
            @Override
            public String call() throws Exception {
                return STR_1;
            }
        }));
    }

    @Test
    public void test_put() throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.put(INT_0, STR_0);
        cache.put(INT_1, STR_1);
        Assert.assertEquals(STR_0, cache.get(INT_0, CALLABLE_RETURNING_NULL_STR));
        Assert.assertEquals(STR_0, cache.get(INT_0, CALLABLE_RETURNING_NULL_STR));
        Assert.assertEquals(STR_1, cache.get(INT_1, CALLABLE_RETURNING_NULL_STR));
    }

    @Test
    public void test_get_getUnchecked_getAll() throws Exception {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder().build(
                new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer integer) throws Exception {
                        return integer.toString();
                    }
                });

        try {
            Assert.assertEquals(STR_OF_INT_0, cache.get(INT_0));
        } catch (ExecutionException e) {
            DebugUtils.neverGoesHere();
        }
        Assert.assertEquals(STR_OF_INT_1, cache.getUnchecked(INT_1));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(
                cache.getAll(ImmutableList.of(INT_2, INT_3, INT_2)).entrySet(),
                ImmutableSet.of(MapEntry.create(INT_2, STR_OF_INT_2), MapEntry.create(INT_3, STR_OF_INT_3))
        ));
    }

    @Test
    public void test_Eviction() throws Exception {
        /*
            Size-based Eviction.
         */
        CacheBuilder.newBuilder().maximumSize(10000);
        CacheBuilder.newBuilder().maximumWeight(10000).weigher(new Weigher<Object, Object>() {
            @Override
            public int weigh(Object o, Object o2) {
                return 100;
            }
        });
        /*
           Only expire entries after the specified duration has passed since the entry was last accessed by a read or
           a write. Note that the order in which entries are evicted will be similar to that of size-based eviction.
         */
        CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES);
        /*
            Expire entries after the specified duration has passed since the entry was created, or the most recent
            replacement of the value. This could be desirable if cached data grows stale after a certain amount of time.
         */
        CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES);
        /*
            CacheBuilder.weakKeys() stores keys using weak references. This allows entries to be garbage-collected if
            there are no other (strong or soft) references to the keys. Since garbage collection depends only on
            identity equality, this causes the whole cache to use identity (==) equality to compare keys, instead of
            equals().
         */
        CacheBuilder.newBuilder().weakKeys();
        /*
            CacheBuilder.weakValues() stores values using weak references. This allows entries to be garbage-collected
            if there are no other (strong or soft) references to the values. Since garbage collection depends only on
            identity equality, this causes the whole cache to use identity (==) equality to compare values, instead of
            equals().
         */
        CacheBuilder.newBuilder().weakValues();
        /*
            CacheBuilder.softValues() wraps values in soft references. Softly referenced objects are garbage-collected
            in a globally least-recently-used manner, in response to memory demand. Because of the performance
            implications of using soft references, we generally recommend using the more predictable maximum cache size
            instead. Use of softValues() will cause values to be compared using identity (==) equality instead of
            equals().
         */
        CacheBuilder.newBuilder().softValues();
    }

    @Test
    public void test_invalidate_invalidateAll() throws Exception {
        Cache<Integer, String> cache = CacheBuilder.newBuilder().build();
        cache.invalidate(INT_0);
        cache.invalidateAll(ImmutableList.of(INT_1, INT_2));
        cache.invalidateAll();
    }

    @Test
    public void test_removalListener() throws Exception {
        RemovalListener<Integer, String> removalListener = new RemovalListener<Integer, String>() {
            @Override
            public void onRemoval(RemovalNotification<Integer, String> integerStringRemovalNotification) {
            }
        };
        CacheBuilder.newBuilder().removalListener(removalListener).build();
        CacheBuilder.newBuilder().removalListener(
                RemovalListeners.asynchronous(removalListener, Executors.newSingleThreadExecutor())).build();
    }

    @Test
    public void test_Statistics() throws Exception {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder().recordStats().maximumSize(1).build(
                new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer integer) throws Exception {
                        return integer.toString();
                    }
                });
        cache.get(INT_0);
        cache.get(INT_0);
        cache.get(INT_1);

        CacheStats stats = cache.stats();
        /*
            The ratio of hits to requests.
         */
        Assert.assertEquals((double)1/3, stats.hitRate(), MathUtils.PARTICLE);
        /*
            The number of cache evictions.
         */
        Assert.assertEquals(1, stats.evictionCount());
    }

    @Test
    public void test_asMap() throws Exception {
        LoadingCache<Integer, String> cache = CacheBuilder.newBuilder().build(
                new CacheLoader<Integer, String>() {
                    @Override
                    public String load(Integer integer) throws Exception {
                        return integer.toString();
                    }
                });
        cache.get(INT_0);
        cache.get(INT_0);
        cache.get(INT_1);

        ConcurrentMap<Integer, String> map = cache.asMap();
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(map.entrySet(),
                ImmutableSet.of(MapEntry.create(INT_0, STR_OF_INT_0), MapEntry.create(INT_1, STR_OF_INT_1))));
    }
}
