package net.milanqiu.mimas.mysql;

import net.milanqiu.mimas.db.DatabaseProfile;
import net.milanqiu.mimas.db.Dbms;
import net.milanqiu.mimas.db.JdbcDatabase;

/**
 * A MySQL database using JDBC to connect.
 * <p>
 * Creation Date: 2017-04-18
 * @author Milan Qiu
 */
public class MysqlDatabase extends JdbcDatabase {

    @Override
    public Dbms getDbms() {
        return Dbms.MYSQL;
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
