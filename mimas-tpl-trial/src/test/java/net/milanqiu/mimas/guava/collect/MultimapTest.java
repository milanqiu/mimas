package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.*;
import net.milanqiu.mimas.collect.CollectionUtils;
import net.milanqiu.mimas.collect.MapEntry;

import java.util.*;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-24
 * @author Milan Qiu
 */
public class MultimapTest {

    /*
        Guava's Multimap framework makes it easy to handle a mapping from keys to multiple values. A Multimap is a
        general way to associate keys with arbitrarily many values, like Map<K, List<V>> or Map<K, Set<V>>.

        Most importantly, there is no such thing as a key which maps to an empty collection: a key either maps to
        at least one value, or it is simply not present in the Multimap.

        You rarely use the Multimap interface directly, however; more often you'll use ListMultimap or SetMultimap,
        which map keys to a List or a Set respectively.

        ---------------------------------------------------------------------------------
        |   Implementation          |   Keys behave like... |   Values behave like..    |
        ---------------------------------------------------------------------------------
        |   ArrayListMultimap       |   HashMap             |   ArrayList               |
        |   HashMultimap            |   HashMap             |   HashSet                 |
        |   LinkedListMultimap*     |   LinkedHashMap*      |   LinkedList*             |
        |   LinkedHashMultimap**    |   LinkedHashMap       |   LinkedHashSet           |
        |   TreeMultimap            |   TreeMap             |   TreeSet                 |
        |   ImmutableListMultimap   |   ImmutableMap        |   ImmutableList           |
        |   ImmutableSetMultimap    |   ImmutableMap        |   ImmutableSet            |
        ---------------------------------------------------------------------------------
        * LinkedListMultimap.entries() preserves iteration order across non-distinct key values.
        ** LinkedHashMultimap preserves insertion order of entries, as well as the insertion order of keys, and the
           set of values associated with any one key.
     */

    private ListMultimap<Integer, String> listMultimap;
    private SetMultimap<Integer, String> setMultimap;

    @Before
    public void setUp() throws Exception {
        listMultimap = ArrayListMultimap.create();
        setMultimap = HashMultimap.create();
    }

    @Test
    public void test_get() throws Exception {
        /*
            Multimap.get(key) returns a view of the values associated with the specified key, even if there are none
            currently. For a ListMultimap, it returns a List, for a SetMultimap, it returns a Set.
            Modifications write through to the underlying Multimap.
         */

        // ListMultimap
        List<String> list0 = listMultimap.get(INT_0);
        Assert.assertTrue(list0.isEmpty());
        list0.add(STR_0);
        list0.add(STR_1);
        List<String> anotherList0 = listMultimap.get(INT_0);
        Assert.assertEquals(2, anotherList0.size());
        Assert.assertEquals(STR_0, anotherList0.get(0));
        Assert.assertEquals(STR_1, anotherList0.get(1));

        // SetMultimap
        Set<String> set0 = setMultimap.get(INT_0);
        Assert.assertTrue(set0.isEmpty());
        set0.add(STR_0);
        set0.add(STR_1);
        Set<String> anotherSet0 = setMultimap.get(INT_0);
        Assert.assertEquals(2, anotherSet0.size());
        Assert.assertTrue(anotherSet0.contains(STR_0));
        Assert.assertTrue(anotherSet0.contains(STR_1));
    }

    @Test
    public void test_put() throws Exception {
        /*
            boolean put(K, V)
            Adds an association from the key to the value.
            Equivalent: multimap.get(key).add(value)
         */

        // ListMultimap
        Assert.assertTrue(listMultimap.put(INT_0, STR_0));
        Assert.assertTrue(listMultimap.put(INT_0, STR_1));
        Assert.assertTrue(listMultimap.put(INT_0, STR_0));
        Assert.assertTrue(listMultimap.get(INT_0).equals(ImmutableList.of(STR_0, STR_1, STR_0)));
        Assert.assertTrue(listMultimap.get(INT_1).isEmpty());

        // SetMultimap
        Assert.assertTrue(setMultimap.put(INT_0, STR_0));
        Assert.assertTrue(setMultimap.put(INT_0, STR_1));
        Assert.assertFalse(setMultimap.put(INT_0, STR_0));
        Assert.assertTrue(setMultimap.get(INT_0).equals(ImmutableSet.of(STR_0, STR_1)));
        Assert.assertTrue(setMultimap.get(INT_1).isEmpty());
    }

