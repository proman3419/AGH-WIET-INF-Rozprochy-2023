package chat.server;

import chat.common.message.MessageBuilder;
import chat.common.message.MessageType;

public class ServerShutdownHook implements Runnable {
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
        server.getQuit().set(true);
    }
}
