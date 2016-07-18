package pt.iceman.carcpu;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;
import pt.iceman.carcpu.interpreters.output.OutputInterpreter;
import pt.iceman.carcpu.settings.CarSettings;

/**
 * Created by iceman on 17/07/2016.
 */
public class CarCPU {
    private Dashboard dashboard;
    private CarSettings settings;

    public CarCPU(Dashboard dashboard, CarSettings settings) {
        this.dashboard = dashboard;
        this.settings = settings;
    }

    public void start() {
        InputInterpreter inputInterpreter = new InputInterpreter(dashboard, settings);
        Thread inputReader = new Thread(inputInterpreter);
        inputReader.start();

        OutputInterpreter outputInterpreter = new OutputInterpreter();
        Thread outputReader = new Thread(outputInterpreter);
        outputReader.start();
    }
}
