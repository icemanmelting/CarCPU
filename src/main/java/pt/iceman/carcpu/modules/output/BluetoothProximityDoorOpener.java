package pt.iceman.carcpu.modules.output;

import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.output.OutputInterpreter;

/**
 * Created by iceman on 18/07/16.
 */
public class BluetoothProximityDoorOpener extends OutputModule {
    public BluetoothProximityDoorOpener(OutputInterpreter outputInterpreter) {
        super(outputInterpreter.getCarData());
    }

    @Override
    public void sendCommand(Command command) {

    }
}
