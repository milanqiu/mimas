package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-7-21
 * @author Milan Qiu
 */
public class ImmutableSeriesTest {

    /*
        ---------------------------------------------------------------------------------
        |   Interface               |   JDK or Guava?   |   Immutable Version           |
        ---------------------------------------------------------------------------------
        |   Collection              |   JDK             |   ImmutableCollection         |
        |   List                    |   JDK             |   ImmutableList               |
        |   Set                     |   JDK             |   ImmutableSet                |
        |   SortedSet/NavigableSet  |   JDK             |   ImmutableSortedSet          |
        |   Map                     |   JDK             |   ImmutableMap                |
        |   SortedMap               |   JDK             |   ImmutableSortedMap          |
        |   Multiset                |   Guava           |   ImmutableMultiset           |
        |   SortedMultiset          |   Guava           |   ImmutableSortedMultiset     |
        |   Multimap                |   Guava           |   ImmutableMultimap           |
        |   ListMultimap            |   Guava           |   ImmutableListMultimap       |
        |   SetMultimap             |   Guava           |   ImmutableSetMultimap        |
        |   BiMap                   |   Guava           |   ImmutableBiMap              |
        |   ClassToInstanceMap      |   Guava           |   ImmutableClassToInstanceMap |
        |   Table                   |   Guava           |   ImmutableTable              |
        ---------------------------------------------------------------------------------
     */

    /**
     * Asserts the specified set is equal to standard instance.
     * @param set the set to assert
     */
    private void assertSetIsSI(Set set) {
        Assert.assertEquals(3, set.size());
        Assert.assertTrue(set.contains(STR_0));
        Assert.assertTrue(set.contains(STR_1));
        Assert.assertTrue(set.contains(STR_2));
    }

    /**
     * Asserts the specified list is equal to standard instance.
     * @param list the list to assert
     */
    private void assertListIsSI(List list) {
        Assert.assertEquals(3, list.size());
        Assert.assertEquals(STR_0, list.get(0));
        Assert.assertEquals(STR_1, list.get(1));
        Assert.assertEquals(STR_2, list.get(2));
    }

    @Test
    public void test_copyOf() throws Exception {
        List<String> list = new ArrayList<>();
        list.add(STR_0);
        list.add(STR_1);
        list.add(STR_2);

        assertSetIsSI(ImmutableSet.copyOf(list));
        assertSetIsSI(ImmutableSet.copyOf(list.iterator()));
        assertSetIsSI(ImmutableSet.copyOf(new String[]{STR_0, STR_1, STR_2, STR_1}));

        assertListIsSI(ImmutableList.copyOf(list));
        assertListIsSI(ImmutableList.copyOf(list.iterator()));
        assertListIsSI(ImmutableList.copyOf(new String[]{STR_0, STR_1, STR_2}));
    }

    @Test
    public void test_of() throws Exception {
        assertSetIsSI(ImmutableSet.of(STR_0, STR_1, STR_2, STR_1));
        assertListIsSI(ImmutableList.of(STR_0, STR_1, STR_2));
    }

    @Test
    public void test_builder_Builder_add_addAll_build() throws Exception {
        assertSetIsSI(ImmutableSet.builder()
                .add(STR_0)
                .add(STR_1)
                .add(STR_2, STR_1)
                .build());
        assertListIsSI(ImmutableList.builder()
                .addAll(ImmutableList.of(STR_0, STR_1))
                .add(STR_2)
                .build());
    }

    @Test
    public void test_asList() throws Exception {
        assertListIsSI(ImmutableSet.of(STR_0, STR_1, STR_2, STR_1).asList());
    }

    @Test
    public void test_reverse() throws Exception {
        assertListIsSI(ImmutableList.of(STR_2, STR_1, STR_0).reverse());
    }
}
