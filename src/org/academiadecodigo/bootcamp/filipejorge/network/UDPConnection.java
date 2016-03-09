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

    private DatagramSocket clientSocket;

    public UDPConnection() {
        try {
            sentBroadcast();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sentBroadcast() throws IOException {
        DatagramSocket broadcastSocket = new DatagramSocket();
        broadcastSocket.setBroadcast(true);

        //broadcast to private network(?)
        byte[] broadcastData = QUESTION.getBytes();
        InetAddress address = InetAddress.getByName("255.255.255.255");
        DatagramPacket isSomeoneThere = new DatagramPacket(broadcastData, broadcastData.length, address, 8888);
        broadcastSocket.send(isSomeoneThere);
        System.out.println("broadcasted.");

        //waiting response
        byte[] receiveData = new byte[BUFFER_SIZE];
        DatagramPacket thereIsSomeone = new DatagramPacket(receiveData, receiveData.length);
        System.out.println("waiting response.");
        broadcastSocket.setSoTimeout(12000);
        try {
            broadcastSocket.receive(thereIsSomeone);
            String message = new String(thereIsSomeone.getData()).trim();
            if (message.equals(ANSWER)) {
                //System.out.println(thereIsSomeone.getAddress().getHostName() + " " + thereIsSomeone.getAddress().getHostAddress() + " " + message);
                //set up the permanent connection- from this side (much do the same on runListening)
                System.out.println("broad " + thereIsSomeone.getPort() + " " + thereIsSomeone.getAddress());
                clientSocket = new DatagramSocket(thereIsSomeone.getSocketAddress());
                System.out.println("Broadcaster. Connections settled: " + clientSocket.getInetAddress() + " " + clientSocket.getPort());
            } else {//do something if the answer is incorrect...
            }

        } catch (SocketTimeoutException ste) {

            System.out.println("Seems nobody's there. Start listening and wait....");
            runListener();

        } finally {


            //close socket
            broadcastSocket.close();

        }


    }

    private void runListener() throws IOException {


        DatagramSocket listeningSocket;

        //Listen to all the UDP trafic that is destined for this port
        listeningSocket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
        listeningSocket.setBroadcast(true);

        // while (true) {
        System.out.println("listening!");

        //Receive a packet
        byte[] recvBuf = new byte[BUFFER_SIZE];
        DatagramPacket imSomeone = new DatagramPacket(recvBuf, recvBuf.length);
        listeningSocket.receive(imSomeone);

        //Packet received
        System.out.println("received from: " + imSomeone.getAddress().getHostAddress());
        System.out.println("data: " + new String(imSomeone.getData()).trim());

        //See if the packet holds the right command (message)
        String message = new String(imSomeone.getData()).trim();
        if (message.equals(QUESTION)) {
            byte[] sendData = ANSWER.getBytes();
            //Send a response
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, imSomeone.getAddress(), imSomeone.getPort());
            listeningSocket.send(sendPacket);

            System.out.println("responded to: " + sendPacket.getAddress().getHostAddress());

            //set up the permanent connection- from this side
            System.out.println("list " + imSomeone.getPort() + " " + imSomeone.getAddress());
            clientSocket = new DatagramSocket(imSomeone.getSocketAddress());
            System.out.println("Listener. Connections settled: " + clientSocket.getInetAddress() + " " + clientSocket.getPort());
        }
        // }

    }


    public void sendDatagram(String message) throws IOException {

        /*clientSocket = new DatagramSocket();
        InetAddress address = InetAddress.getByName(host);
*/
        byte[] sendData = message.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientSocket.getInetAddress(), clientSocket.getPort());
        clientSocket.send(sendPacket);

    }

    public String receiveDatagram() throws IOException {

        byte[] receiveData = new byte[BUFFER_SIZE];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        String response = null;

        System.out.println("Waiting for return packet");
        clientSocket.setSoTimeout(10000);

        try {

            clientSocket.receive(receivePacket);
            response = new String(receivePacket.getData(), 0, receivePacket.getLength());

            System.out.println("Received Message: " + response);

        } catch (SocketTimeoutException ste) {

            System.out.println("Timeout Occurred: Packet assumed lost");

        } finally {

            clientSocket.close();
        }
        return response;


    }

}


