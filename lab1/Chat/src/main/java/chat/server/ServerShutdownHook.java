package chat.server;

import chat.common.ClientServerUtils;
import chat.common.message.MessageBuilder;
import chat.common.message.MessageType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerShutdownHook implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ServerShutdownHook.class);
    private final Server server;

    public ServerShutdownHook(Server server) {
        this.server = server;
    }

    @Override
    public void run() {
        server.toAll("",
                MessageBuilder.getInstance()
                        .setMessageType(MessageType.QUIT)
                        .build());
        server.getClientSockets().forEach((s, socket) -> {
            ClientServerUtils.closeSocket(socket, LOGGER);
        });
        server.getQuit().set(true);
    }
}
