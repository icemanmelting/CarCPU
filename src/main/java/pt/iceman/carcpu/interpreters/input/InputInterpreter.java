package pt.iceman.carcpu.interpreters.input;

import org.reflections.Reflections;
import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.modules.input.InputModule;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by iceman on 18/07/16.
 */
public class InputInterpreter implements Runnable {
    private static Map<Class<? extends InputModule>, InputModule> inputModules;
    private Dashboard dashboard;

    public InputInterpreter(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public Map<Class<? extends InputModule>, InputModule> getInputModules() {
        if(inputModules == null) {
            inputModules = new HashMap<>();
            Reflections reflections = new Reflections(InputModule.getPackageName());

            Set<Class<? extends InputModule>> allClasses =
                    reflections.getSubTypesOf(InputModule.class);

            allClasses.forEach(c -> {
                try {
                    inputModules.put(c, c.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return inputModules;
    }

    @Override
    public void run() {

    }
}
