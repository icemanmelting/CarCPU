package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;

/**
 * Created by iceman on 19/07/2016.
 */
public class Ignition extends InputModule{
    private static final byte IGNITION_OFF = (byte) 0b10101010;
    private static final byte IGNITION_ON = (byte) 0b10101011;

    public Ignition(InputInterpreter inputInterpreter, Dashboard dashboard) {
        super(inputInterpreter, dashboard);
    }

    @Override
    public void interpretCommand(Command command) {
        byte[] commandValues = command.getValues();
        if (commands.contains(commandValues[0])) {
            if (commandValues[0] == IGNITION_OFF) {
                inputInterpreter.getInputModules().forEach((c, o) -> {
                    o.resetValues();
                });
            } else {
                inputInterpreter.getInputModules().forEach((c, o) -> {
                    o.restart();
                });
            }
        }
    }

    @Override
    public void setCommands() {
        commands.add(IGNITION_OFF);
        commands.add(IGNITION_ON);
    }
}
