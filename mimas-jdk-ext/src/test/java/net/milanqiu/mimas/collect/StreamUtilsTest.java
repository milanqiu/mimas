package net.milanqiu.mimas.collect;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2016-02-24
 * @author Milan Qiu
 */
public class StreamUtilsTest {

    @Test
    public void test_streamOf() throws Exception {
        Iterable<Object> itr = Arrays.asList(OBJ_0, OBJ_1, OBJ_2);
        Assert.assertArrayEquals(new Object[] {OBJ_0, OBJ_1, OBJ_2}, StreamUtils.streamOf(itr).toArray());
    }

    @Test
    public void test_parallelStreamOf() throws Exception {
        Iterable<Object> itr = Arrays.asList(OBJ_0, OBJ_1, OBJ_2);
        Assert.assertArrayEquals(new Object[] {OBJ_0, OBJ_1, OBJ_2}, StreamUtils.parallelStreamOf(itr).toArray());
    }
}
