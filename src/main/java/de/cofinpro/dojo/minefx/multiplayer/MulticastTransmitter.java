package de.cofinpro.dojo.minefx.multiplayer;


import de.cofinpro.dojo.minefx.GamePanel;

import java.io.*;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Gregor Tudan, Cofinpro AG
 */
public class MulticastTransmitter implements Runnable {

    private MulticastSocket socket;

    private static final int bufferLength = 32 * 1024;
    private byte[] sendBuffer = new byte[bufferLength];

    private static MulticastTransmitter instance = null;
    private final InetAddress group;
    private static final int port = 4445;

    private GamePanel gamePanel;

    private final Logger log = Logger.getLogger(getClass().getName());
    private Thread transmitterThread;


    private MulticastTransmitter() throws IOException {
        socket = new MulticastSocket(port);
        group = InetAddress.getByName("230.0.0.1");
        socket.joinGroup(group);
    }

    public static MulticastTransmitter getInstance() throws IOException {
        if (instance == null) {
            instance = new MulticastTransmitter();
        }
        return instance;
    }

    public void listen() {
        transmitterThread = new Thread(this);
        transmitterThread.setDaemon(true);
        transmitterThread.start();
    }

    public void send(MultiplayerEvent event) throws IOException {
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream stream = new ObjectOutputStream(out);
        stream.writeObject(event);

        DatagramPacket packet = new DatagramPacket(sendBuffer, sendBuffer.length, group, port);

        packet.setData(out.toByteArray());
        socket.send(packet);
    }

    @Override
    public void run() {
        final byte[] receiveBuffer = new byte[bufferLength];

        DatagramPacket packet = new DatagramPacket(receiveBuffer, bufferLength);
        while (!Thread.currentThread().isInterrupted()) {
            log.info("Listening...");
            try {
                socket.receive(packet);
                parsePacket(packet);
            } catch (IOException e) {
                log.log(Level.WARNING, "Error reading packet", e);
            }
        }

    }

    private void parsePacket(DatagramPacket packet) {
        try {
            log.info("Received...");
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(packet.getData()));
            Object receivedObject = objectInputStream.readObject();
            if (receivedObject instanceof MultiplayerEvent) {
                log.info("Received " + receivedObject.toString());
                ((MultiplayerEvent) receivedObject).execute(gamePanel);
            } else {
                throw new IOException("Received packet is not a multiplayer event: " + receivedObject.getClass());
            }
        } catch (IOException | ClassNotFoundException e) {
            log.log(Level.WARNING, "Ignoring bad packet packet", e);
        }
    }

    public void setGamePanel(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void close() throws IOException {
        transmitterThread.interrupt();
        try {
            socket.leaveGroup(group);
        } finally {
            socket.close();
        }
    }
}
