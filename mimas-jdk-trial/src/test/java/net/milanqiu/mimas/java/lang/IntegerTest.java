package net.milanqiu.mimas.java.lang;

import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2014-12-19
 * @author Milan Qiu
 */
public class IntegerTest {

    @Test
    public void test_toString() throws Exception {
        // String toString()
        Assert.assertEquals("2147483647",  Integer.valueOf(Integer.MAX_VALUE).toString());
        Assert.assertEquals("-2147483648", Integer.valueOf(Integer.MIN_VALUE).toString());

        // String toString(int i)
        Assert.assertEquals("2147483647",  Integer.toString(Integer.MAX_VALUE));
        Assert.assertEquals("-2147483648", Integer.toString(Integer.MIN_VALUE));

        // String toString(int i, int radix)
        Assert.assertEquals("2147483647",  Integer.toString(Integer.MAX_VALUE, 10));
        Assert.assertEquals("-2147483648", Integer.toString(Integer.MIN_VALUE, 10));
        Assert.assertEquals("7fffffff",    Integer.toString(Integer.MAX_VALUE, 16));
        Assert.assertEquals("-80000000",   Integer.toString(Integer.MIN_VALUE, 16));
        Assert.assertEquals("-1",          Integer.toString(-1, 16));
        Assert.assertEquals("1111111111111111111111111111111",   Integer.toString(Integer.MAX_VALUE, 2));
        Assert.assertEquals("-10000000000000000000000000000000", Integer.toString(Integer.MIN_VALUE, 2));
        Assert.assertEquals("-1",                                Integer.toString(-1, 2));
    }

    @Test
    public void test_toUnsignedString() throws Exception {
        // String toUnsignedString(int i)
        Assert.assertEquals("2147483647", Integer.toUnsignedString(Integer.MAX_VALUE));
        Assert.assertEquals("2147483648", Integer.toUnsignedString(Integer.MIN_VALUE));

        // String toUnsignedString(int i, int radix)
        Assert.assertEquals("2147483647", Integer.toUnsignedString(Integer.MAX_VALUE, 10));
        Assert.assertEquals("2147483648", Integer.toUnsignedString(Integer.MIN_VALUE, 10));
        Assert.assertEquals("7fffffff",   Integer.toUnsignedString(Integer.MAX_VALUE, 16));
        Assert.assertEquals("80000000",   Integer.toUnsignedString(Integer.MIN_VALUE, 16));
        Assert.assertEquals("ffffffff",   Integer.toUnsignedString(-1, 16));
        Assert.assertEquals("1111111111111111111111111111111",  Integer.toUnsignedString(Integer.MAX_VALUE, 2));
        Assert.assertEquals("10000000000000000000000000000000", Integer.toUnsignedString(Integer.MIN_VALUE, 2));
        Assert.assertEquals("11111111111111111111111111111111", Integer.toUnsignedString(-1, 2));
    }

    @Test
    public void test_toHexString() throws Exception {
        Assert.assertEquals("7fffffff", Integer.toHexString(Integer.MAX_VALUE));
        Assert.assertEquals("80000000", Integer.toHexString(Integer.MIN_VALUE));
        Assert.assertEquals("ffffffff", Integer.toHexString(-1));
    }

    @Test
    public void test_toOctalString() throws Exception {
        Assert.assertEquals("17777777777", Integer.toOctalString(Integer.MAX_VALUE));
        Assert.assertEquals("20000000000", Integer.toOctalString(Integer.MIN_VALUE));
        Assert.assertEquals("37777777777", Integer.toOctalString(-1));
    }

    @Test
    public void test_toBinaryString() throws Exception {
        Assert.assertEquals("1111111111111111111111111111111",  Integer.toBinaryString(Integer.MAX_VALUE));
        Assert.assertEquals("10000000000000000000000000000000", Integer.toBinaryString(Integer.MIN_VALUE));
        Assert.assertEquals("11111111111111111111111111111111", Integer.toBinaryString(-1));
    }

    @Test
    public void test_parseInt() throws Exception {
        // int parseInt(String s)
        Assert.assertEquals(Integer.MAX_VALUE, Integer.parseInt("2147483647"));
        Assert.assertEquals(Integer.MIN_VALUE, Integer.parseInt("-2147483648"));

        // int parseInt(String s, int radix)
        Assert.assertEquals(Integer.MAX_VALUE, Integer.parseInt("2147483647", 10));
        Assert.assertEquals(Integer.MIN_VALUE, Integer.parseInt("-2147483648", 10));
        Assert.assertEquals(Integer.MAX_VALUE, Integer.parseInt("7fffffff", 16));
        Assert.assertEquals(Integer.MIN_VALUE, Integer.parseInt("-80000000", 16));
        Assert.assertEquals(-1,                Integer.parseInt("-1", 16));
        Assert.assertEquals(Integer.MAX_VALUE, Integer.parseInt("1111111111111111111111111111111", 2));
        Assert.assertEquals(Integer.MIN_VALUE, Integer.parseInt("-10000000000000000000000000000000", 2));
        Assert.assertEquals(-1,                Integer.parseInt("-1", 2));
    }

