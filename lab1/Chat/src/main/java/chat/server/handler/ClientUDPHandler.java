package chat.server.handler;

import chat.common.message.Message;
import chat.common.message.MessageBuilder;
import chat.common.message.MessageType;
import chat.common.writeread.udp.UDPWriteReader;
import chat.server.Server;

import java.net.DatagramSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Objects;

public class ClientUDPHandler implements Runnable {
    private final UDPWriteReader udpWriteReader;
    private final Server server;

    public ClientUDPHandler(DatagramSocket udpSocket, Server server) {
        udpWriteReader = new UDPWriteReader(udpSocket);
        this.server = server;
    }

    @Override
    public void run() {
        while (!server.getQuit().get()) {
            Message message = udpWriteReader.readMessage(server.getQuit());
            if (message != null) {
                switch (message.getMessageType()) {
                    case TEXT_UDP:
                        String nick = message.getArguments()[0];
                        toAll(nick,
                                MessageBuilder.modifyMessage(message)
                                        .setMessageType(MessageType.TEXT_UDP_RESP)
                                        .setArguments(nick)
                                        .build());
                        break;
                }
            }
        }
    }

    private synchronized void toAll(String sender, Message message) {
        for (Map.Entry<String, Socket> entry : server.getClientSockets().entrySet()) {
            if (!Objects.equals(entry.getKey(), sender)) {
                udpWriteReader.writeMessage(message, entry.getValue().getInetAddress(), entry.getValue().getPort());
            }
        }
    }
}
