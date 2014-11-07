package net.milanqiu.mimas.system;

/**
 * Properties and running environment of this project(mimas-tpl-trial).
 *
 * <p>Creation Date: 2014-11-3
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
    public String getSourcePath() {
        return SysUtils.getClassSourcePath(MimasTplTrialConvention.class);
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
