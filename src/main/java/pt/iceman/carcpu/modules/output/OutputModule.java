package pt.iceman.carcpu.modules.output;

import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.modules.Module;
import pt.iceman.cardata.CarData;

/**
 * Created by iceman on 18/07/16.
 */
public class OutputModule extends Module{

    public OutputModule(CarData carData) {
        super(carData);
    }

    public void sendCommand(Command command) {};
}




