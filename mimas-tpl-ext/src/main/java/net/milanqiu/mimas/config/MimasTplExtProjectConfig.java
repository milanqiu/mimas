package net.milanqiu.mimas.config;

import net.milanqiu.mimas.runtime.MavenProjectConvention;
import net.milanqiu.mimas.runtime.RuntimeUtils;

/**
 * Conventional properties and running environment of this project(mimas-tpl-ext).
 * <p>
 * Creation Date: 2014-11-27
 * @author Milan Qiu
 */
public class MimasTplExtProjectConfig extends MavenProjectConvention {

    private static MimasTplExtProjectConfig singleton;

    public static MimasTplExtProjectConfig getSingleton() {
        if (singleton == null) {
            singleton = new MimasTplExtProjectConfig();
        }
        return singleton;
    }

    private MimasTplExtProjectConfig() {}

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
        return "mimas-tpl-ext";
    }
}
