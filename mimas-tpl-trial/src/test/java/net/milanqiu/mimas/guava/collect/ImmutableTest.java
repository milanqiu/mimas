package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-21
 * @author Milan Qiu
 */
public class ImmutableTest {

    /*
        -------------------------------------------------------------------------
        |   Collection              |   JDK     |   ImmutableCollection         |
        |   List                    |   JDK     |   ImmutableList               |
        |   Set                     |   JDK     |   ImmutableSet                |
        |   SortedSet/NavigableSet  |   JDK     |   ImmutableSortedSet          |
        |   Map                     |   JDK     |   ImmutableMap                |
        |   SortedMap               |   JDK     |   ImmutableSortedMap          |
        |   Multiset                |   Guava   |   ImmutableMultiset           |
        |   SortedMultiset          |   Guava   |   ImmutableSortedMultiset     |
        |   Multimap                |   Guava   |   ImmutableMultimap           |
        |   ListMultimap            |   Guava   |   ImmutableListMultimap       |
        |   SetMultimap             |   Guava   |   ImmutableSetMultimap        |
        |   BiMap                   |   Guava   |   ImmutableBiMap              |
        |   ClassToInstanceMap      |   Guava   |   ImmutableClassToInstanceMap |
        |   Table                   |   Guava   |   ImmutableTable              |
        -------------------------------------------------------------------------
     */

    /**
     * Check if the specified set is equal to standard instance.
     * @param s the set to be checked
     */
    private void checkSetIsSI(Set s) {
        Assert.assertEquals(3, s.size());
        Assert.assertTrue(s.contains(STR_0));
        Assert.assertTrue(s.contains(STR_1));
        Assert.assertTrue(s.contains(STR_2));
    }

    /**
     * Check if the specified list is equal to standard instance.
     * @param l the list to be checked
     */
    private void checkListIsSI(List l) {
        Assert.assertEquals(3, l.size());
        Assert.assertEquals(STR_0, l.get(0));
        Assert.assertEquals(STR_1, l.get(1));
        Assert.assertEquals(STR_2, l.get(2));
    }

    @Test
    public void test_copyOf() throws Exception {
        Set<String> s = new HashSet<>();
        s.add(STR_0);
        s.add(STR_1);
        s.add(STR_2);

        checkSetIsSI(ImmutableSet.copyOf(s));
        checkSetIsSI(ImmutableSet.copyOf(s.iterator()));
        checkSetIsSI(ImmutableSet.copyOf(new String[]{STR_0, STR_1, STR_2}));
    }

    @Test
    public void test_of() throws Exception {
        checkSetIsSI(ImmutableSet.of(STR_0, STR_1, STR_2, STR_1));
    }

    @Test
    public void test_builder_build() throws Exception {
        checkSetIsSI(ImmutableSet.<String>builder()
                .add(STR_0)
                .add(STR_1)
                .add(STR_2)
                .add(STR_1)
                .build());
    }

    @Test
    public void test_asList() throws Exception {
        checkListIsSI(ImmutableSet.of(STR_0, STR_1, STR_2, STR_1).asList());
    }
}
