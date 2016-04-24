package net.milanqiu.mimas.guava.base;

import com.google.common.base.CaseFormat;
import com.google.common.base.Converter;
import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2014-11-07
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

    @Test
    public void test_converterTo() throws Exception {
        Converter<String, String> converter = CaseFormat.LOWER_CAMEL.converterTo(CaseFormat.LOWER_HYPHEN);
        Assert.assertEquals("hit-the-road", converter.convert("hitTheRoad"));
    }
}
