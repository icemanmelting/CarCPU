package pt.iceman.carcpu.modules.output;

/**
 * Created by iceman on 18/07/16.
 */
public interface OutputModule {
    void sendCommand();

    static String getPackageName() {
        return OutputModule.class.getPackage().getName();
    }
}




