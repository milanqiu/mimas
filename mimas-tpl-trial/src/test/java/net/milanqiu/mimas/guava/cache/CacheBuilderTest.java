package net.milanqiu.mimas.guava.cache;

import com.google.common.cache.*;
import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Creation Date: 2014-12-15
 * @author Milan Qiu
 */
public class CacheBuilderTest {

    @Test
    public void test_Eviction() throws Exception {
        /*
            Size-based Eviction.
         */
        CacheBuilder.newBuilder().maximumSize(10000).build();
        CacheBuilder.newBuilder().maximumWeight(10000).weigher(new Weigher<Object, Object>() {
            @Override
            public int weigh(Object o, Object o2) {
                return 100;
            }
        }).build();
        /*
           Only expire entries after the specified duration has passed since the entry was last accessed by a read or
           a write. Note that the order in which entries are evicted will be similar to that of size-based eviction.
         */
        CacheBuilder.newBuilder().expireAfterAccess(10, TimeUnit.MINUTES).build();
        /*
            Expire entries after the specified duration has passed since the entry was created, or the most recent
            replacement of the value. This could be desirable if cached data grows stale after a certain amount of time.
         */
        CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).build();
        /*
            CacheBuilder.weakKeys() stores keys using weak references. This allows entries to be garbage-collected if
            there are no other (strong or soft) references to the keys. Since garbage collection depends only on
            identity equality, this causes the whole cache to use identity (==) equality to compare keys, instead of
            equals().
         */
        CacheBuilder.newBuilder().weakKeys().build();
        /*
            CacheBuilder.weakValues() stores values using weak references. This allows entries to be garbage-collected
            if there are no other (strong or soft) references to the values. Since garbage collection depends only on
            identity equality, this causes the whole cache to use identity (==) equality to compare values, instead of
            equals().
         */
        CacheBuilder.newBuilder().weakValues().build();
        /*
            CacheBuilder.softValues() wraps values in soft references. Softly referenced objects are garbage-collected
            in a globally least-recently-used manner, in response to memory demand. Because of the performance
            implications of using soft references, we generally recommend using the more predictable maximum cache size
            instead. Use of softValues() will cause values to be compared using identity (==) equality instead of
            equals().
         */
        CacheBuilder.newBuilder().softValues().build();
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
}
