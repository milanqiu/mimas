package net.milanqiu.mimas.guava.base;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import net.milanqiu.mimas.instrumentation.DebugUtils;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2014-07-16
 * @author Milan Qiu
 */
public class OptionalTest {

    /*
        Optional<T> is a way of replacing a nullable T reference with a non-null value. An Optional may either contain
        a non-null T reference (in which case we say the reference is "present"), or it may contain nothing (in which
        case we say the reference is "absent"). It is never said to "contain null."
     */

    @Test
    public void test_of_get() throws Exception {
        /*
            Optional.of(T)
            Make an Optional containing the given non-null value, or fail fast on null.

            T get()
            Returns the contained T instance, which must be present; otherwise, throws an IllegalStateException.
         */
        Optional<String> o = Optional.of(STR_0);
        Assert.assertEquals(STR_0, o.get());

        try {
            Optional.of(null);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(NullPointerException.class, e);
        }
    }

    @Test
    public void test_absent_get() throws Exception {
        /*
            Optional.absent()
            Return an absent Optional of some type.

            T get()
            Returns the contained T instance, which must be present; otherwise, throws an IllegalStateException.
         */
        Optional<String> o = Optional.absent();

        try {
            o.get();
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(IllegalStateException.class, e);
        }
    }

    @Test
    public void test_fromNullable() throws Exception {
        /*
            Optional.fromNullable(T)
            Turn the given possibly-null reference into an Optional, treating non-null as present and null as absent.
         */
        Optional<String> o = Optional.fromNullable(STR_0);
        Assert.assertEquals(Optional.of(STR_0), o);

        o = Optional.fromNullable(null);
        Assert.assertEquals(Optional.absent(), o);
    }

    @Test
    public void test_isPresent() throws Exception {
        /*
            boolean isPresent()
            Returns true if this Optional contains a non-null instance.
         */
        Assert.assertTrue(Optional.of(STR_0).isPresent());
        Assert.assertFalse(Optional.absent().isPresent());
    }

    @Test
    public void test_or() throws Exception {
        /*
            T or(T)
            Returns the present value in this Optional, or if t here is none, returns the specified default.
         */
        Assert.assertEquals(STR_0, Optional.of(STR_0).or(STR_1));
        Assert.assertEquals(STR_1, Optional.absent().or(STR_1));
    }

    @Test
    public void test_orNull() throws Exception {
        /*
            T orNull()
            Returns the present value in this Optional, or if there is none, returns null. The inverse operation of fromNullable.
         */
        Assert.assertEquals(STR_0, Optional.of(STR_0).orNull());
        Assert.assertEquals(null, Optional.absent().orNull());
    }

    @Test
    public void test_asSet() throws Exception {
        /*
            Set<T> asSet()
            Returns an immutable singleton Set containing the instance in this Optional, if there is one, or otherwise an empty immutable set.
         */
        Set<String> s = Optional.of(STR_0).asSet();
        Assert.assertEquals(1, s.size());
        Assert.assertTrue(s.contains(STR_0));

        Set<Object> s2 = Optional.absent().asSet();
        Assert.assertTrue(s2.isEmpty());
    }

    @Test
    public void test_transform() throws Exception {
        Function<Integer, String> func = new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) {
                if (integer == INT_4)
                    return null;
                else
                    return integer.toString();
            }
        };

        Optional<String> o = Optional.of(INT_0).transform(func);
        Assert.assertEquals(STR_OF_INT_0, o.get());

        Optional<Integer> absent = Optional.absent();
        o = absent.transform(func);
        Assert.assertEquals(Optional.absent(), o);

        try {
            Optional.of(INT_4).transform(func);
            DebugUtils.neverGoesHere();
        } catch (Exception e) {
            AssertExt.assertClassification(NullPointerException.class, e);
        }
    }
}
