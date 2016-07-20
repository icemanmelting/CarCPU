package pt.iceman.carcpu.modules.input;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by iceman on 17/07/2016.
 */
public class Speedometer extends InputModule {
    private static final byte SPEED_PULSE = (byte) 0b1011_0000;
    private double speed = 0;
    private int speedCounter = 0;
    private Timer speedDataTimer;

    public Speedometer(InputInterpreter inputInterpreter, Dashboard dashboard) {
        super(inputInterpreter, dashboard);
    }

    @Override
    public void interpretCommand(Command command) {
        byte[] commandValues = command.getValues();
        if (commands.contains(commandValues[0])) {
            byte firstSpeedFrequencyByte;
            byte secondSpeedFrequencyByte;
            try {
                firstSpeedFrequencyByte = commandValues[1];
                secondSpeedFrequencyByte = commandValues[2];

                int frequency = (firstSpeedFrequencyByte & 0xFF) | ((secondSpeedFrequencyByte << 8) & 0xFF00);

                if (frequency > 0) {
                    speed += (double) (frequency * 40) / (double) 210;
                }

                if (speedCounter == 16) {
                    speed = speed / (double) speedCounter;

                    if (speed > 220) {
                        speed = 0;
                    } else {
                        getDashboard().setSpeed(speed);
                    }
                    speed = 0;
                    speedCounter = 0;
                } else {
                    speedCounter++;
                }
            } catch (Exception e) {
                createErrorMessage("Problem setting speed", e.getCause().toString());
            }
        }
    }

    @Override
    public void resetValues() {
        getDashboard().setSpeed(0);
    }

    @Override
    public void restart() {
        speedDataTimer = new Timer();
        speedDataTimer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
//                try (SqliteConnector connector = new SqliteConnector("javaCarputer"))
//                {
//                    connector.insertSpeedData(new CustomEntry<Double, String>(dashboard.getSpeed(), new Date().toString()));
//                    connector.updateSettings(carSettings);
//                } catch (SQLException e)
//                {
//                    e.printStackTrace();
//                } catch (Exception e)
//                {
//                    e.printStackTrace();
//                }
            }
        }, 0, 20000);
    }

    @Override
    public void setCommands() {
        commands.add(SPEED_PULSE);
    }
}
