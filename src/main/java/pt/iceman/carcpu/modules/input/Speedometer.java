package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;
import pt.iceman.cardata.CarData;
import pt.iceman.cardata.utils.CustomEntry;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by iceman on 17/07/2016.
 */
public class Speedometer extends InputModule {
    private static final float CAR_TYRE_CIRCUNFERENCE_M = 1.81f;
    public static final byte SPEED_PULSE = (byte) 0b1011_0000;

    private double speed = 0;
    private int speedCounter = 0;
    private Timer speedDataTimer;

    public enum Type {
        RESET("reset"),
        INCREASE("increase");

        private String fieldValue;

        Type(String fieldValue) {
            this.fieldValue = fieldValue;
        }

        public String getFieldValue() {
            return fieldValue;
        }
    }

    public Speedometer(InputInterpreter inputInterpreter, Dashboard dashboard) {
        super(inputInterpreter, dashboard);
    }

    @Override
    public void interpretCommand(Command command) {
        if (inputInterpreter.isIgnition()) {
            byte[] commandValues = command.getValues();
            if (commands.contains(commandValues[0])) {
                byte firstSpeedFrequencyByte;
                byte secondSpeedFrequencyByte;
                try {
                    firstSpeedFrequencyByte = commandValues[1];
                    secondSpeedFrequencyByte = commandValues[2];

                    int frequency = (firstSpeedFrequencyByte & 0xFF) | ((secondSpeedFrequencyByte << 8) & 0xFF00);

                    if (frequency > 0) {
                        speed += (double) (frequency * 40) / (double) 210;
                    }

                    if (speedCounter == 16) {
                        speed = speed / (double) speedCounter;

                        if (speed > 220) {
                            speed = 0;
                        } else if (speed > 5) {
                            getDashboard().setSpeed(speed);
                            updateTripKilometers();
                        }
                        speed = 0;
                        speedCounter = 0;
                    } else {
                        speedCounter++;
                    }
                } catch (Exception e) {
                    createErrorMessage(carData, "Problem setting speed");
                }
            }
        }
    }

    @Override
    public void resetValues() {
        getDashboard().setSpeed(0);
    }

    @Override
    public void restart() {
        speedDataTimer = new Timer();
        speedDataTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                carData.executeDbCommand(CarData.DBCommand.SPEEDW, new CustomEntry<>(getDashboard().getSpeed(), new Date().toString()));
                carData.executeDbCommand(CarData.DBCommand.CARSETTINGSW, inputInterpreter.getCarSettings());

            }
        }, 0, 20000);
    }

    @Override
    public void setCommands() {
        commands.add(SPEED_PULSE);
    }

    private synchronized void updateTripKilometers() {
        List<Double> kilometers = carSettings.increaseTripKilometers(((CAR_TYRE_CIRCUNFERENCE_M) / (double) 1000) * carSettings.getTyreOffSet());
        try {
            getDashboard().setSpeed((int) speed);
            getDashboard().setDistance(kilometers.get(0));
            getDashboard().setTotalDistance(kilometers.get(1));

            if(speed > inputInterpreter.getCarTrip().getMaxSpeed()) {
                inputInterpreter.getCarTrip().setMaxSpeed(speed);
            }
        } finally {
            kilometers.clear();
        }
    }
}
