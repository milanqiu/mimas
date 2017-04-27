package net.milanqiu.mimas.mysql;

import net.milanqiu.mimas.config.MimasProperties;
import net.milanqiu.mimas.db.JdbcDatabase;
import net.milanqiu.mimas.junit.AssertExt;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Creation Date: 2017-04-18
 * @author Milan Qiu
 */
public class MysqlDatabaseTest {

    private JdbcDatabase db;

    @Before
    public void setUp() throws Exception {
        db = new MysqlDatabase(MimasProperties.getSingleton().getDbProfile());
    }

    @Test
    public void test_allocateConnection_releaseConnection() throws Exception {
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

    @Test
    public void test_setParams() throws Exception {
        Connection conn = null;
        try {
            db.setParams("");
            AssertExt.assertExceptionThrown(db::allocateConnection, SQLException.class);

            db.setParams("useSSL=true&serverTimezone=UTC&useUnicode=true&characterEncoding=gb2312");
            conn = db.allocateConnection();
            conn.createStatement().execute("CREATE TABLE t_temp (temp_id int(11) NOT NULL, temp_name varchar(50) NOT NULL, PRIMARY KEY (temp_id)) ENGINE=InnoDB DEFAULT CHARSET=utf8");
            conn.createStatement().execute("DROP TABLE IF EXISTS t_temp");
        } finally {
            db.releaseConnection(conn);
        }
    }
}