    @Test
    public void test_putAll() throws Exception {
        /*
            boolean putAll(K, Iterable<V>)
            Adds associations from the key to each of the values in turn.
            Equivalent: Iterables.addAll(multimap.get(key), values)
         */

        // ListMultimap
        Assert.assertTrue(listMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0)));
        Assert.assertTrue(listMultimap.get(INT_0).equals(ImmutableList.of(STR_0, STR_1, STR_0)));
        Assert.assertTrue(listMultimap.get(INT_1).isEmpty());

        // SetMultimap
        Assert.assertTrue(setMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0)));
        Assert.assertTrue(setMultimap.get(INT_0).equals(ImmutableSet.of(STR_0, STR_1)));
        Assert.assertTrue(setMultimap.get(INT_1).isEmpty());
    }

    @Test
    public void test_remove() throws Exception {
        /*
            boolean remove(K, V)
            Removes one association from key to value and returns true if the multimap changed.
            Equivalent: multimap.get(key).remove(value)
         */

        // ListMultimap
        listMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        Assert.assertTrue(listMultimap.remove(INT_0, STR_0));
        Assert.assertFalse(listMultimap.remove(INT_0, STR_2));
        Assert.assertFalse(listMultimap.remove(INT_1, STR_0));
        Assert.assertTrue(listMultimap.get(INT_0).equals(ImmutableList.of(STR_1, STR_0)));
        Assert.assertTrue(listMultimap.get(INT_1).isEmpty());

        // SetMultimap
        setMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        Assert.assertTrue(setMultimap.remove(INT_0, STR_0));
        Assert.assertFalse(setMultimap.remove(INT_0, STR_2));
        Assert.assertFalse(setMultimap.remove(INT_1, STR_0));
        Assert.assertTrue(setMultimap.get(INT_0).equals(ImmutableSet.of(STR_1)));
        Assert.assertTrue(setMultimap.get(INT_1).isEmpty());
    }

    @Test
    public void test_removeAll() throws Exception {
        /*
            List<V>/Set<V> removeAll(K)
            Removes and returns all the values associated with the specified key. The returned collection may or may
            not be modifiable, but modifying it will not affect the multimap. (Returns the appropriate collection type.)
            Equivalent: multimap.get(key).clear()
         */

        // ListMultimap
        listMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        Assert.assertTrue(listMultimap.removeAll(INT_0).equals(ImmutableList.of(STR_0, STR_1, STR_0)));
        Assert.assertTrue(listMultimap.get(INT_0).isEmpty());

        // SetMultimap
        setMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        Assert.assertTrue(setMultimap.removeAll(INT_0).equals(ImmutableSet.of(STR_0, STR_1)));
        Assert.assertTrue(setMultimap.get(INT_0).isEmpty());
    }

    @Test
    public void test_replaceValues() throws Exception {
        /*
            List<V>/Set<V> replaceValues(K, Iterable<V>)
            Clears all the values associated with key and sets key to be associated with each of values. Returns the
            values that were previously associated with the key.
            Equivalent: multimap.get(key).clear(); Iterables.addAll(multimap.get(key), values)
         */

        // ListMultimap
        listMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        Assert.assertTrue(listMultimap.replaceValues(INT_0, ImmutableList.of(STR_3, STR_4, STR_3))
                .equals(ImmutableList.of(STR_0, STR_1, STR_0)));
        Assert.assertTrue(listMultimap.get(INT_0).equals(ImmutableList.of(STR_3, STR_4, STR_3)));

        // SetMultimap
        setMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        Assert.assertTrue(setMultimap.replaceValues(INT_0, ImmutableList.of(STR_3, STR_4, STR_3))
                .equals(ImmutableSet.of(STR_0, STR_1)));
        Assert.assertTrue(setMultimap.get(INT_0).equals(ImmutableSet.of(STR_3, STR_4)));
    }

    @Test
    public void test_asMap() throws Exception {
        /*
            asMap views any Multimap<K, V> as a Map<K, Collection<V>>. The returned map supports remove, and changes
            to the returned collections write through, but the map does not support put or putAll.
            Critically, you can use asMap().get(key) when you want null on absent keys rather than a fresh, writable
            empty collection. (You can and should cast asMap.get(key) to the appropriate collection type -- a Set for
            a SetMultimap, a List for a ListMultimap -- but the type system does not allow ListMultimap to return
            Map<K, List<V>> here.)
         */

        // ListMultimap
        listMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        listMultimap.putAll(INT_1, ImmutableList.of(STR_3));
        Map<Integer, Collection<String>> listMap = listMultimap.asMap();
        Assert.assertEquals(2, listMap.size());
        Assert.assertTrue(listMap.get(INT_0).equals(ImmutableList.of(STR_0, STR_1, STR_0)));
        Assert.assertTrue(listMap.get(INT_1).equals(ImmutableList.of(STR_3)));

        // SetMultimap
        setMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        setMultimap.putAll(INT_1, ImmutableList.of(STR_3));
        Map<Integer, Collection<String>> setMap = setMultimap.asMap();
        Assert.assertEquals(2, setMap.size());
        Assert.assertTrue(setMap.get(INT_0).equals(ImmutableSet.of(STR_0, STR_1)));
        Assert.assertTrue(setMap.get(INT_1).equals(ImmutableSet.of(STR_3)));
    }

    @Test
    public void test_entries() throws Exception {
        /*
            entries views the Collection<Map.Entry<K, V>> of all entries in the Multimap. (For a SetMultimap, this is
            a Set.)
         */

        // ListMultimap
        listMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        listMultimap.putAll(INT_1, ImmutableList.of(STR_3));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listMultimap.entries(), ImmutableList.of(
                MapEntry.create(INT_0, STR_0),
                MapEntry.create(INT_0, STR_1),
                MapEntry.create(INT_0, STR_0),
                MapEntry.create(INT_1, STR_3)
        )));

        // SetMultimap
        setMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        setMultimap.putAll(INT_1, ImmutableList.of(STR_3));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(setMultimap.entries(), ImmutableSet.of(
                MapEntry.create(INT_0, STR_0),
                MapEntry.create(INT_0, STR_1),
                MapEntry.create(INT_1, STR_3)
        )));
    }

    @Test
    public void test_keySet() throws Exception {
        /*
            keySet views the distinct keys in the Multimap as a Set.
         */

        // ListMultimap
        listMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        listMultimap.putAll(INT_1, ImmutableList.of(STR_3));
        Assert.assertTrue(listMultimap.keySet().equals(ImmutableSet.of(INT_0, INT_1)));

        // SetMultimap
        setMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        setMultimap.putAll(INT_1, ImmutableList.of(STR_3));
        Assert.assertTrue(setMultimap.keySet().equals(ImmutableSet.of(INT_0, INT_1)));
    }

    @Test
    public void test_keys() throws Exception {
        /*
            keys views the keys of the Multimap as a Multiset, with multiplicity equal to the number of values
            associated to that key. Elements can be removed from the Multiset, but not added; changes will write
            through.
         */

        // ListMultimap
        listMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        listMultimap.putAll(INT_1, ImmutableList.of(STR_3));
        Multiset<Integer> listMultimapKeys = listMultimap.keys();
        Assert.assertEquals(4, listMultimapKeys.size());
        Assert.assertEquals(3, listMultimapKeys.count(INT_0));
        Assert.assertEquals(1, listMultimapKeys.count(INT_1));

        // SetMultimap
        setMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        setMultimap.putAll(INT_1, ImmutableList.of(STR_3));
        Multiset<Integer> setMultimapKeys = setMultimap.keys();
        Assert.assertEquals(3, setMultimapKeys.size());
        Assert.assertEquals(2, setMultimapKeys.count(INT_0));
        Assert.assertEquals(1, setMultimapKeys.count(INT_1));
    }

    @Test
    public void test_values() throws Exception {
        /*
            values() views the all the values in the Multimap as a "flattened" Collection<V>, all as one collection.
            This is similar to Iterables.concat(multimap.asMap().values()), but returns a full Collection instead.
         */

        // ListMultimap
        listMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        listMultimap.putAll(INT_1, ImmutableList.of(STR_3));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(listMultimap.values(),
                ImmutableList.of(STR_0, STR_1, STR_0, STR_3)));

        // SetMultimap
        setMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        setMultimap.putAll(INT_1, ImmutableList.of(STR_3));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(setMultimap.values(),
                ImmutableSet.of(STR_0, STR_1, STR_3)));
    }

    @Test
    public void test_size() throws Exception {
        /*
            Multimap.size() returns the number of entries in the entire multimap, not the number of distinct keys.
            Use Multimap.keySet().size() instead to get the number of distinct keys.
         */

        // ListMultimap
        listMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        listMultimap.putAll(INT_1, ImmutableList.of(STR_3));
        Assert.assertEquals(4, listMultimap.size());

        // SetMultimap
        setMultimap.putAll(INT_0, ImmutableList.of(STR_0, STR_1, STR_0));
        setMultimap.putAll(INT_1, ImmutableList.of(STR_3));
        Assert.assertEquals(3, setMultimap.size());
    }
}
