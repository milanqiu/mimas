package net.milanqiu.mimas.guava.hash;

import com.google.common.hash.HashCode;
import com.google.common.hash.Hashing;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * Creation Date: 2014-12-29
 * @author Milan Qiu
 */
public class HashCodeTest {

    private HashCode hashCode;

    @Before
    public void setUp() throws Exception {
        // will be "ab56b4d92b40713acc5af89985d4b786"
        hashCode = Hashing.md5().hashString("abcde", StandardCharsets.UTF_8);
    }

    @Test
    public void test_toString() throws Exception {
        Assert.assertEquals("ab56b4d92b40713acc5af89985d4b786", hashCode.toString());
    }

    @Test
    public void test_bits() throws Exception {
        Assert.assertEquals(128, hashCode.bits());
    }

    @Test
    public void test_asInt() throws Exception {
        Assert.assertEquals(0xd9b456ab, hashCode.asInt());
    }

    @Test
    public void test_asLong() throws Exception {
        Assert.assertEquals(0x3a71402bd9b456abL, hashCode.asLong());
    }

    @Test
    public void test_padToLong() throws Exception {
        Assert.assertEquals(0x3a71402bd9b456abL, hashCode.padToLong());
    }

    @Test
    public void test_asBytes() throws Exception {
        Assert.assertArrayEquals(new byte[]{(byte)0xab, (byte)0x56, (byte)0xb4, (byte)0xd9,
                                            (byte)0x2b, (byte)0x40, (byte)0x71, (byte)0x3a,
                                            (byte)0xcc, (byte)0x5a, (byte)0xf8, (byte)0x99,
                                            (byte)0x85, (byte)0xd4, (byte)0xb7, (byte)0x86},
                hashCode.asBytes());
    }
}
