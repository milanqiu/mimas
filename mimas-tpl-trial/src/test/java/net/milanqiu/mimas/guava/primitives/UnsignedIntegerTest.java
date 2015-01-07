package net.milanqiu.mimas.guava.primitives;

import com.google.common.primitives.UnsignedInteger;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Creation Date: 2014-12-21
 * @author Milan Qiu
 */
public class UnsignedIntegerTest {

    @Test
    public void test_plus_minus_times_dividedBy_mod() throws Exception {
        /*
            UnsignedPrim plus(UnsignedPrim), minus, times, dividedBy, mod
            Simple arithmetic operations.
         */
        Assert.assertEquals(UnsignedInteger.valueOf(0xfffffffeL),
                UnsignedInteger.valueOf(0x7fffffffL).plus(UnsignedInteger.valueOf(0x7fffffffL)));

        Assert.assertEquals(UnsignedInteger.valueOf(0xfffffffdL),
                UnsignedInteger.valueOf(0xfffffffeL).minus(UnsignedInteger.valueOf(0x1L)));

        Assert.assertEquals(UnsignedInteger.valueOf(0x80000000L),
                UnsignedInteger.valueOf(0x10000L).times(UnsignedInteger.valueOf(0x8000L)));

        Assert.assertEquals(UnsignedInteger.valueOf(0x10000L),
                UnsignedInteger.valueOf(0x80000000L).dividedBy(UnsignedInteger.valueOf(0x8000L)));

        Assert.assertEquals(UnsignedInteger.valueOf(0x123L),
                UnsignedInteger.valueOf(0x80000123L).mod(UnsignedInteger.valueOf(0x8000L)));
    }

    @Test
    public void test_valueOf() throws Exception {
        /*
            UnsignedPrim valueOf(BigInteger)
            Returns the value from a BigInteger as an UnsignedPrim, or throw an IAE if the specified BigInteger is
            negative or does not fit.
        */
        Assert.assertEquals("4294967295", UnsignedInteger.valueOf(new BigInteger("ffffffff", 16)).toString());
        try {
            UnsignedInteger.valueOf(new BigInteger("ffffffffffffffff", 16));
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
        /*
            UnsignedPrim valueOf(long)
            Returns the value from the long as an UnsignedPrim, or throw an IAE if the specified long is
            negative or does not fit.
         */
        Assert.assertEquals("4294967295", UnsignedInteger.valueOf(0xffffffffL).toString());
        try {
            UnsignedInteger.valueOf(-1);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }
    }

    @Test
    public void test_fromPrimBits() throws Exception {
        /*
            UnsignedPrim fromPrimBits(prim value)
            View the given value as unsigned. For example, UnsignedInteger.fromIntBits(1 << 31) has the value 2^31,
            even though 1 << 31 is negative as an int.
         */
        Assert.assertEquals("4294967295", UnsignedInteger.fromIntBits(-1).toString());
    }

    @Test
    public void test_bigIntegerValue() throws Exception {
        /*
            UnsignedInteger.valueOf(0xffffffffL)
            Get the value of this UnsignedPrim as a BigInteger.
         */
        Assert.assertEquals(new BigInteger("ffffffff", 16), UnsignedInteger.valueOf(0xffffffffL).bigIntegerValue());
    }

    @Test
    public void test_intValue() throws Exception {
        Assert.assertEquals(-1, UnsignedInteger.valueOf(0xffffffffL).intValue());
    }

    @Test
    public void test_longValue() throws Exception {
        Assert.assertEquals(0xffffffffL, UnsignedInteger.valueOf(0xffffffffL).longValue());
    }
}
