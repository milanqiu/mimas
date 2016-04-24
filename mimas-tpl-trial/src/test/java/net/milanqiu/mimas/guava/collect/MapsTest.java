package net.milanqiu.mimas.guava.collect;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import net.milanqiu.mimas.collect.CollectionUtils;
import net.milanqiu.mimas.collect.MapEntry;
import net.milanqiu.mimas.lang.TypeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-27
 * @author Milan Qiu
 */
public class MapsTest {

    private enum Enum {
        a,
        b
    }

    @Test
    public void test_StaticConstructors() throws Exception {
        Maps.newHashMap();
        Maps.newHashMap(ImmutableMap.of(INT_0, STR_0, INT_1, STR_1));
        Maps.newHashMapWithExpectedSize(10);

        Maps.newLinkedHashMap();
        Maps.newLinkedHashMap(ImmutableMap.of(INT_0, STR_0, INT_1, STR_1));

        Maps.newTreeMap();
        Maps.newTreeMap(TypeUtils.OBJECT_COMPARATOR);
        Maps.newTreeMap(new TreeMap<>());

        Maps.newIdentityHashMap();
        Maps.newEnumMap(Enum.class);
        Maps.newEnumMap(ImmutableMap.of(Enum.a, STR_0, Enum.b, STR_1));

        Maps.newConcurrentMap();
    }

    private class Entity {
        int id;
        private Entity(int id) {
            this.id = id;
        }
    }

