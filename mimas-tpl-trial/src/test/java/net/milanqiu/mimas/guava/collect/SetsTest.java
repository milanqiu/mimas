package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.milanqiu.mimas.collect.CollectionUtils;
import net.milanqiu.mimas.lang.TypeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-27
 * @author Milan Qiu
 */
public class SetsTest {

    private enum Enum {
        a,
        b,
        c,
        d
    }

    @SuppressWarnings("RedundantArrayCreation")
    @Test
    public void test_StaticConstructors() throws Exception {
        Sets.newHashSet();
        Sets.newHashSet(STR_0, STR_1, STR_2);
        Sets.newHashSet(new String[]{STR_0, STR_1, STR_2});
        Sets.newHashSet(ImmutableList.of(STR_0, STR_1, STR_2));
        Sets.newHashSet(ImmutableList.of(STR_0, STR_1, STR_2).iterator());
        Sets.newHashSetWithExpectedSize(10);

        Sets.newLinkedHashSet();
        Sets.newLinkedHashSet(ImmutableList.of(STR_0, STR_1, STR_2));
        Sets.newLinkedHashSetWithExpectedSize(10);

        Sets.newTreeSet();
        Sets.newTreeSet(TypeUtils.OBJECT_COMPARATOR);
        Sets.newTreeSet(ImmutableList.of(STR_0, STR_1, STR_2));

        Sets.newIdentityHashSet();
        Sets.newEnumSet(ImmutableList.of(Enum.a, Enum.b, Enum.c), Enum.class);
        Sets.newSetFromMap(new HashMap<Object, Boolean>());

        Sets.newCopyOnWriteArraySet();
        Sets.newCopyOnWriteArraySet(ImmutableList.of(STR_0, STR_1, STR_2));

        Sets.newConcurrentHashSet();
        Sets.newConcurrentHashSet(ImmutableList.of(STR_0, STR_1, STR_2));
    }

    @SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
    @Test
    public void test_union() throws Exception {
        Set<String> set1 = ImmutableSet.of(STR_0, STR_1, STR_2);
        Set<String> set2 = ImmutableSet.of(STR_1, STR_4);
        Sets.SetView<String> result = Sets.union(set1, set2);
        Assert.assertEquals(ImmutableSet.of(STR_0, STR_1, STR_2, STR_4), result);
    }

    @SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
    @Test
    public void test_intersection() throws Exception {
        Set<String> set1 = ImmutableSet.of(STR_0, STR_1, STR_2);
        Set<String> set2 = ImmutableSet.of(STR_1, STR_4);
        Sets.SetView<String> result = Sets.intersection(set1, set2);
        Assert.assertEquals(ImmutableSet.of(STR_1), result);
    }

    @SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
    @Test
    public void test_difference() throws Exception {
        Set<String> set1 = ImmutableSet.of(STR_0, STR_1, STR_2);
        Set<String> set2 = ImmutableSet.of(STR_1, STR_4);
        Sets.SetView<String> result = Sets.difference(set1, set2);
        Assert.assertEquals(ImmutableSet.of(STR_0, STR_2), result);
    }

    @SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
    @Test
    public void test_symmetricDifference() throws Exception {
        Set<String> set1 = ImmutableSet.of(STR_0, STR_1, STR_2);
        Set<String> set2 = ImmutableSet.of(STR_1, STR_4);
        Sets.SetView<String> result = Sets.symmetricDifference(set1, set2);
        Assert.assertEquals(ImmutableSet.of(STR_0, STR_2, STR_4), result);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test_cartesianProduct() throws Exception {
        /*
            Set<List> cartesianProduct(Set...)
            Returns every possible list that can be obtained by choosing one element from each set.
         */
        Set<String> set1 = ImmutableSet.of(STR_0, STR_1, STR_2);
        Set<String> set2 = ImmutableSet.of(STR_1, STR_4);
        Set<List<String>> result = Sets.cartesianProduct(set1, set2);
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(result, ImmutableSet.of(
                ImmutableList.of(STR_0, STR_1),
                ImmutableList.of(STR_0, STR_4),
                ImmutableList.of(STR_1, STR_1),
                ImmutableList.of(STR_1, STR_4),
                ImmutableList.of(STR_2, STR_1),
                ImmutableList.of(STR_2, STR_4)
        )));

        /*
            Set<List> cartesianProduct(List<Set>)
         */
        Set<String> set3 = ImmutableSet.of(STR_3);
        result = Sets.cartesianProduct(ImmutableList.of(set1, set2, set3));
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(result, ImmutableSet.of(
                ImmutableList.of(STR_0, STR_1, STR_3),
                ImmutableList.of(STR_0, STR_4, STR_3),
                ImmutableList.of(STR_1, STR_1, STR_3),
                ImmutableList.of(STR_1, STR_4, STR_3),
                ImmutableList.of(STR_2, STR_1, STR_3),
                ImmutableList.of(STR_2, STR_4, STR_3)
        )));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test_powerSet() throws Exception {
        /*
            Set<Set> powerSet(Set)
            Returns the set of subsets of the specified set.
         */
        Set<String> set = ImmutableSet.of(STR_0, STR_1, STR_2);
        Set<Set<String>> result = Sets.powerSet(set);
        Assert.assertTrue(CollectionUtils.equalsIgnoringOrder(result, ImmutableSet.of(
                ImmutableSet.of(),
                ImmutableSet.of(STR_0),
                ImmutableSet.of(STR_1),
                ImmutableSet.of(STR_2),
                ImmutableSet.of(STR_0, STR_1),
                ImmutableSet.of(STR_0, STR_2),
                ImmutableSet.of(STR_1, STR_2),
                ImmutableSet.of(STR_0, STR_1, STR_2)
        )));
    }

    @Test
    public void test_complementOf() throws Exception {
        EnumSet<Enum> result = Sets.complementOf(ImmutableSet.of(Enum.a, Enum.b));
        Assert.assertEquals(2, result.size());
        Assert.assertTrue(result.contains(Enum.c));
        Assert.assertTrue(result.contains(Enum.d));
    }
}