    @Test
    public void test_parseUnsignedInt() throws Exception {
        // int parseUnsignedInt(String s)
        Assert.assertEquals(Integer.MAX_VALUE, Integer.parseUnsignedInt("2147483647"));
        Assert.assertEquals(Integer.MIN_VALUE, Integer.parseUnsignedInt("2147483648"));

        // int parseUnsignedInt(String s, int radix)
        Assert.assertEquals(Integer.MAX_VALUE, Integer.parseUnsignedInt("2147483647", 10));
        Assert.assertEquals(Integer.MIN_VALUE, Integer.parseUnsignedInt("2147483648", 10));
        Assert.assertEquals(Integer.MAX_VALUE, Integer.parseUnsignedInt("7fffffff", 16));
        Assert.assertEquals(Integer.MIN_VALUE, Integer.parseUnsignedInt("80000000", 16));
        Assert.assertEquals(-1,                Integer.parseUnsignedInt("ffffffff", 16));
        Assert.assertEquals(Integer.MAX_VALUE, Integer.parseUnsignedInt("1111111111111111111111111111111", 2));
        Assert.assertEquals(Integer.MIN_VALUE, Integer.parseUnsignedInt("10000000000000000000000000000000", 2));
        Assert.assertEquals(-1,                Integer.parseUnsignedInt("11111111111111111111111111111111", 2));
    }

    @Test
    public void test_byteValue() throws Exception {
        Assert.assertEquals((byte)0xFF, Integer.valueOf(Integer.MAX_VALUE).byteValue());
        Assert.assertEquals((byte)0,    Integer.valueOf(Integer.MIN_VALUE).byteValue());
    }

    @Test
    public void test_shortValue() throws Exception {
        Assert.assertEquals((short)0xFFFF, Integer.valueOf(Integer.MAX_VALUE).shortValue());
        Assert.assertEquals((short)0,      Integer.valueOf(Integer.MIN_VALUE).shortValue());
    }

    @Test
    public void test_decode() throws Exception {
        Assert.assertEquals(Integer.valueOf(Integer.MAX_VALUE), Integer.decode("2147483647"));
        Assert.assertEquals(Integer.valueOf(Integer.MIN_VALUE), Integer.decode("-2147483648"));
        Assert.assertEquals(Integer.valueOf(Integer.MAX_VALUE), Integer.decode("0x7fffffff"));
        Assert.assertEquals(Integer.valueOf(Integer.MIN_VALUE), Integer.decode("-0x80000000"));
        Assert.assertEquals(Integer.valueOf(Integer.MAX_VALUE), Integer.decode("#7fffffff"));
        Assert.assertEquals(Integer.valueOf(Integer.MIN_VALUE), Integer.decode("-#80000000"));
        Assert.assertEquals(Integer.valueOf(Integer.MAX_VALUE), Integer.decode("017777777777"));
        Assert.assertEquals(Integer.valueOf(Integer.MIN_VALUE), Integer.decode("-020000000000"));
    }

    @Test
    public void test_highestOneBit() throws Exception {
        Assert.assertEquals(0b1000000000, Integer.highestOneBit(0b1000100101));
        Assert.assertEquals(0x40000000,   Integer.highestOneBit(Integer.MAX_VALUE));
        Assert.assertEquals(0x80000000,   Integer.highestOneBit(Integer.MIN_VALUE));
        Assert.assertEquals(0,            Integer.highestOneBit(0));
    }

    @Test
    public void test_lowestOneBit() throws Exception {
        Assert.assertEquals(0b1,        Integer.lowestOneBit(0b1000100101));
        Assert.assertEquals(0b100000,   Integer.lowestOneBit(0b1000100000));
        Assert.assertEquals(0x1,        Integer.lowestOneBit(Integer.MAX_VALUE));
        Assert.assertEquals(0x80000000, Integer.lowestOneBit(Integer.MIN_VALUE));
        Assert.assertEquals(0,          Integer.lowestOneBit(0));
    }

