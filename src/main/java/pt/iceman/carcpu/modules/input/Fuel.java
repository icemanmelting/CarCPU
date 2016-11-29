package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

/**
 * Created by iceman on 17/07/2016.
 */
public class Fuel extends InputModule {
    private static final int DIESEL_BUFFER_SIZE = 3500;
    private static final float FUEL_MAX_VOLUME = 52;
    private static final float FUEL_LEVEL_MAX_RESISTANCE = 38;
    private static final float FUEL_3QUARTERS_RESISTANCE = 69.7f;
    private static final float FUEL_HALF_FULL_RESISTANCE = 123;
    private static final float FUEL_1QUARTER_FULL_RESISTANCE = 188;
    private static final float FUEL_RESERVE_RESISTANCE = 232.8f;
    private static final float FUEL_EMPTY_RESISTANCE = 283;
    private static final byte DIESEL_VALUE = (byte) 0b1110_0000;
    private boolean setInitialFuelLevel = true;
    private List<Double> fuelValues;

    public Fuel(InputInterpreter inputInterpreter, Dashboard dashboard) {
        super(inputInterpreter, dashboard);
        fuelValues = new ArrayList<>(DIESEL_BUFFER_SIZE);
    }

    @Override
    public void interpretCommand(Command command) {
        if (inputInterpreter.isIgnition()) {
            byte[] commandValues = command.getValues();

            if (commands.contains(commandValues[0])) {
                byte firstDieselByte = commandValues[1];
                byte secondDieselByte = commandValues[2];
                int analogValue = (firstDieselByte & 0xFF) | ((secondDieselByte << 8) & 0xFF00);

                if (analogValue > 0) {
                    if (fuelValues.size() == DIESEL_BUFFER_SIZE) {
                        fuelValues.remove(fuelValues.size() - 1);
                        fuelValues.add(0, (double) analogValue);
                    } else {
                        fuelValues.add((double) analogValue);
                    }

                    OptionalDouble avg = fuelValues.stream().mapToDouble(r -> r).average();

                    if (avg.isPresent()) {
                        setFuelLevel(avg.getAsDouble());
                    }
                }
            }
        }
    }

    @Override
    public void setCommands() {
        commands.add(DIESEL_VALUE);
    }

    @Override
    public void resetValues() {
        fuelValues.clear();
        getDashboard().setDiesel(0);
    }

    private void setFuelLevel(double analogLevel) {
        double voltage = analogLevel * STEP;
        double resistance = (voltage * PULL_UP_RESISTOR_VALUE) / (VOLTAGE_LEVEL - voltage);
        double fuelLevel = 0;
        resistance = resistance * (164.5 / (double) 150);

        if (resistance > 0) {
            if (resistance < FUEL_LEVEL_MAX_RESISTANCE) {
                fuelLevel = FUEL_MAX_VOLUME;
            } else if (resistance > FUEL_EMPTY_RESISTANCE) {
                fuelLevel = FUEL_EMPTY_RESISTANCE;
            } else if (resistance >= FUEL_LEVEL_MAX_RESISTANCE && resistance < FUEL_3QUARTERS_RESISTANCE) {
                fuelLevel = FUEL_MAX_VOLUME - ((resistance - FUEL_LEVEL_MAX_RESISTANCE) / (FUEL_3QUARTERS_RESISTANCE - FUEL_LEVEL_MAX_RESISTANCE)) * (FUEL_MAX_VOLUME);
            } else if (resistance >= FUEL_3QUARTERS_RESISTANCE && resistance < FUEL_HALF_FULL_RESISTANCE) {
                fuelLevel = (FUEL_MAX_VOLUME * 0.75) - ((resistance - FUEL_3QUARTERS_RESISTANCE) / (FUEL_HALF_FULL_RESISTANCE - FUEL_3QUARTERS_RESISTANCE)) * (FUEL_MAX_VOLUME * 0.75);
            } else if (resistance >= FUEL_HALF_FULL_RESISTANCE && resistance < FUEL_1QUARTER_FULL_RESISTANCE) {
                fuelLevel = (FUEL_MAX_VOLUME * 0.5) - ((resistance - FUEL_HALF_FULL_RESISTANCE) / (FUEL_1QUARTER_FULL_RESISTANCE - FUEL_HALF_FULL_RESISTANCE)) * (FUEL_MAX_VOLUME * 0.5);
            } else if (resistance >= FUEL_1QUARTER_FULL_RESISTANCE && resistance < FUEL_RESERVE_RESISTANCE) {
                fuelLevel = (FUEL_MAX_VOLUME * 0.25) - ((resistance - FUEL_1QUARTER_FULL_RESISTANCE) / (FUEL_RESERVE_RESISTANCE - FUEL_1QUARTER_FULL_RESISTANCE)) * (FUEL_MAX_VOLUME * 0.25);
            } else if (resistance >= FUEL_RESERVE_RESISTANCE && resistance < FUEL_EMPTY_RESISTANCE) {
                fuelLevel = (FUEL_MAX_VOLUME * 0.12) - ((resistance - FUEL_EMPTY_RESISTANCE) / (FUEL_EMPTY_RESISTANCE - FUEL_RESERVE_RESISTANCE)) * (FUEL_MAX_VOLUME * 0.12);
                if (inputInterpreter.isIgnition()) {
                    createWarningMessage(carData, "low fuel level detected!");
                }
            }
            getDashboard().setDiesel(fuelLevel);

            if (setInitialFuelLevel && fuelLevel > 0) {
                carSettings.setFuelLevel(fuelLevel);
                setInitialFuelLevel = false;
            }
        }
    }
}