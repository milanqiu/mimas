package net.milanqiu.mimas.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-25
 * @author Milan Qiu
 */
public class CollectionTest {

    @Test
    public void test_remove() throws Exception {
        // if the collection contains more than one elements equal to the specified object, just the first occurrence
        // will be removed
        List<String> list = new ArrayList<>(Arrays.asList(STR_0, STR_1, STR_2, STR_0));
        Assert.assertTrue(list.remove(STR_0));
        Assert.assertTrue(list.equals(Arrays.asList(STR_1, STR_2, STR_0)));
    }

    @Test
    public void test_removeAll() throws Exception {
        // if the collection contains more than one elements equal to the specified object, all occurrences will be
        // removed
        List<String> list = new ArrayList<>(Arrays.asList(STR_0, STR_1, STR_2, STR_0));
        List<String> listRemoved = new ArrayList<>(Arrays.asList(STR_0, STR_1));
        Assert.assertTrue(list.removeAll(listRemoved));
        Assert.assertTrue(list.equals(Arrays.asList(STR_2)));
    }
}
