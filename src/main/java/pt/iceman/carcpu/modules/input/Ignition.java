package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;
import pt.iceman.cardata.CarData;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by iceman on 19/07/2016.
 */
public class Ignition extends InputModule {
    public static final byte IGNITION_OFF = (byte) 0b10101010;
    public static final byte IGNITION_ON = (byte) 0b10101011;
    private Timer timer;

    public Ignition(InputInterpreter inputInterpreter, Dashboard dashboard) {
        super(inputInterpreter, dashboard);
    }

    @Override
    public void interpretCommand(Command command) {
        byte[] commandValues = command.getValues();
        if (commands.contains(commandValues[0])) {
            if (commandValues[0] == IGNITION_OFF) {
                timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            Runtime.getRuntime().exec("/etc/init.d/shutdownScreen.sh");
                        } catch (IOException e) {
                            createErrorMessage(carData, "Could not read script to shutdown screen");
                        }
                    }
                }, 5000);
                inputInterpreter.getInputModules().forEach((c, o) -> o.resetValues());
                inputInterpreter.setIgnition(false);
                carData.executeDbCommand(CarData.DBCommand.CARSETTINGSW, carSettings);

                createInfoMessage(carData, "Car turned off");

            } else {
                if (timer != null) {
                    timer.cancel();
                }
                try {
                    Runtime.getRuntime().exec("/etc/init.d/turnonscreen.sh");
                } catch (IOException e) {
                    createErrorMessage(carData, "Could not read script to turn on screen");
                }
                inputInterpreter.getInputModules().forEach((c, o) -> o.restart());
                inputInterpreter.setIgnition(true);

                createInfoMessage(carData, "Car turned on");
            }
        }
    }

    @Override
    public void setCommands() {
        commands.add(IGNITION_OFF);
        commands.add(IGNITION_ON);
    }
}
