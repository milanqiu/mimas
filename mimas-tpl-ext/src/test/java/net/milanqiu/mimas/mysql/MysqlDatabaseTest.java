package net.milanqiu.mimas.mysql;

import net.milanqiu.mimas.config.MimasProperties;
import net.milanqiu.mimas.db.DatabaseTest;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;

/**
 * Creation Date: 2017-04-18
 * @author Milan Qiu
 */
public class MysqlDatabaseTest extends DatabaseTest {

    @Before
    public void setUp() throws Exception {
        if (!MimasProperties.getSingleton().isNeedTestDatabase())
            return;
        db = new MysqlDatabase(MimasProperties.getSingleton().getDbProfile());
    }

    @Test
    @Override
    public void test_allocateConnection_releaseConnection() throws Exception {
        super.test_allocateConnection_releaseConnection();
    }

    @Test
    public void test_DatabaseParams() throws Exception {
        if (!MimasProperties.getSingleton().isNeedTestDatabase())
            return;

        Connection conn = null;
        try {
            ((MysqlDatabase) db).getDbProfile().setParams("useSSL=true&serverTimezone=UTC&useUnicode=true&characterEncoding=gb2312");
            conn = db.allocateConnection();
            conn.createStatement().execute("CREATE TABLE t_temp (temp_id int(11) NOT NULL, temp_name varchar(50) NOT NULL, PRIMARY KEY (temp_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8");
            conn.createStatement().execute("DROP TABLE IF EXISTS t_temp");
        } finally {
            db.releaseConnection(conn);
        }
    }
}
