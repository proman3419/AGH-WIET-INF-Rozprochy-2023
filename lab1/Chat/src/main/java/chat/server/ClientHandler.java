package chat.server;

import chat.common.message.Message;
import chat.common.message.MessageBuilder;
import chat.common.message.MessageType;
import chat.common.wrapper.BufferedReaderWrapper;
import chat.common.wrapper.PrintWriterWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);
    private static final boolean FAIL = true;
    private static final boolean OK = false;
    private final Socket clientSocket;
    private final Server server;
    private String nick;
    private PrintWriterWrapper out;
    private BufferedReaderWrapper in;

    public ClientHandler(Socket clientSocket, Server server) {
        this.clientSocket = clientSocket;
        this.server = server;
    }

    @Override
    public void run() {
        if (initClientSocket() ||
                initConnection() ||
                testConnection() ||
                runMainLoop()
        ) {

        }
    }

    private boolean initClientSocket() {
        try {
            out = new PrintWriterWrapper(
                    new PrintWriter(clientSocket.getOutputStream(), true),
                    clientSocket.getLocalPort(),
                    clientSocket.getPort()
            );
            in = new BufferedReaderWrapper(
                    new BufferedReader(new InputStreamReader(clientSocket.getInputStream())),
                    clientSocket.getPort(),
                    clientSocket.getLocalPort()
            );
        } catch (IOException e) {
            LOGGER.error("Couldn't connect to the server on port " + clientSocket);
            return FAIL;
        }
        return OK;
    }

    private boolean initConnection() {
        Message message = in.readMessage();
        if (message == null) {
            return FAIL;
        }
        if (!message.checkMessageType(MessageType.INIT)) {
            LOGGER.warn("Expected `INIT~<nick>~` message");
            return FAIL;
        }
        nick = message.getArguments()[0];

        if (server.checkIfNickTaken(nick)) {
            out.sendMessage(MessageBuilder.getInstance()
                    .setMessageType(MessageType.INIT_NACK)
                    .setText("The nick is already taken")
                    .build());
            return FAIL;
        }

        out.sendMessage(MessageBuilder.getInstance()
                .setMessageType(MessageType.INIT_ACK)
                .build()
        );
        server.addClient(nick, clientSocket, out);
        return OK;
    }

    private boolean testConnection() {
        System.out.println(in.readMessage().getMessageType());

        out.sendMessage(MessageBuilder.getInstance()
                .setMessageType(MessageType.HELLO)
                .build()
        );
        return OK;
    }

    private boolean runMainLoop() {
        boolean quit = false;
        while (!quit && !server.getQuit().get()) {
            Message message = in.readMessage();
            if (message != null) {
                switch (message.getMessageType()) {
                    case QUIT:
                        server.toOne(nick,
                                MessageBuilder.getInstance()
                                        .setMessageType(MessageType.QUIT)
                                        .build());
                        quit = true;
                        break;
                    case TEXT:
                        server.toAll(nick,
                                MessageBuilder.modifyMessage(message)
                                        .setMessageType(MessageType.TEXT_RESP)
                                        .setArguments(nick)
                                        .build());
                        break;
                }
            }
        }
        server.removeClient(nick);
        return OK;
    }
}
