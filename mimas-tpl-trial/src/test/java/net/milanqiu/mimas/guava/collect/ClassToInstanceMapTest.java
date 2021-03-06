package net.milanqiu.mimas.guava.collect;

import com.google.common.collect.ClassToInstanceMap;
import com.google.common.collect.MutableClassToInstanceMap;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-26
 * @author Milan Qiu
 */
public class ClassToInstanceMapTest {

    /*
        ClassToInstanceMap has a single type parameter, typically named B, representing the upper bound on the types
        managed by the map. Technically, ClassToInstanceMap<B> implements Map<Class<? extends B>, B> -- or in other
        words, a map from subclasses of B to instances of B.
     */

    private ClassToInstanceMap<Object> ctiMap;

    @Before
    public void setUp() throws Exception {
        ctiMap = MutableClassToInstanceMap.create();
    }

    @Test
    public void test_getInstance_putInstance() throws Exception {
        Assert.assertEquals(null, ctiMap.putInstance(Integer.class, INT_0));
        Assert.assertEquals(null, ctiMap.putInstance(String.class, STR_0));
        Assert.assertEquals(null, ctiMap.putInstance(Object.class, OBJ_0));
        Assert.assertEquals(STR_0, ctiMap.putInstance(String.class, STR_1));

        Assert.assertEquals(INT_0, (int) ctiMap.getInstance(Integer.class));
        Assert.assertEquals(STR_1, ctiMap.getInstance(String.class));
        Assert.assertEquals(OBJ_0, ctiMap.getInstance(Object.class));
    }
}
