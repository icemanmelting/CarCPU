package pt.iceman.carcpu.interpreters.output;

import org.reflections.Reflections;
import pt.iceman.carcpu.modules.input.InputModule;
import pt.iceman.carcpu.modules.output.OutputModule;
import pt.iceman.cardata.CarData;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by iceman on 18/07/16.
 */
public class OutputInterpreter extends Thread {
    private static Map<Class<? extends OutputModule>, OutputModule> outputModules;
    private CarData carData;

    public OutputInterpreter(CarData carData) {
        this.carData = carData;
    }

    public Map<Class<? extends OutputModule>, OutputModule> getOutputModules() {
        if (outputModules == null) {
            outputModules = new HashMap<>();
            String classPath = OutputModule.class.getPackage().getName();
            Reflections reflections = new Reflections(classPath);
            Set<Class<? extends OutputModule>> allClasses =
                    reflections.getSubTypesOf(OutputModule.class);

            allClasses.forEach(c -> {
                try {
                    outputModules.put(c, c.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return outputModules;
    }

    @Override
    public void run() {

    }
}
