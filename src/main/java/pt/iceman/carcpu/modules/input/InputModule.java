package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.interpreters.Command;

/**
 * Created by iceman on 17/07/2016.
 */
public interface InputModule {
    void interpretCommand(Command command);

    static String getPackageName() {
        return InputModule.class.getPackage().getName();
    }
}
