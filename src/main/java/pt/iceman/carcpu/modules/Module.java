package pt.iceman.carcpu.modules;

import pt.iceman.cardata.CarData;
import pt.iceman.cardata.log.CarLog;

import java.sql.SQLException;
import java.util.Date;

/**
 * Created by iceman on 25/07/2016.
 */
public class Module {
    protected CarData carData;

    public Module(CarData carData) {
        this.carData = carData;
    }

    protected void createErrorMessage(CarData carData, String message) {
        carData.executeDbCommand(CarData.DBCommand.LOGW, new CarLog() {
            {
                setMessage(message);
                setLogLevel(LogLevel.ERROR);
                setTimeFrame(new Date());
            }
        });
    }

    protected void createInfoMessage(CarData carData, String message) {
        carData.executeDbCommand(CarData.DBCommand.LOGW, new CarLog() {
            {
                setMessage(message);
                setLogLevel(LogLevel.INFO);
                setTimeFrame(new Date());
            }
        });
    }

    //TODO add methods to update database?
}
