package chat.server;

import chat.common.instance.ChatInstance;
import chat.common.message.Message;
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
    private ServerSocket mainSocket;
    private final HashMap<String, Socket> clientSockets = new HashMap<>(); // nick -> socket
    private final HashMap<String, PrintWriterWrapper> clientWriters = new HashMap<>(); // nick -> writer
    private final AtomicBoolean quit = new AtomicBoolean(false);
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
            mainSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            LOGGER.error("Server startup failed, error message: '{}'", e.getMessage());
        }
        LOGGER.info("Server started on port {}", portNumber);
        while (!quit.get()) {
            Socket clientSocket = null;
            try {
                clientSocket = mainSocket.accept();
                Thread clientThread = new Thread(new ClientHandler(clientSocket, this));
                clientThread.start();
            } catch (IOException e) {
                LOGGER.error(e.getMessage());
            }
        }
        for (Thread thread : clientThreads) {
            try {
                thread.join();
            } catch (InterruptedException ignored) {
            }
        }
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

    AtomicBoolean getQuit() {
        return quit;
    }

    HashMap<String, Socket> getClientSockets() {
        return clientSockets;
    }
}
