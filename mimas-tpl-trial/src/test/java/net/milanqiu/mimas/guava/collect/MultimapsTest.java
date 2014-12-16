package net.milanqiu.mimas.guava.collect;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.*;
import net.milanqiu.mimas.collect.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-9-4
 * @author Milan Qiu
 */
    public class MultimapsTest {

    private class Entity {
        int id;
        private Entity(int id) {
            this.id = id;
        }
    }

    @Test
    public void test_index() throws Exception {
        /*
            The cousin to Maps.uniqueIndex, Multimaps.index(Iterable, Function) answers the case when you want to be
            able to look up all objects with some particular attribute in common, which is not necessarily unique.
         */
        Entity entity0 = new Entity(INT_0);
        Entity entity1 = new Entity(INT_1);
        Entity entity2 = new Entity(INT_2);
        Entity entity3 = new Entity(INT_0);
        Entity entity4 = new Entity(INT_2);
        Entity entity5 = new Entity(INT_0);
        List<Entity> entities = ImmutableList.of(entity0, entity1, entity2, entity3, entity4, entity5);
        ImmutableListMultimap<Integer, Entity> result = Multimaps.index(entities, new Function<Entity, Integer>() {
            @Override
            public Integer apply(Entity entity) {
                return entity.id;
            }
        });

        Assert.assertEquals(3, result.keySet().size());
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(result.get(INT_0),
                ImmutableList.of(entity0, entity3, entity5)));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(result.get(INT_1),
                ImmutableList.of(entity1)));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(result.get(INT_2),
                ImmutableList.of(entity2, entity4)));
    }

    @Test
    public void test_invertFrom() throws Exception {
        /*
            Since Multimap can map many keys to one value, and one key to many values, it can be useful to invert a
            Multimap. Guava provides invertFrom(Multimap toInvert, Multimap dest) to let you do this, without choosing
            an implementation for you.
         */
        ListMultimap<Integer, String> from = ArrayListMultimap.create();
        from.putAll(INT_0, ImmutableList.of(STR_3, STR_4));
        from.putAll(INT_1, ImmutableList.of(STR_3));
        from.putAll(INT_2, ImmutableList.of(STR_2, STR_3, STR_4));
        from.putAll(INT_3, ImmutableList.of(STR_2, STR_4));

        TreeMultimap<String, Integer> to = TreeMultimap.create();
        Assert.assertSame(to, Multimaps.invertFrom(from, to));
        Assert.assertEquals(3, to.keySet().size());
        Assert.assertEquals(8, to.size());
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(to.get(STR_2),
                ImmutableList.of(INT_2, INT_3)));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(to.get(STR_3),
                ImmutableList.of(INT_0, INT_1, INT_2)));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(to.get(STR_4),
                ImmutableList.of(INT_0, INT_2, INT_3)));
    }

    @Test
    public void test_forMap() throws Exception {
        /*
            Need to use a Multimap method on a Map? forMap(Map) views a Map as a SetMultimap. This is particularly
            useful, for example, in combination with Multimaps.invertFrom.
         */
        Map<String, Integer> map = ImmutableMap.of(STR_0, INT_0, STR_1, INT_1, STR_2, INT_1);
        SetMultimap<String, Integer> multimap = Multimaps.forMap(map);
        Assert.assertEquals(3, multimap.keySet().size());
        Assert.assertEquals(3, multimap.size());
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(multimap.get(STR_0), ImmutableSet.of(INT_0)));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(multimap.get(STR_1), ImmutableSet.of(INT_1)));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(multimap.get(STR_2), ImmutableSet.of(INT_1)));
    }

    @Test
    public void test_filterKeys() throws Exception {
        ListMultimap<Integer, String> unfiltered = ArrayListMultimap.create();
        unfiltered.putAll(INT_0, ImmutableList.of(STR_3, STR_4));
        unfiltered.putAll(INT_1, ImmutableList.of(STR_3));
        unfiltered.putAll(INT_2, ImmutableList.of(STR_2, STR_3, STR_4));
        unfiltered.putAll(INT_3, ImmutableList.of(STR_2, STR_4));

        ListMultimap<Integer, String> filtered = Multimaps.filterKeys(unfiltered, new Predicate<Integer>() {
            @Override
            public boolean apply(Integer integer) {
                return integer.equals(INT_0) || integer.equals(INT_2);
            }
        });

        Assert.assertEquals(2, filtered.keySet().size());
        Assert.assertEquals(5, filtered.size());
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(filtered.get(INT_0),
                ImmutableList.of(STR_3, STR_4)));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(filtered.get(INT_2),
                ImmutableList.of(STR_2, STR_3, STR_4)));
    }

    @Test
    public void test_filterValues() throws Exception {
        ListMultimap<Integer, String> unfiltered = ArrayListMultimap.create();
        unfiltered.putAll(INT_0, ImmutableList.of(STR_3, STR_4));
        unfiltered.putAll(INT_1, ImmutableList.of(STR_3));
        unfiltered.putAll(INT_2, ImmutableList.of(STR_2, STR_3, STR_4));
        unfiltered.putAll(INT_3, ImmutableList.of(STR_2, STR_4));

        Multimap<Integer, String> filtered = Multimaps.filterValues(unfiltered, new Predicate<String>() {
            @Override
            public boolean apply(String s) {
                return s.equals(STR_2) || s.equals(STR_4);
            }
        });

        Assert.assertEquals(3, filtered.keySet().size());
        Assert.assertEquals(5, filtered.size());
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(filtered.get(INT_0),
                ImmutableSet.of(STR_4)));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(filtered.get(INT_2),
                ImmutableSet.of(STR_2, STR_4)));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(filtered.get(INT_3),
                ImmutableSet.of(STR_2, STR_4)));
    }

    @Test
    public void test_filterEntries() throws Exception {
        ListMultimap<Integer, String> unfiltered = ArrayListMultimap.create();
        unfiltered.putAll(INT_0, ImmutableList.of(STR_3, STR_4));
        unfiltered.putAll(INT_1, ImmutableList.of(STR_3));
        unfiltered.putAll(INT_2, ImmutableList.of(STR_2, STR_3, STR_4));
        unfiltered.putAll(INT_3, ImmutableList.of(STR_2, STR_4));

        Multimap<Integer, String> filtered = Multimaps.filterEntries(unfiltered, new Predicate<Map.Entry<Integer, String>>() {
            @Override
            public boolean apply(Map.Entry<Integer, String> integerStringEntry) {
                return integerStringEntry.getKey().equals(INT_2) || integerStringEntry.getValue().equals(STR_2);
            }
        });

        Assert.assertEquals(2, filtered.keySet().size());
        Assert.assertEquals(4, filtered.size());
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(filtered.get(INT_2),
                ImmutableSet.of(STR_2, STR_3, STR_4)));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(filtered.get(INT_3),
                ImmutableSet.of(STR_2)));
    }
}
