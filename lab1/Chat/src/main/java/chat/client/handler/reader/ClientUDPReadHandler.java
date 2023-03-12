package chat.client.handler.reader;

import chat.client.Client;
import chat.common.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientUDPReadHandler extends ClientReadHandler {
    private static final Logger LOGGER = LogManager.getLogger(ClientUDPReadHandler.class);

    public ClientUDPReadHandler(Client client) {
        super(client, LOGGER);
    }

    protected ClientUDPReadHandler(Client client, Logger logger) {
        super(client, logger);
    }

    @Override
    protected void handleMessage(Message message) {
        switch (message.getMessageType()) {
            case TEXT_UDP_RESP:
                printAsciiArt(message);
                break;
        }
    }

    @Override
    protected Message getMessage() {
        return client.getUdpWriteReader().readMessage(client.getQuit());
    }

    protected void printAsciiArt(Message message) {
        System.out.println(">>> from: '" + message.getArguments()[0] + "'\n" + message.getText());
    }
}
