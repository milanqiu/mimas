package net.milanqiu.mimas.db;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A general database interface to allocate and release connections.
 * <p>
 * Creation Date: 2015-02-17
 * @author Milan Qiu
 */
public interface Database {

    /**
     * Allocates a {@link java.sql.Connection} in auto-commit mode.
     * It may be a new instance or an available earlier created instance.
     * @return the allocated {@link java.sql.Connection}
     * @throws SQLException if a database access error occurs
     */
    Connection allocateConnection() throws SQLException;

    /**
     * Allocates a {@link java.sql.Connection} and sets this connection's auto-commit mode to the given state.
     * It may be a new instance or an available earlier created instance.
     * @param autoCommit {@code true} to enable auto-commit mode, {@code false} to disable it
     * @return the allocated {@link java.sql.Connection}
     * @throws SQLException if a database access error occurs
     */
    Connection allocateConnection(boolean autoCommit) throws SQLException;

    /**
     * Releases the specified {@link java.sql.Connection}.
     * @param conn the {@link java.sql.Connection} to be released
     * @throws SQLException if a database access error occurs
     */
    void releaseConnection(Connection conn) throws SQLException;
}
