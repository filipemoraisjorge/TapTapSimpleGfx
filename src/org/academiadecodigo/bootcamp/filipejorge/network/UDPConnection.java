package org.academiadecodigo.bootcamp.filipejorge.network;

import java.io.IOException;
import java.net.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public UDPConnection() {
        try {
            setConnection();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setConnection() throws IOException {
        DatagramSocket broadcastSocket = new DatagramSocket();
        broadcastSocket.setBroadcast(true);

        //broadcast to private network(?)
        byte[] broadcastData = "TAPTAP_SOMEONETHERE".getBytes();
        InetAddress address = InetAddress.getByName("255.255.255.255");
        DatagramPacket isSomeoneThere = new DatagramPacket(broadcastData, broadcastData.length, address, 8888);
        broadcastSocket.send(isSomeoneThere);
        System.out.println("broadcasted.");

        //waiting response
        System.out.println(broadcastSocket);
        byte[] receiveData = new byte[BUFFER_SIZE];
        DatagramPacket thereIsSomeone = new DatagramPacket(receiveData, receiveData.length);
        System.out.println("waiting response.");
        broadcastSocket.receive(thereIsSomeone);

        String message = new String(thereIsSomeone.getData()).trim();
        if (message.equals("TAPTAP_YESIMHERE")) {
            System.out.println(thereIsSomeone.getAddress().getHostName() + " " + thereIsSomeone.getAddress().getHostAddress() + " " + message);
        }
        System.out.println(thereIsSomeone.getAddress().getHostName() + " " + thereIsSomeone.getPort() + " " + message);
        System.out.println(message);

        //close socket
        broadcastSocket.close();


        host = thereIsSomeone.getAddress().getHostAddress();
        port = thereIsSomeone.getPort();



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


