package chat.client;

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
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class Client implements ChatInstance {
    private static final Logger LOGGER = LogManager.getLogger(Client.class);
    private static final String HOSTNAME = "localhost";
    private static final boolean FAIL = true;
    private static final boolean OK = false;
    private final int serverPortNumber;
    private final String nick;
    private Socket serverSocket;
    private PrintWriterWrapper out;
    private BufferedReaderWrapper in;
    private AtomicBoolean quit = new AtomicBoolean(false);
    private AtomicBoolean quitSequenceExecuted = new AtomicBoolean(false);

    public Client(int serverPortNumber, String nick) {
        this.serverPortNumber = serverPortNumber;
        this.nick = nick;
    }

    @Override
    public void start() {
        Runtime.getRuntime().addShutdownHook(new Thread(new ClientShutdownHook(this)));

        if (initServerSocket() ||
                initConnection() ||
                testConnection() ||
                runMainLoop()
        ) {
            // If's purpose is to fail early
            System.out.println("Shutdown");
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
            LOGGER.error("Couldn't connect to the server on port " + serverPortNumber);
            return FAIL;
        }
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
            return OK;
        } else if (message.checkMessageType(MessageType.INIT_NACK)) {
            System.out.println(message.getText());
        }
        return FAIL;
    }

    private boolean testConnection() {
        out.sendMessage(MessageBuilder.getInstance()
                .setMessageType(MessageType.HELLO)
                .build()
        );

        Message message = in.readMessage();
        System.out.println(message.getMessageType());
        return OK;
    }

    private boolean runMainLoop() {
        Scanner console = new Scanner(System.in);
        ClientReadHandler clientReadHandler = new ClientReadHandler(this, in, nick);
        Thread clientReadThread = new Thread(clientReadHandler);
        ClientWriteHandler clientWriteHandler = new ClientWriteHandler(this, console, out, nick);
        Thread clientWriteThread = new Thread(clientWriteHandler);

        clientReadThread.start();
        clientWriteThread.start();

        while (!quit.get()) {

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
            try {
                serverSocket.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            quit.set(true);
        }
    }

    AtomicBoolean getQuit() {
        return quit;
    }

    PrintWriterWrapper getOut() {
        return out;
    }

    public Socket getServerSocket() {
        return serverSocket;
    }
}
