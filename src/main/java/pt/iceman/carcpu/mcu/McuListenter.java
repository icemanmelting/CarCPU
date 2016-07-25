package pt.iceman.carcpu.mcu;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;
import pt.iceman.carcpu.interpreters.output.OutputInterpreter;
import pt.iceman.carcpu.modules.input.InputModule;
import pt.iceman.cardata.CarData;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by iceman on 18/07/2016.
 */
public class McuListenter extends Thread {
    public static final int CONTROLLER_PORT = 9887;
    public static final String LISTEN_ADDRESS = "localhost";
    private InputInterpreter inputInterpreter;
    private OutputInterpreter outputInterpreter;
    private static BlockingQueue<Command> inputQueue;
    private DatagramSocket serverSocket;
    private CarData carData;

    public McuListenter(Dashboard dashboard) throws ClassNotFoundException {
        carData = new CarData();
        inputQueue = new ArrayBlockingQueue<>(100);

        inputInterpreter = new InputInterpreter(dashboard, carData, inputQueue);
        inputInterpreter.start();

        outputInterpreter = new OutputInterpreter(carData);
        outputInterpreter.start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new DatagramSocket(CONTROLLER_PORT);
            byte[] receiveData = new byte[1200];

            while (true)
            {
                DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);

                byte[] actualData = new byte[receivePacket.getLength()];

                for (int i = 0; i < actualData.length; i++)
                {
                    actualData[i] = receivePacket.getData()[i];
                }
                readMessages(actualData);
            }
        } catch (SocketException e) {
            System.out.println("Could not execute server socket. " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Could not write to socket! "+e.getMessage());
        }
    }

    public void readMessages(byte[] values) {
        Class<? extends InputModule> clazz = inputInterpreter.getCommandModuleAssociator().get(values[0]);

        if(clazz != null) {
            Command cmd = new Command();
            cmd.setValues(values);
            cmd.setClazz(clazz);
            try {
                inputQueue.put(cmd);
            } catch (InterruptedException e) {
                System.out.println("Problem adding command to queue!");
            }
        }
    }
}
