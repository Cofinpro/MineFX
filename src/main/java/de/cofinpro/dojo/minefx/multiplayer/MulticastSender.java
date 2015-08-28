package de.cofinpro.dojo.minefx.multiplayer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class MulticastSender {

    private byte[] buffer = new byte[256 * 1024];
    private DatagramSocket socket = new DatagramSocket();
    private InetAddress group;

    private static MulticastSender instance = null;


    private MulticastSender() throws SocketException {
        socket = new DatagramSocket();
        try {
            group = Inet4Address.getByName("230.0.0.1");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void send(MultiplayerEvent event) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(event);

        int port = 4445;
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, group, port);

        packet.setData(out.toByteArray());
        socket.send(packet);
    }

    public static void main(String[] args) throws IOException {
        new MulticastSender().send(new ClickEvent(1,1));
    }

    public void close() {
        socket.close();
    }

    public static MulticastSender getInstance() throws SocketException, UnknownHostException {
        if (instance == null) {
            instance = new MulticastSender();
        }
        return instance;
    }
}
