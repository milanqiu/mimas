package net.milanqiu.mimas.guava.base;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Supplier;
import com.google.common.collect.ImmutableMap;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-09-24
 * @author Milan Qiu
 */
public class FunctionsTest {

    @Test
    public void test_forMap() throws Exception {
        /*
            public static <K,V> Function<K,V> forMap(Map<K,V> map)
            public static <K,V> Function<K,V> forMap(Map<K,? extends V> map,
                         @Nullable
                         V defaultValue)
            Returns a function which performs a map lookup.
         */
        Map<Integer, String> map = ImmutableMap.of(INT_0, STR_0, INT_1, STR_1, INT_2, STR_2);

        Function<Integer, String> func = Functions.forMap(map, STR_4);
        Assert.assertEquals(STR_0, func.apply(INT_0));
        Assert.assertEquals(STR_1, func.apply(INT_1));
        Assert.assertEquals(STR_2, func.apply(INT_2));
        Assert.assertEquals(STR_4, func.apply(INT_3));

        Function<Integer, String> func2 = Functions.forMap(map);
        Assert.assertEquals(STR_0, func2.apply(INT_0));
        Assert.assertEquals(STR_1, func2.apply(INT_1));
        Assert.assertEquals(STR_2, func2.apply(INT_2));
        AssertExt.assertExceptionThrown(() -> func2.apply(INT_3), IllegalArgumentException.class);
    }

    @Test
    public void test_compose() throws Exception {
        /*
            public static <A,B,C> Function<A,C> compose(Function<B,C> g,
                            Function<A,? extends B> f)
            Returns the composition of two functions. For f: A->B and g: B->C, composition is defined as the function h
            such that h(a) == g(f(a)) for each a.
         */
        Function<Integer, Integer> funcAddTwo = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer + 2;
            }
        };
        Function<Integer, Integer> funcMultipleThree = new Function<Integer, Integer>() {
            @Override
            public Integer apply(Integer integer) {
                return integer * 3;
            }
        };
        Function<Integer, Integer> funcComposition = Functions.compose(funcMultipleThree, funcAddTwo);
        Assert.assertEquals(9, (int) funcComposition.apply(1)); // (1 + 2) * 3
    }

    @Test
    public void test_constant() throws Exception {
        /*
            public static <E> Function<Object,E> constant(@Nullable
                              E value)
            Creates a function that returns value for any input.
         */
        Function<Object, String> func = Functions.constant(STR_0);
        Assert.assertEquals(STR_0, func.apply(INT_0));
        Assert.assertEquals(STR_0, func.apply(INT_1));
        Assert.assertEquals(STR_0, func.apply(STR_0));
        Assert.assertEquals(STR_0, func.apply(STR_1));
        Assert.assertEquals(STR_0, func.apply(null));
    }

    @Test
    public void test_identity() throws Exception {
        /*
            public static <E> Function<E,E> identity()
            Returns the identity function.
         */
        Function<String, String> func = Functions.identity();
        Assert.assertEquals(STR_0, func.apply(STR_0));
        Assert.assertEquals(STR_1, func.apply(STR_1));
        Assert.assertEquals(null, func.apply(null));
    }

    @Test
    public void test_toStringFunction() throws Exception {
        /*
            public static Function<Object,String> toStringFunction()
            Returns a function that calls toString() on its argument. The function does not accept nulls; it will throw
            a NullPointerException when applied to null.
         */
        Function<Object,String> func = Functions.toStringFunction();
        Assert.assertEquals(STR_0, func.apply(STR_0));
        Assert.assertEquals(STR_OF_INT_0, func.apply(INT_0));
        Assert.assertEquals(OBJ_0.toString(), func.apply(OBJ_0));
        AssertExt.assertExceptionThrown(() -> func.apply(null), NullPointerException.class);
    }

    @Test
    public void test_forPredicate() throws Exception {
        Function<Integer, Boolean> func = Functions.forPredicate(new Predicate<Integer>() {
            @Override
            public boolean apply(Integer integer) {
                return integer >= 0;
            }
        });
        Assert.assertTrue(func.apply(1));
        Assert.assertTrue(func.apply(0));
        Assert.assertFalse(func.apply(-1));
    }

    @Test
    public void test_forSupplier() throws Exception {
        Function<Object, String> func = Functions.forSupplier(new Supplier<String>() {
            @Override
            public String get() {
                return STR_0;
            }
        });
        Assert.assertEquals(STR_0, func.apply(OBJ_0));
    }
}
