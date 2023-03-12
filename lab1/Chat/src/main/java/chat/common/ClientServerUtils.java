package chat.common;

import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.net.Socket;

public class ClientServerUtils {
    public static void closeCloseable(@Nullable Closeable closeable, String closeableName, Logger logger) {
        if (closeable != null) {
            try {
                closeable.close();
                logger.info("Closed closeable '{}'", closeableName);
            } catch (IOException e) {
                logger.error("Failed to close closeable '{}', error message: '{}'", closeableName, e.getMessage());
            }
        } else {
            logger.warn("The passed closeable was null");
        }
    }

    public static void closeSocket(@Nullable Socket socket, Logger logger) {
        if (socket != null) {
            int localPort = socket.getLocalPort();
            int port = socket.getPort();
            String socketName = String.format("TCP socket (localPort: %d, port: %d)", localPort, port);
            if (socket.isClosed()) {
                logger.info("Attempted to close {} but it has already been closed", socketName);
            } else {
                closeCloseable(socket, socketName, logger);
            }
        } else {
            logger.warn("The passed socket was null");
        }
    }
}
