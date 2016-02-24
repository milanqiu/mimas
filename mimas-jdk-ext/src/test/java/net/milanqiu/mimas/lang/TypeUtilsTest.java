package net.milanqiu.mimas.lang;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-25
 * @author Milan Qiu
 */
public class TypeUtilsTest {
    
    private boolean bool;
    private char ch;
    private byte b;
    private short s;
    private int n;
    private long l;
    private float f;
    private double d;
    private String str;
    private Object obj;

    @Test
    public void test_isDefault() throws Exception {
        Assert.assertTrue(TypeUtils.isDefault(false));
        Assert.assertTrue(TypeUtils.isDefault('\u0000'));
        Assert.assertTrue(TypeUtils.isDefault((byte) 0));
        Assert.assertTrue(TypeUtils.isDefault((short) 0));
        Assert.assertTrue(TypeUtils.isDefault(0));
        Assert.assertTrue(TypeUtils.isDefault(0L));
        Assert.assertTrue(TypeUtils.isDefault(0.0f));
        Assert.assertTrue(TypeUtils.isDefault(0.0d));
        Assert.assertTrue(TypeUtils.isDefault(null));

        Assert.assertTrue(TypeUtils.isDefault(bool));
        Assert.assertTrue(TypeUtils.isDefault(ch));
        Assert.assertTrue(TypeUtils.isDefault(b));
        Assert.assertTrue(TypeUtils.isDefault(s));
        Assert.assertTrue(TypeUtils.isDefault(n));
        Assert.assertTrue(TypeUtils.isDefault(l));
        Assert.assertTrue(TypeUtils.isDefault(f));
        Assert.assertTrue(TypeUtils.isDefault(d));
        Assert.assertTrue(TypeUtils.isDefault(str));
        Assert.assertTrue(TypeUtils.isDefault(obj));
    }

    @Test
    public void test_OBJECT_COMPARATOR() throws Exception {
        Assert.assertEquals(0, TypeUtils.OBJECT_COMPARATOR.compare(OBJ_0, OBJ_0));
        Assert.assertTrue(Arrays.asList(-1, 1).contains(TypeUtils.OBJECT_COMPARATOR.compare(OBJ_0, OBJ_1)));

        Assert.assertEquals(0, TypeUtils.OBJECT_COMPARATOR.compare(10, 10));
        Assert.assertEquals(1, TypeUtils.OBJECT_COMPARATOR.compare(10, 9));
        Assert.assertEquals(-1, TypeUtils.OBJECT_COMPARATOR.compare(10, 11));

        // null test
        Assert.assertEquals(1, TypeUtils.OBJECT_COMPARATOR.compare(OBJ_0, null));
        Assert.assertEquals(-1, TypeUtils.OBJECT_COMPARATOR.compare(null, OBJ_0));
        Assert.assertEquals(0, TypeUtils.OBJECT_COMPARATOR.compare(null, null));
    }

    @Test
    public void test_ValueCountSeries() throws Exception {
        Assert.assertEquals(TypeUtils.CHAR_VALUE_COUNT,           65536);
        Assert.assertEquals(TypeUtils.BYTE_VALUE_COUNT,           256);
        Assert.assertEquals(TypeUtils.UNSIGNED_BYTE_VALUE_COUNT,  256);
        Assert.assertEquals(TypeUtils.SHORT_VALUE_COUNT,          65536);
        Assert.assertEquals(TypeUtils.UNSIGNED_SHORT_VALUE_COUNT, 65536);
    }

    @Test
    public void test_getAllCharValues() throws Exception {
        char[] charArr = TypeUtils.getAllCharValues();
        Assert.assertEquals(TypeUtils.CHAR_VALUE_COUNT, charArr.length);
        for (int i = 0; i < TypeUtils.CHAR_VALUE_COUNT; i++)
            Assert.assertEquals((char)(i+Character.MIN_VALUE), charArr[i]);
    }

    @Test
    public void test_getAllByteValues() throws Exception {
        byte[] byteArr = TypeUtils.getAllByteValues();
        Assert.assertEquals(TypeUtils.BYTE_VALUE_COUNT, byteArr.length);
        for (int i = 0; i < TypeUtils.BYTE_VALUE_COUNT; i++)
            Assert.assertEquals((byte)(i+Byte.MIN_VALUE), byteArr[i]);
    }

    @Test
    public void test_getAllUnsignedByteValues() throws Exception {
        int[] ubyteArr = TypeUtils.getAllUnsignedByteValues();
        Assert.assertEquals(TypeUtils.UNSIGNED_BYTE_VALUE_COUNT, ubyteArr.length);
        for (int i = 0; i < TypeUtils.UNSIGNED_BYTE_VALUE_COUNT; i++)
            Assert.assertEquals(i+TypeUtils.UNSIGNED_BYTE_MIN_VALUE, ubyteArr[i]);
    }

