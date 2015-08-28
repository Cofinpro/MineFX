package de.cofinpro.dojo.minefx.multiplayer;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Logger;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class MulticastReceiver implements Runnable {

    private MulticastSocket socket;
    private static final int bufferLength = 256;
    private byte[] buffer = new byte[bufferLength];

    private Logger log = Logger.getLogger(getClass().getName());

    public MulticastReceiver() throws IOException {
        InetAddress address = Inet4Address.getByName("230.0.0.1");

        socket = new MulticastSocket(4445);
        socket.joinGroup(address);
    }

    @Override
    public void run() {
        DatagramPacket packet = new DatagramPacket(buffer, bufferLength);
        try {
            log.info("Listening...");
            socket.receive(packet);
            String message = new String(packet.getData());
            log.info(message);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();
        }
    }

    public static void main(String[] args) throws IOException {
        new MulticastReceiver().run();
    }
}
