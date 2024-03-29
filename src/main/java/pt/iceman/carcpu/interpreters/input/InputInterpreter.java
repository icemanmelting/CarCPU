package pt.iceman.carcpu.interpreters.input;

import org.reflections.Reflections;
import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.modules.input.InputModule;
import pt.iceman.cardata.CarData;
import pt.iceman.cardata.log.CarTrip;
import pt.iceman.cardata.settings.CarSettings;

import java.lang.reflect.Constructor;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by iceman on 18/07/16.
 */
public class InputInterpreter extends Thread {
    private Map<Class<? extends InputModule>, InputModule> inputModules;
    private Map<Byte, Class<? extends InputModule>> commandModuleAssociator;
    private Dashboard dashboard;
    private BlockingQueue<Command> inputQueue;
    private boolean ignition;
    private CarSettings carSettings;
    private CarData carData;
    private CarTrip carTrip;

    public InputInterpreter(Dashboard dashboard, CarData carData, BlockingQueue<Command> inputQueue) throws ClassNotFoundException {
        this.dashboard = dashboard;
        this.carData = carData;
        this.inputQueue = inputQueue;
        this.carTrip = new CarTrip();
        this.carSettings = carData.getSettings(1L);
        getInputModules();
        configureTripAndAbsoluteKilometers();
    }

    public Map<Class<? extends InputModule>, InputModule> getInputModules() {
        if (inputModules == null) {
            inputModules = new ConcurrentHashMap<>();
            commandModuleAssociator = new HashMap<>();
            String classPath = InputModule.class.getPackage().getName();
            Reflections reflections = new Reflections(classPath);
            Set<Class<? extends InputModule>> allClasses = reflections.getSubTypesOf(InputModule.class);

            allClasses.forEach(c -> {
                try {
                    Constructor constructor = c.getConstructor(InputInterpreter.class, Dashboard.class);
                    InputModule inputModule = (InputModule) constructor.newInstance(this, dashboard);
                    inputModule.setCommands();
                    List<Byte> commands = inputModule.getCommands();
                    commands.forEach(b -> commandModuleAssociator.put(b, c));
                    inputModules.put(c, inputModule);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        return Collections.synchronizedMap(inputModules);
    }

    public void configureTripAndAbsoluteKilometers() {
        if (carSettings != null && dashboard != null) {
            dashboard.setDistance(carSettings.getTripKilometers());
            dashboard.setTotalDistance(carSettings.getConstantKilometers());
        }
    }

    public Map<Byte, Class<? extends InputModule>> getCommandModuleAssociator() {
        return commandModuleAssociator;
    }

    public boolean isIgnition() {
        return ignition;
    }

    public void setIgnition(boolean ignition) {
        this.ignition = ignition;
    }

    public CarSettings getCarSettings() {
        return carSettings;
    }

    public CarData getCarData() {
        return carData;
    }

    public void setCarSettings(CarSettings carSettings) {
        this.carSettings = carSettings;
    }

    public CarTrip getCarTrip() {
        return carTrip;
    }

    public void setCarTrip(CarTrip carTrip) {
        this.carTrip = carTrip;
    }

    @Override
    public void run() {
        Map<Class<? extends InputModule>, InputModule> inputModules = getInputModules();

        while (true) {
            if (inputQueue.size() > 0) {
                try {
                    Command cmd = inputQueue.take();
                    Class clazz = getCommandModuleAssociator().getOrDefault(cmd.getValues()[0], null);

                    if (clazz != null) {
                        inputModules.get(clazz).interpretCommand(cmd);
                    }
                } catch (InterruptedException e) {
                    System.out.println("Problem taking element from queue!");
                }
            }
        }
    }
}
