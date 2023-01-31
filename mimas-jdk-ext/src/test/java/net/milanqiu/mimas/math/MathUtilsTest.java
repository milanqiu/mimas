package net.milanqiu.mimas.math;

import net.milanqiu.mimas.collect.ArrayUtils;
import net.milanqiu.mimas.config.MimasJdkExtProjectConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2017-05-04
 * @author Milan Qiu
 */
public class MathUtilsTest {

    @Test
    public void test_randomInRange() throws Exception {
        int[] occurrence = ArrayUtils.duplicate(0, 20);
        for (int i = 0; i < 2000; i++) {
            if (i < 1000) {
                int r = MathUtils.randomInRange(11, 20);
                Assert.assertTrue(r >= 11 && r <= 20);
                occurrence[r-11]++;
            } else {
                int r = MathUtils.randomInRange(30, 21);
                Assert.assertTrue(r >= 21 && r <= 30);
                occurrence[r-11]++;
            }
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            Assert.assertTrue(occurrence[i] > 0);
            sb.append(i+11).append(":").append(occurrence[i]).append(System.lineSeparator());
        }
        MimasJdkExtProjectConfig.getSingleton().writeFileInTestOutDirUsingUtf8(sb.toString());
    }
}
