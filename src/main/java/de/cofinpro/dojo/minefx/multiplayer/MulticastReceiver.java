package de.cofinpro.dojo.minefx.multiplayer;


import de.cofinpro.dojo.minefx.GamePanel;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
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
    private InetAddress multipass;
    private GamePanel gamePanel;

    private Logger log = Logger.getLogger(getClass().getName());

    public MulticastReceiver(GamePanel gamePanel) throws IOException {
        multipass = Inet4Address.getByName("230.0.0.1");

        socket = new MulticastSocket(4445);
        socket.joinGroup(multipass);

        this.gamePanel = gamePanel;
    }

    @Override
    public void run() {
        DatagramPacket packet = new DatagramPacket(buffer, bufferLength);
        try {
            while (true) {
                log.info("Listening...");
                socket.receive(packet);
                ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
                Object receivedObject = objectInputStream.readObject();
                if (receivedObject instanceof ClickEvent) {
                    log.info("Received " + receivedObject.toString());
                    ClickEvent clickEvent = (ClickEvent) receivedObject;
                    gamePanel.revealField(clickEvent.getX(), clickEvent.getY());
                } else {
                    throw new RuntimeException("Unhandled Event received. Fail hard!");
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.leaveGroup(multipass);
            } catch (IOException e) {
                e.printStackTrace();
            }
            socket.close();
        }
    }
}
