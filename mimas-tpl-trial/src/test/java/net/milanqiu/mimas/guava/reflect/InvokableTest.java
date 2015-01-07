package net.milanqiu.mimas.guava.reflect;

import com.google.common.reflect.Invokable;
import com.google.common.reflect.TypeToken;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

/**
 * Creation Date: 2015-1-5
 * @author Milan Qiu
 */
public class InvokableTest {

    private Invokable<Map<Integer, String>, ?> mapPutMethod;

    @Before
    public void setUp() throws Exception {
        TypeToken<Map<Integer, String>> mapToken = new TypeToken<Map<Integer, String>>() {};
        mapPutMethod = mapToken.method(Map.class.getMethod("put", Object.class, Object.class));
    }

    @Test
    public void test_isPublic() throws Exception {
        Assert.assertTrue(mapPutMethod.isPublic());
    }

    @Test
    public void test_isPackagePrivate() throws Exception {
        Assert.assertFalse(mapPutMethod.isPackagePrivate());
    }

    @Test
    public void test_isOverridable() throws Exception {
        Assert.assertTrue(mapPutMethod.isOverridable());
    }

    @Test
    public void test_getReturnType() throws Exception {
        Assert.assertEquals(TypeToken.of(String.class), mapPutMethod.getReturnType());
    }

    @Test
    public void test_isVarArgs() throws Exception {
        Assert.assertFalse(mapPutMethod.isVarArgs());
    }

    @Test
    public void test_getParameters() throws Exception {
        Assert.assertEquals(TypeToken.of(Integer.class), mapPutMethod.getParameters().get(0).getType());
        Assert.assertEquals(TypeToken.of(String.class), mapPutMethod.getParameters().get(1).getType());
    }

    @Test
    public void test_getExceptionTypes() throws Exception {
        Assert.assertTrue(mapPutMethod.getExceptionTypes().isEmpty());
    }

    @Test
    public void test_getDeclaringClass() throws Exception {
        Assert.assertEquals(Map.class, mapPutMethod.getDeclaringClass());
    }

    @Test
    public void test_getOwnerType() throws Exception {
        Assert.assertEquals(new TypeToken<Map<Integer, String>>() {}, mapPutMethod.getOwnerType());
    }
}
