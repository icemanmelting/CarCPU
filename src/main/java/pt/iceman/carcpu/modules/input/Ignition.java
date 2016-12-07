package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;
import pt.iceman.cardata.CarData;
import pt.iceman.cardata.log.CarTrip;

import java.io.IOException;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * Created by iceman on 19/07/2016.
 */
public class Ignition extends InputModule {
    private static final byte IGNITION_OFF = (byte) 0b10101010;
    private static final byte IGNITION_ON = (byte) 0b10101011;
    private static final byte TURN_OFF = (byte) 0b10101000;
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

                inputInterpreter.getCarTrip().setEndTime(new Date());
                inputInterpreter.getCarTrip().setEndingKm(getDashboard().getTotalDistance());

                double tripLength = inputInterpreter.getCarTrip().getEndingKm() - inputInterpreter.getCarTrip().getStartingKm();

                if (tripLength > 0) {
                    inputInterpreter.getCarTrip().setTripLengthKm(tripLength);

                    long tripDuration = inputInterpreter.getCarTrip().getEndTime().getTime() - inputInterpreter.getCarTrip().getStartTime().getTime();
                    inputInterpreter.getCarTrip().setTripDuration(tripDuration);

                    double tripdurationSeconds = TimeUnit.MILLISECONDS.toSeconds((long) inputInterpreter.getCarTrip().getTripDuration());
                    double tripDurationHours = tripdurationSeconds / 3600;
                    double speedAverage = inputInterpreter.getCarTrip().getTripLengthKm() / tripDurationHours;
                    inputInterpreter.getCarTrip().setAverageSpeed(speedAverage);

                    carData.updateSettings(carSettings);
                    carData.insertTrip(inputInterpreter.getCarTrip());
                }

                createInfoMessage(carData, "Ignition turned off");
            } else if (commandValues[0] == IGNITION_ON) {
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

                createInfoMessage(carData, "Ignition turned on");

                CarTrip carTrip = new CarTrip();
                carTrip.setStartingKm(getDashboard().getTotalDistance());
                carTrip.setStartTime(new Date());

                inputInterpreter.setCarTrip(carTrip);
            } else if (commandValues[0] == TURN_OFF) {
                try {
                    Runtime.getRuntime().exec("/etc/init.d/turnOff.sh");
                } catch (IOException e) {
                    createErrorMessage(carData, "Could not read script to turn cpu off ");
                }
            }
        }
    }

    @Override
    public void setCommands() {
        commands.add(IGNITION_OFF);
        commands.add(IGNITION_ON);
        commands.add(TURN_OFF);
    }
}
