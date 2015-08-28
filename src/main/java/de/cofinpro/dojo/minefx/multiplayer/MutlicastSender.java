package de.cofinpro.dojo.minefx.multiplayer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class MutlicastSender {



    public void send() throws IOException {
        byte[] buffer = new byte[256];
        DatagramSocket socket = new DatagramSocket();
        InetAddress group = Inet4Address.getByName("230.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, 4445);

        packet.setData("Foo".getBytes());
        socket.send(packet);
    }

    public static void main(String[] args) throws IOException {
        new MutlicastSender().send();
    }

}
