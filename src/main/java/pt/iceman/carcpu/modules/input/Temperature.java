package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;

/**
 * Created by iceman on 17/07/2016.
 */
public class Temperature extends InputModule {
    public Temperature(Dashboard dashboard) {
        super(dashboard);
    }

    @Override
    public void interpretCommand(Command command) {

    }
}