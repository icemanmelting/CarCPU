package pt.iceman.carcpu.interpreters.input;

import org.reflections.Reflections;
import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.modules.input.InputModule;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

/**
 * Created by iceman on 18/07/16.
 */
public class InputInterpreter extends Thread {
    private Map<Class<? extends InputModule>, InputModule> inputModules;
    private Map<Byte, Class<? extends InputModule>> commandModuleAssociator;
    private Dashboard dashboard;
    private BlockingQueue<Command> inputQueue;

    public InputInterpreter(Dashboard dashboard, BlockingQueue<Command> inputQueue) {
        this.dashboard = dashboard;
        this.inputQueue = inputQueue;
        getInputModules();
    }

    public Map<Class<? extends InputModule>, InputModule> getInputModules() {
        if (inputModules == null) {
            inputModules = new HashMap<>();
            commandModuleAssociator = new HashMap<>();
            Reflections reflections = new Reflections(InputModule.class.getPackage().getName());
            Set<Class<? extends InputModule>> allClasses =
                    reflections.getSubTypesOf(InputModule.class);

            allClasses.forEach(c -> {
                try {
                    Constructor constructor = c.getConstructor(Dashboard.class);
                    InputModule inputModule = (InputModule) constructor.newInstance(this, dashboard);
                    List<Byte> commands = inputModule.getCommands();
                    commands.forEach(b -> {
                        commandModuleAssociator.put(b, c);
                    });

                    inputModules.put(c, inputModule);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return inputModules;
    }

    public Map<Byte, Class<? extends InputModule>> getCommandModuleAssociator() {
        return commandModuleAssociator;
    }

    @Override
    public void run() {
        while (true) {
            if (inputQueue.size() > 0) {
                try {
                    Command cmd = inputQueue.take();
                    inputModules.get(cmd.getClazz()).interpretCommand(cmd);
                } catch (InterruptedException e) {
                    System.out.println("Problem taking element from queue!");
                }
            }
        }
    }
}
