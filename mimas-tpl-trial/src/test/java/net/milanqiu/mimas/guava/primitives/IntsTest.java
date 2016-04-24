package net.milanqiu.mimas.guava.primitives;

import com.google.common.primitives.Ints;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-12-20
 * @author Milan Qiu
 */
public class IntsTest {

    /*
        -----------------------------------------------------------------
        |   Primitive Type  |   Guava Utilities                         |
        -----------------------------------------------------------------
        |   byte            |   Bytes, SignedBytes, UnsignedBytes       |
        |   short           |   Shorts                                  |
        |   int             |   Ints, UnsignedInteger, UnsignedInts     |
        |   long            |   Longs, UnsignedLong, UnsignedLongs      |
        |   float           |   Floats                                  |
        |   double          |   Doubles                                 |
        |   char            |   Chars                                   |
        |   boolean         |   Booleans                                |
        -----------------------------------------------------------------
     */

    /*
        Primitive array utilities
     */

    @Test
    public void test_asList() throws Exception {
        /*
            List<Wrapper> asList(prim... backingArray)
            Wraps a primitive array as a List of the corresponding wrapper type.
            Collection analogue: Arrays.asList
            Sign-independent
         */
        Assert.assertEquals(Arrays.asList(INT_0, INT_1, INT_2), Ints.asList(INT_0, INT_1, INT_2));
    }

    @Test
    public void test_toArray() throws Exception {
        /*
            prim[] toArray(Collection<Wrapper> collection)
            Copies a collection into a new prim[]. This method is as thread-safe as collection.toArray().
            Collection analogue: Collection.toArray()
            Sign-independent
         */
        Assert.assertArrayEquals(new int[]{INT_0, INT_1, INT_2}, Ints.toArray(Arrays.asList(INT_0, INT_1, INT_2)));
    }

    @Test
    public void test_concat() throws Exception {
        /*
            prim[] concat(prim[]... arrays)
            Concatenate several primitive arrays.
            Collection analogue: Iterables.concat
            Sign-independent
         */
        Assert.assertArrayEquals(new int[]{INT_0, INT_1, INT_2, INT_3, INT_4},
                Ints.concat(new int[]{INT_0, INT_1}, new int[]{INT_2}, new int[]{INT_3, INT_4}));
    }

    @Test
    public void test_contains() throws Exception {
        /*
            boolean contains(prim[] array, prim target)
            Determines if the specified element is in the specified array.
            Collection analogue: Collection.contains
            Sign-independent
         */
        Assert.assertTrue(Ints.contains(new int[]{INT_0, INT_1, INT_2}, INT_1));
        Assert.assertFalse(Ints.contains(new int[]{INT_0, INT_1, INT_2}, INT_3));
    }

    @Test
    public void test_indexOf() throws Exception {
        /*
            int indexOf(prim[] array, prim target)
            Finds the index of the first appearance of the value target in array, or returns -1 if no such value exists.
            Collection analogue: List.indexOf
            Sign-independent
         */
        Assert.assertEquals(1, Ints.indexOf(new int[]{INT_0, INT_1, INT_2, INT_1}, INT_1));
        Assert.assertEquals(-1, Ints.indexOf(new int[]{INT_0, INT_1, INT_2, INT_1}, INT_3));

        /*
            int indexOf(int[] array, int[] target)
         */
        Assert.assertEquals(1, Ints.indexOf(new int[]{INT_0, INT_1, INT_2, INT_1}, new int[]{INT_1, INT_2}));
        Assert.assertEquals(-1, Ints.indexOf(new int[]{INT_0, INT_1, INT_2, INT_1}, new int[]{INT_1, INT_3}));
    }

    @Test
    public void test_lastIndexOf() throws Exception {
        /*
            int lastIndexOf(prim[] array, prim target)
            Finds the index of the last appearance of the value target in array, or returns -1 if no such value exists.
            Collection analogue: List.lastIndexOf
            Sign-independent
         */
        Assert.assertEquals(3, Ints.lastIndexOf(new int[]{INT_0, INT_1, INT_2, INT_1}, INT_1));
        Assert.assertEquals(-1, Ints.lastIndexOf(new int[]{INT_0, INT_1, INT_2, INT_1}, INT_3));
    }

    @Test
    public void test_min() throws Exception {
        /*
            prim min(prim... array)
            Returns the minimum element of the array.
            Collection analogue: Collections.min
            Sign-dependent
         */
        Assert.assertEquals(1, Ints.min(5, 30, 1, 4, 2));
    }

    @Test
    public void test_max() throws Exception {
        /*
            prim max(prim... array)
            Returns the maximum element of the array.
            Collection analogue: Collections.max
            Sign-dependent
         */
        Assert.assertEquals(30, Ints.max(5, 30, 1, 4, 2));
    }

