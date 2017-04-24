package net.milanqiu.mimas.config;

import net.milanqiu.mimas.collect.LinkedProperties;

import java.io.File;
import java.io.IOException;

/**
 * Properties of this workspace(mimas).
 * <p>
 * Creation Date: 2017-04-19
 * @author Milan Qiu
 */
public class MimasProperties {

    private static MimasProperties singleton;

    public static MimasProperties getSingleton() {
        if (singleton == null) {
            singleton = new MimasProperties();
        }
        return singleton;
    }

    private MimasProperties() {
        File file = new File(MimasJdkExtProjectConfig.getSingleton().getFilesDir(), "Properties/mimas.properties");
        LinkedProperties properties = new LinkedProperties();
        try {
            properties.loadFromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        dbAddress = properties.getProperty("db_address", "localhost:3306");
        dbName = properties.getProperty("db_name", "mimas");
        dbUser = properties.getProperty("db_user", "root");
        dbPassword = properties.getProperty("db_password", "123456");
    }

    private String dbAddress;
    private String dbName;
    private String dbUser;
    private String dbPassword;

    public String getDbAddress() {
        return dbAddress;
    }
    public String getDbName() {
        return dbName;
    }
    public String getDbUser() {
        return dbUser;
    }
    public String getDbPassword() {
        return dbPassword;
    }
}
