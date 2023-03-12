package chat.common.writeread.udp;

import chat.common.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketTimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;

public class UDPWriteReader {
    private static final Logger LOGGER = LogManager.getLogger(UDPWriteReader.class);
    private static final int READ_BUFFER_SIZE_B = 1024;
    private final byte[] readBuffer = new byte[READ_BUFFER_SIZE_B];
    private final DatagramSocket udpSocket;

    public UDPWriteReader(DatagramSocket udpSocket) {
        this.udpSocket = udpSocket;
    }

    @Nullable
    public Message readMessage(AtomicBoolean suppressLogger) {
        Message message = null;
        DatagramPacket recPacket = new DatagramPacket(readBuffer, READ_BUFFER_SIZE_B);
        try {
            udpSocket.receive(recPacket);
            message = new Message(new String(recPacket.getData(), recPacket.getOffset(), recPacket.getLength()));
            // change logging
            LOGGER.debug("Read message from {}:{} with content '{}'", recPacket.getAddress(), recPacket.getPort(), message);
        } catch (SocketTimeoutException ignored) {
            // Timeout is expected because I'm using DatagramSocket::setSoTimeout
        } catch (IOException e) {
            if (!suppressLogger.get()) {
                LOGGER.error("Failed to read message, error message: '{}'", e.getMessage());
            }
        }
        return message;
    }

    public void writeMessage(Message message, InetAddress receiverAddress, int receiverPort) {
        byte[] writeBuffer = message.toString().getBytes();
        DatagramPacket packet = new DatagramPacket(writeBuffer, writeBuffer.length, receiverAddress, receiverPort);
        try {
            udpSocket.send(packet);
            LOGGER.debug("Sent message to {}:{} with content '{}'", receiverAddress, receiverPort, message);
        } catch (IOException e) {
            LOGGER.debug("Failed to send message to {}:{} with content '{}'", receiverAddress, receiverPort, message);
        }
    }
}
