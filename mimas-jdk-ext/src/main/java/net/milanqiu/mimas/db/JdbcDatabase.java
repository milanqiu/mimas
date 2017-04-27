package net.milanqiu.mimas.db;

import net.milanqiu.mimas.instrumentation.exception.CodeContextException;
import net.milanqiu.mimas.string.StrUtils;

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
     * Returns the jdbc protocol to access database.
     * @return the jdbc protocol to access database
     */
    public abstract String getProtocol();

    /**
     * The profile of database.
     */
    private DatabaseProfile dbProfile;
    /**
     * The parameters to access database.
     */
    private String params;

    /**
     * Returns the profile of database.
     * @return the profile of database
     */
    public DatabaseProfile getDbProfile() {
        return dbProfile;
    }
    /**
     * A setter corresponding to the getter {@link #getDbProfile()}.
     */
    public void setDbProfile(DatabaseProfile dbProfile) {
        this.dbProfile = dbProfile.clone();
    }
    /**
     * Returns the parameters to access database.
     * @return the parameters to access database
     */
    public String getParams() {
        return params;
    }
    /**
     * A setter corresponding to the getter {@link #getParams()}.
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * Returns the default parameters to access database.
     * @return the default parameters to access database
     */
    public String getDefaultParams() {
        return StrUtils.STR_EMPTY;
    }

    /**
     * Constructs a new {@code JdbcDatabase}. Just for inherited.
     */
    protected JdbcDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new CodeContextException(e);
        }
        dbProfile = new DatabaseProfile();
        params = getDefaultParams();
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
        Connection conn = DriverManager.getConnection(dbProfile.getJdbcUrl(getProtocol(), getParams()), dbProfile.getUser(), dbProfile.getPassword());
        conn.setAutoCommit(autoCommit);
        return conn;
    }
}
