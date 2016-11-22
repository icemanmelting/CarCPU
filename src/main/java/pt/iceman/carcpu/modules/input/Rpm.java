package pt.iceman.carcpu.modules.input;

import pt.iceman.carai.neuralnet.NeuralNetwork;
import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;

/**
 * Created by iceman on 17/07/2016.
 */
public class Rpm extends InputModule {
    public static final byte RPM_PULSE = (byte) 0b1011_0100;
    private NeuralNetwork nn;

    public Rpm(InputInterpreter inputInterpreter, Dashboard dashboard) {
        super(inputInterpreter, dashboard);
        this.nn = new NeuralNetwork(
                0.5576882884109496,
                0.3285294608908279,
                0.5328666792713027,
                0.7115359993634642,
                0.7879252794415132
        );
    }

    @Override
    public void interpretCommand(Command command) {
        if (inputInterpreter.isIgnition()) {
            byte[] commandValues = command.getValues();

            if (commands.contains(commandValues[0])) {
                byte firstRPMFrequencyByte;
                byte secondRPMFrequencyByte;
                double rpm = 0;

                try {
                    firstRPMFrequencyByte = commandValues[1];
                    secondRPMFrequencyByte = commandValues[2];

                    int frequency = (firstRPMFrequencyByte & 0xFF) | ((secondRPMFrequencyByte << 8) & 0xFF00);
                    if (frequency > 0) {
                        rpm = (double) (frequency * 900) / (double) 155;
                    }

                    if (rpm < 7000) {
                        getDashboard().setRpm(rpm);
                        getDashboard().setGear(nn.getGear(getDashboard().getSpeed(), rpm));
                    }
                } catch (Exception e) {
                    createErrorMessage(carData, "Problem setting rpm");
                }
            }
        }
    }

    @Override
    public void setCommands() {
        commands.add(RPM_PULSE);
    }

    @Override
    public void resetValues() {
        getDashboard().setRpm(0);
    }

    @Override
    public void restart() {

    }
}
