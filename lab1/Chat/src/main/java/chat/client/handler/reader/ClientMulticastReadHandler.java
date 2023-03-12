package chat.client.handler.reader;

import chat.client.Client;
import chat.common.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class ClientMulticastReadHandler extends ClientUDPReadHandler {
    private static final Logger LOGGER = LogManager.getLogger(ClientMulticastReadHandler.class);

    public ClientMulticastReadHandler(Client client) {
        super(client, LOGGER);
    }

    @Override
    protected void handleMessage(Message message) {
        switch (message.getMessageType()) {
            case TEXT_UDP_RESP:
                if (!Objects.equals(message.getArguments()[0], client.getNick())) {
                    printAsciiArt(message);
                }
                break;
        }
    }

    @Override
    protected Message getMessage() {
        return client.getMulticastWriteReader().readMessage(client.getQuit());
    }
}
