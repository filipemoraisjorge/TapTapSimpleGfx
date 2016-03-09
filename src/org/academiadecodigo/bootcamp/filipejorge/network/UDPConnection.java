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
        InetAddress address = InetAddress.getByName("255.255.255.255"); //read that most routers block this. 192.168.0.255 should be better but inflexible.
        DatagramPacket isSomeoneThere = new DatagramPacket(broadcastData, broadcastData.length, address, 8888);
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

            System.out.println("b received from: " + thereIsSomeone.getSocketAddress());
            System.out.println("b data: " + new String(thereIsSomeone.getData()).trim());

            String[] msgStrings = message.split(" "); //first part equals ANSWER, then the IP and Port
            System.out.println(message);
            if (msgStrings[0].equals(ANSWER)) {


                //System.out.println(thereIsSomeone.getAddress().getHostName() + " " + thereIsSomeone.getAddress().getHostAddress() + " " + message);


                //set up the permanent connection- from this side (must do the same on runListening)
                System.out.println("broad " + msgStrings[1] + " " + msgStrings[1]);
                System.out.println("broad " + thereIsSomeone.getAddress().getHostAddress() + " ");
                clientSocket = new DatagramSocket(new Integer(msgStrings[2]), InetAddress.getByName(msgStrings[1]));
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


        System.out.println("listening!");

        //Listen to all the UDP trafic that is destined for this port
        DatagramSocket listeningSocket;
        listeningSocket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
        listeningSocket.setBroadcast(true);

        // while (true) {

        //Receive a packet
        byte[] recvBuf = new byte[BUFFER_SIZE];
        DatagramPacket imSomeone = new DatagramPacket(recvBuf, recvBuf.length);
        listeningSocket.receive(imSomeone);

        //Packet received
        System.out.println("l received from: " + imSomeone.getAddress());
        System.out.println("l data: " + new String(imSomeone.getData()).trim());

        //See if the packet holds the right command (message)
        String message = new String(imSomeone.getData()).trim();

        if (message.equals(QUESTION)) {
            byte[] sendData = (ANSWER + " " + listeningSocket.getLocalAddress() + " " + listeningSocket.getLocalPort()).getBytes(); //joint IP and PORT
            System.out.println(ANSWER + " " + listeningSocket.getLocalSocketAddress() + " " + listeningSocket.getLocalPort());
            //Send a response
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, imSomeone.getAddress(), imSomeone.getPort());
            listeningSocket.send(sendPacket);

            System.out.println("l responded to: " + sendPacket.getAddress().getHostAddress());

            //set up the permanent connection- from this side
            System.out.println("list " + imSomeone.getPort() + " " + imSomeone.getAddress().getHostAddress());
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


