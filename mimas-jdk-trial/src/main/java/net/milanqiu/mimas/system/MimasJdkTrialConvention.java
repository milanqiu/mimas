package net.milanqiu.mimas.system;

/**
 * Conventional properties and running environment of this project(mimas-jdk-trial).
 * <p>
 * Creation Date: 2014-11-27
 * @author Milan Qiu
 */
public class MimasJdkTrialConvention extends MavenProjectConvention {

    private static MimasJdkTrialConvention singleton;

    public static MimasJdkTrialConvention getSingleton() {
        if (singleton == null) {
            singleton = new MimasJdkTrialConvention();
        }
        return singleton;
    }

    private MimasJdkTrialConvention() {}

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
        return "mimas-jdk-trial";
    }
}
