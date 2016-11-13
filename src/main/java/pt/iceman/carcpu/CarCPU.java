package pt.iceman.carcpu;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.mcu.McuListenter;
import pt.iceman.carcpu.modules.input.InputModule;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by iceman on 17/07/2016.
 */
public class CarCPU {
    public void start(Dashboard dashboard) throws ClassNotFoundException {
        try {
            Runtime.getRuntime().exec("/etc/init.d/turnonscreen.sh");
        } catch (IOException e) {
            e.printStackTrace();
        }
        McuListenter mcuListenter = new McuListenter(dashboard);
        mcuListenter.start();
    }
}
