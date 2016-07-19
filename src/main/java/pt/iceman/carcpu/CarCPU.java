package pt.iceman.carcpu;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.mcu.McuListenter;

/**
 * Created by iceman on 17/07/2016.
 */
public class CarCPU {
    public void start(Dashboard dashboard) {
        McuListenter mcuListenter = new McuListenter(dashboard);
        mcuListenter.start();
    }
}
