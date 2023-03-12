package chat.client.handler.reader;

import chat.client.Client;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientUDPReadHandler extends ClientReadHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ClientUDPReadHandler.class);


    public ClientUDPReadHandler(Client client) {
        super(client, LOGGER);
    }

    @Override
    public void run() {
        while (!client.getQuit().get()) {
            handleMessage(client.getUdpWriteReader().readMessage(client.getQuit()));
        }
    }
}
