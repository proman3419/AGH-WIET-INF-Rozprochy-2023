package chat.common.wrapper;

import chat.common.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;

public class BufferedReaderWrapper {
    private static final Logger LOGGER = LogManager.getLogger(BufferedReaderWrapper.class);
    private final BufferedReader bufferedReader;
    private final int senderPort;
    private final int receiverPort;

    public BufferedReaderWrapper(BufferedReader bufferedReader, int senderPort, int receiverPort) {
        this.bufferedReader = bufferedReader;
        this.senderPort = senderPort;
        this.receiverPort = receiverPort;
    }

    @Nullable
    public Message readMessage() {
        Message message = null;
        try {
            message = new Message(bufferedReader.readLine());
            LOGGER.debug("Read message from " + senderPort + " to " + receiverPort + " with content '" + message + "'");
        } catch (IOException e) {
            LOGGER.error("Failed to read message from " + senderPort + " to " + receiverPort);
        }
        return message;
    }
}
