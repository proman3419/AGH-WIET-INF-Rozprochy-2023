package chat.common.writeread.tcp;

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

    public BufferedReaderWrapper(BufferedReader bufferedReader, int senderPort) {
        this.senderPort = senderPort;
        this.bufferedReader = bufferedReader;
    }

    // The suppressLogger variable is used to prevent logging when a chat instance shutdowns
    // In such case the socket has been closed to stop BufferedReader::readLine
    @Nullable
    public Message readMessage(AtomicBoolean suppressLogger) {
        Message message = null;
        try {
            message = new Message(bufferedReader.readLine());
            if (!suppressLogger.get()) {
                LOGGER.debug("Read message from {} with content '{}'", senderPort, message);
            }
        } catch (IOException e) {
            if (!suppressLogger.get()) {
                LOGGER.error("Failed to read message from {}, error message: '{}'", senderPort, e.getMessage());
            }
        }
        return message;
    }

    @Nullable
    public Message readMessage() {
        return readMessage(new AtomicBoolean(false));
    }
}
