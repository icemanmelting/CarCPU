package pt.iceman.carcpu;

import org.reflections.Reflections;
import pt.iceman.carcpu.modules.Module;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by iceman on 17/07/2016.
 */
public class CarCPU {
    private static Map<Class<? extends Module >, Module> modules;

    public Map<Class<? extends Module >, Module> getModules() {
        if(modules == null) {
            modules = new HashMap<>();
            Reflections reflections = new Reflections("pt.iceman.carcpu.modules");

            Set<Class<? extends Module>> allClasses =
                    reflections.getSubTypesOf(Module.class);

            allClasses.forEach(c -> {
                try {
                    modules.put(c, c.newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return modules;
    }
}
