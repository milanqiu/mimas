package net.milanqiu.mimas.lang;

import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2017-05-29
 * @author Milan Qiu
 */
public class LangUtilsTest {

    @Test
    public void test_choose() throws Exception {
        // int choose(int participantA, int participantB, int valueIfGreater, int valueIfEqual, int valueIfLess)
        Assert.assertEquals(INT_0, LangUtils.choose(2, 1, INT_0, INT_1, INT_2));
        Assert.assertEquals(INT_1, LangUtils.choose(1, 1, INT_0, INT_1, INT_2));
        Assert.assertEquals(INT_2, LangUtils.choose(1, 2, INT_0, INT_1, INT_2));

        // <T> T choose(int participantA, int participantB, T valueIfGreater, T valueIfEqual, T valueIfLess)
        Assert.assertEquals(OBJ_0, LangUtils.choose(2, 1, OBJ_0, OBJ_1, OBJ_2));
        Assert.assertEquals(OBJ_1, LangUtils.choose(1, 1, OBJ_0, OBJ_1, OBJ_2));
        Assert.assertEquals(OBJ_2, LangUtils.choose(1, 2, OBJ_0, OBJ_1, OBJ_2));
    }

    @Test
    public void test_dualControl() throws Exception {
        Pointer<Integer> value = new Pointer<>(0);

        LangUtils.dualControl(true, true, ()->{value.set(1);}, ()->{value.set(2);}, ()->{value.set(3);}, ()->{value.set(4);});
        Assert.assertEquals(1, (int) value.get());

        LangUtils.dualControl(true, false, ()->{value.set(1);}, ()->{value.set(2);}, ()->{value.set(3);}, ()->{value.set(4);});
        Assert.assertEquals(2, (int) value.get());

        LangUtils.dualControl(false, true, ()->{value.set(1);}, ()->{value.set(2);}, ()->{value.set(3);}, ()->{value.set(4);});
        Assert.assertEquals(3, (int) value.get());

        LangUtils.dualControl(false, false, ()->{value.set(1);}, ()->{value.set(2);}, ()->{value.set(3);}, ()->{value.set(4);});
        Assert.assertEquals(4, (int) value.get());
    }
}
