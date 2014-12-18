package net.milanqiu.mimas.java.util;

import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.system.MimasJdkTrialConvention;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-12-17
 * @author Milan Qiu
 */
public class CollectionsTest {

    private List<Integer> ints = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        ints.add(5);
        ints.add(30);
        ints.add(1);
        ints.add(4);
        ints.add(2);
    }

    @Test
    public void test_sort() throws Exception {
        Collections.sort(ints);
        Assert.assertEquals(Arrays.asList(1, 2, 4, 5, 30), ints);
    }

    @Test
    public void test_binarySearch() throws Exception {
        Collections.sort(ints);
        Assert.assertEquals(1, Collections.binarySearch(ints, 2));
        Assert.assertEquals(3, Collections.binarySearch(ints, 5));
        Assert.assertEquals(-1, Collections.binarySearch(ints, 0));
        Assert.assertEquals(-6, Collections.binarySearch(ints, 100));
    }

    @Test
    public void test_reverse() throws Exception {
        Collections.reverse(ints);
        Assert.assertEquals(Arrays.asList(2, 4, 1, 30, 5), ints);
    }

    @Test
    public void test_shuffle() throws Exception {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            Collections.shuffle(ints);
            sb.append(ints).append(System.lineSeparator());
        }
        MimasJdkTrialConvention.getSingleton().writeWorkFileInTestOutDir(sb.toString());
    }

    @Test
    public void test_swap() throws Exception {
        Collections.swap(ints, 1, 3);
        Assert.assertEquals(Arrays.asList(5, 4, 1, 30, 2), ints);
    }

    @Test
    public void test_fill() throws Exception {
        Collections.fill(ints, INT_0);
        Assert.assertEquals(Arrays.asList(INT_0, INT_0, INT_0, INT_0, INT_0), ints);
    }

    @Test
    public void test_copy() throws Exception {
        List<Integer> dest = new ArrayList<>();
        try {
            Collections.copy(dest, ints);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IndexOutOfBoundsException);
        }

        dest = Arrays.asList(INT_0, INT_0, INT_0, INT_0, INT_0, INT_0, INT_0);
        Collections.copy(dest, ints);
        Assert.assertEquals(Arrays.asList(5, 30, 1, 4, 2, INT_0, INT_0), dest);
    }

    @Test
    public void test_min() throws Exception {
        Assert.assertEquals(1, (int) Collections.min(ints));
    }

    @Test
    public void test_max() throws Exception {
        Assert.assertEquals(30, (int) Collections.max(ints));
    }

    @Test
    public void test_rotate() throws Exception {
        Collections.rotate(ints, 2);
        Assert.assertEquals(Arrays.asList(4, 2, 5, 30, 1), ints);
    }

    @Test
    public void test_replaceAll() throws Exception {
        Assert.assertTrue(Collections.replaceAll(ints, 4, INT_0));
        Assert.assertEquals(Arrays.asList(5, 30, 1, INT_0, 2), ints);

        Assert.assertFalse(Collections.replaceAll(ints, 9, INT_1));
        Assert.assertEquals(Arrays.asList(5, 30, 1, INT_0, 2), ints);
    }

    @Test
    public void test_indexOfSubList() throws Exception {
        Assert.assertEquals(2, Collections.indexOfSubList(ints, Arrays.asList(1, 4)));
        Assert.assertEquals(-1, Collections.indexOfSubList(ints, Arrays.asList(1, 2)));
        Assert.assertEquals(0, Collections.indexOfSubList(Arrays.asList(1, 2, 3, 1, 2, 3), Arrays.asList(1, 2)));
    }

    @Test
    public void test_lastIndexOfSubList() throws Exception {
        Assert.assertEquals(2, Collections.lastIndexOfSubList(ints, Arrays.asList(1, 4)));
        Assert.assertEquals(-1, Collections.lastIndexOfSubList(ints, Arrays.asList(1, 2)));
        Assert.assertEquals(3, Collections.lastIndexOfSubList(Arrays.asList(1, 2, 3, 1, 2, 3), Arrays.asList(1, 2)));
    }

    @Test
    public void test_UnmodifiableSeries() throws Exception {
        List<Integer> unmodifiable = Collections.unmodifiableList(ints);
        try {
            unmodifiable.add(INT_0);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UnsupportedOperationException);
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test_CheckedSeries() throws Exception {
        List list = new ArrayList();
        list.add(OBJ_0);
        list.add(INT_0);
        list.add(STR_0);

        List checked = Collections.checkedList(list, String.class);
        Assert.assertEquals(Arrays.asList(OBJ_0, INT_0, STR_0), checked);

        checked.add(STR_1);
        checked.add(STR_2);
        Assert.assertEquals(Arrays.asList(OBJ_0, INT_0, STR_0, STR_1, STR_2), checked);

        try {
            checked.add(INT_1);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ClassCastException);
        }
    }

    @Test
    public void test_EmptySeries() throws Exception {
        List<Integer> emptyList = Collections.emptyList();
        Assert.assertTrue(emptyList.isEmpty());

        try {
            emptyList.add(INT_0);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UnsupportedOperationException);
        }
    }

    @Test
    public void test_SingletonSeries() throws Exception {
        List<Integer> singletonList = Collections.singletonList(INT_0);
        Assert.assertEquals(Arrays.asList(INT_0), singletonList);

        try {
            singletonList.add(INT_0);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UnsupportedOperationException);
        }
    }

    @Test
    public void test_nCopies() throws Exception {
        List<Integer> list = Collections.nCopies(3, INT_0);
        Assert.assertEquals(Arrays.asList(INT_0, INT_0, INT_0), list);

        try {
            list.add(INT_0);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof UnsupportedOperationException);
        }
    }

    @Test
    public void test_reverseOrder() throws Exception {
        Collections.sort(ints, Collections.reverseOrder());
        Assert.assertEquals(Arrays.asList(30, 5, 4, 2, 1), ints);
    }

    @Test
    public void test_frequency() throws Exception {
        List<String> list = Arrays.asList(STR_0, STR_1, STR_1, STR_1, STR_2, STR_2);
        Assert.assertEquals(1, Collections.frequency(list, STR_0));
        Assert.assertEquals(3, Collections.frequency(list, STR_1));
        Assert.assertEquals(2, Collections.frequency(list, STR_2));
        Assert.assertEquals(0, Collections.frequency(list, STR_3));
    }

    @Test
    public void test_disjoint() throws Exception {
        Assert.assertTrue(Collections.disjoint(Arrays.asList(STR_0, STR_1, STR_2), Arrays.asList(STR_3, STR_4)));
        Assert.assertFalse(Collections.disjoint(Arrays.asList(STR_0, STR_1, STR_2), Arrays.asList(STR_2, STR_4)));
    }

    @Test
    public void test_addAll() throws Exception {
        Set<String> set = new HashSet<>();
        set.add(STR_0);
        set.add(STR_1);

        Assert.assertTrue(Collections.addAll(set, STR_1, STR_2));
        Assert.assertEquals(new HashSet<>(Arrays.asList(STR_0, STR_1, STR_2)), set);
        Assert.assertFalse(Collections.addAll(set, STR_1, STR_2));
        Assert.assertEquals(new HashSet<>(Arrays.asList(STR_0, STR_1, STR_2)), set);
    }
}
