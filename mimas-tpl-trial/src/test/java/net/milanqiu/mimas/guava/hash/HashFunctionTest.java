package net.milanqiu.mimas.guava.hash;

import com.google.common.hash.HashFunction;
import com.google.common.hash.Hashing;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-12-29
 * @author Milan Qiu
 */
public class HashFunctionTest {

    private HashFunction md5;

    @Before
    public void setUp() throws Exception {
        md5 = Hashing.md5();
    }

    @Test
    public void test_hashInt() throws Exception {
        Assert.assertEquals(md5.hashInt(INT_0), md5.newHasher().putInt(INT_0).hash());
    }

    @Test
    public void test_hashString() throws Exception {
        Assert.assertEquals(md5.hashString(STR_0, StandardCharsets.UTF_8),
                md5.newHasher().putString(STR_0, StandardCharsets.UTF_8).hash());
    }

    @Test
    public void test_bits() throws Exception {
        Assert.assertEquals(128, md5.bits());
    }
}
