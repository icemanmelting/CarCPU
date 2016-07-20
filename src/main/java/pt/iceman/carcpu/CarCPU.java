package pt.iceman.carcpu;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.mcu.McuListenter;
import pt.iceman.carcpu.modules.input.InputModule;

/**
 * Created by iceman on 17/07/2016.
 */
public class CarCPU {
    public void start(Dashboard dashboard) {
        McuListenter mcuListenter = new McuListenter(dashboard);
        mcuListenter.start();
    }

    public static void main (String[] args) {
        CarCPU carCPU = new CarCPU();
        carCPU.start(null);
    }
}