    @Test
    public void test_uniqueIndex() throws Exception {
        /*
            Maps.uniqueIndex(Iterable, Function) addresses the common case of having a bunch of objects that each have
            some unique attribute, and wanting to be able to look up those objects based on that attribute.
         */
        Entity entity0 = new Entity(INT_0);
        Entity entity1 = new Entity(INT_1);
        Entity entity2 = new Entity(INT_2);
        List<Entity> entities = ImmutableList.of(entity0, entity1, entity2);
        ImmutableMap<Integer, Entity> result = Maps.uniqueIndex(entities, new Function<Entity, Integer>() {
            @Override
            public Integer apply(Entity entity) {
                return entity.id;
            }
        });
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(result.entrySet(), ImmutableSet.of(
                MapEntry.create(INT_0, entity0),
                MapEntry.create(INT_1, entity1),
                MapEntry.create(INT_2, entity2)
        )));
    }

    @Test
    public void test_difference() throws Exception {
        /*
            Maps.difference(Map, Map) allows you to compare all the differences between two maps. It returns a
            MapDifference object, which breaks down the Venn diagram into:
            entriesInCommon()	 The entries which are in both maps, with both matching keys and values.
            entriesDiffering()	 The entries with the same keys, but differing values. The values in this map are of
                                 type MapDifference.ValueDifference, which lets you look at the left and right values.
            entriesOnlyOnLeft()	 Returns the entries whose keys are in the left but not in the right map.
            entriesOnlyOnRight() Returns the entries whose keys are in the right but not in the left map.
         */
        Map<String, Integer> left = ImmutableMap.of(STR_0, INT_0, STR_1, INT_1, STR_2, INT_2);
        Map<String, Integer> right = ImmutableMap.of(STR_1, INT_1, STR_2, INT_4, STR_3, INT_3);
        MapDifference<String, Integer> diff = Maps.difference(left, right);

        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(diff.entriesInCommon().entrySet(), ImmutableSet.of(
                MapEntry.create(STR_1, INT_1))));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(diff.entriesOnlyOnLeft().entrySet(), ImmutableSet.of(
                MapEntry.create(STR_0, INT_0))));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(diff.entriesOnlyOnRight().entrySet(), ImmutableSet.of(
                MapEntry.create(STR_3, INT_3))));

        Map<String, MapDifference.ValueDifference<Integer>> entriesDiffering = diff.entriesDiffering();
        Assert.assertEquals(1, entriesDiffering.size());
        for (Map.Entry<String, MapDifference.ValueDifference<Integer>> entry : entriesDiffering.entrySet()) {
            Assert.assertEquals(STR_2, entry.getKey());
            Assert.assertEquals(INT_2, (int) entry.getValue().leftValue());
            Assert.assertEquals(INT_4, (int) entry.getValue().rightValue());
        }
    }

    @Test
    public void test_filterKeys() throws Exception {
        Map<String, Integer> unfiltered = ImmutableMap.of(STR_0, INT_0, STR_1, INT_1, STR_2, INT_2);
        Map<String, Integer> filtered = Maps.filterKeys(unfiltered, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_0) || s.equals(STR_2);
            }
        });
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(filtered.entrySet(),
                ImmutableSet.of(MapEntry.create(STR_0, INT_0), MapEntry.create(STR_2, INT_2))));
    }

    @Test
    public void test_filterValues() throws Exception {
        Map<String, Integer> unfiltered = ImmutableMap.of(STR_0, INT_0, STR_1, INT_1, STR_2, INT_2);
        Map<String, Integer> filtered = Maps.filterValues(unfiltered, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer integer) {
                return integer.equals(INT_0) || integer.equals(INT_2);
            }
        });
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(filtered.entrySet(),
                ImmutableSet.of(MapEntry.create(STR_0, INT_0), MapEntry.create(STR_2, INT_2))));
    }

    @Test
    public void test_filterEntries() throws Exception {
        Map<String, Integer> unfiltered = ImmutableMap.of(STR_0, INT_0, STR_1, INT_1, STR_2, INT_2);
        Map<String, Integer> filtered = Maps.filterEntries(unfiltered, new Predicate<Map.Entry<String, Integer>>() {
            @Override
            public boolean apply(Map.Entry<String, Integer> stringIntegerEntry) {
                return stringIntegerEntry.getKey().equals(STR_0) || stringIntegerEntry.getValue().equals(INT_2);
            }
        });
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(filtered.entrySet(),
                ImmutableSet.of(MapEntry.create(STR_0, INT_0), MapEntry.create(STR_2, INT_2))));
    }

    @Test
    public void test_transformValues() throws Exception {
        Map<String, Integer> from = ImmutableMap.of(STR_0, INT_0, STR_1, INT_1, STR_2, INT_2);
        Map<String, String> to = Maps.transformValues(from, new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return integer.toString();
            }
        });
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(to.entrySet(), ImmutableSet.of(
                        MapEntry.create(STR_0, STR_OF_INT_0),
                        MapEntry.create(STR_1, STR_OF_INT_1),
                        MapEntry.create(STR_2, STR_OF_INT_2))));
    }

    @Test
    public void test_transformEntries() throws Exception {
        Map<String, Integer> from = ImmutableMap.of(STR_0, INT_0, STR_1, INT_1, STR_2, INT_2);
        Map<String, String> to = Maps.transformEntries(from, new Maps.EntryTransformer<String, Integer, String>() {
            @Override
            public String transformEntry(String s, Integer integer) {
                return s + integer.toString();
            }
        });
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(to.entrySet(), ImmutableSet.of(
                MapEntry.create(STR_0, STR_0+STR_OF_INT_0),
                MapEntry.create(STR_1, STR_1+STR_OF_INT_1),
                MapEntry.create(STR_2, STR_2+STR_OF_INT_2))));
    }

    @Test
    public void test_asMap() throws Exception {
        Set<Integer> set = Sets.newHashSet(INT_0, INT_1, INT_2);
        Map<Integer, String> result = Maps.asMap(set, new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return integer.toString();
            }
        });
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(result.entrySet(), ImmutableSet.of(
                MapEntry.create(INT_0, STR_OF_INT_0),
                MapEntry.create(INT_1, STR_OF_INT_1),
                MapEntry.create(INT_2, STR_OF_INT_2))));

        set.add(INT_3);
        set.remove(INT_1);
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(result.entrySet(), ImmutableSet.of(
                MapEntry.create(INT_0, STR_OF_INT_0),
                MapEntry.create(INT_2, STR_OF_INT_2),
                MapEntry.create(INT_3, STR_OF_INT_3))));
    }

    @Test
    public void test_toMap() throws Exception {
        Iterable<Integer> itr = FluentIterable.of(new Integer[]{INT_0, INT_1, INT_2});
        ImmutableMap<Integer, String> result = Maps.toMap(itr, new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                return integer.toString();
            }
        });
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(result.entrySet(), ImmutableSet.of(
                MapEntry.create(INT_0, STR_OF_INT_0),
                MapEntry.create(INT_1, STR_OF_INT_1),
                MapEntry.create(INT_2, STR_OF_INT_2))));
    }
}
