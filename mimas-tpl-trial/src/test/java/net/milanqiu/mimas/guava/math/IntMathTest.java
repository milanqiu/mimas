package net.milanqiu.mimas.guava.math;

import com.google.common.math.IntMath;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import org.junit.Assert;
import org.junit.Test;

import java.math.RoundingMode;

/**
 * Creation Date: 2014-12-23
 * @author Milan Qiu
 */
public class IntMathTest {

    @Test
    public void test_checkedAdd() throws Exception {
        try {
            IntMath.checkedAdd(Integer.MAX_VALUE, 1);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ArithmeticException);
        }
    }

    @Test
    public void test_checkedSubtract() throws Exception {
        try {
            IntMath.checkedSubtract(Integer.MIN_VALUE, 1);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ArithmeticException);
        }
    }

    @Test
    public void test_checkedMultiply() throws Exception {
        try {
            IntMath.checkedMultiply(0x10000, 0x10000);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ArithmeticException);
        }
    }

    @Test
    public void test_checkedPow() throws Exception {
        try {
            IntMath.checkedPow(2, 31);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            Assert.assertTrue(e instanceof ArithmeticException);
        }
    }

    @Test
    public void test_divide() throws Exception {
        /*
            Division
         */
        Assert.assertEquals(3, IntMath.divide(5, 2, RoundingMode.CEILING));
        Assert.assertEquals(2, IntMath.divide(5, 2, RoundingMode.HALF_EVEN));
    }

    @Test
    public void test_log2() throws Exception {
        /*
            Base-2 logarithm
         */
        Assert.assertEquals(5, IntMath.log2(63, RoundingMode.DOWN));
    }

    @Test
    public void test_log10() throws Exception {
        /*
            Base-10 logarithm
         */
        Assert.assertEquals(6, IntMath.log10(123456, RoundingMode.UP));
    }

    @Test
    public void test_sqrt() throws Exception {
        /*
            Square root
         */
        Assert.assertEquals(9, IntMath.sqrt(87, RoundingMode.HALF_DOWN));
    }

    @Test
    public void test_gcd() throws Exception {
        /*
            Greatest common divisor
         */
        Assert.assertEquals(12, IntMath.gcd(24, 36));
    }

    @Test
    public void test_mod() throws Exception {
        /*
            Modulus (always nonnegative, -5 mod 3 is 1)
         */
        Assert.assertEquals(2, IntMath.mod(5, 3));
        Assert.assertEquals(1, IntMath.mod(-5, 3));
        Assert.assertEquals(2, 5%3);
        Assert.assertEquals(-2, -5%3);
    }

    @Test
    public void test_pow() throws Exception {
        /*
            Exponentiation (may overflow)
         */
        Assert.assertEquals(256, IntMath.pow(2, 8));
        Assert.assertEquals(Integer.MIN_VALUE, IntMath.pow(2, 31));
        Assert.assertEquals(0, IntMath.pow(2, 32));
    }

    @Test
    public void test_isPowerOfTwo() throws Exception {
        /*
            Power-of-two testing
         */
        Assert.assertTrue(IntMath.isPowerOfTwo(256));
        Assert.assertFalse(IntMath.isPowerOfTwo(257));
    }

    @Test
    public void test_factorial() throws Exception {
        /*
            Factorial (returns MAX_VALUE if input too big)
         */
        Assert.assertEquals(120, IntMath.factorial(5));
        Assert.assertEquals(Integer.MAX_VALUE, IntMath.factorial(100));
    }

    @SuppressWarnings("PointlessArithmeticExpression")
    @Test
    public void test_binomial() throws Exception {
        /*
            Binomial coefficient (returns MAX_VALUE if too big)
         */
        Assert.assertEquals(5*4/2/1, IntMath.binomial(5, 2));
        Assert.assertEquals(20*19*18*17/4/3/2/1, IntMath.binomial(20, 16));
    }

    @Test
    public void test_mean() throws Exception {
        Assert.assertEquals(6, IntMath.mean(4, 8));
        Assert.assertEquals(5, IntMath.mean(4, 7));
    }
}
