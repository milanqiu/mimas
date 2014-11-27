package net.milanqiu.mimas.system;

/**
 * Conventional properties and running environment of this project(mimas-tpl-ext).
 * <p>
 * Creation Date: 2014-11-27
 * @author Milan Qiu
 */
public class MimasTplExtConvention extends MavenProjectConvention {

    private static MimasTplExtConvention singleton;

    public static MimasTplExtConvention getSingleton() {
        if (singleton == null) {
            singleton = new MimasTplExtConvention();
        }
        return singleton;
    }

    private MimasTplExtConvention() {}

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
        return "mimas-tpl-ext";
    }
}
