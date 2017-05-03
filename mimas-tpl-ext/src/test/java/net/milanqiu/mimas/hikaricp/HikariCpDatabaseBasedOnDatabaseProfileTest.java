package net.milanqiu.mimas.hikaricp;

import net.milanqiu.mimas.config.MimasProperties;
import net.milanqiu.mimas.db.DatabaseTest;
import net.milanqiu.mimas.db.Dbms;
import org.junit.Before;
import org.junit.Test;

/**
 * Creation Date: 2017-05-03
 * @author Milan Qiu
 */
public class HikariCpDatabaseBasedOnDatabaseProfileTest extends DatabaseTest {

    @Before
    public void setUp() throws Exception {
        db = new HikariCpDatabase(Dbms.MYSQL, MimasProperties.getSingleton().getDbProfile());
    }

    @Test
    @Override
    public void test_allocateConnection_releaseConnection() throws Exception {
        super.test_allocateConnection_releaseConnection();
    }
}
