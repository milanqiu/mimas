package net.milanqiu.mimas.lang;

import java.util.Arrays;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-25
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
        Assert.assertTrue(LangUtils.isDefault(0.0F));
        Assert.assertTrue(LangUtils.isDefault(0.0D));
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
    public void test_hasDefaultToString() throws Exception {
        Assert.assertTrue(LangUtils.hasDefaultToString(new Object()));
        Assert.assertTrue(LangUtils.hasDefaultToString(this));
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
        Assert.assertEquals(65536, count);
        Assert.assertEquals(2147450880, sum);
        Assert.assertEquals(Character.MAX_VALUE-Character.MIN_VALUE+1, count);
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
        Assert.assertEquals(256, count);
        Assert.assertEquals(-128, sum);
        Assert.assertEquals(Byte.MAX_VALUE-Byte.MIN_VALUE+1, count);
        Assert.assertEquals((long)count*(Byte.MAX_VALUE+Byte.MIN_VALUE)/2, sum);
    }

    @Test
    public void test_traverseUnsignedByteValues() throws Exception {
        cursor = 0;
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
        Assert.assertEquals(256, count);
        Assert.assertEquals(32640, sum);
        Assert.assertEquals(255-0+1, count);
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
        Assert.assertEquals(65536, count);
        Assert.assertEquals(-32768, sum);
        Assert.assertEquals(Short.MAX_VALUE-Short.MIN_VALUE+1, count);
        Assert.assertEquals((long)count*(Short.MAX_VALUE+Short.MIN_VALUE)/2, sum);
    }

    @Test
    public void test_traverseUnsignedShortValues() throws Exception {
        cursor = 0;
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
        Assert.assertEquals(65536, count);
        Assert.assertEquals(2147450880, sum);
        Assert.assertEquals(65535-0+1, count);
        Assert.assertEquals((long)count*(65535+0)/2, sum);
    }
}
