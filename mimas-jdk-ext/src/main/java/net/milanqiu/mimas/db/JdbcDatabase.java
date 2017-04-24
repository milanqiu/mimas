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
public abstract class JdbcDatabase implements Database {

    /**
     * Returns the jdbc driver for database.
     * @return the jdbc driver for database
     */
    public abstract String getDriver();

    /**
     * Returns the jdbc protocol for database.
     * @return the jdbc protocol for database
     */
    public abstract String getProtocol();

    /**
     * The address of database, composed of host and optional port.
     */
    private String address;
    /**
     * The name of database.
     */
    private String name;
    /**
     * The user to access database.
     */
    private String user;
    /**
     * The password to access database.
     */
    private String password;
    /**
     * The parameters to access database.
     */
    private String params;

    /**
     * Returns the address of database, composed of host and optional port.
     * @return the address of database, composed of host and optional port
     */
    public String getAddress() {
        return address;
    }
    /**
     * A setter corresponding to the getter {@link #getAddress()}.
     */
    public void setAddress(String address) {
        this.address = address;
    }
    /**
     * Returns the name of database.
     * @return the name of database
     */
    public String getName() {
        return name;
    }
    /**
     * A setter corresponding to the getter {@link #getName()}.
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * Returns the user to access database.
     * @return the user to access database
     */
    public String getUser() {
        return user;
    }
    /**
     * A setter corresponding to the getter {@link #getUser()}.
     */
    public void setUser(String user) {
        this.user = user;
    }
    /**
     * Returns the password to access database.
     * @return the password to access database
     */
    public String getPassword() {
        return password;
    }
    /**
     * A setter corresponding to the getter {@link #getPassword()}.
     */
    public void setPassword(String password) {
        this.password = password;
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
            Class.forName(getDriver());
        } catch (ClassNotFoundException e) {
            throw new CodeContextException(e);
        }
        params = getDefaultParams();
    }

    /**
     * Constructs a new {@code JdbcDatabase} with the specified address, name, user and password. Just for inherited.
     * @param address the address of mysql database, composed of host and optional port
     * @param name the name of mysql database
     * @param user the user to access mysql database
     * @param password the password to access mysql database
     */
    protected JdbcDatabase(String address, String name, String user, String password) {
        this();
        this.address = address;
        this.name = name;
        this.user = user;
        this.password = password;
    }

    @Override
    public Connection allocateConnection() throws SQLException {
        return allocateConnection(true);
    }

    @Override
    public Connection allocateConnection(boolean autoCommit) throws SQLException {
        String url = getProtocol() + address + "/" + name + StrUtils.addPrefixIfNotEmpty("?", params);
        Connection conn = DriverManager.getConnection(url, user, password);
        conn.setAutoCommit(autoCommit);
        return conn;
    }

    @Override
    public void releaseConnection(Connection conn) throws SQLException {
        if (conn != null)
            conn.close();
    }
}
