package net.milanqiu.mimas.guava.primitives;

import com.google.common.primitives.UnsignedInts;
import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2014-12-21
 * @author Milan Qiu
 */
public class UnsignedIntsTest {

    @Test
    public void test_parseUnsignedInt() throws Exception {
        /*
            int UnsignedInts.parseUnsignedInt(String)
            Parses an unsigned value from a string in base 10.
         */
        Assert.assertEquals(-1, UnsignedInts.parseUnsignedInt("4294967295"));

        /*
            int UnsignedInts.parseUnsignedInt(String string, int radix)
            Parses an unsigned value from a string in the specified base.
         */
        Assert.assertEquals(-1, UnsignedInts.parseUnsignedInt("ffffffff", 16));
    }

    @Test
    public void test_toString() throws Exception {
        /*
            String UnsignedInts.toString(int)
            Returns a string representation of the unsigned value in base 10.
         */
        Assert.assertEquals("4294967295", UnsignedInts.toString(-1));

        /*
            String UnsignedInts.toString(int value, int radix)
            Returns a string representation of the unsigned value in the specified base.
         */
        Assert.assertEquals("ffffffff", UnsignedInts.toString(-1, 16));
    }
}
