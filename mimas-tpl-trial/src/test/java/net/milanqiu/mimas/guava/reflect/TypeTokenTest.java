package net.milanqiu.mimas.guava.reflect;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.TypeParameter;
import com.google.common.reflect.TypeToken;
import net.milanqiu.mimas.collect.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.Serializable;
import java.util.*;

/**
 * Creation Date: 2014-12-30
 * @author Milan Qiu
 */
public class TypeTokenTest {

    private TypeToken<Integer> intToken;
    private TypeToken<String> strToken;
    private TypeToken<String[]> strArrayToken;
    private TypeToken<List<String>> strListToken;
    private TypeToken<Map<?, ?>> mapToken;

    @Before
    public void setUp() throws Exception {
        intToken      = TypeToken.of(Integer.TYPE);
        strToken      = TypeToken.of(String.class);
        strArrayToken = TypeToken.of(String[].class);
        strListToken  = new TypeToken<List<String>>() {};
        mapToken      = new TypeToken<Map<?, ?>>() {};
    }

    @Test
    public void test_getType() throws Exception {
        /*
            getType()
            Returns the wrapped java.lang.reflect.Type.
         */
        Assert.assertEquals("int",                              intToken.getType().toString());
        Assert.assertEquals("class java.lang.String",           strToken.getType().toString());
        Assert.assertEquals("class [Ljava.lang.String;",        strArrayToken.getType().toString());
        Assert.assertEquals("java.util.List<java.lang.String>", strListToken.getType().toString());
        Assert.assertEquals("java.util.Map<?, ?>",              mapToken.getType().toString());
    }

    @Test
    public void test_getRawType() throws Exception {
        /*
            getRawType()
            Returns the most-known runtime class.
         */
        Assert.assertEquals("int",                       intToken.getRawType().toString());
        Assert.assertEquals("class java.lang.String",    strToken.getRawType().toString());
        Assert.assertEquals("class [Ljava.lang.String;", strArrayToken.getRawType().toString());
        Assert.assertEquals("interface java.util.List",  strListToken.getRawType().toString());
        Assert.assertEquals("interface java.util.Map",   mapToken.getRawType().toString());
    }

    @Test
    public void test_getSubtype() throws Exception {
        /*
            getSubtype(Class<?>)
            Returns some subtype of this that has the specified raw class. For example, if this is Iterable<String> and
            the argument is List.class, the result will be List<String>.
         */
        Assert.assertEquals("java.lang.String",                      strToken.getSubtype(String.class).toString());
        Assert.assertEquals("[Ljava.lang.String;",                   strArrayToken.getSubtype(String[].class).toString());
        Assert.assertEquals("java.util.ArrayList<java.lang.String>", strListToken.getSubtype(ArrayList.class).toString());
        Assert.assertEquals("java.util.HashMap<?, ?>",               mapToken.getSubtype(HashMap.class).toString());
    }

    @Test
    public void test_getSupertype() throws Exception {
        /*
            getSupertype(Class<?>)
            Generifies the specified raw class to be a supertype of this type. For example, if this is Set<String> and
            the argument is Iterable.class, the result will be Iterable<String>.
         */
        Assert.assertEquals("java.lang.CharSequence",               strToken.getSupertype(CharSequence.class).toString());
        Assert.assertEquals("[Ljava.lang.CharSequence;",            strArrayToken.getSupertype(CharSequence[].class).toString());
        Assert.assertEquals("java.lang.Iterable<java.lang.String>", strListToken.getSupertype(Iterable.class).toString());
        Assert.assertEquals("java.lang.Object",                     mapToken.getSupertype(Object.class).toString());
    }

    @Test
    public void test_isAssignableFrom() throws Exception {
        /*
            isAssignableFrom(type)
            isAssignableFrom(TypeToken)
            Returns true if this type is assignable from the given type, taking into account generic parameters.
            List<? extends Number> is assignable from List<Integer>, but List<String> is not.
         */
        Assert.assertFalse(intToken.isAssignableFrom(Integer.class));
        Assert.assertFalse(strToken.isAssignableFrom(CharSequence.class));
        Assert.assertFalse(strArrayToken.isAssignableFrom(CharSequence[].class));
        Assert.assertTrue(strListToken.isAssignableFrom(new TypeToken<ArrayList<String>>() {}));
        Assert.assertTrue(mapToken.isAssignableFrom(HashMap.class));
    }

