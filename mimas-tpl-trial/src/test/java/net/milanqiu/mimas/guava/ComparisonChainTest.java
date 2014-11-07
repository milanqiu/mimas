package net.milanqiu.mimas.guava;

import com.google.common.collect.ComparisonChain;

import java.util.Comparator;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-17
 * @author Milan Qiu
 */
public class ComparisonChainTest {

    @Test
    public void test_ComparisonChain() throws Exception {
        Assert.assertEquals(0,
                ComparisonChain.start()
                        .compare(INT_0, INT_0)
                        .compare(FLOAT_0, FLOAT_0)
                        .compare(STR_0, STR_0)
                        .compare(INT_1, INT_2, new Comparator<Integer>() {
                            @Override
                            public int compare(Integer o1, Integer o2) {
                                return 0;
                            }
                        })
                        .result());

        Assert.assertEquals(1,
                ComparisonChain.start()
                        .compare(INT_0+1, INT_0)
                        .result());

        Assert.assertEquals(-1,
                ComparisonChain.start()
                        .compare(INT_0, INT_0)
                        .compare(INT_1-1, INT_2)
                        .result());
    }
}
