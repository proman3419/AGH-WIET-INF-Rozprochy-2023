package chat.client;

import chat.common.ClientServerUtils;
import chat.common.instance.ChatInstance;
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
import java.util.concurrent.atomic.AtomicBoolean;

import static chat.common.Constants.FAIL;
import static chat.common.Constants.OK;

public class Client implements ChatInstance {
    private static final Logger LOGGER = LogManager.getLogger(Client.class);
    private static final String HOSTNAME = "localhost";
    private static final int CHECK_QUIT_SLEEP_MS = 300;
    private final int serverPortNumber;
    private final String nick;
    private Socket serverSocket;
    private PrintWriterWrapper out;
    private BufferedReaderWrapper in;
    private final AtomicBoolean quit = new AtomicBoolean(false);
    private final AtomicBoolean quitSequenceExecuted = new AtomicBoolean(false);

    public Client(int serverPortNumber, String nick) {
        this.serverPortNumber = serverPortNumber;
        this.nick = nick;
    }

    @Override
    public void start() {
        Thread shutdownHookThread = new Thread(new ClientShutdownHook(this));
        shutdownHookThread.setName("Shutdown hook");
        Runtime.getRuntime().addShutdownHook(shutdownHookThread);
        if (initServerSocket() ||
                initConnection() ||
                testConnection() ||
                runMainLoop()
        ) {
            // If's purpose is to fail early
        }
    }

    private boolean initServerSocket() {
        try {
            serverSocket = new Socket(HOSTNAME, serverPortNumber);
            out = new PrintWriterWrapper(
                    new PrintWriter(serverSocket.getOutputStream(), true),
                    serverSocket.getLocalPort(),
                    serverSocket.getPort()
            );
            in = new BufferedReaderWrapper(
                    new BufferedReader(new InputStreamReader(serverSocket.getInputStream())),
                    serverSocket.getPort(),
                    serverSocket.getLocalPort()
            );
        } catch (IOException e) {
            LOGGER.error("Couldn't connect to the server");
            return FAIL;
        }
        LOGGER.info("Client started on port {}", serverSocket.getLocalPort());
        return OK;
    }

    private boolean initConnection() {
        out.sendMessage(MessageBuilder.getInstance()
                .setMessageType(MessageType.INIT)
                .setArguments(nick)
                .build()
        );

        Message message = in.readMessage();
        if (message == null) {
            return FAIL;
        }

        if (message.checkMessageType(MessageType.INIT_ACK)) {
            LOGGER.info("Connected to the server");
            return OK;
        } else if (message.checkMessageType(MessageType.INIT_NACK)) {
            LOGGER.error("Couldn't connect to the server, cause: '{}'", message.getText());
        } else {
            LOGGER.error("Expected INIT_ACK/INIT_NACK");
        }
        return FAIL;
    }

    private boolean testConnection() {
        boolean result = OK;
        out.sendMessage(MessageBuilder.getInstance()
                .setMessageType(MessageType.HELLO)
                .build()
        );

        Message message = in.readMessage();
        if (message == null || !message.checkMessageType(MessageType.HELLO)) {
            result = FAIL;
        }

        if (OK == result) {
            out.sendMessage(MessageBuilder.getInstance()
                    .setMessageType(MessageType.HELLO)
                    .build()
            );
        }

        if (OK == result) {
            LOGGER.info("Connection test succeeded");
        } else {
            LOGGER.error("Connection test failed");
        }
        return result;
    }

    private boolean runMainLoop() {
        Thread clientReadThread = new Thread(new ClientReadHandler(this));
        Thread clientWriteThread = new Thread(new ClientWriteHandler(this));
        clientReadThread.setName("Read");
        clientWriteThread.setName("Write");
        clientReadThread.start();
        clientWriteThread.start();

        while (!quit.get()) {
            try {
                Thread.sleep(CHECK_QUIT_SLEEP_MS);
            } catch (InterruptedException ignored) {
            }
        }

        quitSequence();

        return OK;
    }

    void quitSequence() {
        if (!quitSequenceExecuted.get()) {
            quitSequenceExecuted.set(true);
            out.sendMessage(MessageBuilder.getInstance()
                    .setMessageType(MessageType.QUIT)
                    .build());
            ClientServerUtils.closeSocket(serverSocket, LOGGER);
            quit.set(true);
        }
    }

    AtomicBoolean getQuit() {
        return quit;
    }

    public AtomicBoolean getQuitSequenceExecuted() {
        return quitSequenceExecuted;
    }

    PrintWriterWrapper getOut() {
        return out;
    }

    BufferedReaderWrapper getIn() {
        return in;
    }

    String getNick() {
        return nick;
    }
}
