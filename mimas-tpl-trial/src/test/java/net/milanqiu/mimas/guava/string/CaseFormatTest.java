package net.milanqiu.mimas.guava.string;

import com.google.common.base.CaseFormat;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>Creation Date: 2014-11-7
 * @author Milan Qiu
 */
public class CaseFormatTest {

    @Test
    public void test_to() throws Exception {
        Assert.assertEquals("hitTheRoad",   CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_CAMEL,      "hitTheRoad"));
        Assert.assertEquals("hit-the-road", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN,     "hitTheRoad"));
        Assert.assertEquals("hit_the_road", CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, "hitTheRoad"));
        Assert.assertEquals("HitTheRoad",   CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_CAMEL,      "hitTheRoad"));
        Assert.assertEquals("HIT_THE_ROAD", CaseFormat.LOWER_CAMEL.to(CaseFormat.UPPER_UNDERSCORE, "hitTheRoad"));
    }
}
