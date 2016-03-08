package org.academiadecodigo.bootcamp.filipejorge.network;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @author Filipe Jorge.
 *         At <Academia de Código_> on 08/03/16.
 */

public class UDPServerTest {


    public static void main(String[] args) throws IOException {
        UDPServerTest server = new UDPServerTest();
    }


    public UDPServerTest() throws IOException {
        run();
    }

    public void run() throws IOException {


    DatagramSocket socket;

        //Keep a socket open to listen to all the UDP trafic that is destined for this port
        socket = new DatagramSocket(8888, InetAddress.getByName("0.0.0.0"));
        socket.setBroadcast(true);

        while (true) {
            System.out.println(getClass().getName() + ">>>Ready to receive broadcast packets!");

            //Receive a packet
            byte[] recvBuf = new byte[15000];
            DatagramPacket packet = new DatagramPacket(recvBuf, recvBuf.length);
            socket.receive(packet);

            //Packet received
            System.out.println(getClass().getName() + ">>>Discovery packet received from: " + packet.getAddress().getHostAddress());
            System.out.println(getClass().getName() + ">>>Packet received; data: " + new String(packet.getData()));

            //See if the packet holds the right command (message)
            String message = new String(packet.getData()).trim();
            if (message.equals("TAPTAP_SOMEONETHERE")) {
                byte[] sendData = "TAPTAP_YESIMHERE".getBytes();

                //Send a response
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, packet.getAddress(), packet.getPort());
                socket.send(sendPacket);

                System.out.println(getClass().getName() + ">>>Sent packet to: " + sendPacket.getAddress().getHostAddress());
            }
        }

    }
}
