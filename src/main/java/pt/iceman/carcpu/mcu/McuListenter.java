package pt.iceman.carcpu.mcu;

import pt.iceman.carcpu.dashboard.Dashboard;
import pt.iceman.carcpu.interpreters.Command;
import pt.iceman.carcpu.interpreters.input.InputInterpreter;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by iceman on 18/07/2016.
 */
public class McuListenter extends Thread {
    private InputInterpreter inputInterpreter;
    private static BlockingQueue<Command> inputQueue;
    private int controllerPort = 9887;
    private DatagramSocket serverSocket;

    public McuListenter(Dashboard dashboard) {
        inputQueue = new ArrayBlockingQueue<>(100);
        inputInterpreter = new InputInterpreter(dashboard, inputQueue);
    }

    @Override
    public void run() {
        try {
            serverSocket = new DatagramSocket(controllerPort);
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
        Command cmd = new Command();

        try {
            inputQueue.put(cmd);
        } catch (InterruptedException e) {
            System.out.println("Problem adding command to queue!");
        }
    }
}
