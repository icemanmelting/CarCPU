package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;

/**
 * Created by iceman on 17/07/2016.
 */
public class Lights extends InputModule {
    private static final byte OIL_PRESSURE_ON = 0b0001_1111;
    private static final byte OIL_PRESSURE_OFF = 0b0001_0000;
    private static final byte BATTERY_ON = 0b0010_1111;
    private static final byte BATTERY_OFF = 0b0010_0000;
    private static final byte PARKING_BRAKE_ON = 0b0011_1111;
    private static final byte PARKING_BRAKE_OFF = 0b0011_0000;
    private static final byte BRAKES_OIL_ON = 0b0100_1111;
    private static final byte BRAKES_OIL_OFF = 0b0100_0000;
    private static final byte TURNING_SIGNS_ON = 0b0101_1111;
    private static final byte TURNING_SIGNS_OFF = 0b0101_0000;
    private static final byte SPARK_PLUGS_ON = 0b0111_1111;
    private static final byte SPARK_PLUGS_OFF = 0b0111_0000;
    private static final byte ABS_ANOMALY_ON = (byte) 0b1000_1111;
    private static final byte ABS_ANOMALY_OFF = (byte) 0b1000_0000;
    private static final byte HIGH_BEAM_ON = (byte) 0b1001_1111;
    private static final byte HIGH_BEAM_OFF = (byte) 0b1001_0000;

    public Lights(Dashboard dashboard) {
        super(dashboard);
    }

    @Override
    public void interpretCommand(Command command) {
        byte [] commandValues = command.getValues();
        if (commands.contains(commandValues[0])) {
            switch (commandValues[0]) {
                case OIL_PRESSURE_ON:
                    getDashboard().setOilPressure(true);
                    break;
                case OIL_PRESSURE_OFF:
                    getDashboard().setOilPressure(false);
                    break;
                case BATTERY_ON:
                    getDashboard().setBattery(true);
                    break;
                case BATTERY_OFF:
                    getDashboard().setBattery(false);
                    break;
                case PARKING_BRAKE_ON:
                    getDashboard().setParking(true);
                    break;
                case PARKING_BRAKE_OFF:
                    getDashboard().setParking(false);
                    break;
                case BRAKES_OIL_ON:
                    getDashboard().setBrakesOil(true);
                    break;
                case BRAKES_OIL_OFF:
                    getDashboard().setBrakesOil(false);
                    break;
                case TURNING_SIGNS_ON:
                    getDashboard().setTurnSigns(true);
                    break;
                case TURNING_SIGNS_OFF:
                    getDashboard().setTurnSigns(false);
                    break;
                case SPARK_PLUGS_ON:
                    getDashboard().setSparkPlug(true);
                    break;
                case SPARK_PLUGS_OFF:
                    getDashboard().setSparkPlug(false);
                    break;
                case ABS_ANOMALY_ON:
                    getDashboard().setAbs(true);
                    break;
                case ABS_ANOMALY_OFF:
                    getDashboard().setAbs(false);
                    break;
                case HIGH_BEAM_ON:
                    getDashboard().setHighBeams(true);
                    break;
                case HIGH_BEAM_OFF:
                    getDashboard().setHighBeams(false);
                    break;
            }
        }
    }

    @Override
    public void setCommands() {
        commands.add(OIL_PRESSURE_ON);
        commands.add(OIL_PRESSURE_OFF);
        commands.add(BATTERY_ON);
        commands.add(BATTERY_OFF);
        commands.add(PARKING_BRAKE_ON);
        commands.add(PARKING_BRAKE_OFF);
        commands.add(BRAKES_OIL_ON);
        commands.add(BRAKES_OIL_OFF);
        commands.add(TURNING_SIGNS_ON);
        commands.add(TURNING_SIGNS_OFF);
        commands.add(SPARK_PLUGS_ON);
        commands.add(SPARK_PLUGS_OFF);
        commands.add(ABS_ANOMALY_ON);
        commands.add(ABS_ANOMALY_OFF);
        commands.add(HIGH_BEAM_ON);
        commands.add(HIGH_BEAM_OFF);
    }
}
