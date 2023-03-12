package chat.server.handler;

import chat.common.ClientServerUtils;
import chat.common.message.Message;
import chat.common.message.MessageBuilder;
import chat.common.message.MessageType;
import chat.common.writeread.tcp.BufferedReaderWrapper;
import chat.common.writeread.tcp.PrintWriterWrapper;
import chat.server.Server;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicBoolean;

import static chat.common.Constants.FAIL;
import static chat.common.Constants.OK;

public class ClientTCPHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ClientTCPHandler.class);
    private static final String NICK_TAKEN_RESP = "The nick is already taken";
    private final Socket tcpSocket;
    private final Server server;
    private String nick;
    private PrintWriterWrapper out;
    private BufferedReaderWrapper in;
    private final AtomicBoolean quit = new AtomicBoolean(false);

    public ClientTCPHandler(Socket tcpSocket, Server server) {
        this.tcpSocket = tcpSocket;
        this.server = server;
    }

    @Override
    public void run() {
        if (initSockets() ||
                initConnection() ||
                testConnection() ||
                runMainLoop()
        ) {
            // If's purpose is to fail early
        }
        LOGGER.info("Client handler shutdown");
    }

    private boolean initSockets() {
        try {
            Thread.currentThread().setName(String.format("TCP Handler - %d", tcpSocket.getPort()));
            out = new PrintWriterWrapper(
                    new PrintWriter(tcpSocket.getOutputStream(), true),
                    tcpSocket.getPort()
            );
            in = new BufferedReaderWrapper(
                    new BufferedReader(new InputStreamReader(tcpSocket.getInputStream())),
                    tcpSocket.getPort()
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
            out.writeMessage(MessageBuilder.getInstance()
                    .setMessageType(MessageType.INIT_NACK)
                    .setText(NICK_TAKEN_RESP)
                    .build());
            return FAIL;
        }

        LOGGER.info("Accepted the client");
        out.writeMessage(MessageBuilder.getInstance()
                .setMessageType(MessageType.INIT_ACK)
                .build()
        );
        server.addClient(nick, tcpSocket, out);
        return OK;
    }

    private boolean testConnection() {
        boolean result = OK;
        Message message = in.readMessage();
        if (message == null || !message.checkMessageType(MessageType.HELLO)) {
            result = FAIL;
        }

        if (OK == result) {
            out.writeMessage(MessageBuilder.getInstance()
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

    protected void handleMessage(Message message) {
        if (message != null) {
            switch (message.getMessageType()) {
                case QUIT:
                    server.toOne(nick,
                            MessageBuilder.getInstance()
                                    .setMessageType(MessageType.QUIT)
                                    .build()
                    );
                    quit.set(true);
                    break;
                case TEXT_TCP:
                    server.toAll(nick,
                            MessageBuilder.modifyMessage(message)
                                    .setMessageType(MessageType.TEXT_TCP_RESP)
                                    .setArguments(nick)
                                    .build()
                    );
                    break;
            }
        }
    }

    private boolean runMainLoop() {
        while (!quit.get() && !server.getQuit().get()) {
            handleMessage(in.readMessage(server.getQuit()));
        }
        ClientServerUtils.closeSocket(tcpSocket, LOGGER);
        server.removeClient(nick);
        return OK;
    }
}
