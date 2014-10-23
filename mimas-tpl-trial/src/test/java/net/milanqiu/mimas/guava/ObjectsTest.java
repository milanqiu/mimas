package net.milanqiu.mimas.guava;

import com.google.common.base.Objects;
import net.milanqiu.mimas.instrumentation.DebugUtils;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-7-17
 * @author Milan Qiu
 */
public class ObjectsTest {

    @Test
    public void test_firstNonNull() throws Exception {
        Assert.assertEquals(STR_0, Objects.firstNonNull(STR_0, STR_1));
        Assert.assertEquals(STR_0, Objects.firstNonNull(STR_0, null));
        Assert.assertEquals(STR_1, Objects.firstNonNull(null, STR_1));

        try {
            Objects.firstNonNull(null, null);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(NullPointerException.class, e);
        }
    }

    @Test
    public void test_toStringHelper() throws Exception {
       Assert.assertEquals(
               "ObjectsTest{" + STR_0 + "=" + INT_0 + ", " + INT_2 + "}",
               Objects.toStringHelper(this)
                       .add(STR_0, INT_0)
                       .add(STR_1, null)
                       .addValue(INT_2)
                       .omitNullValues()
                       .toString());
    }
}
