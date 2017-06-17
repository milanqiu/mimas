package net.milanqiu.mimas.db;

import net.milanqiu.mimas.string.StrUtils;

/**
 * The profile of database, including address(composed of host and optional port), name, user, password and connection parameters.
 * <p>
 * Creation Date: 2017-04-26
 * @author Milan Qiu
 */
public class DatabaseProfile implements Cloneable {

    /**
     * The address of database, composed of host and optional port.
     */
    private String address = StrUtils.STR_EMPTY;
    /**
     * The name of database.
     */
    private String name = StrUtils.STR_EMPTY;
    /**
     * The user to access database.
     */
    private String user = StrUtils.STR_EMPTY;
    /**
     * The password to access database.
     */
    private String password = StrUtils.STR_EMPTY;
    /**
     * The connection parameters to access database.
     */
    private String params = StrUtils.STR_EMPTY;

    /**
     * Returns the address of database, composed of host and optional port.
     * @return the address of database, composed of host and optional port
     */
    public String getAddress() {
        return address;
    }
    /**
     * A setter corresponding to the getter {@link #getAddress()}.
     * @param address the address of database, composed of host and optional port
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
     * @param name the name of database
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
     * @param user the user to access database
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
     * @param password the password to access database
     */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
     * Returns the connection parameters to access database.
     * @return the connection parameters to access database
     */
    public String getParams() {
        return params;
    }
    /**
     * A setter corresponding to the getter {@link #getParams()}.
     * @param params the connection parameters to access database
     */
    public void setParams(String params) {
        this.params = params;
    }

    /**
     * Constructs a new {@code DatabaseProfile}.
     */
    public DatabaseProfile() {
    }

    /**
     * Constructs a new {@code DatabaseProfile} with the specified address, name, user and password.
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
     * Constructs a new {@code DatabaseProfile} with the specified address, name, user, password and connection parameters.
     * @param address the address of database, composed of host and optional port
     * @param name the name of database
     * @param user the user to access database
     * @param password the password to access database
     * @param params the connection parameters to access database
     */
    public DatabaseProfile(String address, String name, String user, String password, String params) {
        this.address = address;
        this.name = name;
        this.user = user;
        this.password = password;
        this.params = params;
    }

    /**
     * Returns a string representation of this object.
     * The string representation consists of a list of properties, enclosed in braces("{}").
     * Adjacent properties are separated by comma and space(", ").
     * The result may be <i><tt>DatabaseProfile{address=a, name=b, user=c, password=d, params=e}</tt></i>.
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        return "DatabaseProfile{" +
                "address=" + address +
                ", name=" + name +
                ", user=" + user +
                ", password=" + password +
                ", params=" + params +
                '}';
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
