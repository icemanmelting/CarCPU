package pt.iceman.carcpu.interpreters.output;

import org.reflections.Reflections;
import pt.iceman.carcpu.modules.output.BluetoothProximityDoorOpener;
import pt.iceman.carcpu.modules.output.OutputModule;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by iceman on 18/07/16.
 */
public class OutputInterpreter implements Runnable {
    private static Map<Class<? extends OutputModule>, OutputModule> outputModules;

    public Map<Class<? extends OutputModule>, OutputModule> getOutputModules() {
        if (outputModules == null) {
            outputModules = new HashMap<>();

            Reflections reflections = new Reflections(OutputModule.getPackageName());
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
