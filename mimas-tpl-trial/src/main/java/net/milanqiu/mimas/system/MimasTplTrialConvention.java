package net.milanqiu.mimas.system;

/**
 * Conventional properties and running environment of this project(mimas-jdk-trial).
 * <p>
 * Creation Date: 2014-11-3
 * @author Milan Qiu
 */
public class MimasTplTrialConvention extends MavenProjectConvention {

    private static MimasTplTrialConvention singleton;

    public static MimasTplTrialConvention getSingleton() {
        if (singleton == null) {
            singleton = new MimasTplTrialConvention();
        }
        return singleton;
    }

    private MimasTplTrialConvention() {}

    @Override
    public String getSourceDir() {
        return SysUtils.getClassSourceDir(this.getClass());
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
