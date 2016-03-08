package org.academiadecodigo.bootcamp.filipejorge.network;

import java.io.IOException;
import java.net.*;

/**
 * Created by filipejorge on 05/03/16.
 * <p>
 * a class to connect to other game player
 * first broadcast to find other games in network.
 */
public class UDPConnection {

    private static int BUFFER_SIZE = 1024;

    private String host;
    private int port;
    private String message;

    private DatagramSocket clientSocket;


    private void getUserInput() {

        host = "192.168.0.255";

        port = 70777;

        message = "";

    }

    private void sendDatagram() throws IOException {

        clientSocket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(host);

        byte[] sendData = message.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, address, port);
        clientSocket.send(sendPacket);

    }

    private void receiveDatagram() throws IOException {

        byte[] receiveData = new byte[BUFFER_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

        System.out.println("Waiting for return packet");
        clientSocket.setSoTimeout(10000);

        try {

            clientSocket.receive(receivePacket);
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());

            System.out.println("Received Message: " + response);

        } catch (SocketTimeoutException ste) {

            System.out.println("Timeout Occurred: Packet assumed lost");

        } finally {

            clientSocket.close();

        }

    }

}


