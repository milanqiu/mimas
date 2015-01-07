package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.ContiguousSet;
import com.google.common.collect.DiscreteDomain;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Range;
import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2014-12-21
 * @author Milan Qiu
 */
public class ContiguousSetTest {

    @Test
    public void test_create() throws Exception {
        ContiguousSet cs = ContiguousSet.create(Range.closed(1, 3), DiscreteDomain.integers());
        Assert.assertEquals(3, cs.size());
        Assert.assertEquals(ImmutableList.of(1, 2, 3), cs.asList());
    }
}
