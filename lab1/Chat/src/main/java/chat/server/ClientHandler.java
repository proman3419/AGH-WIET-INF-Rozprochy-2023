package chat.server;

import chat.common.ClientServerUtils;
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

import static chat.common.Constants.FAIL;
import static chat.common.Constants.OK;

public class ClientHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ClientHandler.class);
    private static final String NICK_TAKEN_RESP = "The nick is already taken";
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
            // If's purpose is to fail early
        }
        LOGGER.info("Client handler shutdown");
    }

    private boolean initClientSocket() {
        try {
            Thread.currentThread().setName(String.format("%d", clientSocket.getPort()));
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
            LOGGER.error("Couldn't connect to the client");
            return FAIL;
        }
        LOGGER.info("Client handler started");
        return OK;
    }

    private boolean initConnection() {
        Message message = in.readMessage();
        if (message == null) {
            return FAIL;
        }
        if (!message.checkMessageType(MessageType.INIT)) {
            LOGGER.error("Expected INIT");
            return FAIL;
        }
        nick = message.getArguments()[0];

        if (server.checkIfNickTaken(nick)) {
            LOGGER.info("Rejected the client, cause: " + NICK_TAKEN_RESP);
            out.sendMessage(MessageBuilder.getInstance()
                    .setMessageType(MessageType.INIT_NACK)
                    .setText(NICK_TAKEN_RESP)
                    .build());
            return FAIL;
        }

        LOGGER.info("Accepted the client");
        out.sendMessage(MessageBuilder.getInstance()
                .setMessageType(MessageType.INIT_ACK)
                .build()
        );
        server.addClient(nick, clientSocket, out);
        return OK;
    }

    private boolean testConnection() {
        boolean result = OK;
        Message message = in.readMessage();
        if (message == null || !message.checkMessageType(MessageType.HELLO)) {
            result = FAIL;
        }

        if (OK == result) {
            out.sendMessage(MessageBuilder.getInstance()
                    .setMessageType(MessageType.HELLO)
                    .build()
            );

            message = in.readMessage();
            if (message == null || !message.checkMessageType(MessageType.HELLO)) {
                result = FAIL;
            }
        }

        if (OK == result) {
            LOGGER.info("Connection test succeeded");
        } else {
            LOGGER.error("Connection test failed");
        }
        return result;
    }

    private boolean runMainLoop() {
        boolean quit = false;
        while (!quit && !server.getQuit().get()) {
            Message message = in.readMessage(server.getQuit());
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
        ClientServerUtils.closeSocket(clientSocket, LOGGER);
        server.removeClient(nick);
        return OK;
    }
}
