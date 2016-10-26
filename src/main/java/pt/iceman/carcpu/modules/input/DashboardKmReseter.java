package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;


/**
 * Created by iceman on 23/07/2016.
 */
public class DashboardKmReseter extends InputModule {
    public static final byte RESET_TRIP_KM = (byte) 0b1111_1111;

    public DashboardKmReseter(InputInterpreter inputInterpreter, Dashboard dashboard) {
        super(inputInterpreter, dashboard);
    }

    @Override
    public void setCommands() {
        commands.add(RESET_TRIP_KM);
    }

    @Override
    public void interpretCommand(Command command) {
        byte[] commandValues = command.getValues();
        if (commands.contains(commandValues[0])) {
            getDashboard().resetDistance();
            carSettings.setTripKilometers(0);
        }
    }
}
