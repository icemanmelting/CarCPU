package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;

/**
 * Created by iceman on 18/07/2016.
 */
public abstract class InputModule {
    private Dashboard dashboard;

    public InputModule(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void interpretCommand(Command command){}
}
