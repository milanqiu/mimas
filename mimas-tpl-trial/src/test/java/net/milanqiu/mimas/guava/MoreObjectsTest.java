package net.milanqiu.mimas.guava;

import com.google.common.base.MoreObjects;
import net.milanqiu.mimas.instrumentation.DebugUtils;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-17
 * @author Milan Qiu
 */
public class MoreObjectsTest {

    @Test
    public void test_firstNonNull() throws Exception {
        Assert.assertEquals(STR_0, MoreObjects.firstNonNull(STR_0, STR_1));
        Assert.assertEquals(STR_0, MoreObjects.firstNonNull(STR_0, null));
        Assert.assertEquals(STR_1, MoreObjects.firstNonNull(null, STR_1));

        try {
            MoreObjects.firstNonNull(null, null);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(NullPointerException.class, e);
        }
    }

    @Test
    public void test_toStringHelper() throws Exception {
       Assert.assertEquals(
               "MoreObjectsTest{" + STR_0 + "=" + INT_0 + ", " + INT_2 + "}",
               MoreObjects.toStringHelper(this)
                       .add(STR_0, INT_0)
                       .add(STR_1, null)
                       .addValue(INT_2)
                       .omitNullValues()
                       .toString());
    }
}
