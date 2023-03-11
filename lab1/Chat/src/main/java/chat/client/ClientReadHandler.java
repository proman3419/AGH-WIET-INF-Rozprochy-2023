package chat.client;

import chat.common.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientReadHandler implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger(ClientReadHandler.class);
    private static final int LOG_QUIT_SLEEP_MS = 300;
    private final Client client;

    public ClientReadHandler(Client client) {
        this.client = client;
    }

    @Override
    public void run() {
        while (!client.getQuit().get()) {
            Message message = client.getIn().readMessage(client.getQuit());
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
                            LOGGER.info("Server disconnected, press ENTER to exit");
                        }
                        return;
                    case TEXT_RESP:
                        printUserMessage(message);
                        break;
                }
            }
        }
    }

    private void printUserMessage(Message message) {
        System.out.println(">>> from: '" + message.getArguments()[0] + "' ||| content: '" + message.getText() + "' <<<");
    }
}
