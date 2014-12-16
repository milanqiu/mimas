package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-7-26
 * @author Milan Qiu
 */
public class ClassToInstanceMapTest {

    /*
        ClassToInstanceMap has a single type parameter, typically named B, representing the upper bound on the types
        managed by the map. Technically, ClassToInstanceMap<B> implements Map<Class<? extends B>, B> -- or in other
        words, a map from subclasses of B to instances of B.
     */

    private ClassToInstanceMap<Object> mctiMap;

    @Before
    public void setUp() throws Exception {
        mctiMap = MutableClassToInstanceMap.create();
    }

    @Test
    public void test_getInstance_putInstance() throws Exception {
        Assert.assertEquals(null, mctiMap.putInstance(Integer.class, INT_0));
        Assert.assertEquals(null, mctiMap.putInstance(String.class, STR_0));
        Assert.assertEquals(null, mctiMap.putInstance(Object.class, OBJ_0));
        Assert.assertEquals(STR_0, mctiMap.putInstance(String.class, STR_1));

        Assert.assertEquals(INT_0, (int) mctiMap.getInstance(Integer.class));
        Assert.assertEquals(STR_1, mctiMap.getInstance(String.class));
        Assert.assertEquals(OBJ_0, mctiMap.getInstance(Object.class));
    }
}
