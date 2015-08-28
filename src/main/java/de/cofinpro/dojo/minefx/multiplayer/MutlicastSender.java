package de.cofinpro.dojo.minefx.multiplayer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.InetAddress;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class MutlicastSender {

    private byte[] buffer = new byte[256 * 1024];

    public void send(MultiplayerEvent event) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(event);


        DatagramSocket socket = new DatagramSocket();
        InetAddress group = Inet4Address.getByName("230.0.0.1");
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, 4445);

        packet.setData(out.toByteArray());
        socket.send(packet);
        socket.close();
    }

    public static void main(String[] args) throws IOException {
        new MutlicastSender().send(new ClickEvent(5,5));
    }

}
