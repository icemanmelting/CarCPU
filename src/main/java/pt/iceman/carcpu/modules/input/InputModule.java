package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;
import pt.iceman.carcpu.modules.Module;
import pt.iceman.cardata.log.CarTrip;
import pt.iceman.cardata.settings.CarSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iceman on 18/07/2016.
 */
public abstract class InputModule extends Module {
    static final int PULL_UP_RESISTOR_VALUE = 975;
    static final double VOLTAGE_LEVEL = 12;
    static final int PIN_RESOLUTION = 1023;
    static final double STEP = (double) 15 / (double) PIN_RESOLUTION;
    InputInterpreter inputInterpreter;
    CarSettings carSettings;
    List<Byte> commands;
    private Dashboard dashboard;

    public InputModule(InputInterpreter inputInterpreter, Dashboard dashboard) {
        super(inputInterpreter.getCarData());
        this.inputInterpreter = inputInterpreter;
        this.carSettings = this.inputInterpreter.getCarSettings();
        this.dashboard = dashboard;
        commands = new ArrayList<>();
    }

    public synchronized Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void interpretCommand(Command command) {
    }

    public void resetValues() {
    }

    public void restart() {
    }

    public void setCommands() {
    }

    public List<Byte> getCommands() {
        return commands;
    }

    @Deprecated
    protected double calculateAverage(List<Double> marks) {
        Double sum = 0d;
        if (!marks.isEmpty()) {
            for (Double mark : marks) {
                sum += mark;
            }
            return sum / (double) marks.size();
        }
        return sum;
    }
}
