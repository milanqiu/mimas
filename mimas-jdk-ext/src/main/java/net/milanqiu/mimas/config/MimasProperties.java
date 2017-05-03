package net.milanqiu.mimas.config;

import net.milanqiu.mimas.collect.LinkedProperties;
import net.milanqiu.mimas.db.DatabaseProfile;

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

        dbProfile = new DatabaseProfile();
        dbProfile.setAddress(properties.getProperty("db_address", "localhost:3306"));
        dbProfile.setName(properties.getProperty("db_name", "mimas"));
        dbProfile.setUser(properties.getProperty("db_user", "root"));
        dbProfile.setPassword(properties.getProperty("db_password", "123456"));
        dbProfile.setParams(properties.getProperty("db_params", "useSSL=true&serverTimezone=UTCn"));
    }

    private DatabaseProfile dbProfile;

    public DatabaseProfile getDbProfile() {
        return dbProfile;
    }
}
