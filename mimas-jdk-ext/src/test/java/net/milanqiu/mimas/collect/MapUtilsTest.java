package net.milanqiu.mimas.collect;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2023-02-19
 * @author Milan Qiu
 */
public class MapUtilsTest {

    @Test
    public void test_toString() throws Exception {
        Map<String, String[]> map = new HashMap<>();
        map.put(STR_0, new String[]{STR_3});
        map.put(STR_1, new String[]{STR_4, STR_4});
        map.put(STR_2, new String[]{});
        Assert.assertEquals(String.format("{%s=[%s], %s=[%s, %s], %s=[]}", STR_0, STR_3, STR_1, STR_4, STR_4, STR_2), MapUtils.toString(map));
    }
}
