package net.milanqiu.mimas.guava.base;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Lists;
import net.milanqiu.mimas.string.RegExpConsts;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.regex.Pattern;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-9-25
 * @author Milan Qiu
 */
public class PredicatesTest {

    @Test
    public void test_instanceOf() throws Exception {
        /*
            @GwtIncompatible(value="Class.isInstance")
            public static Predicate<Object> instanceOf(Class<?> clazz)
            Returns a predicate that evaluates to true if the object being tested is an instance of the given class. If
            the object being tested is null this predicate evaluates to false.
            If you want to filter an Iterable to narrow its type, consider using Iterables.filter(Iterable, Class) in
            preference.
            Warning: contrary to the typical assumptions about predicates (as documented at Predicate.apply(T)), the
            returned predicate may not be consistent with equals. For example, instanceOf(ArrayList.class) will yield
            different results for the two equal instances Lists.newArrayList(1) and Arrays.asList(1).
         */
        Assert.assertTrue(Predicates.instanceOf(Integer.class).apply(INT_0));
        Assert.assertFalse(Predicates.instanceOf(Integer.class).apply(STR_0));
        Assert.assertFalse(Predicates.instanceOf(Integer.class).apply(OBJ_0));
        Assert.assertFalse(Predicates.instanceOf(Integer.class).apply(null));

        Assert.assertFalse(Predicates.instanceOf(String.class).apply(INT_0));
        Assert.assertTrue(Predicates.instanceOf(String.class).apply(STR_0));
        Assert.assertFalse(Predicates.instanceOf(String.class).apply(OBJ_0));
        Assert.assertFalse(Predicates.instanceOf(String.class).apply(null));

        Assert.assertTrue(Predicates.instanceOf(Object.class).apply(INT_0));
        Assert.assertTrue(Predicates.instanceOf(Object.class).apply(STR_0));
        Assert.assertTrue(Predicates.instanceOf(Object.class).apply(OBJ_0));
        Assert.assertFalse(Predicates.instanceOf(Object.class).apply(null));
    }

    @Test
    public void test_assignableFrom() throws Exception {
        /*
            @GwtIncompatible(value="Class.isAssignableFrom")
            @Beta
            public static Predicate<Class<?>> assignableFrom(Class<?> clazz)
            Returns a predicate that evaluates to true if the class being tested is assignable from the given class. The
            returned predicate does not allow null inputs.
         */
        Assert.assertTrue(Predicates.assignableFrom(Integer.class).apply(Integer.class));
        Assert.assertFalse(Predicates.assignableFrom(Integer.class).apply(String.class));
        Assert.assertFalse(Predicates.assignableFrom(Integer.class).apply(Object.class));

        Assert.assertFalse(Predicates.assignableFrom(String.class).apply(Integer.class));
        Assert.assertTrue(Predicates.assignableFrom(String.class).apply(String.class));
        Assert.assertFalse(Predicates.assignableFrom(String.class).apply(Object.class));

        Assert.assertTrue(Predicates.assignableFrom(Object.class).apply(Integer.class));
        Assert.assertTrue(Predicates.assignableFrom(Object.class).apply(String.class));
        Assert.assertTrue(Predicates.assignableFrom(Object.class).apply(Object.class));
    }

    @Test
    public void test_containsPattern() throws Exception {
        /*
            @GwtIncompatible(value="java.util.regex.Pattern")
            public static Predicate<CharSequence> containsPattern(String pattern)
            Returns a predicate that evaluates to true if the CharSequence being tested contains any match for the given
            regular expression pattern. The test used is equivalent to Pattern.compile(pattern).matcher(arg).find()
         */
        Predicate<CharSequence> pred = Predicates.containsPattern(RegExpConsts.REG_EXP_STRICT_IDENTIFIER_NAME);
        Assert.assertTrue(pred.apply("a"));
        Assert.assertTrue(pred.apply("a0"));
        Assert.assertTrue(pred.apply("a_"));
        Assert.assertTrue(pred.apply("_0"));
        Assert.assertFalse(pred.apply(""));
        Assert.assertFalse(pred.apply("%"));
        Assert.assertFalse(pred.apply("8a"));
        Assert.assertFalse(pred.apply("$a"));
        Assert.assertFalse(pred.apply("abcd*"));
    }

