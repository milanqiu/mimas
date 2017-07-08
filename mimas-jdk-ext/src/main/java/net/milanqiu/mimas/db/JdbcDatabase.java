package net.milanqiu.mimas.db;

import net.milanqiu.mimas.instrumentation.exception.CodeContextException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A database using JDBC to connect.
 * <p>
 * Creation Date: 2017-04-18
 * @author Milan Qiu
 */
public abstract class JdbcDatabase extends AbstractDatabase {

    /**
     * Returns the database management system.
     * @return the database management system
     */
    public abstract Dbms getDbms();

    /**
     * The profile of database.
     */
    private DatabaseProfile dbProfile;

    /**
     * Returns the profile of database.
     * @return the profile of database
     */
    public DatabaseProfile getDbProfile() {
        return dbProfile;
    }
    /**
     * A setter corresponding to the getter {@link #getDbProfile()}.
     * @param dbProfile the profile of database
     */
    public void setDbProfile(DatabaseProfile dbProfile) {
        this.dbProfile = dbProfile.clone();
    }

    /**
     * Constructs a new {@code JdbcDatabase}. Just for inherited.
     */
    protected JdbcDatabase() {
        try {
            Class.forName(getDbms().getJdbcDriverClassName());
        } catch (ClassNotFoundException e) {
            throw new CodeContextException(e);
        }
        dbProfile = new DatabaseProfile();
    }

    /**
     * Constructs a new {@code JdbcDatabase} with the specified database profile. Just for inherited.
     * @param dbProfile the profile of database
     */
    protected JdbcDatabase(DatabaseProfile dbProfile) {
        this();
        setDbProfile(dbProfile);
    }

    @Override
    public Connection allocateConnection(boolean autoCommit) throws SQLException {
        Connection conn = DriverManager.getConnection(getDbms().getJdbcUrl(dbProfile), dbProfile.getUser(), dbProfile.getPassword());
        conn.setAutoCommit(autoCommit);
        return conn;
    }
}
