package net.milanqiu.mimas.lang;

import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2016-08-05
 * @author Milan Qiu
 */
public class PointerTest {

    private Pointer<Integer> pointer;
    private Pointer<Integer> anotherPointer;
    private Pointer<String> anotherPointerWithDifferentType;

    @Test
    public void test_Constructor() throws Exception {
        pointer = new Pointer<>(INT_0);
        Assert.assertEquals(INT_0, (int) pointer.get());

        // null test
        pointer = new Pointer<>(null);
        Assert.assertNull(pointer.get());
    }

    @Test
    public void test_get_set() throws Exception {
        pointer = new Pointer<>(INT_0);
        Assert.assertEquals(INT_0, (int) pointer.get());
        Assert.assertEquals(INT_0, (int) pointer.set(INT_1));
        Assert.assertEquals(INT_1, (int) pointer.get());

        // null test
        Assert.assertEquals(INT_1, (int) pointer.set(null));
        Assert.assertNull(pointer.get());
    }

    @Test
    public void test_equals() throws Exception {
        pointer = new Pointer<>(INT_0);
        anotherPointer = new Pointer<>(INT_0);
        Assert.assertTrue(pointer.equals(anotherPointer));
        Assert.assertTrue(anotherPointer.equals(pointer));

        anotherPointer.set(INT_1);
        Assert.assertFalse(pointer.equals(anotherPointer));
        Assert.assertFalse(anotherPointer.equals(pointer));

        anotherPointerWithDifferentType = new Pointer<>(STR_OF_INT_0);
        Assert.assertFalse(pointer.equals(anotherPointerWithDifferentType));
        Assert.assertFalse(anotherPointerWithDifferentType.equals(pointer));

        // null test
        anotherPointer.set(null);
        Assert.assertFalse(pointer.equals(anotherPointer));
        Assert.assertFalse(anotherPointer.equals(pointer));

        pointer.set(null);
        Assert.assertTrue(pointer.equals(anotherPointer));
        Assert.assertTrue(anotherPointer.equals(pointer));
    }

    @Test
    public void test_hashCode() throws Exception {
        pointer = new Pointer<>(INT_0);
        anotherPointer = new Pointer<>(INT_0);
        Assert.assertEquals(pointer.hashCode(), anotherPointer.hashCode());

        anotherPointer.set(INT_1);
        Assert.assertNotEquals(pointer.hashCode(), anotherPointer.hashCode());

        anotherPointerWithDifferentType = new Pointer<>(STR_OF_INT_0);
        Assert.assertNotEquals(pointer.hashCode(), anotherPointerWithDifferentType.hashCode());

        // null test
        anotherPointer.set(null);
        Assert.assertNotEquals(pointer.hashCode(), anotherPointer.hashCode());

        pointer.set(null);
        Assert.assertEquals(pointer.hashCode(), anotherPointer.hashCode());
    }

    @Test
    public void test_toString() throws Exception {
        pointer = new Pointer<>(INT_0);
        Assert.assertEquals(STR_OF_INT_0, pointer.toString());

        // null test
        pointer.set(null);
        Assert.assertEquals("", pointer.toString());
    }
}