    @Test
    public void test_contains() throws Exception {
        /*
            @GwtIncompatible(value="java.util.regex.Pattern")
            public static Predicate<CharSequence> contains(Pattern pattern)
            Returns a predicate that evaluates to true if the CharSequence being tested contains any match for the given
            regular expression pattern. The test used is equivalent to pattern.matcher(arg).find()
         */
        Pattern pattern = Pattern.compile(RegExpConsts.REG_EXP_STRICT_IDENTIFIER_NAME);
        Predicate<CharSequence> pred = Predicates.contains(pattern);
        Assert.assertTrue(pred.apply("a"));
        Assert.assertTrue(pred.apply("a0"));
        Assert.assertTrue(pred.apply("a_"));
        Assert.assertTrue(pred.apply("_0"));
        Assert.assertFalse(pred.apply(""));
        Assert.assertFalse(pred.apply("%"));
        Assert.assertFalse(pred.apply("8a"));
        Assert.assertFalse(pred.apply("$a"));
        Assert.assertFalse(pred.apply("abcd*"));
    }

    @Test
    public void test_in() throws Exception {
        /*
            public static <T> Predicate<T> in(Collection<? extends T> target)
            Returns a predicate that evaluates to true if the object reference being tested is a member of the given
            collection. It does not defensively copy the collection passed in, so future changes to it will alter the
            behavior of the predicate.
            This method can technically accept any Collection<?>, but using a typed collection helps prevent bugs. This
            approach doesn't block any potential users since it is always possible to use Predicates.<Object>in().
         */
        List<String> list = Lists.newArrayList(STR_0, STR_1, STR_2);
        Predicate<String> pred = Predicates.in(list);
        Assert.assertTrue(pred.apply(STR_0));
        Assert.assertTrue(pred.apply(STR_1));
        Assert.assertTrue(pred.apply(STR_2));
        Assert.assertFalse(pred.apply(STR_3));
        Assert.assertFalse(pred.apply(STR_4));

        list.add(STR_4);
        Assert.assertTrue(pred.apply(STR_0));
        Assert.assertTrue(pred.apply(STR_1));
        Assert.assertTrue(pred.apply(STR_2));
        Assert.assertFalse(pred.apply(STR_3));
        Assert.assertTrue(pred.apply(STR_4));
    }

    @Test
    public void test_isNull() throws Exception {
        /*
            @GwtCompatible(serializable=true)
            public static <T> Predicate<T> isNull()
            Returns a predicate that evaluates to true if the object reference being tested is null.
         */
        Assert.assertTrue(Predicates.isNull().apply(null));
        Assert.assertFalse(Predicates.isNull().apply(OBJ_0));
    }

    @Test
    public void test_notNull() throws Exception {
        /*
            @GwtCompatible(serializable=true)
            public static <T> Predicate<T> notNull()
            Returns a predicate that evaluates to true if the object reference being tested is not null.
         */
        Assert.assertTrue(Predicates.notNull().apply(OBJ_0));
        Assert.assertFalse(Predicates.notNull().apply(null));
    }

    @Test
    public void test_alwaysFalse() throws Exception {
        /*
            @GwtCompatible(serializable=true)
            public static <T> Predicate<T> alwaysFalse()
            Returns a predicate that always evaluates to false.
         */
        Assert.assertFalse(Predicates.alwaysFalse().apply(OBJ_0));
    }

    @Test
    public void test_alwaysTrue() throws Exception {
        /*
            @GwtCompatible(serializable=true)
            public static <T> Predicate<T> alwaysTrue()
            Returns a predicate that always evaluates to true.
         */
        Assert.assertTrue(Predicates.alwaysTrue().apply(OBJ_0));
    }

