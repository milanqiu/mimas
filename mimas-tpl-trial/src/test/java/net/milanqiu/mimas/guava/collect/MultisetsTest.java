package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.*;
import net.milanqiu.mimas.instrumentation.DebugUtils;

import java.util.Arrays;
import java.util.ConcurrentModificationException;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-9-4
 * @author Milan Qiu
 */
public class MultisetsTest {

    @Test
    public void test_containsOccurrences() throws Exception {
        /*
            containsOccurrences(Multiset sup, Multiset sub)
            Returns true if sub.count(o) <= super.count(o) for all o.
            Difference from Collection method: Collection.containsAll ignores counts, and only tests whether elements
            are contained at all.
         */
        Multiset<String> set1 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_0));
        Multiset<String> set2 = HashMultiset.create(ImmutableList.of(STR_0, STR_1));
        Multiset<String> set3 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2));
        Multiset<String> set4 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_3));
        Multiset<String> set5 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_0, STR_0));

        Assert.assertTrue(Multisets.containsOccurrences(set1, set1));
        Assert.assertTrue(Multisets.containsOccurrences(set1, set2));
        Assert.assertTrue(Multisets.containsOccurrences(set1, set3));
        Assert.assertFalse(Multisets.containsOccurrences(set1, set4));
        Assert.assertFalse(Multisets.containsOccurrences(set1, set5));

        Assert.assertTrue(set1.containsAll(set1));
        Assert.assertTrue(set1.containsAll(set2));
        Assert.assertTrue(set1.containsAll(set3));
        Assert.assertFalse(set1.containsAll(set4));
        Assert.assertTrue(set1.containsAll(set5));
    }

    @Test
    public void test_removeOccurrences() throws Exception {
        /*
            removeOccurrences(Multiset removeFrom, Multiset toRemove)
            Removes one occurrence in removeFrom for each occurrence of an element in toRemove.
            Returns true if multisetToModify was changed as a result of this operation.
            Difference from Collection method: Collection.removeAll removes all occurences of any element that occurs
            even once in toRemove.
         */
        Multiset<String> originalSet = ImmutableMultiset.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_0);
        Multiset<String> set1 = HashMultiset.create(originalSet);
        Multiset<String> set2 = HashMultiset.create(ImmutableList.of(STR_0, STR_1));
        Multiset<String> set3 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2));
        Multiset<String> set4 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_3));
        Multiset<String> set5 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_0, STR_0));

        Assert.assertTrue(Multisets.removeOccurrences(set1, set1));
        Assert.assertEquals(0, set1.size());

        set1 = HashMultiset.create(originalSet);
        Assert.assertTrue(Multisets.removeOccurrences(set1, set2));
        Assert.assertEquals(4, set1.size());
        Assert.assertEquals(2, set1.count(STR_0));
        Assert.assertEquals(2, set1.count(STR_2));

        set1 = HashMultiset.create(originalSet);
        Assert.assertTrue(Multisets.removeOccurrences(set1, set3));
        Assert.assertEquals(1, set1.size());
        Assert.assertEquals(1, set1.count(STR_0));

        set1 = HashMultiset.create(originalSet);
        Assert.assertTrue(Multisets.removeOccurrences(set1, set4));
        Assert.assertEquals(1, set1.size());
        Assert.assertEquals(1, set1.count(STR_0));

        set1 = HashMultiset.create(originalSet);
        Assert.assertTrue(Multisets.removeOccurrences(set1, set5));
        Assert.assertEquals(0, set1.size());

        set1 = HashMultiset.create(originalSet);
        try {
            set1.removeAll(set1);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(ConcurrentModificationException.class, e);
        }

        set1 = HashMultiset.create(originalSet);
        Assert.assertTrue(set1.removeAll(set2));
        Assert.assertEquals(2, set1.size());
        Assert.assertEquals(2, set1.count(STR_2));

        set1 = HashMultiset.create(originalSet);
        Assert.assertTrue(set1.removeAll(set3));
        Assert.assertEquals(0, set1.size());

        set1 = HashMultiset.create(originalSet);
        Assert.assertTrue(set1.removeAll(set4));
        Assert.assertEquals(0, set1.size());

        set1 = HashMultiset.create(originalSet);
        Assert.assertTrue(set1.removeAll(set5));
        Assert.assertEquals(0, set1.size());
    }

    @Test
    public void test_retainOccurrences() throws Exception {
        /*
            retainOccurrences(Multiset removeFrom, Multiset toRetain)
            Guarantees that removeFrom.count(o) <= toRetain.count(o) for all o.
            Returns true if multisetToModify was changed as a result of this operation.
            Difference from Collection method: nCollection.retainAll keeps all occurrences of elements that occur even once in toRetain.
         */
        Multiset<String> originalSet = ImmutableMultiset.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_0);
        Multiset<String> set1 = HashMultiset.create(originalSet);
        Multiset<String> set2 = HashMultiset.create(ImmutableList.of(STR_0, STR_1));
        Multiset<String> set3 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2));
        Multiset<String> set4 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_3));
        Multiset<String> set5 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_0, STR_0));

        Assert.assertFalse(Multisets.retainOccurrences(set1, set1));
        Assert.assertEquals(6, set1.size());
        Assert.assertEquals(3, set1.count(STR_0));
        Assert.assertEquals(1, set1.count(STR_1));
        Assert.assertEquals(2, set1.count(STR_2));

        set1 = HashMultiset.create(originalSet);
        Assert.assertTrue(Multisets.retainOccurrences(set1, set2));
        Assert.assertEquals(2, set1.size());
        Assert.assertEquals(1, set1.count(STR_0));
        Assert.assertEquals(1, set1.count(STR_1));

        set1 = HashMultiset.create(originalSet);
        Assert.assertTrue(Multisets.retainOccurrences(set1, set3));
        Assert.assertEquals(5, set1.size());
        Assert.assertEquals(2, set1.count(STR_0));
        Assert.assertEquals(1, set1.count(STR_1));
        Assert.assertEquals(2, set1.count(STR_2));

        set1 = HashMultiset.create(originalSet);
        Assert.assertTrue(Multisets.retainOccurrences(set1, set4));
        Assert.assertEquals(5, set1.size());
        Assert.assertEquals(2, set1.count(STR_0));
        Assert.assertEquals(1, set1.count(STR_1));
        Assert.assertEquals(2, set1.count(STR_2));

        set1 = HashMultiset.create(originalSet);
        Assert.assertFalse(Multisets.retainOccurrences(set1, set5));
        Assert.assertEquals(6, set1.size());
        Assert.assertEquals(3, set1.count(STR_0));
        Assert.assertEquals(1, set1.count(STR_1));
        Assert.assertEquals(2, set1.count(STR_2));

        set1 = HashMultiset.create(originalSet);
        set1.retainAll(set1);
        Assert.assertEquals(6, set1.size());
        Assert.assertEquals(3, set1.count(STR_0));
        Assert.assertEquals(1, set1.count(STR_1));
        Assert.assertEquals(2, set1.count(STR_2));

        set1 = HashMultiset.create(originalSet);
        set1.retainAll(set2);
        Assert.assertEquals(4, set1.size());
        Assert.assertEquals(3, set1.count(STR_0));
        Assert.assertEquals(1, set1.count(STR_1));

        set1 = HashMultiset.create(originalSet);
        set1.retainAll(set3);
        Assert.assertEquals(6, set1.size());
        Assert.assertEquals(3, set1.count(STR_0));
        Assert.assertEquals(1, set1.count(STR_1));
        Assert.assertEquals(2, set1.count(STR_2));

        set1 = HashMultiset.create(originalSet);
        set1.retainAll(set4);
        Assert.assertEquals(6, set1.size());
        Assert.assertEquals(3, set1.count(STR_0));
        Assert.assertEquals(1, set1.count(STR_1));
        Assert.assertEquals(2, set1.count(STR_2));

        set1 = HashMultiset.create(originalSet);
        set1.retainAll(set5);
        Assert.assertEquals(6, set1.size());
        Assert.assertEquals(3, set1.count(STR_0));
        Assert.assertEquals(1, set1.count(STR_1));
        Assert.assertEquals(2, set1.count(STR_2));
    }

    @Test
    public void test_intersection() throws Exception {
        /*
            intersection(Multiset, Multiset)
            Returns a view of the intersection of two multisets; a nondestructive alternative to retainOccurrences.
         */
        Multiset<String> originalSet = ImmutableMultiset.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_0);
        Multiset<String> set1 = HashMultiset.create(originalSet);
        Multiset<String> set2 = HashMultiset.create(ImmutableList.of(STR_0, STR_1));
        Multiset<String> set3 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2));
        Multiset<String> set4 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_3));
        Multiset<String> set5 = HashMultiset.create(ImmutableList.of(STR_0, STR_1, STR_2, STR_0, STR_2, STR_0, STR_0));

        Multiset result = Multisets.intersection(set1, set1);
        Assert.assertEquals(6, result.size());
        Assert.assertEquals(3, result.count(STR_0));
        Assert.assertEquals(1, result.count(STR_1));
        Assert.assertEquals(2, result.count(STR_2));

        result = Multisets.intersection(set1, set2);
        Assert.assertEquals(2, result.size());
        Assert.assertEquals(1, result.count(STR_0));
        Assert.assertEquals(1, result.count(STR_1));

        result = Multisets.intersection(set1, set3);
        Assert.assertEquals(5, result.size());
        Assert.assertEquals(2, result.count(STR_0));
        Assert.assertEquals(1, result.count(STR_1));
        Assert.assertEquals(2, result.count(STR_2));

        result = Multisets.intersection(set1, set4);
        Assert.assertEquals(5, result.size());
        Assert.assertEquals(2, result.count(STR_0));
        Assert.assertEquals(1, result.count(STR_1));
        Assert.assertEquals(2, result.count(STR_2));

        result = Multisets.intersection(set1, set5);
        Assert.assertEquals(6, set1.size());
        Assert.assertEquals(3, result.count(STR_0));
        Assert.assertEquals(1, result.count(STR_1));
        Assert.assertEquals(2, result.count(STR_2));
    }

    @Test
    public void test_copyHighestCountFirst() throws Exception {
        /*
            copyHighestCountFirst(Multiset)
            Returns an immutable copy of the multiset that iterates over elements in descending frequency order.
         */
        Multiset<String> set = HashMultiset.create(ImmutableList.of(STR_1, STR_0, STR_2, STR_0, STR_2, STR_0));
        ImmutableMultiset result = Multisets.copyHighestCountFirst(set);
        Assert.assertArrayEquals(new String[]{STR_0, STR_0, STR_0, STR_2, STR_2, STR_1}, result.toArray());
    }
}
