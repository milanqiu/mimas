package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Table;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-7-26
 * @author Milan Qiu
 */
public class TableTest {

    /*
        Several Table implementations are provided, including:
         - HashBasedTable, which is essentially backed by a HashMap<R, HashMap<C, V>>.
         - TreeBasedTable, which is essentially backed by a TreeMap<R, TreeMap<C, V>>.
         - ImmutableTable, which is essentially backed by an ImmutableMap<R, ImmutableMap<C, V>>. (Note: ImmutableTable
           has optimized implementations for sparser and denser data sets.)
         - ArrayTable, which requires that the complete universe of rows and columns be specified at construction time,
           but is backed by a two-dimensional array to improve speed and memory efficiency when the table is dense.
           ArrayTable works somewhat differently from other implementations; consult the Javadoc for details.
     */

    private Table<Integer, String, Object> table;

    @Before
    public void setUp() throws Exception {
        table = HashBasedTable.create();
        table.put(INT_0, STR_0, OBJ_0);
        table.put(INT_0, STR_1, OBJ_1);
        table.put(INT_1, STR_1, OBJ_2);
        /*
            Then the table will be:
                    STR_0   STR_1
            INT_0   OBJ_0   OBJ_1
            INT_1           OBJ_2
         */
    }

    @Test
    public void test_rowMap() throws Exception {
        /*
            rowMap(), which views a Table<R, C, V> as a Map<R, Map<C, V>>.
         */
        Assert.assertTrue(table.rowMap().equals(ImmutableMap.of(
                INT_0, ImmutableMap.of(STR_0, OBJ_0, STR_1, OBJ_1),
                INT_1, ImmutableMap.of(STR_1, OBJ_2)
        )));
    }

    @Test
    public void test_rowKeySet() throws Exception {
        /*
            Similarly, rowKeySet() returns a Set<R>.
         */
        Assert.assertTrue(table.rowKeySet().equals(ImmutableSet.of(INT_0, INT_1)));
    }

    @Test
    public void test_row() throws Exception {
        /*
            row(r) returns a non-null Map<C, V>. Writes to the Map will write through to the underlying Table.
         */
        Assert.assertTrue(table.row(INT_0).equals(ImmutableMap.of(STR_0, OBJ_0, STR_1, OBJ_1)));
        Assert.assertTrue(table.row(INT_1).equals(ImmutableMap.of(STR_1, OBJ_2)));
    }

    @Test
    public void test_columnMap() throws Exception {
        Assert.assertTrue(table.columnMap().equals(ImmutableMap.of(
                STR_0, ImmutableMap.of(INT_0, OBJ_0),
                STR_1, ImmutableMap.of(INT_0, OBJ_1, INT_1, OBJ_2)
        )));
    }

    @Test
    public void test_columnKeySet() throws Exception {
        Assert.assertTrue(table.columnKeySet().equals(ImmutableSet.of(STR_0, STR_1)));
    }

    @Test
    public void test_column() throws Exception {
        Assert.assertTrue(table.column(STR_0).equals(ImmutableMap.of(INT_0, OBJ_0)));
        Assert.assertTrue(table.column(STR_1).equals(ImmutableMap.of(INT_0, OBJ_1, INT_1, OBJ_2)));
    }

    @Test
    public void test_cellSet() throws Exception {
        /*
            cellSet() returns a view of the Table as a set of Table.Cell<R, C, V>. Cell is much like Map.Entry, but
            distinguishes the row and column keys.
         */
        for (Table.Cell<Integer, String, Object> cell : table.cellSet()) {
            if (cell.getRowKey().equals(INT_0) && cell.getColumnKey().equals(STR_0))
                Assert.assertEquals(OBJ_0, cell.getValue());
            else if (cell.getRowKey().equals(INT_0) && cell.getColumnKey().equals(STR_1))
                Assert.assertEquals(OBJ_1, cell.getValue());
            else if (cell.getRowKey().equals(INT_1) && cell.getColumnKey().equals(STR_1))
                Assert.assertEquals(OBJ_2, cell.getValue());
            else
                DebugUtils.neverGoesHere();
        }
    }
}