    @Test
    public void test_numberOfLeadingZeros() throws Exception {
        Assert.assertEquals(22, Integer.numberOfLeadingZeros(0b1000100100));
        Assert.assertEquals(1,  Integer.numberOfLeadingZeros(Integer.MAX_VALUE));
        Assert.assertEquals(0,  Integer.numberOfLeadingZeros(Integer.MIN_VALUE));
        Assert.assertEquals(32, Integer.numberOfLeadingZeros(0));
    }

    @Test
    public void test_numberOfTrailingZeros() throws Exception {
        Assert.assertEquals(2,  Integer.numberOfTrailingZeros(0b1000100100));
        Assert.assertEquals(0,  Integer.numberOfTrailingZeros(Integer.MAX_VALUE));
        Assert.assertEquals(31, Integer.numberOfTrailingZeros(Integer.MIN_VALUE));
        Assert.assertEquals(32, Integer.numberOfTrailingZeros(0));
    }

    @Test
    public void test_bitCount() throws Exception {
        Assert.assertEquals(3,  Integer.bitCount(0b1000100100));
        Assert.assertEquals(31, Integer.bitCount(Integer.MAX_VALUE));
        Assert.assertEquals(1,  Integer.bitCount(Integer.MIN_VALUE));
        Assert.assertEquals(0,  Integer.bitCount(0));
    }

    @Test
    public void test_rotateLeft() throws Exception {
        Assert.assertEquals(0b10001001000,   Integer.rotateLeft(0b1000100100, 1));
        Assert.assertEquals(0b100010010000,  Integer.rotateLeft(0b1000100100, 2));
        Assert.assertEquals(0b1000100100000, Integer.rotateLeft(0b1000100100, 3));

        Assert.assertEquals(-2, Integer.rotateLeft(Integer.MAX_VALUE, 1));
        Assert.assertEquals(-3, Integer.rotateLeft(Integer.MAX_VALUE, 2));
        Assert.assertEquals(-5, Integer.rotateLeft(Integer.MAX_VALUE, 3));

        Assert.assertEquals(1, Integer.rotateLeft(Integer.MIN_VALUE, 1));
        Assert.assertEquals(2, Integer.rotateLeft(Integer.MIN_VALUE, 2));
        Assert.assertEquals(4, Integer.rotateLeft(Integer.MIN_VALUE, 3));

        Assert.assertEquals(0, Integer.rotateLeft(0, 1));
        Assert.assertEquals(0, Integer.rotateLeft(0, 2));
        Assert.assertEquals(0, Integer.rotateLeft(0, 3));
    }

    @Test
    public void test_rotateRight() throws Exception {
        Assert.assertEquals(0b100010010, Integer.rotateRight(0b1000100100, 1));
        Assert.assertEquals(0b10001001,  Integer.rotateRight(0b1000100100, 2));

        Assert.assertEquals(0xbfffffff, Integer.rotateRight(Integer.MAX_VALUE, 1));
        Assert.assertEquals(0xdfffffff, Integer.rotateRight(Integer.MAX_VALUE, 2));

        Assert.assertEquals(0x40000000, Integer.rotateRight(Integer.MIN_VALUE, 1));
        Assert.assertEquals(0x20000000, Integer.rotateRight(Integer.MIN_VALUE, 2));

        Assert.assertEquals(0, Integer.rotateRight(0, 1));
        Assert.assertEquals(0, Integer.rotateRight(0, 2));
    }

    @Test
    public void test_reverse() throws Exception {
        Assert.assertEquals(0b00100100_01000000_00000000_00000000, Integer.reverse(0b1000100100));
        Assert.assertEquals(-2, Integer.reverse(Integer.MAX_VALUE));
        Assert.assertEquals(1,  Integer.reverse(Integer.MIN_VALUE));
        Assert.assertEquals(0,  Integer.reverse(0));
    }

    @Test
    public void test_signum() throws Exception {
        Assert.assertEquals(1,  Integer.signum(0b1000100100));
        Assert.assertEquals(1,  Integer.signum(Integer.MAX_VALUE));
        Assert.assertEquals(-1, Integer.signum(Integer.MIN_VALUE));
        Assert.assertEquals(0,  Integer.signum(0));
    }

    @Test
    public void test_reverseBytes() throws Exception {
        Assert.assertEquals(0x78563412, Integer.reverseBytes(0x12345678));
        Assert.assertEquals(0xffffff7f, Integer.reverseBytes(Integer.MAX_VALUE));
        Assert.assertEquals(0x80,       Integer.reverseBytes(Integer.MIN_VALUE));
        Assert.assertEquals(0,          Integer.reverseBytes(0));
    }
}
