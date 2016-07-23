package pt.iceman.carcpu.utils;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPClient
{
	public static void send(byte[] dataToSend, String destinationIP, int port)
	{
		try{
		      DatagramSocket clientSocket = new DatagramSocket();
		      InetAddress IPAddress = InetAddress.getByName(destinationIP);
		      DatagramPacket sendPacket = new DatagramPacket(dataToSend, dataToSend.length, IPAddress, port);
		      clientSocket.send(sendPacket);
		      clientSocket.close();
		      
		      }
		catch(Exception e)
		{
			System.out.println("Could not send packet! Cause: "+e.getCause());
		}
	}
	
	public static void send(byte dataToSend, String destinationIP, int port)
	{
		byte[] data = new byte[2];
		data[0]=dataToSend;
		try{
		      DatagramSocket clientSocket = new DatagramSocket();
		      InetAddress IPAddress = InetAddress.getByName(destinationIP);
		      DatagramPacket sendPacket = new DatagramPacket(data, 1, IPAddress, port);
		      clientSocket.send(sendPacket);
		      clientSocket.close();
		      
		      }
		catch(Exception e)
		{
			System.out.println("Could not send packet! Cause: "+e.getCause());
		}
	}
}