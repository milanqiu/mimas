package net.milanqiu.mimas.mysql;

import net.milanqiu.mimas.db.JdbcDatabase;

/**
 * A mysql database using JDBC to connect.
 * <p>
 * Creation Date: 2017-04-18
 * @author Milan Qiu
 */
public class MysqlDatabase extends JdbcDatabase {

    @Override
    public String getDriver() {
        return "com.mysql.cj.jdbc.Driver";
    }

    @Override
    public String getProtocol() {
        return "jdbc:mysql://";
    }

    @Override
    public String getDefaultParams() {
        return "useSSL=true&serverTimezone=UTC";
    }

    /**
     * Constructs a new {@code MysqlDatabase}.
     */
    public MysqlDatabase() {
        super();
    }

    /**
     * Constructs a new {@code MysqlDatabase} with the specified address, name, user and password.
     * @param address the address of mysql database, composed of host and optional port
     * @param name the name of mysql database
     * @param user the user to access mysql database
     * @param password the password to access mysql database
     */
    public MysqlDatabase(String address, String name, String user, String password) {
        super(address, name, user, password);
    }
}
