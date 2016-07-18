package pt.iceman.carcpu.modules.output;

import pt.iceman.carcpu.interpreters.Command;

/**
 * Created by iceman on 18/07/16.
 */
public interface OutputModule {
    void sendCommand(Command command);

    static String getPackageName() {
        return OutputModule.class.getPackage().getName();
    }
}




