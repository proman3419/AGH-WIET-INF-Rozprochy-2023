package chat.client.handler.reader;

import chat.client.Client;
import chat.common.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientTCPReadHandler extends ClientReadHandler {
    private static final Logger LOGGER = LogManager.getLogger(ClientTCPReadHandler.class);

    public ClientTCPReadHandler(Client client) {
        super(client, LOGGER);
    }

    protected void handleMessage(Message message) {
        switch (message.getMessageType()) {
            case QUIT:
                client.getQuit().set(true);
                if (!client.getQuitSequenceExecuted().get()) {
                    try {
                        // To display it as the last message
                        Thread.sleep(LOG_QUIT_SLEEP_MS);
                    } catch (InterruptedException ignored) {
                    }
                    LOGGER.info("Server disconnected, press ENTER to exit");
                }
                return;
            case TEXT_TCP_RESP:
                printUserMessage(message);
                break;
        }
    }

    @Override
    protected Message getMessage() {
        return client.getIn().readMessage(client.getQuit());
    }

    private void printUserMessage(Message message) {
        System.out.println("\n>>> from: '" + message.getArguments()[0] + "' ||| content: '" + message.getText() + "' <<<");
    }
}
