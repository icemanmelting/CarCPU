package pt.iceman.carcpu;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.mcu.McuListenter;
import pt.iceman.carcpu.modules.input.InputModule;

import java.sql.SQLException;

/**
 * Created by iceman on 17/07/2016.
 */
public class CarCPU {
    public void start(Dashboard dashboard) throws ClassNotFoundException {
        McuListenter mcuListenter = new McuListenter(dashboard);
        mcuListenter.start();
    }
}
