package pt.iceman.carcpu.modules;

import pt.iceman.cardata.CarData;
import pt.iceman.cardata.log.CarLog;

import java.util.Date;
import java.util.UUID;

/**
 * Created by iceman on 25/07/2016.
 */
public class Module {
    protected CarData carData;

    public Module(CarData carData) {
        this.carData = carData;
    }

    protected void createErrorMessage(CarData carData, String message, UUID tripId) {
        carData.createLog(new CarLog() {
            {
                setTripId(tripId);
                setMessage(message);
                setLogLevel(LogLevel.ERROR.toString());
                setTs(new Date());
            }
        });
    }

    protected void createInfoMessage(CarData carData, String message, UUID tripId) {
        carData.createLog(new CarLog() {
            {
                setTripId(tripId);
                setMessage(message);
                setLogLevel(LogLevel.INFO.toString());
                setTs(new Date());
            }
        });
    }

    protected void createWarningMessage(CarData carData, String message, UUID tripId) {
        carData.createLog(new CarLog() {
            {
                setTripId(tripId);
                setMessage(message);
                setLogLevel(LogLevel.WARNIMG.toString());
                setTs(new Date());
            }
        });
    }
}
