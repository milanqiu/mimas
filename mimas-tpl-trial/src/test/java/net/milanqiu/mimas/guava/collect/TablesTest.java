package net.milanqiu.mimas.guava.collect;

import com.google.common.base.Function;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;
import com.google.common.collect.Tables;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-9-5
 * @author Milan Qiu
 */
public class TablesTest {

    @Test
    public void test_transpose() throws Exception {
        /*
            The transpose(Table<R, C, V>) method allows you to view a Table<R, C, V> as a Table<C, R, V>. For example,
            if you're using a Table to model a weighted digraph, this will let you view the graph with all the edges
            reversed.
         */
        Table<Integer, String, Object> table = HashBasedTable.create();
        table.put(INT_0, STR_0, OBJ_0);
        table.put(INT_0, STR_1, OBJ_1);
        table.put(INT_1, STR_1, OBJ_2);
        /*
            Then the table will be:
                    STR_0   STR_1
            INT_0   OBJ_0   OBJ_1
            INT_1           OBJ_2
         */

        Table<String, Integer, Object> transposedTable = Tables.transpose(table);
        Assert.assertTrue(transposedTable.rowMap().equals(ImmutableMap.of(
                STR_0, ImmutableMap.of(INT_0, OBJ_0),
                STR_1, ImmutableMap.of(INT_0, OBJ_1, INT_1, OBJ_2)
        )));
    }

    @Test
    public void test_transformValues() throws Exception {
        Table<Integer, String, Object> from = HashBasedTable.create();
        from.put(INT_0, STR_0, OBJ_0);
        from.put(INT_0, STR_1, OBJ_1);
        from.put(INT_1, STR_1, OBJ_2);

        Table<Integer, String, String> to = Tables.transformValues(from, new Function<Object, String>() {
            @Override
            public String apply(Object o) {
                return o.toString();
            }
        });
        Assert.assertTrue(to.rowMap().equals(ImmutableMap.of(
                INT_0, ImmutableMap.of(STR_0, STR_OF_OBJ_0, STR_1, STR_OF_OBJ_1),
                INT_1, ImmutableMap.of(STR_1, STR_OF_OBJ_2)
        )));
    }
}
