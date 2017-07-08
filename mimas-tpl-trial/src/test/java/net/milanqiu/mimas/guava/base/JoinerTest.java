package net.milanqiu.mimas.guava.base;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-10-24
 * @author Milan Qiu
 */
public class JoinerTest {

    List<String> list;
    List<String> listWithNull;
    String separator;

    @Before
    public void setUp() throws Exception {
        list = Lists.newArrayList(STR_0, STR_1, STR_2);
        listWithNull = Lists.newArrayList(STR_0, STR_1, null, STR_2);
        separator = ", ";
    }

    @Test
    public void test_on_join() throws Exception {
        Joiner joiner = Joiner.on(separator);
        Assert.assertEquals(STR_0+separator+STR_1+separator+STR_2, joiner.join(list));
        AssertExt.assertExceptionThrown(() -> joiner.join(listWithNull), NullPointerException.class);
    }

    @Test
    public void test_appendTo() throws Exception {
        Joiner joiner = Joiner.on(separator);
        StringBuilder sb = new StringBuilder();
        sb.append(STR_4);
        Assert.assertEquals(STR_4+STR_0+separator+STR_1+separator+STR_2, joiner.appendTo(sb, list).toString());
        AssertExt.assertExceptionThrown(() -> joiner.appendTo(sb, listWithNull), NullPointerException.class);
    }

    @Test
    public void test_skipNulls() throws Exception {
        Joiner joiner = Joiner.on(separator).skipNulls();
        Assert.assertEquals(STR_0+separator+STR_1+separator+STR_2, joiner.join(list));
        Assert.assertEquals(STR_0+separator+STR_1+separator+STR_2, joiner.join(listWithNull));
    }

    @Test
    public void test_useForNull() throws Exception {
        Joiner joiner = Joiner.on(separator).useForNull(STR_4);
        Assert.assertEquals(STR_0+separator+STR_1+separator+STR_2,                 joiner.join(list));
        Assert.assertEquals(STR_0+separator+STR_1+separator+STR_4+separator+STR_2, joiner.join(listWithNull));
    }

    @Test
    public void test_MapJoiner() throws Exception {
        Joiner.MapJoiner mapJoiner = Joiner.on(separator).withKeyValueSeparator("=");
        Assert.assertEquals(STR_0+"="+INT_0+separator+STR_1+"="+INT_1+separator+STR_2+"="+INT_2,
                mapJoiner.join(ImmutableMap.of(STR_0, INT_0, STR_1, INT_1, STR_2, INT_2)));
    }
}
