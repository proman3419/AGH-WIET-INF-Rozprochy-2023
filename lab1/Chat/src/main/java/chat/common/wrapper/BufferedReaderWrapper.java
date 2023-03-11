package chat.common.wrapper;

import chat.common.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

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

    // The suppressLogger variable is used to prevent logging when a chat instance shutdowns
    // In such case the socket has been closed to stop BufferedReader::readLine
    @Nullable
    public Message readMessage(AtomicBoolean suppressLogger) {
        Message message = null;
        try {
            message = new Message(bufferedReader.readLine());
            if (!suppressLogger.get()) {
                LOGGER.debug("Read message from {} to {} with content '{}'", senderPort, receiverPort, message);
            }
        } catch (IOException e) {
            if (!suppressLogger.get()) {
                LOGGER.error("Failed to read message from {} to {}", senderPort, receiverPort);
            }
        }
        return message;
    }

    @Nullable
    public Message readMessage() {
        return readMessage(new AtomicBoolean(false));
    }
}
