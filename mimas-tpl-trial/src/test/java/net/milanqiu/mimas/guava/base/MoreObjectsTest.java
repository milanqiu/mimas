package net.milanqiu.mimas.guava.base;

import com.google.common.base.MoreObjects;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-17
 * @author Milan Qiu
 */
public class MoreObjectsTest {

    @Test
    public void test_firstNonNull() throws Exception {
        Assert.assertEquals(STR_0, MoreObjects.firstNonNull(STR_0, STR_1));
        Assert.assertEquals(STR_0, MoreObjects.firstNonNull(STR_0, null));
        Assert.assertEquals(STR_1, MoreObjects.firstNonNull(null, STR_1));

        AssertExt.assertExceptionThrown(() -> MoreObjects.firstNonNull(null, null), NullPointerException.class);
    }

    @Test
    public void test_toStringHelper_ToStringHelper_add_addValue_omitNullValues_toString() throws Exception {
       Assert.assertEquals(
               "MoreObjectsTest{" + STR_0 + "=" + INT_0 + ", " + STR_1 + "=null, " + INT_2 + "}",
               MoreObjects.toStringHelper(this)
                       .add(STR_0, INT_0)
                       .add(STR_1, null)
                       .addValue(INT_2)
                       .toString());

       Assert.assertEquals(
               "MoreObjectsTest{" + STR_0 + "=" + INT_0 + ", " + INT_2 + "}",
               MoreObjects.toStringHelper(this.getClass())
                       .add(STR_0, INT_0)
                       .add(STR_1, null)
                       .addValue(INT_2)
                       .omitNullValues()
                       .toString());
    }
}