    @Test
    public void test_getAllShortValues() throws Exception {
        short[] shortArr = TypeUtils.getAllShortValues();
        Assert.assertEquals(TypeUtils.SHORT_VALUE_COUNT, shortArr.length);
        for (int i = 0; i < TypeUtils.SHORT_VALUE_COUNT; i++)
            Assert.assertEquals((short)(i+Short.MIN_VALUE), shortArr[i]);
    }

    @Test
    public void test_getAllUnsignedShortValues() throws Exception {
        int[] ushortArr = TypeUtils.getAllUnsignedShortValues();
        Assert.assertEquals(TypeUtils.UNSIGNED_SHORT_VALUE_COUNT, ushortArr.length);
        for (int i = 0; i < TypeUtils.UNSIGNED_SHORT_VALUE_COUNT; i++)
            Assert.assertEquals(i+TypeUtils.UNSIGNED_SHORT_MIN_VALUE, ushortArr[i]);
    }

    private int cursor;
    private int count;
    private long sum;

    @Test
    public void test_traverseCharValues() throws Exception {
        cursor = Character.MIN_VALUE;
        count = 0;
        sum = 0;
        TypeUtils.traverseCharValues((value) -> {
            Assert.assertEquals(cursor++, value);
            count++;
            sum += value;
        });
        Assert.assertEquals(Character.MAX_VALUE+1, cursor);
        Assert.assertEquals(TypeUtils.CHAR_VALUE_COUNT, count);
        Assert.assertEquals(2147450880, sum);
        Assert.assertEquals((long)count*(Character.MAX_VALUE+Character.MIN_VALUE)/2, sum);
    }

    @Test
    public void test_traverseByteValues() throws Exception {
        cursor = Byte.MIN_VALUE;
        count = 0;
        sum = 0;
        TypeUtils.traverseByteValues((value) -> {
            Assert.assertEquals(cursor++, value);
            count++;
            sum += value;
        });
        Assert.assertEquals(Byte.MAX_VALUE+1, cursor);
        Assert.assertEquals(TypeUtils.BYTE_VALUE_COUNT, count);
        Assert.assertEquals(-128, sum);
        Assert.assertEquals((long)count*(Byte.MAX_VALUE+Byte.MIN_VALUE)/2, sum);
    }

    @Test
    public void test_traverseUnsignedByteValues() throws Exception {
        cursor = TypeUtils.UNSIGNED_BYTE_MIN_VALUE;
        count = 0;
        sum = 0;
        TypeUtils.traverseUnsignedByteValues((value) -> {
            Assert.assertEquals(cursor++, value);
            count++;
            sum += value;
        });
        Assert.assertEquals(TypeUtils.UNSIGNED_BYTE_MAX_VALUE+1, cursor);
        Assert.assertEquals(TypeUtils.UNSIGNED_BYTE_VALUE_COUNT, count);
        Assert.assertEquals(32640, sum);
        Assert.assertEquals((long)count*(TypeUtils.UNSIGNED_BYTE_MAX_VALUE+TypeUtils.UNSIGNED_BYTE_MIN_VALUE)/2, sum);
    }

    @Test
    public void test_traverseShortValues() throws Exception {
        cursor = Short.MIN_VALUE;
        count = 0;
        sum = 0;
        TypeUtils.traverseShortValues((value) -> {
            Assert.assertEquals(cursor++, value);
            count++;
            sum += value;
        });
        Assert.assertEquals(Short.MAX_VALUE+1, cursor);
        Assert.assertEquals(TypeUtils.SHORT_VALUE_COUNT, count);
        Assert.assertEquals(-32768, sum);
        Assert.assertEquals((long)count*(Short.MAX_VALUE+Short.MIN_VALUE)/2, sum);
    }

    @Test
    public void test_traverseUnsignedShortValues() throws Exception {
        cursor = TypeUtils.UNSIGNED_SHORT_MIN_VALUE;
        count = 0;
        sum = 0;
        TypeUtils.traverseUnsignedShortValues((value) -> {
            Assert.assertEquals(cursor++, value);
            count++;
            sum += value;
        });
        Assert.assertEquals(TypeUtils.UNSIGNED_SHORT_MAX_VALUE+1, cursor);
        Assert.assertEquals(TypeUtils.UNSIGNED_SHORT_VALUE_COUNT, count);
        Assert.assertEquals(2147450880, sum);
        Assert.assertEquals((long)count*(TypeUtils.UNSIGNED_SHORT_MAX_VALUE+TypeUtils.UNSIGNED_SHORT_MIN_VALUE)/2, sum);
    }
}
