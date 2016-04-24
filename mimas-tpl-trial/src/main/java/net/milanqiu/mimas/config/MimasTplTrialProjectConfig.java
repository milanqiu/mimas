package net.milanqiu.mimas.config;

import net.milanqiu.mimas.runtime.MavenProjectConvention;
import net.milanqiu.mimas.runtime.RuntimeUtils;

/**
 * Conventional properties and running environment of this project(mimas-tpl-trial).
 * <p>
 * Creation Date: 2014-11-03
 * @author Milan Qiu
 */
public class MimasTplTrialProjectConfig extends MavenProjectConvention {

    private static MimasTplTrialProjectConfig singleton;

    public static MimasTplTrialProjectConfig getSingleton() {
        if (singleton == null) {
            singleton = new MimasTplTrialProjectConfig();
        }
        return singleton;
    }

    private MimasTplTrialProjectConfig() {}

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
        return "mimas-tpl-trial";
    }
}