    @SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
    @Test
    public void test_getTypes() throws Exception {
        /*
            getTypes()
            Returns the set of all classes and interfaces that this type is or is a subtype of. The returned Set also
            provides methods classes() and interfaces() to let you view only the superclasses and superinterfaces.
         */
        TypeToken.TypeSet intTypeSet = intToken.getTypes();
        Assert.assertEquals(1, intTypeSet.size());
        Assert.assertEquals(ImmutableSet.of(
                TypeToken.of(Integer.TYPE)
        ), intTypeSet);

        TypeToken.TypeSet strTypeSet = strToken.getTypes();
        Assert.assertEquals(5, strTypeSet.size());
        Assert.assertEquals(ImmutableSet.of(
                TypeToken.of(String.class),
                TypeToken.of(CharSequence.class),
                new TypeToken<Comparable<String>>() {},
                TypeToken.of(Serializable.class),
                TypeToken.of(Object.class)
        ), strTypeSet);

        TypeToken.TypeSet strArrayTypeSet = strArrayToken.getTypes();
        Assert.assertEquals(4, strArrayTypeSet.size());
        Assert.assertEquals(ImmutableSet.of(
                TypeToken.of(String[].class),
                TypeToken.of(Cloneable.class),
                TypeToken.of(Serializable.class),
                TypeToken.of(Object.class)
        ), strArrayTypeSet);

        TypeToken.TypeSet strListTypeSet = strListToken.getTypes();
        Assert.assertEquals(3, strListTypeSet.size());
        Assert.assertEquals(ImmutableSet.of(
                new TypeToken<List<String>>() {},
                new TypeToken<Collection<String>>() {},
                new TypeToken<Iterable<String>>() {}
        ), strListTypeSet);

        TypeToken.TypeSet mapTypeSet = mapToken.getTypes();
        Assert.assertEquals(1, mapTypeSet.size());
        Assert.assertEquals(ImmutableSet.of(
                new TypeToken<Map<?, ?>>() {}
        ), mapTypeSet);
    }

    @Test
    public void test_isArray() throws Exception {
        /*
            isArray()
            Checks if this type is known to be an array, such as int[] or even <? extends A[]>.
         */
        Assert.assertFalse(intToken.isArray());
        Assert.assertFalse(strToken.isArray());
        Assert.assertTrue(strArrayToken.isArray());
        Assert.assertFalse(strListToken.isArray());
        Assert.assertFalse(mapToken.isArray());
    }

    @Test
    public void test_getComponentType() throws Exception {
        /*
            getComponentType()
            Returns the array component type.
         */
        Assert.assertNull(intToken.getComponentType());
        Assert.assertNull(strToken.getComponentType());
        Assert.assertEquals(TypeToken.of(String.class), strArrayToken.getComponentType());
        Assert.assertNull(strListToken.getComponentType());
        Assert.assertNull(mapToken.getComponentType());
    }

    @SuppressWarnings("AssertEqualsBetweenInconvertibleTypes")
    @Test
    public void test_resolveType() throws Exception {
        TypeToken<Map<Integer, String>> mapToken = new TypeToken<Map<Integer, String>>() {};
        Assert.assertEquals(TypeToken.of(String.class),
                mapToken.resolveType(Map.class.getTypeParameters()[1]));
        Assert.assertEquals(new TypeToken<Set<Map.Entry<Integer, String>>>() {},
                mapToken.resolveType(Map.class.getMethod("entrySet").getGenericReturnType()));
    }

    private static <K, V> TypeToken<Map<K, V>> createMapToken(TypeToken<K> keyToken, TypeToken<V> valueToken) {
        return new TypeToken<Map<K, V>>() {}
                .where(new TypeParameter<K>() {}, keyToken)
                .where(new TypeParameter<V>() {}, valueToken);
    }

    @Test
    public void test_where() throws Exception {
        TypeToken<Map<Integer, String>> mapToken = createMapToken(TypeToken.of(Integer.class), TypeToken.of(String.class));
        Assert.assertEquals("java.util.Map<java.lang.Integer, java.lang.String>", mapToken.getType().toString());
    }

    @Test
    public void test_isPrimitive() throws Exception {
        Assert.assertTrue(intToken.isPrimitive());
        Assert.assertFalse(strToken.isPrimitive());
        Assert.assertFalse(strArrayToken.isPrimitive());
        Assert.assertFalse(strListToken.isPrimitive());
        Assert.assertFalse(mapToken.isPrimitive());
    }

    @Test
    public void test_wrap() throws Exception {
        Assert.assertEquals(TypeToken.of(Integer.class), intToken.wrap());
        Assert.assertEquals(strToken,                    strToken.wrap());
        Assert.assertEquals(strArrayToken,               strArrayToken.wrap());
        Assert.assertEquals(strListToken,                strListToken.wrap());
        Assert.assertEquals(mapToken,                    mapToken.wrap());
    }

    @Test
    public void test_unwrap() throws Exception {
        Assert.assertEquals(intToken,      TypeToken.of(Integer.class).unwrap());
        Assert.assertEquals(intToken,      intToken.unwrap());
        Assert.assertEquals(strToken,      strToken.unwrap());
        Assert.assertEquals(strArrayToken, strArrayToken.unwrap());
        Assert.assertEquals(strListToken,  strListToken.unwrap());
        Assert.assertEquals(mapToken,      mapToken.unwrap());
    }
}
