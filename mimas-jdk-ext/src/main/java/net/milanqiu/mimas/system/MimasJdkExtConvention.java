package net.milanqiu.mimas.system;

/**
 * Properties and running environment of this project(mimas-jdk-ext).
 *
 * <p>Creation Date: 2014-11-3
 * @author Milan Qiu
 */
public class MimasJdkExtConvention extends MavenProjectConvention {

    private static MimasJdkExtConvention singleton;

    public static MimasJdkExtConvention getSingleton() {
        if (singleton == null) {
            singleton = new MimasJdkExtConvention();
        }
        return singleton;
    }

    private MimasJdkExtConvention() {}

    @Override
    public String getSourcePath() {
        return SysUtils.getClassSourcePath(MimasJdkExtConvention.class);
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
