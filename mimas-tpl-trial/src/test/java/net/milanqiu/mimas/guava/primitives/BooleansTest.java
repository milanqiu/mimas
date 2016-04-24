package net.milanqiu.mimas.guava.primitives;

import com.google.common.primitives.Booleans;
import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2014-12-21
 * @author Milan Qiu
 */
public class BooleansTest {

    @Test
    public void test_countTrue() throws Exception {
        Assert.assertEquals(3, Booleans.countTrue(true, false, true, false, true));
    }
}
