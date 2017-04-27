package net.milanqiu.mimas.db;

import net.milanqiu.mimas.string.StrUtils;

/**
 * The profile of database, including address(composed of host and optional port), name, user and password.
 * <p>
 * Creation Date: 2017-04-26
 * @author Milan Qiu
 */
public class DatabaseProfile implements Cloneable {

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
     * Constructs a new {@code DatabaseProfile}.
     */
    public DatabaseProfile() {
    }

    /**
     * Constructs a new {@code DatabaseProfile} with the specified address, name, user and password..
     * @param address the address of database, composed of host and optional port
     * @param name the name of database
     * @param user the user to access database
     * @param password the password to access database
     */
    public DatabaseProfile(String address, String name, String user, String password) {
        this.address = address;
        this.name = name;
        this.user = user;
        this.password = password;
    }

    /**
     * Returns the database url used for jdbc.
     * @param protocol the jdbc protocol to access database
     * @return the database url used for jdbc
     */
    public String getJdbcUrl(String protocol) {
        return protocol + address + "/" + name;
    }

    /**
     * Returns the database url used for jdbc.
     * @param protocol the jdbc protocol to access database
     * @param params the parameters to access database
     * @return the database url used for jdbc
     */
    public String getJdbcUrl(String protocol, String params) {
        return protocol + address + "/" + name + StrUtils.addPrefixIfNotEmpty("?", params);
    }

    @Override
    public DatabaseProfile clone() {
        try {
            return (DatabaseProfile) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new InternalError(e);
        }
    }
}
