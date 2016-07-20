package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iceman on 17/07/2016.
 */
public class Temperature extends InputModule {
    protected static final float CAR_TERMISTOR_ALPHA_VALUE = -0.00001423854206f;
    protected static final float CAR_TERMISTOR_BETA_VALUE = 0.0007620444171f;
    protected static final float CAR_TERMISTOR_C_VALUE = -0.000006511973919f;
    private static final byte TEMPERATURE_VALUE = (byte) 0b1100_0000;
    public static final int TEMPERATURE_BUFFER_SIZE = 32;
    private List<Double> tempValues;

    public Temperature(InputInterpreter inputInterpreter, Dashboard dashboard) {
        super(inputInterpreter, dashboard);
        tempValues = new ArrayList<>(32);
    }

    @Override
    public void interpretCommand(Command command) {
        byte [] commandValues = command.getValues();
        if (commands.contains(commandValues[0])) {
            byte firstTemperatureByte;
            byte secondTemperatureByte;

            try
            {
                firstTemperatureByte = commandValues[1];
                secondTemperatureByte = commandValues[2];
                int analogTValue = (firstTemperatureByte & 0xFF) | ((secondTemperatureByte << 8) & 0xFF00);
                if (tempValues.size() == TEMPERATURE_BUFFER_SIZE)
                {
                    tempValues.remove(tempValues.size() - 1);
                    tempValues.add(0, (double) analogTValue);
                } else
                {
                    tempValues.add((double) analogTValue);
                }
                setTemperatureLevel(calculateAverage(tempValues));
            } catch (Exception e)
            {
                createErrorMessage("Problem setting temperature", e.getCause().toString());
            }
        }
    }

    @Override
    public void setCommands() {
        commands.add(TEMPERATURE_VALUE);
    }

    @Override
    public void resetValues() {

    }

    @Override
    public void restart() {

    }

    public void setTemperatureLevel(double analogLevel)
    {
        double voltageLevel = analogLevel * STEP;
        double resistance = (voltageLevel * PULL_UP_RESISTOR_VALUE) / (VOLTAGE_LEVEL - voltageLevel);
        double temperature = 1 / (CAR_TERMISTOR_ALPHA_VALUE + CAR_TERMISTOR_BETA_VALUE * (Math.log(resistance)) + CAR_TERMISTOR_C_VALUE * Math.log(resistance) * Math.log(resistance) * Math.log(resistance)) - 273.15;
        getDashboard().setTemp(temperature);
    }
}