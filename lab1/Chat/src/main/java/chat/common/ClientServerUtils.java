package chat.common;

import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class ClientServerUtils {
    public static void closeSocket(Socket socket, Logger logger) {
        try {
            int localPort = socket.getLocalPort();
            int port = socket.getPort();
            if (socket.isClosed()) {
                logger.info("Attempted to close (localPort: {}, port: {}) but it has already been closed", localPort, port);
            } else {
                socket.close();
                logger.info("Closed socket (localPort: {}, port: {})", localPort, port);
            }
        } catch (IOException e) {
            logger.error("'{}' '{}'", e.getClass().getCanonicalName(), e.getMessage());
            logger.error("Failed to close the socket, error message: '{}'", e.getMessage());
        }
    }
}
