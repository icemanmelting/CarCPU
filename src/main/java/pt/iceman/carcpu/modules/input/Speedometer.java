package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;

/**
 * Created by iceman on 17/07/2016.
 */
public class Speedometer extends InputModule {
    public Speedometer(InputInterpreter inputInterpreter, Dashboard dashboard) {
        super(inputInterpreter, dashboard);
    }

    @Override
    public void interpretCommand(Command command) {

    }

    @Override
    public void resetValues() {

    }

    @Override
    public void restart() {

    }
}
