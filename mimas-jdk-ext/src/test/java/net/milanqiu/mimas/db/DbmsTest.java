package net.milanqiu.mimas.db;

import org.junit.Assert;
import org.junit.Test;

/**
 * Creation Date: 2017-05-03
 * @author Milan Qiu
 */
public class DbmsTest {

    @Test
    public void test_getJdbcUrl() throws Exception {
        DatabaseProfile dbProfile = new DatabaseProfile("address", "name", "user", "password");
        Assert.assertEquals("jdbc:mysql://address/name", Dbms.MYSQL.getJdbcUrl(dbProfile));

        dbProfile.setParams("params");
        Assert.assertEquals("jdbc:mysql://address/name?params", Dbms.MYSQL.getJdbcUrl(dbProfile));
    }
}
