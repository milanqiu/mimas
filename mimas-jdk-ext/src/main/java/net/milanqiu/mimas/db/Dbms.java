package net.milanqiu.mimas.db;

import net.milanqiu.mimas.string.StrUtils;

/**
 * Database management system explicitly supported by mimas.
 * <p>
 * Creation Date: 2017-05-03
 * @author Milan Qiu
 */
public enum Dbms {

    /**
     * MySQL.
     */
    MYSQL {
        @Override
        public String getJdbcDriverClassName() {
            return "com.mysql.cj.jdbc.Driver";
        }

        @Override
        public String getJdbcProtocol() {
            return "jdbc:mysql://";
        }
    };

    /**
     * Returns the jdbc driver class name to access database.
     * @return the jdbc driver class name to access database
     */
    public abstract String getJdbcDriverClassName();

    /**
     * Returns the jdbc protocol to access database.
     * @return the jdbc protocol to access database
     */
    public abstract String getJdbcProtocol();

    /**
     * Returns the database url used by jdbc.
     * @param dbProfile the profile of database to be accessed
     * @return the database url used by jdbc
     */
    public String getJdbcUrl(DatabaseProfile dbProfile) {
        return getJdbcProtocol() + dbProfile.getAddress() + "/" + dbProfile.getName() + StrUtils.addPrefixIfNotEmpty("?", dbProfile.getParams());
    }
}
