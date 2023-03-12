package chat.client.handler.reader;

import chat.client.Client;
import chat.common.message.Message;
import org.apache.logging.log4j.Logger;

public class ClientReadHandler {
    protected static final int LOG_QUIT_SLEEP_MS = 300;
    protected final Client client;
    protected final Logger logger;

    public ClientReadHandler(Client client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    protected void handleMessage(Message message) {
        if (message != null) {
            switch (message.getMessageType()) {
                case QUIT:
                    client.getQuit().set(true);
                    if (!client.getQuitSequenceExecuted().get()) {
                        try {
                            // To display it as the last message
                            Thread.sleep(LOG_QUIT_SLEEP_MS);
                        } catch (InterruptedException ignored) {
                        }
                        logger.info("Server disconnected, press ENTER to exit");
                    }
                    return;
                case TEXT_TCP_RESP:
                    printUserMessage(message);
                    break;
                case TEXT_UDP_RESP:
                    printAsciiArt(message);
                    break;
            }
        }
    }

    protected void printUserMessage(Message message) {
        System.out.println(">>> from: '" + message.getArguments()[0] + "' ||| content: '" + message.getText() + "' <<<");
    }

    protected void printAsciiArt(Message message) {
        System.out.println(">>> from: '" + message.getArguments()[0] + "\n" + message.getText());
    }
}
