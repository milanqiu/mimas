package net.milanqiu.mimas.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * This class provides a skeletal implementation of {@link Database} interface to minimize the effort required to
 * implement this interface backed.
 * <p>
 * Creation Date: 2017-04-26
 * @author Milan Qiu
 */
public abstract class AbstractDatabase implements Database {

    @Override
    public Connection allocateConnection() throws SQLException {
        return allocateConnection(true);
    }

    @Override
    public void releaseConnection(Connection conn) throws SQLException {
        if (conn != null)
            conn.close();
    }
}
