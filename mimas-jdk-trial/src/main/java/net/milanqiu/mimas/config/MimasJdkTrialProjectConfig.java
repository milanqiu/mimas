package net.milanqiu.mimas.config;

import net.milanqiu.mimas.runtime.MavenProjectConvention;
import net.milanqiu.mimas.runtime.RuntimeUtils;

/**
 * Conventional properties and running environment of this project(mimas-jdk-trial).
 * <p>
 * Creation Date: 2014-11-27
 * @author Milan Qiu
 */
public class MimasJdkTrialProjectConfig extends MavenProjectConvention {

    private static MimasJdkTrialProjectConfig singleton;

    public static MimasJdkTrialProjectConfig getSingleton() {
        if (singleton == null) {
            singleton = new MimasJdkTrialProjectConfig();
        }
        return singleton;
    }

    private MimasJdkTrialProjectConfig() {}

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
        return "mimas-jdk-trial";
    }
}
