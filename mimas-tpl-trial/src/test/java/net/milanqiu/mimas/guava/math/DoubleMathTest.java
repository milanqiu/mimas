package net.milanqiu.mimas.guava.math;

import com.google.common.math.DoubleMath;
import net.milanqiu.mimas.junit.AssertExt;
import net.milanqiu.mimas.lang.TypeUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Creation Date: 2014-12-29
 * @author Milan Qiu
 */
public class DoubleMathTest {

    @Test
    public void test_isMathematicalInteger() throws Exception {
        /*
            Tests if the input is finite and an exact integer.
         */
        Assert.assertTrue(DoubleMath.isMathematicalInteger(5.0));
        Assert.assertTrue(DoubleMath.isMathematicalInteger(-5.0));
        Assert.assertFalse(DoubleMath.isMathematicalInteger(5.1));
        Assert.assertFalse(DoubleMath.isMathematicalInteger(-5.1));
    }

    @Test
    public void test_roundToInt() throws Exception {
        /*
            Rounds the specified number and casts it to an int, if it fits into an int, failing fast otherwise.
         */
        Assert.assertEquals(5, DoubleMath.roundToInt(5.5, RoundingMode.DOWN));

        AssertExt.assertExceptionThrown(() -> DoubleMath.roundToInt(4294967296.5, RoundingMode.DOWN), ArithmeticException.class);
    }

    @Test
    public void test_roundToLong() throws Exception {
        /*
            Rounds the specified number and casts it to a long, if it fits into a long, failing fast otherwise.
         */
        Assert.assertEquals(6, DoubleMath.roundToLong(5.5, RoundingMode.HALF_EVEN));

        AssertExt.assertExceptionThrown(() -> DoubleMath.roundToLong(18446744073709551616.5, RoundingMode.HALF_EVEN), ArithmeticException.class);
    }

    @Test
    public void test_roundToBigInteger() throws Exception {
        /*
            Rounds the specified number to a BigInteger, if it is finite, failing fast otherwise.
         */
        Assert.assertEquals(new BigInteger("18446744073709551616"), DoubleMath.roundToBigInteger(18446744073709551616.5, RoundingMode.FLOOR));

        AssertExt.assertExceptionThrown(() -> DoubleMath.roundToLong(Double.NaN, RoundingMode.FLOOR), ArithmeticException.class);
    }

    @Test
    public void test_log2() throws Exception {
        /*
            Takes the base-2 logarithm, and rounds to an int using the specified RoundingMode. Faster than Math.log(double).
         */
        Assert.assertEquals(5, DoubleMath.log2(63.4, RoundingMode.DOWN));
    }

    @Test
    public void test_fuzzyEquals() throws Exception {
        Assert.assertFalse(1.0/9 == 0.11111111111111);
        Assert.assertTrue(DoubleMath.fuzzyEquals(1.0/9, 0.11111111111111, TypeUtils.PARTICLE_DOUBLE));
    }

    @Test
    public void test_fuzzyCompare() throws Exception {
        Assert.assertEquals(0, DoubleMath.fuzzyCompare(1.0/9, 0.11111111111111, TypeUtils.PARTICLE_DOUBLE));
    }

    @Test
    public void test_mean() throws Exception {
        Assert.assertEquals(5.125, DoubleMath.mean(1, 2, 3, 4, 5, 6, 9, 11), TypeUtils.PARTICLE_DOUBLE);
    }
}
