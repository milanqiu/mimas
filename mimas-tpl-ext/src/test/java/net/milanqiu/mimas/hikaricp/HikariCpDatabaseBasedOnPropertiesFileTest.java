package net.milanqiu.mimas.hikaricp;

import net.milanqiu.mimas.config.MimasJdkExtProjectConfig;
import net.milanqiu.mimas.config.MimasProperties;
import net.milanqiu.mimas.db.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

/**
 * Creation Date: 2017-04-26
 * @author Milan Qiu
 */
public class HikariCpDatabaseBasedOnPropertiesFileTest extends DatabaseTest {

    @Before
    public void setUp() throws Exception {
        if (!MimasProperties.getSingleton().isNeedTestDatabase())
            return;
        db = new HikariCpDatabase(MimasJdkExtProjectConfig.getSingleton().getFilesDir() + "/Properties/HikariCP.properties");
    }

    @Test
    @Override
    public void test_allocateConnection_releaseConnection() throws Exception {
        super.test_allocateConnection_releaseConnection();
    }
}
