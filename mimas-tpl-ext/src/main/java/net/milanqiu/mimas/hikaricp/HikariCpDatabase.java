package net.milanqiu.mimas.hikaricp;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import net.milanqiu.mimas.db.AbstractDatabase;
import net.milanqiu.mimas.db.DatabaseProfile;
import net.milanqiu.mimas.db.Dbms;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * A database using Hikari Connection Pool to connect.
 * <p>
 * Creation Date: 2017-04-25
 * @author Milan Qiu
 */
public class HikariCpDatabase extends AbstractDatabase {

    private HikariDataSource dataSource;

    /**
     * Constructs a new {@code HikariCpDatabase} with the specified DBMS and database profile.
     * @param dbms the database management system
     * @param dbProfile the profile of database
     */
    public HikariCpDatabase(Dbms dbms, DatabaseProfile dbProfile) {
        dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dbms.getJdbcUrl(dbProfile));
        dataSource.setUsername(dbProfile.getUser());
        dataSource.setPassword(dbProfile.getPassword());
    }

    /**
     * Constructs a new {@code HikariCpDatabase} with the specified Hikari configuration.
     * @param config the Hikari configuration
     */
    public HikariCpDatabase(HikariConfig config) {
        dataSource = new HikariDataSource(config);
    }

    /**
     * Constructs a new {@code HikariCpDatabase} with the specified properties file.
     * @param propertiesFileName the properties file name
     */
    public HikariCpDatabase(String propertiesFileName) {
        HikariConfig config = new HikariConfig(propertiesFileName);
        dataSource = new HikariDataSource(config);
    }

    @Override
    public Connection allocateConnection(boolean autoCommit) throws SQLException {
        Connection conn = dataSource.getConnection();
        conn.setAutoCommit(autoCommit);
        return conn;
    }
}
