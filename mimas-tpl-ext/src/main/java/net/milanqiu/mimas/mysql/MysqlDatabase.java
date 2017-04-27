package net.milanqiu.mimas.mysql;

import net.milanqiu.mimas.db.DatabaseProfile;
import net.milanqiu.mimas.db.JdbcDatabase;

/**
 * A mysql database using JDBC to connect.
 * <p>
 * Creation Date: 2017-04-18
 * @author Milan Qiu
 */
public class MysqlDatabase extends JdbcDatabase {

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
     * Constructs a new {@code MysqlDatabase} with the specified database profile.
     * @param dbProfile the profile of database
     */
    public MysqlDatabase(DatabaseProfile dbProfile) {
        super(dbProfile);
    }
}
