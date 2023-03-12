package chat.server;

import chat.common.ClientServerUtils;
import chat.common.instance.ChatInstance;
import chat.common.message.Message;
import chat.common.message.MessageBuilder;
import chat.common.message.MessageType;
import chat.common.wrapper.PrintWriterWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server implements ChatInstance {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    private final int portNumber;
    private ServerSocket serverSocket;
    private final HashMap<String, Socket> clientSockets = new HashMap<>(); // nick -> socket
    private final HashMap<String, PrintWriterWrapper> clientWriters = new HashMap<>(); // nick -> writer
    private final AtomicBoolean quit = new AtomicBoolean(false);
    private final AtomicBoolean quitSequenceExecuted = new AtomicBoolean(false);
    private final List<Thread> clientThreads = new ArrayList<>();

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void start() {
        Thread shutdownHookThread = new Thread(new ServerShutdownHook(this));
        shutdownHookThread.setName("Shutdown hook");
        Runtime.getRuntime().addShutdownHook(shutdownHookThread);
        try {
            serverSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            LOGGER.error("Server startup failed, error message: '{}'", e.getMessage());
        }
        LOGGER.info("Server started on port {}", portNumber);
        while (!quit.get()) {
            Socket clientSocket = null;
            try {
                if (serverSocket != null && !serverSocket.isClosed()) {
                    clientSocket = serverSocket.accept();
                    Thread clientThread = new Thread(new ClientHandler(clientSocket, this));
                    clientThreads.add(clientThread);
                    clientThread.start();
                }
            } catch (IOException ignored) {
            }
        }
        for (Thread thread : clientThreads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
        quitSequence();
    }

    void addClient(String nick, Socket socket, PrintWriterWrapper writer) {
        clientSockets.put(nick, socket);
        clientWriters.put(nick, writer);
    }

    void removeClient(String nick) {
        clientSockets.remove(nick);
        clientWriters.remove(nick);
    }

    void toOne(String receiver, Message message) {
        clientWriters.get(receiver).sendMessage(message);
    }

    void toAll(String sender, Message message) {
        for (Map.Entry<String, PrintWriterWrapper> entry : clientWriters.entrySet()) {
            if (!Objects.equals(entry.getKey(), sender)) {
                entry.getValue().sendMessage(message);
            }
        }
    }

    boolean checkIfNickTaken(String nick) {
        return clientSockets.containsKey(nick);
    }

    void quitSequence() {
        if (!quitSequenceExecuted.getAndSet(true)) {
            toAll("",
                    MessageBuilder.getInstance()
                            .setMessageType(MessageType.QUIT)
                            .build());
            closeServerSocket();
            clientSockets.forEach((s, socket) -> {
                ClientServerUtils.closeSocket(socket, LOGGER);
            });
            quit.set(true);
        }
    }

    private void closeServerSocket() {
        try {
            if (serverSocket.isClosed()) {
                LOGGER.info("Attempted to close the server socket but it has already been closed");
            } else {
                serverSocket.close();
                LOGGER.info("Closed the server socket");
            }
        } catch (IOException e) {
            LOGGER.error("Failed to close the server socket, error message: '{}'", e.getMessage());
        }
    }

    AtomicBoolean getQuit() {
        return quit;
    }
}