    @Test
    public void test_join() throws Exception {
        /*
            String join(String separator, prim... array)
            Constructs a string containing the elements of array, separated by separator.
            Collection analogue: Joiner.on(separator).join
            Sign-dependent
         */
        Assert.assertEquals(INT_0 + ";" + INT_1 + ";" + INT_2, Ints.join(";", INT_0, INT_1, INT_2));
    }

    @Test
    public void test_lexicographicalComparator() throws Exception {
        /*
            Comparator<prim[]> lexicographicalComparator()
            A comparator which compares primitive arrays lexicographically.
            Collection analogue: Ordering.natural().lexicographical()
            Sign-dependent
         */
        Assert.assertEquals(1,  Ints.lexicographicalComparator().compare(new int[]{INT_0, INT_1}, new int[]{INT_0}));
        Assert.assertEquals(-1, Ints.lexicographicalComparator().compare(new int[]{INT_0}, new int[]{INT_0, INT_1}));
    }

    /*
        General utility methods
     */

    @Test
    public void test_compare() throws Exception {
        /*
            int compare(prim a, prim b)
            A traditional Comparator.compare method, but on the primitive types. Provided in the JDK wrapper classes as
            of JDK 7.
            Sign-dependent
         */
        Assert.assertEquals(1, Ints.compare(2, 1));
        Assert.assertEquals(0, Ints.compare(1, 1));
        Assert.assertEquals(-1, Ints.compare(1, 2));
    }

    @Test
    public void test_checkedCast() throws Exception {
        /*
            prim checkedCast(long value)
            Casts the specified value to prim, unless the specified value does not fit into a prim, in which case
            an IllegalArgumentException is thrown.
            Sign-dependent for integral types only
         */
        Assert.assertEquals(Integer.MAX_VALUE, Ints.checkedCast(Integer.MAX_VALUE));
        try {
            Ints.checkedCast(Long.MAX_VALUE);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(IllegalArgumentException.class, e);
        }
    }

    @Test
    public void test_saturatedCast() throws Exception {
        /*
            prim saturatedCast(long value)
            Casts the specified value to prim, unless the specified value does not fit into a prim, in which case
            the closest prim value is used.
            Sign-dependent for integral types only
         */
        Assert.assertEquals(Integer.MAX_VALUE, Ints.saturatedCast(Integer.MAX_VALUE));
        Assert.assertEquals(Integer.MAX_VALUE, Ints.saturatedCast(Long.MAX_VALUE));
    }

    /*
        Byte conversion methods
     */

    @Test
    public void test_BYTES() throws Exception {
        /*
            int BYTES
            Constant representing the number of bytes needed to represent a prim value.
            Sign-independent
         */
        Assert.assertEquals(4, Ints.BYTES);
    }

    @Test
    public void test_fromByteArray() throws Exception {
        /*
            prim fromByteArray(byte[] bytes)
            Returns the prim value whose big-endian representation is the first Prims.BYTES bytes in the array bytes. 
            Throws an IllegalArgumentException if bytes.length < Prims.BYTES.
            Sign-independent
         */
        Assert.assertEquals(0x12345678, Ints.fromByteArray(new byte[]{0x12, 0x34, 0x56, 0x78, 0x55}));
        Assert.assertEquals(0x12345678, Ints.fromByteArray(new byte[]{0x12, 0x34, 0x56, 0x78}));
        try {
            Ints.fromByteArray(new byte[]{0x12, 0x34, 0x56});
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(IllegalArgumentException.class, e);
        }
    }

    @Test
    public void test_fromBytes() throws Exception {
        /*
            prim fromBytes(byte b1, ..., byte bk)
            Takes Prims.BYTES byte arguments. Returns the prim value whose byte representation is the specified bytes
            in big-endian order.
            Sign-independent
         */
        Assert.assertEquals(0x12345678, Ints.fromBytes((byte) 0x12, (byte) 0x34, (byte) 0x56, (byte) 0x78));
    }

    @Test
    public void test_toByteArray() throws Exception {
        /*
            byte[] toByteArray(prim value)
            Returns an array containing the big-endian byte representation of value.
            Sign-independent
         */
        Assert.assertArrayEquals(new byte[]{0x12, 0x34, 0x56, 0x78}, Ints.toByteArray(0x12345678));
    }

    @Test
    public void test_ensureCapacity() throws Exception {
        Assert.assertArrayEquals(new int[]{INT_0, INT_1, INT_2, 0, 0, 0},
                Ints.ensureCapacity(new int[]{INT_0, INT_1, INT_2}, 4, 2));
        Assert.assertArrayEquals(new int[]{INT_0, INT_1, INT_2},
                Ints.ensureCapacity(new int[]{INT_0, INT_1, INT_2}, 2, 2));
    }
}
