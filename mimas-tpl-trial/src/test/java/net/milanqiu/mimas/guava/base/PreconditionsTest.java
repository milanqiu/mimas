package net.milanqiu.mimas.guava.base;

import com.google.common.base.Preconditions;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-16
 * @author Milan Qiu
 */
public class PreconditionsTest {

    /*
        Each method has three variants:
         - No extra arguments. Any exceptions are thrown without error messages.
         - An extra Object argument. Any exception is thrown with the error message object.toString().
         - An extra String argument, with an arbitrary number of additional Object arguments. This behaves something
           like printf, but for GWT compatibility and efficiency, it only allows %s indicators.
     */

    @Test
    public void test_checkArgument() throws Exception {
        /*
            void checkArgument(boolean)
            Checks that the boolean is true. Use for validating arguments to methods.
         */
        Preconditions.checkArgument(true);

        AssertExt.assertExceptionThrown(() -> Preconditions.checkArgument(false), IllegalArgumentException.class);

        // void checkArgument(boolean expression, @Nullable Object errorMessage)
        AssertExt.assertExceptionThrown(() -> Preconditions.checkArgument(false, OBJ_0),
                IllegalArgumentException.class, STR_OF_OBJ_0);

        // checkArgument(boolean expression, @Nullable String errorMessageTemplate, @Nullable Object... errorMessageArgs)
        AssertExt.assertExceptionThrown(() -> Preconditions.checkArgument(false, "here %s error %s", "is", 404),
                IllegalArgumentException.class, "here is error 404");
    }

    @Test
    public void test_checkNotNull() throws Exception {
        /*
            T checkNotNull(T)
            Checks that the value is not null. Returns the value directly, so you can use checkNotNull(value) inline.
         */
        Assert.assertSame(OBJ_0, Preconditions.checkNotNull(OBJ_0));

        AssertExt.assertExceptionThrown(() -> Preconditions.checkNotNull(null), NullPointerException.class);
    }

    @Test
    public void test_checkState() throws Exception {
        /*
            void checkState(boolean)
            Checks some state of the object, not dependent on the method arguments. For example, an Iterator might
            use this to check that next has been called before any call to remove.
         */
        Preconditions.checkState(true);

        AssertExt.assertExceptionThrown(() -> Preconditions.checkState(false), IllegalStateException.class);
    }

    @Test
    public void test_checkElementIndex() throws Exception {
        /*
            int checkElementIndex(int index, int size)
            Checks that index is a valid element index into a list, string, or array with the specified size. An
            element index may range from 0 inclusive to size exclusive. You don't pass the list, string, or array
            directly; you just pass its size.
            Returns index.
         */
        Assert.assertEquals(0, Preconditions.checkElementIndex(0, INT_0));
        Assert.assertEquals(INT_0-1, Preconditions.checkElementIndex(INT_0-1, INT_0));

        AssertExt.assertExceptionThrown(() -> Preconditions.checkElementIndex(-1, INT_0), IndexOutOfBoundsException.class);
        AssertExt.assertExceptionThrown(() -> Preconditions.checkElementIndex(INT_0, INT_0), IndexOutOfBoundsException.class);
    }

    @Test
    public void test_checkPositionIndex() throws Exception {
        /*
            int checkPositionIndex(int index, int size)
            Checks that index is a valid position index into a list, string, or array with the specified size. A
            position index may range from 0 inclusive to size inclusive. You don't pass the list, string, or array
            directly; you just pass its size.
            Returns index.
         */
        Assert.assertEquals(0, Preconditions.checkPositionIndex(0, INT_3));
        Assert.assertEquals(INT_3, Preconditions.checkPositionIndex(INT_3, INT_3));

        AssertExt.assertExceptionThrown(() -> Preconditions.checkPositionIndex(-1, INT_3), IndexOutOfBoundsException.class);
        AssertExt.assertExceptionThrown(() -> Preconditions.checkPositionIndex(INT_3+1, INT_3), IndexOutOfBoundsException.class);
    }

    @Test
    public void test_checkPositionIndexes() throws Exception {
        /*
            void checkPositionIndexes(int start, int end, int size)
            Checks that [start, end) is a valid sub range of a list, string, or array with the specified size. Comes with its own error message.
         */
        Preconditions.checkPositionIndexes(0, INT_3, INT_3);

        AssertExt.assertExceptionThrown(() -> Preconditions.checkPositionIndexes(-1, INT_3, INT_3), IndexOutOfBoundsException.class);
        AssertExt.assertExceptionThrown(() -> Preconditions.checkPositionIndexes(0, INT_3+1, INT_3), IndexOutOfBoundsException.class);
    }
}
