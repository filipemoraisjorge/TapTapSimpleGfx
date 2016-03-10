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

    public static final String QUESTION = "TAPTAP_SOMEONETHERE";
    public static final String ANSWER = "TAPTAP_YESIMHERE";
    private static int BUFFER_SIZE = 24;

    private InetAddress hostAddress;
    private InetAddress destAddress;
    private int hostPort = 49152 + (int) (Math.random() * (49152 - 65535)); //49152–65535
    private int destPort;

    public UDPConnection() {
        try {

            this.hostAddress = InetAddress.getLocalHost();
            this.destAddress = getAddressByBroadcast();

        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Connection settled: from " + hostAddress + ":" + hostPort + " to " + destAddress.getHostAddress() + ":" + destPort);
    }

    /**
     * The one with the biggest port number starts
     *
     * @return
     */
    public boolean chooseTurn() {
        return hostPort < destPort;
    }

    private InetAddress getAddressByBroadcast() throws IOException {

        InetAddress clientAddress = null;

        DatagramSocket broadcastSocket = new DatagramSocket();
        broadcastSocket.setBroadcast(true);

        //broadcast to private network(?)
        byte[] broadcastData = QUESTION.getBytes();
        InetAddress address = InetAddress.getByName("255.255.255.255"); //read that most routers block this. 192.168.0.255 should be better but inflexible.
        DatagramPacket isSomeoneThere = new DatagramPacket(broadcastData, broadcastData.length, address, 55555);
        broadcastSocket.send(isSomeoneThere);
        System.out.println("broadcasted.");

        //waiting response
        byte[] receiveData = new byte[BUFFER_SIZE];
        DatagramPacket thereIsSomeone = new DatagramPacket(receiveData, receiveData.length);
        System.out.println("waiting response.");
        broadcastSocket.setSoTimeout(2000);
        try {
            broadcastSocket.receive(thereIsSomeone);
            String message = new String(thereIsSomeone.getData()).trim();

            System.out.println("b received from: " + thereIsSomeone.getAddress().getHostAddress() + " " + thereIsSomeone.getPort());
            System.out.println("b data: " + new String(thereIsSomeone.getData()).trim());

            if (message.equals(ANSWER)) {

                //set up the permanent connection- from this side (must do the same on runListening)
                System.out.println("broad " + thereIsSomeone.getAddress().getHostAddress() + " " + thereIsSomeone.getPort());

                clientAddress = thereIsSomeone.getAddress();
                hostPort = broadcastSocket.getLocalPort();
                destPort = thereIsSomeone.getPort();
            } else {//do something if the answer is incorrect...
            }

        } catch (SocketTimeoutException ste) {

            System.out.println("Seems nobody's there. Start listening and wait....");
            clientAddress = getAddressByListening();

        } finally {

            //close socket
            broadcastSocket.close();

        }
        System.out.println("finaly client address: " + clientAddress);
        return clientAddress;
    }

    private InetAddress getAddressByListening() throws IOException {

        InetAddress clientAddress = null;
        System.out.println("listening!");

        //Listen to all the UDP trafic that is destined for this port
        DatagramSocket listeningSocket;
        listeningSocket = new DatagramSocket(55555, InetAddress.getByName("0.0.0.0"));
        listeningSocket.setBroadcast(true);

        //Receive a packet
        byte[] receivedBuffer = new byte[BUFFER_SIZE];
        DatagramPacket imSomeone = new DatagramPacket(receivedBuffer, receivedBuffer.length);
        listeningSocket.receive(imSomeone);

        //Packet received
        System.out.println("l received from: " + imSomeone.getAddress().getHostAddress() + " " + imSomeone.getPort());
        System.out.println("l data: " + new String(imSomeone.getData()).trim());

        //See if the packet holds the right command (message)
        String message = new String(imSomeone.getData()).trim();

        if (message.equals(QUESTION)) {
            byte[] sendData = ANSWER.getBytes();
            //Send a response
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, imSomeone.getAddress(), imSomeone.getPort());
            listeningSocket.send(sendPacket);

            System.out.println("l responded to: " + sendPacket.getAddress().getHostAddress() + " " + sendPacket.getPort());

            //set up the permanent connection- from this side
            System.out.println("list " + imSomeone.getAddress().getHostAddress() + " " + imSomeone.getPort() + " " + imSomeone.getSocketAddress());
            clientAddress = imSomeone.getAddress();
            destPort = imSomeone.getPort();
            hostPort = listeningSocket.getLocalPort();
        }

        listeningSocket.close();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return clientAddress;
    }

    public void out(String message) throws IOException {

        DatagramSocket outSocket = new DatagramSocket();
        byte[] sendData = message.getBytes();
        System.out.println("Sent Message: " + message + " to " + destAddress + " " + destPort);
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, destAddress, destPort);
        outSocket.send(sendPacket);
        outSocket.close();


    }

    public String in() throws IOException {

        DatagramSocket inSocket = new DatagramSocket(hostPort, hostAddress);

        byte[] receiveData = new byte[BUFFER_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        String response = null;

        System.out.println("Waiting for return packet");
        //clientSocket.setSoTimeout(100000);

        //try {

        inSocket.receive(receivePacket);
        response = new String(receivePacket.getData(), 0, receivePacket.getLength());

        System.out.println("Received Message: " + response + "from" + destAddress + " " + destPort);
        inSocket.close();
/*        } catch (SocketTimeoutException ste) {


            System.out.println("Timeout Occurred: Packet assumed lost");

        } finally {

            //   clientSocket.close();
        }*/
        return response;


    }
}


