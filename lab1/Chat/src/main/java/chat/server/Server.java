package chat.server;

import chat.common.ClientServerUtils;
import chat.common.instance.ChatInstance;
import chat.common.message.Message;
import chat.common.message.MessageBuilder;
import chat.common.message.MessageType;
import chat.common.writeread.tcp.PrintWriterWrapper;
import chat.server.handler.ClientTCPHandler;
import chat.server.handler.ClientUDPHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static chat.common.Constants.UDP_SOCKET_TIMEOUT_MS;

public class Server implements ChatInstance {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    private final int portNumber;
    private ServerSocket tcpServerSocket;
    private DatagramSocket udpServerSocket;
    private final HashMap<String, Socket> clientSockets = new HashMap<>(); // nick -> socket
    private final HashMap<String, PrintWriterWrapper> clientWriters = new HashMap<>(); // nick -> writer
    private final AtomicBoolean quit = new AtomicBoolean(false);
    private final AtomicBoolean quitSequenceExecuted = new AtomicBoolean(false);
    private final List<Thread> tcpThreads = new ArrayList<>();

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void start() {
        Thread shutdownHookThread = new Thread(new ServerShutdownHook(this));
        shutdownHookThread.setName("Shutdown hook");
        Runtime.getRuntime().addShutdownHook(shutdownHookThread);
        try {
            tcpServerSocket = new ServerSocket(portNumber);
            udpServerSocket = new DatagramSocket(portNumber);
            udpServerSocket.setSoTimeout(UDP_SOCKET_TIMEOUT_MS);
        } catch (IOException e) {
            LOGGER.error("Server startup failed, error message: '{}'", e.getMessage());
            System.exit(1);
        }
        LOGGER.info("Server started on port {}", portNumber);
        Thread udpHandlerThread = new Thread(new ClientUDPHandler(udpServerSocket, this));
        udpHandlerThread.setName("UDP Handler");
        udpHandlerThread.start();
        while (!quit.get()) {
            try {
                if (!tcpServerSocket.isClosed()) {
                    Socket tcpClientSocket = tcpServerSocket.accept();
                    Thread tcpHandlerThread = new Thread(new ClientTCPHandler(tcpClientSocket, this));
                    tcpThreads.add(tcpHandlerThread);
                    tcpHandlerThread.start();
                }
            } catch (IOException ignored) {
            }
        }
        for (Thread thread : tcpThreads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
        quitSequence();
    }

    public void addClient(String nick, Socket socket, PrintWriterWrapper writer) {
        clientSockets.put(nick, socket);
        clientWriters.put(nick, writer);
    }

    public void removeClient(String nick) {
        clientSockets.remove(nick);
        clientWriters.remove(nick);
    }

    public void toOne(String receiver, Message message) {
        clientWriters.get(receiver).writeMessage(message);
    }

    public void toAll(String sender, Message message) {
        for (Map.Entry<String, PrintWriterWrapper> entry : clientWriters.entrySet()) {
            if (!Objects.equals(entry.getKey(), sender)) {
                entry.getValue().writeMessage(message);
            }
        }
    }

    public boolean checkIfNickTaken(String nick) {
        return clientSockets.containsKey(nick);
    }

    void quitSequence() {
        if (!quitSequenceExecuted.getAndSet(true)) {
            toAll("",
                    MessageBuilder.getInstance()
                            .setMessageType(MessageType.QUIT)
                            .build()
            );
            ClientServerUtils.closeCloseable(tcpServerSocket, "TCP server socket", LOGGER);
            clientSockets.forEach((s, socket) -> {
                ClientServerUtils.closeCloseable(socket, s, LOGGER);
            });
            ClientServerUtils.closeCloseable(udpServerSocket, "UDP server socket", LOGGER);
            quit.set(true);
        }
    }

    public AtomicBoolean getQuit() {
        return quit;
    }

    public HashMap<String, Socket> getClientSockets() {
        return clientSockets;
    }
}
