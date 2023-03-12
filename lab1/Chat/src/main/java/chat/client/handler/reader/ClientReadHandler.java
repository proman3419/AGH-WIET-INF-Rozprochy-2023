package chat.client.handler.reader;

import chat.client.Client;
import chat.common.message.Message;
import org.apache.logging.log4j.Logger;

public abstract class ClientReadHandler implements Runnable {
    protected static final int LOG_QUIT_SLEEP_MS = 300;
    protected final Client client;
    protected final Logger logger;

    public ClientReadHandler(Client client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    protected abstract void handleMessage(Message message);

    protected abstract Message getMessage();

    @Override
    public void run() {
        while (!client.getQuit().get()) {
            Message message = getMessage();
            if (message != null) {
                handleMessage(message);
            }
        }
    }
}
