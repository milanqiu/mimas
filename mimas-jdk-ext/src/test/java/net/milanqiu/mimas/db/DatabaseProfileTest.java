package net.milanqiu.mimas.db;

import org.junit.Assert;
import org.junit.Test;

import static net.milanqiu.mimas.instrumentation.TestConsts.*;

/**
 * Creation Date: 2017-05-03
 * @author Milan Qiu
 */
public class DatabaseProfileTest {

    @Test
    public void test_clone() throws Exception {
        DatabaseProfile dbProfile = new DatabaseProfile(STR_0, STR_1, STR_2, STR_3, STR_4);
        DatabaseProfile dbProfileCloned = dbProfile.clone();
        Assert.assertEquals(STR_0, dbProfileCloned.getAddress());
        Assert.assertEquals(STR_1, dbProfileCloned.getName());
        Assert.assertEquals(STR_2, dbProfileCloned.getUser());
        Assert.assertEquals(STR_3, dbProfileCloned.getPassword());
        Assert.assertEquals(STR_4, dbProfileCloned.getParams());

        dbProfile.setAddress(STR_1);
        dbProfile.setName(STR_2);
        dbProfile.setUser(STR_3);
        dbProfile.setPassword(STR_4);
        dbProfile.setParams(STR_0);
        Assert.assertEquals(STR_0, dbProfileCloned.getAddress());
        Assert.assertEquals(STR_1, dbProfileCloned.getName());
        Assert.assertEquals(STR_2, dbProfileCloned.getUser());
        Assert.assertEquals(STR_3, dbProfileCloned.getPassword());
        Assert.assertEquals(STR_4, dbProfileCloned.getParams());
    }
}
