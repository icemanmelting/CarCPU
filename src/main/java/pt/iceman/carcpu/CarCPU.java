package pt.iceman.carcpu;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;
import pt.iceman.carcpu.mcu.McuListenter;
import pt.iceman.cardata.CarData;

import java.io.IOException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

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

        CarData carData = new CarData();

        BlockingQueue<Command> inputQueue = new ArrayBlockingQueue<>(100);

        InputInterpreter inputInterpreter = new InputInterpreter(dashboard, carData, inputQueue);
        inputInterpreter.start();

//        OutputInterpreter outputInterpreter = new OutputInterpreter(carData);
//        outputInterpreter.start();
//
        McuListenter mcuListenter = new McuListenter(inputQueue);
        mcuListenter.start();
    }
}
