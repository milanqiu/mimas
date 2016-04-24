package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.milanqiu.mimas.lang.RunnableWithParam;
import net.milanqiu.mimas.lang.TypeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-27
 * @author Milan Qiu
 */
public class ListsTest {

    @Test
    public void test_StaticConstructors() throws Exception {
        Lists.newArrayList();
        Lists.newArrayList(STR_0, STR_1, STR_2);
        Lists.newArrayList(ImmutableSet.of(STR_0, STR_1, STR_2));
        Lists.newArrayList(ImmutableSet.of(STR_0, STR_1, STR_2).iterator());
        Lists.newArrayListWithCapacity(10);
        Lists.newArrayListWithExpectedSize(100);

        Lists.newLinkedList();
        Lists.newLinkedList(ImmutableSet.of(STR_0, STR_1, STR_2));

        Lists.newCopyOnWriteArrayList();
        Lists.newCopyOnWriteArrayList(ImmutableSet.of(STR_0, STR_1, STR_2));
    }

    @Test
    public void test_partition() throws Exception {
        /*
            List<List> partition(List, int)
            Returns a view of the underlying list, partitioned into chunks of the specified size.
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_1, STR_2, STR_2, STR_2, STR_3);
        List<List<String>> result = Lists.partition(list, 3);
        Assert.assertEquals(3, result.size());
        Assert.assertEquals(ImmutableList.of(STR_0, STR_1, STR_1), result.get(0));
        Assert.assertEquals(ImmutableList.of(STR_2, STR_2, STR_2), result.get(1));
        Assert.assertEquals(ImmutableList.of(STR_3),               result.get(2));
    }

    @Test
    public void test_reverse() throws Exception {
        /*
            List reverse(List)
            Returns a reversed view of the specified list. Note: if the list is immutable, consider
            ImmutableList.reverse() instead.
         */
        List<String> list = ImmutableList.of(STR_0, STR_1, STR_2, STR_2, STR_3);
        Assert.assertEquals(ImmutableList.of(STR_3, STR_2, STR_2, STR_1, STR_0), Lists.reverse(list));
    }

    @Test
    public void test_charactersOf() throws Exception {
        final ImmutableList<Character> list = Lists.charactersOf(TypeUtils.getAllCharValuesString());
        Assert.assertEquals(TypeUtils.CHAR_VALUE_COUNT, list.size());
        TypeUtils.traverseCharValues(new RunnableWithParam.WithChar() {
            int cursor = 0;
            @Override
            public void run(char param) {
                Assert.assertEquals(param, (char) (list.get(cursor++)));
            }
        });
    }
}
