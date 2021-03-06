package net.milanqiu.mimas.db;

import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

/**
 * Creation Date: 2016-05-08
 * @author Milan Qiu
 */
public class SqlUtilsTest {

    @Test
    public void test_parameterizedInsert() throws Exception {
        // String parameterizedInsert(String tableName, String... fieldNames)
        Assert.assertEquals("INSERT INTO table (field1, field2) VALUES (?, ?)", SqlUtils.parameterizedInsert("table", "field1", "field2"));
        Assert.assertEquals("INSERT INTO table (field1) VALUES (?)",            SqlUtils.parameterizedInsert("table", "field1"));

        AssertExt.assertExceptionThrown(() -> SqlUtils.parameterizedInsert("table"), IllegalArgumentException.class);

        // String parameterizedInsert(String tableName, Collection<String> fieldNames)
        Assert.assertEquals("INSERT INTO table (field1, field2) VALUES (?, ?)", SqlUtils.parameterizedInsert("table", Arrays.asList("field1", "field2")));
        Assert.assertEquals("INSERT INTO table (field1) VALUES (?)",            SqlUtils.parameterizedInsert("table", Collections.singletonList("field1")));

        AssertExt.assertExceptionThrown(() -> SqlUtils.parameterizedInsert("table", Collections.emptyList()), IllegalArgumentException.class);
    }

    @Test
    public void test_parameterizedUpdate() throws Exception {
        // String parameterizedUpdate(String tableName, String filterFieldName, String... updatedFieldNames)
        Assert.assertEquals("UPDATE table SET field1 = ?, field2 = ? WHERE filter_field = ?", SqlUtils.parameterizedUpdate("table", "filter_field", "field1", "field2"));
        Assert.assertEquals("UPDATE table SET field1 = ? WHERE filter_field = ?",             SqlUtils.parameterizedUpdate("table", "filter_field", "field1"));

        AssertExt.assertExceptionThrown(() -> SqlUtils.parameterizedUpdate("table", "filter_field"), IllegalArgumentException.class);

        // String parameterizedUpdate(String tableName, String filterFieldName, Collection<String> updatedFieldNames)
        Assert.assertEquals("UPDATE table SET field1 = ?, field2 = ? WHERE filter_field = ?", SqlUtils.parameterizedUpdate("table", "filter_field", Arrays.asList("field1", "field2")));
        Assert.assertEquals("UPDATE table SET field1 = ? WHERE filter_field = ?",             SqlUtils.parameterizedUpdate("table", "filter_field", Collections.singletonList("field1")));

        AssertExt.assertExceptionThrown(() -> SqlUtils.parameterizedUpdate("table", "filter_field", Collections.emptyList()), IllegalArgumentException.class);

        // String parameterizedUpdate(String tableName, Collection<String> filterFieldNames, Collection<String> updatedFieldNames)
        Assert.assertEquals("UPDATE table SET field1 = ?, field2 = ? WHERE filter_field1 = ? AND filter_field2 = ?",
                SqlUtils.parameterizedUpdate("table", Arrays.asList("filter_field1", "filter_field2"), Arrays.asList("field1", "field2")));
        Assert.assertEquals("UPDATE table SET field1 = ? WHERE filter_field1 = ?",
                SqlUtils.parameterizedUpdate("table", Collections.singletonList("filter_field1"), Collections.singletonList("field1")));

        AssertExt.assertExceptionThrown(() -> SqlUtils.parameterizedUpdate("table", Collections.singletonList("filter_field1"), Collections.emptyList()), IllegalArgumentException.class);
        AssertExt.assertExceptionThrown(() -> SqlUtils.parameterizedUpdate("table", Collections.emptyList(), Collections.singletonList("field1")), IllegalArgumentException.class);
    }

    @Test
    public void test_parameterizedDelete() throws Exception {
        // String parameterizedDelete(String tableName, String filterFieldName)
        Assert.assertEquals("DELETE FROM table WHERE filter_field = ?", SqlUtils.parameterizedDelete("table", "filter_field"));

        // String parameterizedDelete(String tableName, String... filterFieldNames)
        Assert.assertEquals("DELETE FROM table WHERE filter_field1 = ? AND filter_field2 = ?",
                SqlUtils.parameterizedDelete("table", "filter_field1", "filter_field2"));

        AssertExt.assertExceptionThrown(() -> SqlUtils.parameterizedDelete("table"), IllegalArgumentException.class);

        // String parameterizedDelete(String tableName, Collection<String> filterFieldNames)
        Assert.assertEquals("DELETE FROM table WHERE filter_field1 = ? AND filter_field2 = ?",
                SqlUtils.parameterizedDelete("table", Arrays.asList("filter_field1", "filter_field2")));
        Assert.assertEquals("DELETE FROM table WHERE filter_field1 = ?",
                SqlUtils.parameterizedDelete("table", Collections.singletonList("filter_field1")));

        AssertExt.assertExceptionThrown(() -> SqlUtils.parameterizedDelete("table", Collections.emptyList()), IllegalArgumentException.class);
    }
}
