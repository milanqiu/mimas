package net.milanqiu.mimas.config;

import net.milanqiu.mimas.runtime.MavenProjectConvention;
import net.milanqiu.mimas.runtime.RuntimeUtils;

/**
 * Conventional properties and running environment of this project(mimas-jdk-ext).
 * <p>
 * Creation Date: 2014-11-03
 * @author Milan Qiu
 */
public class MimasJdkExtProjectConfig extends MavenProjectConvention {

    private static MimasJdkExtProjectConfig singleton;

    public static MimasJdkExtProjectConfig getSingleton() {
        if (singleton == null) {
            singleton = new MimasJdkExtProjectConfig();
        }
        return singleton;
    }

    private MimasJdkExtProjectConfig() {}

    @Override
    public String getSourceDir() {
        return RuntimeUtils.getClassSourceDir(this.getClass());
    }

    @Override
    public String getWorkspaceName() {
        return "mimas";
    }

    @Override
    public String getProjectName() {
        return "mimas-jdk-ext";
    }
}
