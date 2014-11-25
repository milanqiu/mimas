package net.milanqiu.mimas.lang;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-7-25
 * @author Milan Qiu
 */
public class LangUtilsTest {

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
        Assert.assertTrue(LangUtils.isDefault(false));
        Assert.assertTrue(LangUtils.isDefault('\u0000'));
        Assert.assertTrue(LangUtils.isDefault((byte) 0));
        Assert.assertTrue(LangUtils.isDefault((short) 0));
        Assert.assertTrue(LangUtils.isDefault(0));
        Assert.assertTrue(LangUtils.isDefault(0L));
        Assert.assertTrue(LangUtils.isDefault(0.0f));
        Assert.assertTrue(LangUtils.isDefault(0.0d));
        Assert.assertTrue(LangUtils.isDefault(null));

        Assert.assertTrue(LangUtils.isDefault(bool));
        Assert.assertTrue(LangUtils.isDefault(ch));
        Assert.assertTrue(LangUtils.isDefault(b));
        Assert.assertTrue(LangUtils.isDefault(s));
        Assert.assertTrue(LangUtils.isDefault(n));
        Assert.assertTrue(LangUtils.isDefault(l));
        Assert.assertTrue(LangUtils.isDefault(f));
        Assert.assertTrue(LangUtils.isDefault(d));
        Assert.assertTrue(LangUtils.isDefault(str));
        Assert.assertTrue(LangUtils.isDefault(obj));
    }

    @Test
    public void test_OBJECT_COMPARATOR() throws Exception {
        Assert.assertEquals(0, LangUtils.OBJECT_COMPARATOR.compare(OBJ_0, OBJ_0));
        Assert.assertTrue(Arrays.asList(-1, 1).contains(LangUtils.OBJECT_COMPARATOR.compare(OBJ_0, OBJ_1)));

        Assert.assertEquals(0, LangUtils.OBJECT_COMPARATOR.compare(10, 10));
        Assert.assertEquals(1, LangUtils.OBJECT_COMPARATOR.compare(10, 9));
        Assert.assertEquals(-1, LangUtils.OBJECT_COMPARATOR.compare(10, 11));

        // null test
        Assert.assertEquals(1, LangUtils.OBJECT_COMPARATOR.compare(OBJ_0, null));
        Assert.assertEquals(-1, LangUtils.OBJECT_COMPARATOR.compare(null, OBJ_0));
        Assert.assertEquals(0, LangUtils.OBJECT_COMPARATOR.compare(null, null));
    }

    @Test
    public void test_ValueCountSeries() throws Exception {
        Assert.assertEquals(LangUtils.CHAR_VALUE_COUNT,           65536);
        Assert.assertEquals(LangUtils.BYTE_VALUE_COUNT,           256);
        Assert.assertEquals(LangUtils.UNSIGNED_BYTE_VALUE_COUNT,  256);
        Assert.assertEquals(LangUtils.SHORT_VALUE_COUNT,          65536);
        Assert.assertEquals(LangUtils.UNSIGNED_SHORT_VALUE_COUNT, 65536);
    }

    @Test
    public void test_getAllCharValues() throws Exception {
        char[] charArr = LangUtils.getAllCharValues();
        Assert.assertEquals(LangUtils.CHAR_VALUE_COUNT, charArr.length);
        for (int i = 0; i < LangUtils.CHAR_VALUE_COUNT; i++)
            Assert.assertEquals((char)(i+Character.MIN_VALUE), charArr[i]);
    }

    @Test
    public void test_getAllByteValues() throws Exception {
        byte[] byteArr = LangUtils.getAllByteValues();
        Assert.assertEquals(LangUtils.BYTE_VALUE_COUNT, byteArr.length);
        for (int i = 0; i < LangUtils.BYTE_VALUE_COUNT; i++)
            Assert.assertEquals((byte)(i+Byte.MIN_VALUE), byteArr[i]);
    }

    @Test
    public void test_getAllUnsignedByteValues() throws Exception {
        int[] ubyteArr = LangUtils.getAllUnsignedByteValues();
        Assert.assertEquals(LangUtils.UNSIGNED_BYTE_VALUE_COUNT, ubyteArr.length);
        for (int i = 0; i < LangUtils.UNSIGNED_BYTE_VALUE_COUNT; i++)
            Assert.assertEquals(i+LangUtils.UNSIGNED_BYTE_MIN_VALUE, ubyteArr[i]);
    }

    @Test
    public void test_getAllShortValues() throws Exception {
        short[] shortArr = LangUtils.getAllShortValues();
        Assert.assertEquals(LangUtils.SHORT_VALUE_COUNT, shortArr.length);
        for (int i = 0; i < LangUtils.SHORT_VALUE_COUNT; i++)
            Assert.assertEquals((short)(i+Short.MIN_VALUE), shortArr[i]);
    }

    @Test
    public void test_getAllUnsignedShortValues() throws Exception {
        int[] ushortArr = LangUtils.getAllUnsignedShortValues();
        Assert.assertEquals(LangUtils.UNSIGNED_SHORT_VALUE_COUNT, ushortArr.length);
        for (int i = 0; i < LangUtils.UNSIGNED_SHORT_VALUE_COUNT; i++)
            Assert.assertEquals(i+LangUtils.UNSIGNED_SHORT_MIN_VALUE, ushortArr[i]);
    }

    private int cursor;
    private int count;
    private long sum;

    @Test
    public void test_traverseCharValues() throws Exception {
        cursor = Character.MIN_VALUE;
        count = 0;
        sum = 0;
        LangUtils.traverseCharValues(new RunnableWithParam.WithChar() {
            @Override
            public void run(char param) {
                Assert.assertEquals(cursor++, param);
                count++;
                sum += param;
            }
        });
        Assert.assertEquals(Character.MAX_VALUE+1, cursor);
        Assert.assertEquals(LangUtils.CHAR_VALUE_COUNT, count);
        Assert.assertEquals(2147450880, sum);
        Assert.assertEquals((long)count*(Character.MAX_VALUE+Character.MIN_VALUE)/2, sum);
    }

    @Test
    public void test_traverseByteValues() throws Exception {
        cursor = Byte.MIN_VALUE;
        count = 0;
        sum = 0;
        LangUtils.traverseByteValues(new RunnableWithParam.WithByte() {
            @Override
            public void run(byte param) {
                Assert.assertEquals(cursor++, param);
                count++;
                sum += param;
            }
        });
        Assert.assertEquals(Byte.MAX_VALUE+1, cursor);
        Assert.assertEquals(LangUtils.BYTE_VALUE_COUNT, count);
        Assert.assertEquals(-128, sum);
        Assert.assertEquals((long)count*(Byte.MAX_VALUE+Byte.MIN_VALUE)/2, sum);
    }

    @Test
    public void test_traverseUnsignedByteValues() throws Exception {
        cursor = LangUtils.UNSIGNED_BYTE_MIN_VALUE;
        count = 0;
        sum = 0;
        LangUtils.traverseUnsignedByteValues(new RunnableWithParam.WithInt() {
            @Override
            public void run(int param) {
                Assert.assertEquals(cursor++, param);
                count++;
                sum += param;
            }
        });
        Assert.assertEquals(LangUtils.UNSIGNED_BYTE_MAX_VALUE+1, cursor);
        Assert.assertEquals(LangUtils.UNSIGNED_BYTE_VALUE_COUNT, count);
        Assert.assertEquals(32640, sum);
        Assert.assertEquals((long)count*(255+0)/2, sum);
    }

    @Test
    public void test_traverseShortValues() throws Exception {
        cursor = Short.MIN_VALUE;
        count = 0;
        sum = 0;
        LangUtils.traverseShortValues(new RunnableWithParam.WithShort() {
            @Override
            public void run(short param) {
                Assert.assertEquals(cursor++, param);
                count++;
                sum += param;
            }
        });
        Assert.assertEquals(Short.MAX_VALUE+1, cursor);
        Assert.assertEquals(LangUtils.SHORT_VALUE_COUNT, count);
        Assert.assertEquals(-32768, sum);
        Assert.assertEquals((long)count*(Short.MAX_VALUE+Short.MIN_VALUE)/2, sum);
    }

    @Test
    public void test_traverseUnsignedShortValues() throws Exception {
        cursor = LangUtils.UNSIGNED_SHORT_MIN_VALUE;
        count = 0;
        sum = 0;
        LangUtils.traverseUnsignedShortValues(new RunnableWithParam.WithInt() {
            @Override
            public void run(int param) {
                Assert.assertEquals(cursor++, param);
                count++;
                sum += param;
            }
        });
        Assert.assertEquals(LangUtils.UNSIGNED_SHORT_MAX_VALUE+1, cursor);
        Assert.assertEquals(LangUtils.UNSIGNED_SHORT_VALUE_COUNT, count);
        Assert.assertEquals(2147450880, sum);
        Assert.assertEquals((long)count*(65535+0)/2, sum);
    }
}
