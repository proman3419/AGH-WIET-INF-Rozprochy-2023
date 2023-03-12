package chat.client.handler.reader;

import chat.client.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientTCPReadHandler extends ClientReadHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ClientTCPReadHandler.class);

    public ClientTCPReadHandler(Client client) {
        super(client, LOGGER);
    }

    @Override
    public void run() {
        while (!client.getQuit().get()) {
            handleMessage(client.getIn().readMessage(client.getQuit()));
        }
    }
}
