package chat.common.wrapper;

import chat.common.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;

public class PrintWriterWrapper {
    private static final Logger LOGGER = LogManager.getLogger(PrintWriterWrapper.class);
    private final PrintWriter printWriter;
    private final int senderPort;
    private final int receiverPort;


    public PrintWriterWrapper(PrintWriter printWriter, int senderPort, int receiverPort) {
        this.printWriter = printWriter;
        this.senderPort = senderPort;
        this.receiverPort = receiverPort;
    }

    public void sendMessage(Message message) {
        printWriter.println(message);
        LOGGER.debug("Sent message from {} to {} with content '{}'", senderPort, receiverPort, message);
    }
}
