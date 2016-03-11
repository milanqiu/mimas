package net.milanqiu.mimas.java.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-25
 * @author Milan Qiu
 */
public class CollectionTest {

    @Test
    public void test_remove() throws Exception {
        // if the collection contains more than one elements equal to the specified object,
        // only the first occurrence will be removed
        Collection<String> arrayList = new ArrayList<>(Arrays.asList(STR_0, STR_1, STR_2, STR_0));
        Assert.assertTrue(arrayList.remove(STR_0));
        Assert.assertTrue(arrayList.equals(Arrays.asList(STR_1, STR_2, STR_0)));
        Assert.assertTrue(arrayList.remove(STR_0));
        Assert.assertTrue(arrayList.equals(Arrays.asList(STR_1, STR_2)));
        Assert.assertFalse(arrayList.remove(STR_0));
    }

    @Test
    public void test_removeAll() throws Exception {
        // if the collection contains more than one elements equal to the specified object,
        // all occurrences will be removed
        Collection<String> arrayList = new ArrayList<>(Arrays.asList(STR_0, STR_1, STR_2, STR_0));
        Collection<String> arrayListRemoved = Collections.singletonList(STR_0);
        Assert.assertTrue(arrayList.removeAll(arrayListRemoved));
        Assert.assertTrue(arrayList.equals(Arrays.asList(STR_1, STR_2)));
        Assert.assertFalse(arrayList.removeAll(arrayListRemoved));
    }
}
