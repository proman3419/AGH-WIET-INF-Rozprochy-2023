package chat.client;

import chat.client.handler.reader.ClientMulticastReadHandler;
import chat.client.handler.reader.ClientTCPReadHandler;
import chat.client.handler.reader.ClientUDPReadHandler;
import chat.client.handler.writer.ClientWriteHandler;
import chat.common.ClientServerUtils;
import chat.common.instance.ChatInstance;
import chat.common.message.Message;
import chat.common.message.MessageBuilder;
import chat.common.message.MessageType;
import chat.common.writeread.tcp.BufferedReaderWrapper;
import chat.common.writeread.tcp.PrintWriterWrapper;
import chat.common.writeread.udp.UDPWriteReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static chat.common.Constants.*;

public class Client implements ChatInstance {
    private static final Logger LOGGER = LogManager.getLogger(Client.class);
    private static final int CHECK_QUIT_SLEEP_MS = 300;
    public static final String MULTICAST_IP = "235.1.2.3";
    public static final int MULTICAST_PORT = 16821;
    private final int serverPortNumber;
    private int portNumber;
    private final String nick;
    private Socket tcpSocket;
    private DatagramSocket udpSocket;
    private MulticastSocket multicastSocket;
    private InetAddress multicastGroup;
    private PrintWriterWrapper out;
    private BufferedReaderWrapper in;
    private UDPWriteReader udpWriteReader;
    private UDPWriteReader multicastWriteReader;
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
        if (initSockets() ||
                initConnection() ||
                testConnection() ||
                runMainLoop()
        ) {
            // If's purpose is to fail early
        }
        quitSequence();
    }

    private boolean initSockets() {
        try {
            tcpSocket = new Socket(SERVER_HOSTNAME, serverPortNumber);
            out = new PrintWriterWrapper(
                    new PrintWriter(tcpSocket.getOutputStream(), true),
                    tcpSocket.getPort()
            );
            in = new BufferedReaderWrapper(
                    new BufferedReader(new InputStreamReader(tcpSocket.getInputStream())),
                    tcpSocket.getPort()
            );

            portNumber = tcpSocket.getLocalPort();
            udpSocket = new DatagramSocket(portNumber);
            udpSocket.setSoTimeout(UDP_SOCKET_TIMEOUT_MS); // To break the blocking DatagramSocket::receive call
            udpWriteReader = new UDPWriteReader(udpSocket);

            multicastSocket = new MulticastSocket(MULTICAST_PORT);
            multicastGroup = getInetAddressByName(MULTICAST_IP);
            multicastSocket.joinGroup(multicastGroup);
            LOGGER.info("Joined a multicast group '{}'", multicastGroup);
            multicastWriteReader = new UDPWriteReader(multicastSocket);
        } catch (IOException e) {
            LOGGER.error("Couldn't connect to the server");
            return FAIL;
        }
        LOGGER.info("Client started on port {}", tcpSocket.getLocalPort());
        return OK;
    }

    private boolean initConnection() {
        out.writeMessage(MessageBuilder.getInstance()
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
        out.writeMessage(MessageBuilder.getInstance()
                .setMessageType(MessageType.HELLO)
                .build()
        );

        Message message = in.readMessage();
        if (message == null || !message.checkMessageType(MessageType.HELLO)) {
            result = FAIL;
        }

        if (OK == result) {
            out.writeMessage(MessageBuilder.getInstance()
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
        Thread clientTCPReadThread = new Thread(new ClientTCPReadHandler(this));
        Thread clientUDPReadThread = new Thread(new ClientUDPReadHandler(this));
        Thread clientMulticastReadThread = new Thread(new ClientMulticastReadHandler(this));
        Thread clientWriteThread = new Thread(new ClientWriteHandler(this));

        clientTCPReadThread.setName("TCP Reader");
        clientUDPReadThread.setName("UDP Reader");
        clientWriteThread.setName("Writer");
        clientMulticastReadThread.setName("Multicast Reader");

        clientTCPReadThread.start();
        clientUDPReadThread.start();
        clientWriteThread.start();
        clientMulticastReadThread.start();

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
        if (!quitSequenceExecuted.getAndSet(true)) {
            if (out != null) {
                out.writeMessage(MessageBuilder.getInstance()
                        .setMessageType(MessageType.QUIT)
                        .build()
                );
            }
            ClientServerUtils.closeSocket(tcpSocket, LOGGER);
            ClientServerUtils.closeCloseable(udpSocket, "UDP socket", LOGGER);
            try {
                multicastSocket.leaveGroup(multicastGroup);
                LOGGER.info("Left the multicast group '{}'", multicastGroup);
            } catch (IOException e) {
                LOGGER.error("Failed to leave the multicast group '{}'", multicastGroup);
            }
            ClientServerUtils.closeCloseable(multicastSocket, "Multicast socket", LOGGER);
            quit.set(true);
        }
    }

    public InetAddress getInetAddressByName(String name) {
        InetAddress localhost = null;
        try {
            localhost = InetAddress.getByName(LOCALHOST);
            return InetAddress.getByName(name);
        } catch (UnknownHostException e) {
            LOGGER.error("The hostname '{}' couldn't be resolved, error message: '{}'; using the default value '{}'", name, e.getMessage(), localhost);
        }
        return localhost;
    }

    public AtomicBoolean getQuit() {
        return quit;
    }

    public AtomicBoolean getQuitSequenceExecuted() {
        return quitSequenceExecuted;
    }

    public PrintWriterWrapper getOut() {
        return out;
    }

    public BufferedReaderWrapper getIn() {
        return in;
    }

    public String getNick() {
        return nick;
    }

    public UDPWriteReader getUdpWriteReader() {
        return udpWriteReader;
    }

    public Socket getTcpSocket() {
        return tcpSocket;
    }

    public UDPWriteReader getMulticastWriteReader() {
        return multicastWriteReader;
    }

    public InetAddress getMulticastGroup() {
        return multicastGroup;
    }
}
