package chat.server;

import chat.common.instance.ChatInstance;
import chat.common.message.Message;
import chat.common.wrapper.PrintWriterWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server implements ChatInstance {
    private static final Logger LOGGER = LogManager.getLogger(Server.class);
    private final int portNumber;
    private ServerSocket mainSocket;
    private final HashMap<String, Socket> clientSockets = new HashMap<>(); // nick -> socket
    private final HashMap<String, PrintWriterWrapper> clientWriters = new HashMap<>(); // nick -> writer
    private AtomicBoolean quit = new AtomicBoolean(false);
    private final List<Thread> clientThreads = new ArrayList<>();

    public Server(int portNumber) {
        this.portNumber = portNumber;
    }

    @Override
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(new ServerShutdownHook(this)));

        LOGGER.info("Server starting on port " + portNumber);
        try {
            mainSocket = new ServerSocket(portNumber);
        } catch (IOException e) {
            LOGGER.error("Server startup failed, error message: `" + e.getMessage() + "`");
        }
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
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
            if (entry.getKey() != sender) {
                entry.getValue().sendMessage(message);
            }
        }
    }

    boolean checkIfNickTaken(String nick) {
        return clientSockets.containsKey(nick);
    }

    public AtomicBoolean getQuit() {
        return quit;
    }
}
