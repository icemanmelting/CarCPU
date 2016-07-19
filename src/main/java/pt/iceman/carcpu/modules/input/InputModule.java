package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by iceman on 18/07/2016.
 */
public abstract class InputModule {
    protected static final float CAR_TERMISTOR_ALPHA_VALUE = -0.00001423854206f;
    protected static final float CAR_TERMISTOR_BETA_VALUE = 0.0007620444171f;
    protected static final float CAR_TERMISTOR_C_VALUE = -0.000006511973919f;
    protected static final int PULL_UP_RESISTOR_VALUE = 975;
    protected static final double VOLTAGE_LEVEL = 12;
    protected static final int PIN_RESOLUTION = 1023;
    protected static final double STEP = (double) 15 / (double) PIN_RESOLUTION;
    protected InputInterpreter inputInterpreter;
    protected List<Byte> commands;
    private Dashboard dashboard;

    public InputModule(InputInterpreter inputInterpreter, Dashboard dashboard) {
        this.inputInterpreter = inputInterpreter;
        this.dashboard = dashboard;
        commands = new ArrayList<>();
    }

    public Dashboard getDashboard() {
        return dashboard;
    }

    public void setDashboard(Dashboard dashboard) {
        this.dashboard = dashboard;
    }

    public void interpretCommand(Command command){}

    public void resetValues() {}

    public void restart () {}

    public void setCommands() {}

    public List<Byte> getCommands() {
        return commands;
    }

    public void setCommands(List<Byte> commands) {
        this.commands = commands;
    }

    protected void createErrorMessage(String message, String cause)
    {
//        try (SqliteConnector connector = new SqliteConnector("javaCarputer"))
//        {
//            connector.insertError(new ErrorData()
//            {
//                {
//                    setMessage(message);
//                    setCause(cause);
//                    setTimeframe(new Date().toString());
//                }
//            });
//        } catch (ClassNotFoundException e)
//        {
//            System.out.println("Problem loading library from classpath!");
//        } catch (SQLException e)
//        {
//            System.out.println("Problem loading db");
//        } catch (Exception e)
//        {
//            System.out.println("Problem closing connector");
//        }
    }

    protected double calculateAverage(List<Double> marks)
    {
        Double sum = 0d;
        if (!marks.isEmpty())
        {
            for (Double mark : marks)
            {
                sum += mark;
            }
            return sum / (double) marks.size();
        }
        return sum;
    }
}
