package pt.iceman.carcpu.modules.input;

/**
 * Created by iceman on 17/07/2016.
 */
public interface InputModule {
    void interpretCommand();

    static String getPackageName() {
        return InputModule.class.getPackage().getName();
    }
}