    @Test
    public void test_equalTo() throws Exception {
        /*
            public static <T> Predicate<T> equalTo(@Nullable
                       T target)
            Returns a predicate that evaluates to true if the object being tested equals() the given target or both are
            null.
         */
        Assert.assertTrue(Predicates.equalTo(OBJ_0).apply(OBJ_0));
        Assert.assertFalse(Predicates.equalTo(OBJ_0).apply(OBJ_1));
        Assert.assertFalse(Predicates.equalTo(OBJ_0).apply(null));

        Assert.assertFalse(Predicates.equalTo(null).apply(OBJ_0));
        Assert.assertFalse(Predicates.equalTo(null).apply(OBJ_1));
        Assert.assertTrue(Predicates.equalTo(null).apply(null));
    }

    @Test
    public void test_compose() throws Exception {
        /*
            public static <A,B> Predicate<A> compose(Predicate<B> predicate,
                         Function<A,? extends B> function)
            Returns the composition of a function and a predicate. For every x, the generated predicate returns
            predicate(function(x)).
         */
        Function<String, Integer> funcAddTwo = new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return Integer.parseInt(s) + 2;
            }
        };
        Predicate<Integer> predGreaterThanThree = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer integer) {
                return integer > 3;
            }
        };
        Predicate<String> predComposition = Predicates.compose(predGreaterThanThree, funcAddTwo);
        Assert.assertFalse(predComposition.apply("0"));
        Assert.assertFalse(predComposition.apply("1"));
        Assert.assertTrue(predComposition.apply("2"));
        Assert.assertTrue(predComposition.apply("3"));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test_and() throws Exception {
        /*
            public static <T> Predicate<T> and(Iterable<? extends Predicate<? super T>> components)
            public static <T> Predicate<T> and(Predicate<? super T>... components)
            public static <T> Predicate<T> and(Predicate<? super T> first, Predicate<? super T> second)
            Returns a predicate that evaluates to true if each of its components evaluates to true. The components are
            evaluated in order, and evaluation will be "short-circuited" as soon as a false predicate is found. It
            defensively copies the array passed in, so future changes to it won't alter the behavior of this predicate.
            If components is empty, the returned predicate will always evaluate to true.
         */
        Predicate<Integer> predGreaterThanThree = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer integer) {
                return integer > 3;
            }
        };
        Predicate<Integer> predLessThanFive = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer integer) {
                return integer < 5;
            }
        };
        Predicate<Integer> pred = Predicates.and(predGreaterThanThree, predLessThanFive);
        Assert.assertFalse(pred.apply(3));
        Assert.assertTrue(pred.apply(4));
        Assert.assertFalse(pred.apply(5));

        Assert.assertTrue(Predicates.and().apply(OBJ_0));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void test_or() throws Exception {
        /*
            public static <T> Predicate<T> or(Iterable<? extends Predicate<? super T>> components)
            public static <T> Predicate<T> or(Predicate<? super T>... components)
            public static <T> Predicate<T> or(Predicate<? super T> first, Predicate<? super T> second)
            Returns a predicate that evaluates to true if any one of its components evaluates to true. The components
            are evaluated in order, and evaluation will be "short-circuited" as soon as a true predicate is found. It
            defensively copies the array passed in, so future changes to it won't alter the behavior of this predicate.
            If components is empty, the returned predicate will always evaluate to false.
         */
        Predicate<Integer> predGreaterThanThree = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer integer) {
                return integer > 3;
            }
        };
        Predicate<Integer> predLessThanOne = new Predicate<Integer>() {
            @Override
            public boolean apply(Integer integer) {
                return integer < 1;
            }
        };
        Predicate<Integer> pred = Predicates.or(predGreaterThanThree, predLessThanOne);
        Assert.assertTrue(pred.apply(0));
        Assert.assertFalse(pred.apply(1));
        Assert.assertFalse(pred.apply(2));
        Assert.assertFalse(pred.apply(3));
        Assert.assertTrue(pred.apply(4));

        Assert.assertFalse(Predicates.or().apply(OBJ_0));
    }

    @Test
    public void test_not() throws Exception {
        /*
            public static <T> Predicate<T> not(Predicate<T> predicate)
            Returns a predicate that evaluates to true if the given predicate evaluates to false.
         */
        Assert.assertFalse(Predicates.not(Predicates.alwaysTrue()).apply(OBJ_0));
        Assert.assertTrue(Predicates.not(Predicates.alwaysFalse()).apply(OBJ_0));
    }
}
