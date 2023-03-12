package chat.common.writeread.tcp;

import chat.common.message.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.PrintWriter;

public class PrintWriterWrapper {
    private static final Logger LOGGER = LogManager.getLogger(PrintWriterWrapper.class);
    private final PrintWriter printWriter;
    private final int receiverPort;

    public PrintWriterWrapper(PrintWriter printWriter, int receiverPort) {
        this.printWriter = printWriter;
        this.receiverPort = receiverPort;
    }

    public void writeMessage(Message message) {
        printWriter.println(message);
        LOGGER.debug("Sent message to {} with content '{}'", receiverPort, message);
    }
}
