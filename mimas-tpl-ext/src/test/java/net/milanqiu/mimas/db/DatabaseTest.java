package net.milanqiu.mimas.db;

import net.milanqiu.mimas.config.MimasProperties;
import org.junit.Assert;

import java.sql.Connection;

/**
 * Creation Date: 2017-05-03
 * @author Milan Qiu
 */
public abstract class DatabaseTest {

    protected Database db;

    public void test_allocateConnection_releaseConnection() throws Exception {
        if (!MimasProperties.getSingleton().isNeedTestDatabase())
            return;

        Connection connAutoCommit = null;
        Connection connNoAutoCommit = null;
        try {
            connAutoCommit = db.allocateConnection();
            connNoAutoCommit = db.allocateConnection(false);

            connAutoCommit.createStatement().execute("CREATE TABLE t_temp (temp_id int(11) NOT NULL, temp_name varchar(50) NOT NULL, PRIMARY KEY (temp_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8");

            connAutoCommit.createStatement().execute("INSERT INTO t_temp VALUES (1, 'a')");
            Assert.assertTrue(connNoAutoCommit.createStatement().executeQuery("SELECT * FROM t_temp WHERE temp_id = 1").next());

            connNoAutoCommit.createStatement().execute("INSERT INTO t_temp VALUES (2, 'b')");
            Assert.assertFalse(connAutoCommit.createStatement().executeQuery("SELECT * FROM t_temp WHERE temp_id = 2").next());
            connNoAutoCommit.commit();
            Assert.assertTrue(connAutoCommit.createStatement().executeQuery("SELECT * FROM t_temp WHERE temp_id = 2").next());

            connAutoCommit.createStatement().execute("DROP TABLE IF EXISTS t_temp");
        } finally {
            db.releaseConnection(connAutoCommit);
            db.releaseConnection(connNoAutoCommit);
        }
    }
}
